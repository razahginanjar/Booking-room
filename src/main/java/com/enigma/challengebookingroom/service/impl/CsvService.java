package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.dto.response.ReportResponse;
import com.enigma.challengebookingroom.entity.Equipment;
import com.enigma.challengebookingroom.entity.Room;
import com.enigma.challengebookingroom.repository.RoomRepository;
import com.enigma.challengebookingroom.service.ReservationService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvService {
    private final ReservationService reservationService;

    public void Download(HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        String filename = "users.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<ReportResponse> writer = new StatefulBeanToCsvBuilder<ReportResponse>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        List<ReportResponse> list = reservationService.getAll().stream().map(
                reservation -> ReportResponse.builder()
                        .reservationStatus(reservation.getReservationStatus().toString())
                        .reserveDate(reservation.getReserveDate())
                        .startTime(reservation.getStartTime())
                        .equipments(reservation.getEquipments().stream().map(
                                Equipment::getEquipmentName
                        ).toList())
                        .endTime(reservation.getEndTime())
                        .reservationDescription(reservation.getReservationDescription())
                        .roomType(reservation.getRoom().getRoomType())
                        .employeeName(reservation.getEmployee().getEmployeeName())
                        .build()
        ).toList();
        //write all users to csv file
        writer.write(list);
    }
}