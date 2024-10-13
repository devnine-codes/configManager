package com.aice.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum EmailUseType {
    SMTP("B1009","smtp")
    ,POP3("B1016","pop3")
    ,IMAP("B1017","imap")
    ;

    @JsonValue
    @Getter
    private final String value;
    @Getter
    private final String desc;

    public static EmailUseType findByValue(String code) {
        return Arrays.stream(EmailUseType.values())
                .filter(enumObj -> enumObj.getValue().equals(code) == true)
                .findFirst()
                .orElse(SMTP);
    }
}
