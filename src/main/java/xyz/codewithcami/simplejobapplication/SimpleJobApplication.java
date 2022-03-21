package xyz.codewithcami.simplejobapplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.codewithcami.simplejobapplication.models.ApplicationUser;
import xyz.codewithcami.simplejobapplication.services.UserService;

@SpringBootApplication
public class SimpleJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleJobApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000","https://guarded-ocean-90978.herokuapp.com")
                        .allowedMethods("PUT", "DELETE", "GET", "POST")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        // return args -> userService.saveUser(new ApplicationUser(null, "admin", "admin@job.com", "password"));
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

