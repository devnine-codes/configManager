package com.aice.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumEntityType {
    COMPANIES("companies", "회사"),
    MEMBERS("members", "일반직원"),
    AIS("ais", "AI직원");

    private final String value;
    private final String desc;

    public static EnumEntityType findByValue(String code) {
        return Arrays.stream(EnumEntityType.values())
                .filter(enumObj -> enumObj.getValue().equals(code) == true)
                .findFirst()
                .orElse(COMPANIES);
    }
}
