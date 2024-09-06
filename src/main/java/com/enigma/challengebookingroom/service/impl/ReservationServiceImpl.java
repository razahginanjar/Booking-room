package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.request.ReservationRequest;
import com.enigma.challengebookingroom.dto.request.UpdateReservationByUser;
import com.enigma.challengebookingroom.dto.request.UpdateReservationRequestByAdmin;
import com.enigma.challengebookingroom.dto.response.ReservationResponse;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.entity.Reservation;
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

import java.time.LocalDate;
import java.util.List;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ReservationResponse create(ReservationRequest request) {
        validation.validate(request);
        Employee employee = employeeService.getById(request.getEmployeeId());
        Reservation reservation = Reservation.builder()
                .employee(employee)
                .room(roomService.getById(request.getRoomId()))
                .reserveDate(LocalDate.now())
                .startTime(converter.convertToLocalDate(request.getStartTime()))
                .endTime(converter.convertToLocalDate(request.getEndTime()))
                .reservationStatus(ConstantReservationStatus.PENDING)
                .reservationDescriptionByUser(request.getReservationDescription())
                .build();
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
    @Override
    public List<ReservationResponse> getAllByStatus(ConstantReservationStatus status) {
        List<Reservation> reservations;
        if (status != null) {
            return reservationRepository.findAllByReservationStatus(status).stream()
                    .map(reservationMapper::toResponse).toList();
        }
        return reservationRepository.findAll().stream().map(reservationMapper::toResponse).toList();
    }

    @Override
    public ReservationResponse getById(String id) {
        Reservation reservation = getReservationById(id);
        return reservationMapper.toResponse(reservation);
    }

    @Override
    public Reservation getReservationById(String id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    @Override
    public ReservationResponse update(UpdateReservationByUser request) {
        Reservation reservation = getReservationById(request.getIdReservation());
        validation.validate(request);
        reservation.setStartTime(converter.convertToLocalDate(request.getStartTime()));
        reservation.setEndTime(converter.convertToLocalDate(request.getEndTime()));
        reservation.setReservationDescriptionByUser(request.getReservationDescription());
        reservation.setRoom(roomService.getById(request.getRoomId()));
        Reservation saved = reservationRepository.saveAndFlush(reservation);
        return reservationMapper.toResponse(saved);
    }

    @Override
    public ReservationResponse updateByAdmin(UpdateReservationRequestByAdmin request) {
        Reservation reservation = getReservationById(request.getIdReservation());
        validation.validate(request);
        reservation.setReservationStatus(request.getReservationStatus());
        reservation.setReservationDescriptionByGA(request.getActionReason());
        Reservation saved = reservationRepository.saveAndFlush(reservation);
        return reservationMapper.toResponse(saved);
    }


}
