package com.spring.spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    //encriptamos nuestro password

//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    //despues de que el proyecto sea ejecutado
//    @Bean
//    public CommandLineRunner createPasswordsCommand(){
//        return args -> {
//            //para codear o generar las contraseñas
//            System.out.println(passwordEncoder.encode("clave123"));
//            System.out.println(passwordEncoder.encode("clave456"));
//        };
//    }

}
