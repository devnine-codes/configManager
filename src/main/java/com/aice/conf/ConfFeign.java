package com.aice.conf;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.JsonFormWriter;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.aice.filter.FilterFeignLogger;

import feign.Logger;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@Configuration
public class ConfFeign {
//    @Autowired
//    private ObjectFactory<HttpMessageConverters> messageConverters;

//    @Bean
//    Encoder feignFormEncoder() {
//        return new FormEncoder(new SpringEncoder(this.messageConverters));
//    }

    @Bean
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(new ObjectFactory<HttpMessageConverters>() {
            @Override
            public HttpMessageConverters getObject() throws BeansException {
                return new HttpMessageConverters(new RestTemplate().getMessageConverters());
            }
        }));
    }

    @Bean
    public JsonFormWriter jsonFormWriter() {
       return new JsonFormWriter();
    }

    @Bean
    Logger logger() {
        return new FilterFeignLogger();
    }
}
