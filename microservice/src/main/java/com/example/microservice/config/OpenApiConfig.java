package com.example.microservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
      info = @Info(
            contact = @Contact(
                  name = "Zakhar Zakharchuk",
                  email = "Zakhar_Zakharchuk@epam.com"
            ),
            description = "OpenApi documentation for Gym CRM Project",
            title = "OpenApi specification for Gym-CRM report service"
      )
)
public class OpenApiConfig {

}

