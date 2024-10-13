package com.aice.dao;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_EMPTY)
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DaoJson {
    public String toJson(){
        StringBuilder sb = new StringBuilder();
        try{
            // ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            sb.append(mapper.writeValueAsString(this));
        }catch(Exception e){
            log.error("json parse error",e);
        }
        return sb.toString();
    }

    public String toJson(Object obj){
        StringBuilder sb = new StringBuilder();
        try{
            // ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            ObjectMapper mapper = new ObjectMapper();
            sb.append(mapper.writeValueAsString(obj));
        }catch(Exception e){
            log.error("json parse error",e);
        }
        return sb.toString();
    }

    public String toJsonTrim() {
        StringBuilder sb = new StringBuilder();
        try {
            ObjectMapper mapper = new ObjectMapper();
            sb.append(mapper.writeValueAsString(this));
        }catch(Exception e) {
            log.error("json parse error",e);
        }
        return sb.toString();
    }
}
