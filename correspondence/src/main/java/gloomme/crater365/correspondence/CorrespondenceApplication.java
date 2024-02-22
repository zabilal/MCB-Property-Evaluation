package gloomme.crater365.correspondence;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients
@OpenAPIDefinition(info =
@Info(title = "CORRESPONDENCE API", version = "1.0.0", description = "Documentation for Correspondence API v1.0.0")
)
public class CorrespondenceApplication {
    public static void main(String[] args) {
            SpringApplication.run(CorrespondenceApplication.class, args);
    }
}