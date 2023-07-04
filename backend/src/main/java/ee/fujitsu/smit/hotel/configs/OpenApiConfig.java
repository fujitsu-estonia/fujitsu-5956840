package ee.fujitsu.smit.hotel.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "HotelApplication REST API"))
public class OpenApiConfig {
  // configuration for openAPI
}
