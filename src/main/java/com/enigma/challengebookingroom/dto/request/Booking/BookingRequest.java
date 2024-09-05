package com.enigma.challengebookingroom.dto.request.Booking;

import com.enigma.challengebookingroom.dto.request.Equipment.EquipmentRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {
    @NotBlank
    @NotEmpty
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "Format tanggal harus 'yyyy-MM-dd'")
    @NotNull(message = "cannot be null")
    private String requestDate;
    @NotBlank
    @NotEmpty
    private String division;
    @NotBlank
    @NotEmpty
    private String employeeName;
    @NotBlank
    @NotEmpty
    private String typeRoom;
    @NotBlank
    @NotEmpty
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "Format tanggal harus 'yyyy-MM-dd'")
    @NotNull(message = "cannot be null")
    private String startDate;
    @NotBlank
    @NotEmpty
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "Format tanggal harus 'yyyy-MM-dd'")
    @NotNull(message = "cannot be null")
    private String endDate;
    @NotBlank
    @NotEmpty
    private String description;

    private List<EquipmentRequest> equipmentRequests;
}
