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

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationRequest implements Serializable {
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    String reservationId;
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "Format tanggal harus 'yyyy-MM-dd'")
    LocalDateTime reserveDate;
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startTime;
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endTime;
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    String reservationDescription;
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    String actionReason;
}