package com.certimetergroup.formazione.security.enumeration;

public enum TokenValidationResultEnum {

    VALID(0, "VALID"),

    EXPIRED(1, "EXPIRED"),
    INVALID_SIGNATURE(2, "INVALID_SIGNATURE"),
    MALFORMED(3, "MALFORMED"),

    ERROR_DURING_VALIDATION(500, "ERROR_DURING_VALIDATION");


    public int code;
    public String value;

    TokenValidationResultEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
