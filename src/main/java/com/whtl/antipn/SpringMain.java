package com.whtl.antipn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.whtl.antipn.repositories.InMemoryRepository;

@SpringBootApplication
public class SpringMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringMain.class, args);
        InMemoryRepository inMemoryRepository = context.getBean(InMemoryRepository.class);

        inMemoryRepository.showMaps();
    }
}
