package com.enigma.challengebookingroom.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse<T> {
    private int statusCode;
    private String message;
    private T data;
}

// kalo mau nambahin paging ntar tambahin sendiri kesini juga hehe