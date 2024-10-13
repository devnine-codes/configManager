package com.aice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiRoleData {
    private Long aiStaffSeq;
    private Long companySeq;

    @JsonIgnore
    private String role;
}
