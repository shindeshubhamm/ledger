package com.zigartiq.ledger.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "Ledger API Documentation", version = "1.0"), security = @SecurityRequirement(name = "Bearer Token"))
@Configuration
public class OpenAPIConfig {

}