package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.constant.ConstantMessage;
import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.request.*;
import com.enigma.challengebookingroom.dto.response.GetReservationStatusResponse;
import com.enigma.challengebookingroom.dto.response.ReservationResponse;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.entity.Equipment;
import com.enigma.challengebookingroom.entity.Reservation;
import com.enigma.challengebookingroom.entity.User;
import com.enigma.challengebookingroom.mapper.ReservationMapper;
import com.enigma.challengebookingroom.repository.ReservationRepository;
import com.enigma.challengebookingroom.service.ReservationService;
import com.enigma.challengebookingroom.service.RoomService;
import com.enigma.challengebookingroom.util.ConverterUtils;
import com.enigma.challengebookingroom.util.ValidationUtils;
import com.mailjet.client.errors.MailjetException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    private final RoomService roomService;
    private final ConverterUtils converter;
    private final ValidationUtils validation;
    private final MailSenderService mailSenderService;
    private final ReservationMapper reservationMapper;
    private final UserServiceImpl userServiceImpl;
    private final EquipmentServiceImpl equipmentServiceImpl;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ReservationResponse create(ReservationRequest request) throws MailjetException {
        validation.validate(request);
        Employee employee = userServiceImpl.getByContext().getEmployee();

        LocalDate localDate = converter.convertToLocalDate(request.getStartTime());
        LocalDate localDate1 = converter.convertToLocalDate(request.getEndTime());
        if(localDate.isBefore(LocalDate.now()) || localDate1.isBefore(LocalDate.now()))
        {
            throw new DateTimeException(ConstantMessage.ERROR_DATE);
        }

        // cek ruangan avail atau ga
        List<Reservation> conflictingReservations = reservationRepository.findAvailRoom(
                request.getRoomId(),
                converter.convertToLocalDate(request.getStartTime()),
                converter.convertToLocalDate(request.getEndTime())
        );

        if (!conflictingReservations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room is not available");
        }


        Reservation reservation = Reservation.builder()
                .employee(employee)
                .room(roomService.getById(request.getRoomId()))
                .reserveDate(LocalDate.now())
                .startTime(converter.convertToLocalDate(request.getStartTime()))
                .endTime(converter.convertToLocalDate(request.getEndTime()))
                .reservationStatus(ConstantReservationStatus.PENDING)
                .reservationDescription(request.getReservationDescription())
                .build();
        if(Objects.nonNull(request.getEquipmentRequests()))
        {
            List<Equipment> list = request.getEquipmentRequests().stream().map(
                    equipmentServiceImpl::create
            ).toList();
            reservation.setEquipments(list);
        }
        Reservation saved = reservationRepository.saveAndFlush(reservation);

        MailSenderRequest mailSenderRequest = MailSenderRequest.builder()
                .startDate(request.getStartTime())
                .endDate(request.getEndTime())
                .idReservation(saved.getReservationId())
                .roomType(saved.getRoom().getRoomType())
                .employeeName(employee.getEmployeeName())
                .build();

        if(Objects.nonNull(saved.getEquipments()))
        {
            mailSenderRequest.setEquipment(saved.getEquipments().stream().map(
                    Equipment::getEquipmentName
            ).toList());
        }
        mailSenderService.create(mailSenderRequest);
        return reservationMapper.toResponse(saved);
    }

//    @Override
//    public List<ReservationResponse> getAll() {
//        return reservationRepository.findAll().stream()
//                .map(reservationMapper::toResponse)
//                .toList();
//    }

    // aku bikin ini juga nanti tinggal make yg mana monggo
    @Transactional(readOnly = true)
    @Override
    public List<ReservationResponse> getAllByStatus(ConstantReservationStatus status) {

        if (status != null) {
            return reservationRepository.findAllByReservationStatus(status).stream()
                    .map(reservationMapper::toResponse).toList();
        }
        return reservationRepository.findAll().stream().map(reservationMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ReservationResponse getById(String id) {
        Reservation reservation = getReservationById(id);
        return reservationMapper.toResponse(reservation);
    }

    @Transactional(readOnly = true)
    @Override
    public Reservation getReservationById(String id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ReservationResponse update(UpdateReservationByAdmin request) {
        validation.validate(request);
        Reservation reservation = getReservationById(request.getIdReservation());

        LocalDate localDate = converter.convertToLocalDate(request.getStartTime());
        LocalDate localDate1 = converter.convertToLocalDate(request.getEndTime());
        if(localDate.isBefore(LocalDate.now()) || localDate1.isBefore(LocalDate.now()))
        {
            throw new DateTimeException(ConstantMessage.ERROR_DATE);
        }

        reservation.setStartTime(converter.convertToLocalDate(request.getStartTime()));
        reservation.setEndTime(converter.convertToLocalDate(request.getEndTime()));
        reservation.setRoom(roomService.getById(request.getRoomId()));

        //reservation.setReservationStatus(ConstantReservationStatus.CANCELLED);
        Reservation saved = reservationRepository.saveAndFlush(reservation);

        return reservationMapper.toResponse(saved);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(String id, ConstantReservationStatus status) {
        Reservation reservation = getReservationById(id);
        reservation.setReservationStatus(status);
        reservationRepository.updateReservationStatus(id, status);
        reservationMapper.toResponse(reservation);
    }


    @Transactional(readOnly = true)
    @Override
    public List<GetReservationStatusResponse> getStatusReservations(InsertDateRequest request) {
        validation.validate(request);
        LocalDate date = converter.convertToLocalDate(request.getDate());
        List<Reservation> statusReservation = reservationRepository.findStatusReservation(date);
        return statusReservation.stream().map(
                reservationMapper::getResponseStatus
        ).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationResponse> historyOfCustomer() {
        User user = userServiceImpl.getByContext();
        List<Reservation> list = reservationRepository.findAllByEmployee(user.getEmployee());
        return list.stream().map(reservationMapper::toResponse).toList();
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }
}
