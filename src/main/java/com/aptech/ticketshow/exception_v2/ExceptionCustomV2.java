package com.aptech.ticketshow.exception_v2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ExceptionCustomV2 extends RuntimeException {

    private final Object errors;

    public ExceptionCustomV2(String msg, Object errors) {
        super(msg);
        this.errors = errors;
    }
}
