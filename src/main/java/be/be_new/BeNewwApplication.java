package be.be_new;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(title = "Test Management API", version = "v1"),
		servers = @Server(url = "http://localhost:8080/api/v1")
)
@SpringBootApplication
public class BeNewwApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeNewwApplication.class, args);
	}

}
