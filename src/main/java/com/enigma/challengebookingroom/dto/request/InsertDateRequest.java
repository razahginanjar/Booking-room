package com.enigma.challengebookingroom.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsertDateRequest {
    @NotNull(message = "Please fill the date")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid format date (format : yyyy-mm-dd)")
    private String date;
}
