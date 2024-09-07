package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.constant.ConstantMessage;
import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.request.ReservationRequest;
import com.enigma.challengebookingroom.dto.request.UpdateReservationByUser;
import com.enigma.challengebookingroom.dto.request.UpdateReservationRequestByAdmin;
import com.enigma.challengebookingroom.dto.response.ReservationResponse;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.entity.Equipment;
import com.enigma.challengebookingroom.entity.Reservation;
import com.enigma.challengebookingroom.entity.User;
import com.enigma.challengebookingroom.mapper.ReservationMapper;
import com.enigma.challengebookingroom.repository.ReservationRepository;
import com.enigma.challengebookingroom.service.EmployeeService;
import com.enigma.challengebookingroom.service.ReservationService;
import com.enigma.challengebookingroom.service.RoomService;
import com.enigma.challengebookingroom.util.ConverterUtils;
import com.enigma.challengebookingroom.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    private final RoomService roomService;
    private final EmployeeService employeeService;
    private final ConverterUtils converter;
    private final ValidationUtils validation;

    private final ReservationMapper reservationMapper;
    private final UserServiceImpl userServiceImpl;
    private final EquipmentServiceImpl equipmentServiceImpl;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ReservationResponse create(ReservationRequest request) {
        validation.validate(request);
        //Employee employee = employeeService.getById(request.getEmployeeId());
        // menurutku ini mendingan pake bycontext aja
        //jadinya di request gak butuh employee id
        Employee employee = userServiceImpl.getByContext().getEmployee();

        LocalDate localDate = converter.convertToLocalDate(request.getStartTime());
        LocalDate localDate1 = converter.convertToLocalDate(request.getEndTime());
        if(localDate.isBefore(LocalDate.now()) || localDate1.isBefore(LocalDate.now()))
        {
            throw new DateTimeException(ConstantMessage.ERROR_DATE);
        }

        //need checker from booking :))) buat ruangah ditanggal sdari request


        Reservation reservation = Reservation.builder()
                .employee(employee)
                .room(roomService.getById(request.getRoomId()))
                .reserveDate(LocalDate.now())
                .startTime(converter.convertToLocalDate(request.getStartTime()))
                .endTime(converter.convertToLocalDate(request.getEndTime()))
                .reservationStatus(ConstantReservationStatus.PENDING)
                .reservationDescriptionByUser(request.getReservationDescription())
                .build();
        if(Objects.nonNull(request.getEquipmentRequests()))
        {
            List<Equipment> list = request.getEquipmentRequests().stream().map(
                    equipmentServiceImpl::create
            ).toList();
            reservation.setEquipments(list);
        }
        Reservation saved = reservationRepository.saveAndFlush(reservation);
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
//        List<Reservation> reservations;
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
    public ReservationResponse update(UpdateReservationByUser request) {
        validation.validate(request);
        Reservation reservation = getReservationById(request.getIdReservation());
        Employee employee = userServiceImpl.getByContext().getEmployee();

        if(!reservation.getEmployee().getEmployeeId().equals(employee.getEmployeeId()))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        LocalDate localDate = converter.convertToLocalDate(request.getStartTime());
        LocalDate localDate1 = converter.convertToLocalDate(request.getEndTime());
        if(localDate.isBefore(LocalDate.now()) || localDate1.isBefore(LocalDate.now()))
        {
            throw new DateTimeException(ConstantMessage.ERROR_DATE);
        }

        reservation.setStartTime(converter.convertToLocalDate(request.getStartTime()));
        reservation.setEndTime(converter.convertToLocalDate(request.getEndTime()));
        reservation.setReservationDescriptionByUser(request.getReservationDescription());
        reservation.setRoom(roomService.getById(request.getRoomId()));

        reservation.setReservationStatus(ConstantReservationStatus.CANCELLED); // karena satu"nya update4 yang bisa dari user adalah cancel

        Reservation saved = reservationRepository.saveAndFlush(reservation);

        ///logic removed booking dia disini


        if(Objects.nonNull(saved.getEquipments()))
        {
            ///logic booking untuk kembalian equipment pas di hari yang dipinjam ada atau tidak untuk pengembalian equipment
        }
        return reservationMapper.toResponse(saved);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ReservationResponse updateByAdmin(UpdateReservationRequestByAdmin request) {
        Reservation reservation = getReservationById(request.getIdReservation());
        validation.validate(request);
        reservation.setReservationStatus(request.getReservationStatus());
        reservation.setReservationDescriptionByGA(request.getActionReason());
        Reservation saved = reservationRepository.saveAndFlush(reservation);
        if(saved.getReservationStatus().equals(ConstantReservationStatus.APPROVED))
        {
            //Logic booking disini untuk ketika approved untuk atur rooom

            //logic dari booking untuk atur equipment
        }
        return reservationMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Reservation> historyOfCustomer() {
        User user = userServiceImpl.getByContext();
        return reservationRepository.findAllByEmployee(user.getEmployee());
    }

}
