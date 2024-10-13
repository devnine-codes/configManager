package com.aice.conf;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info=@Info(
                title="CONFIG-MANAGER API"
                ,version="0.0.1"
                ,description="ploonet config-manager api"
        )
        ,servers={
        @Server(
                description="local"
                ,url="http://localhost:8151/aice/configManager"
        )
        ,@Server(
        description="dev"
        ,url="http://10.0.131.55:8151/aice/configManager"
)
}
)
@Configuration
public class ConfSwagger {
    @Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("config-manager")
                .pathsToMatch("/**")
                .build();
    }
}