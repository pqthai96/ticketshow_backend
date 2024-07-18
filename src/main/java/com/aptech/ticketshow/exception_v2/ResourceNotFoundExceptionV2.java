package com.aptech.ticketshow.exception_v2;

import java.util.Map;

public class ResourceNotFoundExceptionV2 extends ExceptionCustomV2 {

    public ResourceNotFoundExceptionV2(Map<String, Object> errors) {
        super("DATA NOT FOUND!", errors);
    }
}
