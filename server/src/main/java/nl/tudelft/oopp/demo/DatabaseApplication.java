package nl.tudelft.oopp.demo;

import java.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring boot application that is executed whenever the server is launched.
 * Includes the base packages, as they are not recognised otherwise.
 */
@SpringBootApplication(scanBasePackages = {
        "nl.tudelft.oopp.demo.repositories",
        "nl.tudelft.oopp.demo.controllers",
        "nl.tudelft.oopp.demo.services",
        "nl.tudelft.oopp.demo.security",
        "nl.tudelft.oopp.demo.events",
        "nl.tudelft.oopp.demo.specifications"
})

public class DatabaseApplication {

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * The bean responsible for making an object, which sends emails.
     * @return MailSender
     */
    @Bean(name = "mailSender")
    public MailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setProtocol("smtp");
        javaMailSender.setUsername("confirmationemail121@gmail.com");
        javaMailSender.setPassword("998877@r");
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.debug", "true");
        javaMailSender.setJavaMailProperties(mailProperties);
        return javaMailSender;
    }

    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class, args);
    }

}
