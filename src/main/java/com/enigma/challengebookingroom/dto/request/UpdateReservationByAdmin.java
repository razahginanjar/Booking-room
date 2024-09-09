package com.enigma.challengebookingroom.dto.request;

import com.enigma.challengebookingroom.constant.ConstantMessage;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateReservationByAdmin {
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
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    private String idReservation;

    // ini aku pikir2 juga ga perlu karena kita ga bakal ngerubah siapa yg pesen kan?
    // kalo ga jadi ya tinggal nanti si GA ganti statusnya ke batal (masuknya update by admin)
//    @NotBlank(message = ConstantMessage.NOT_BLANK)
//    private String employeeId;

    // ini aku bingung biar dia bisa ambil banyak sih kalo di updatenya, jadi aku komen dulu
//    private String equipmentRequestId;

}
