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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonResponse<?> that = (CommonResponse<?>) o;
        return statusCode == that.statusCode &&
                Objects.equals(message, that.message) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, message, data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonResponse<?> that = (CommonResponse<?>) o;
        return statusCode == that.statusCode &&
                Objects.equals(message, that.message) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, message, data);
    }
// kalo mau nambahin paging ntar tambahin sendiri kesini juga hehe