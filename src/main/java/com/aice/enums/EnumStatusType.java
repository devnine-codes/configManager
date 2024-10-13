package com.aice.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum EnumStatusType {
    Checking("CHECKING","확인중")
    ,Ready("READY","준비")
    ,Registed("REGISTED","승인")
    ,RegistFailed("REGIST FAILED","승인실패")
    ,UnderReview("UNDER REVIEW","승인대기")
    ,Ungegisted("UNREGISTED","승인해지")
    ;
    @JsonValue
    @Getter
    private final String value;
    @Getter
    private final String desc;
    public static EnumStatusType findByValue(String code) {
        return Arrays.stream(EnumStatusType.values())
                .filter(enumObj -> enumObj.getValue().equals(code) == true)
                .findFirst()
                .orElse(Ready);
    }
}
