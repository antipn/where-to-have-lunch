package com.whtl.antipn;

import com.whtl.antipn.repositories.RestaurantRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringMain.class, args);
        RestaurantRepository restaurantRepository = context.getBean(RestaurantRepository.class);
        System.out.println(restaurantRepository.findAll());
        //InMemoryRepository inMemoryRepository = context.getBean(InMemoryRepository.class);
        //inMemoryRepository.showMaps();
    }
}
