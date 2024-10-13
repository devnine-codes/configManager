package com.aice.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum EnumUserType {
    COMPANY("B2001","회사법인")
    ,PERSONAL("B2002","개인")
    ;
    @JsonValue
    @Getter
    private final String value;
    @Getter
    private final String desc;

    public static EnumUserType findByValue(String code) {
        return Arrays.stream(EnumUserType.values())
                .filter(enumObj -> enumObj.getValue().equals(code) == true)
                .findFirst()
                .orElse(COMPANY);
    }
}
