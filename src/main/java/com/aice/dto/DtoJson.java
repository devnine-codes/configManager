package com.aice.dto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_EMPTY)
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DtoJson {
    private final Logger log = LoggerFactory.getLogger(getClass());
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
}
