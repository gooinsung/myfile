package com.shop.myfile.exception;

import lombok.Builder;

@Builder
public record ExceptionResponse(
        String code,
        String message,
        String detail
) {
}
