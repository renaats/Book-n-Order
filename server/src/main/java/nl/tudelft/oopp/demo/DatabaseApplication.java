package nl.tudelft.oopp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = {
        "nl.tudelft.oopp.demo.repositories",
        "nl.tudelft.oopp.demo.controllers",
        "nl.tudelft.oopp.demo.services",
        "nl.tudelft.oopp.demo.security"
})
public class DatabaseApplication {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class, args);
    }

}
