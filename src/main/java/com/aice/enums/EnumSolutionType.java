package com.aice.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum EnumSolutionType {
    UnDefined("B10","솔루션타입")
    ,WorkCenter("B11","워크센터")
    ,MetaHuman("B12","메타휴먼")
    ,Studio("B13","스튜디오")
    ;
    @JsonValue
    @Getter
    private final String value;
    @Getter
    private final String desc;
    public static EnumSolutionType findByValue(String code) {
        return Arrays.stream(EnumSolutionType.values())
                .filter(enumObj -> enumObj.getValue().equals(code) == true)
                .findFirst()
                .orElse(WorkCenter);
    }
}
