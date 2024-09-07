package com.enigma.challengebookingroom.dto.request;

import com.enigma.challengebookingroom.constant.ConstantMessage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ReservationRequest implements Serializable {
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "Format tanggal harus 'yyyy-MM-dd'")
    private String startTime;
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "Format tanggal harus 'yyyy-MM-dd'")
    private String endTime;
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    private String reservationDescription;
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    private String roomId; // diganti id karena kita relasinya ke id room. kalo mau nampilinnya ntar bisa disetting di servicenya

    // tambahan buat ngecek dia employee yg mana
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    private String employeeId;

    // ini aku ganti jadi id karena dia tinggal ngambil data dr tablenya, gausah nambahin lagi dr request
    private List<EquipmentRequest> equipmentRequests;
    //private String equipmentRequestId;
}