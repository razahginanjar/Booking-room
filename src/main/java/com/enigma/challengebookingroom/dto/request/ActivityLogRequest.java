package com.enigma.challengebookingroom.dto.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.enigma.challengebookingroom.constant.ConstantMessage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityLogRequest implements Serializable {
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    String activityLogId;
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    LocalDateTime activityLogTimestamp;
    @NotBlank(message = ConstantMessage.NOT_BLANK)
    @NotEmpty(message = ConstantMessage.NOT_EMPTY)
    @NotNull(message = ConstantMessage.NOT_NULL)
    String activityLogDescription;
}