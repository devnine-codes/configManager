package com.aice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AiRoleResponse {
    @JsonProperty("HANDS")
    private List<AiRoleData> HANDS = new ArrayList<>();
    @JsonProperty("KIOSK")
    private List<AiRoleData> KIOSK = new ArrayList<>();
    @JsonProperty("MANAGE")
    private List<AiRoleData> MANAGE = new ArrayList<>();
    @JsonProperty("RESERV")
    private List<AiRoleData> RESERV = new ArrayList<>();
    @JsonProperty("RECEPT")
    private List<AiRoleData> RECEPT = new ArrayList<>();

    public void addAiRoleData(String role, AiRoleData data) {
        String processedRole = role.replace("CTGR1_", "");
        switch (processedRole) {
            case "HANDS":
                if (HANDS == null) HANDS = new ArrayList<>();
                HANDS.add(data);
                break;
            case "KIOSK":
                if (KIOSK == null) KIOSK = new ArrayList<>();
                KIOSK.add(data);
                break;
            case "MANAGE":
                if (MANAGE == null) MANAGE = new ArrayList<>();
                MANAGE.add(data);
                break;
            case "RECEPT":
                if (RECEPT == null) RECEPT = new ArrayList<>();
                RECEPT.add(data);
                break;
            case "RESERV":
                if (RESERV == null) RESERV = new ArrayList<>();
                RESERV.add(data);
                break;
            default:
                break;
        }
    }
}
