package com.whtl.antipn.repositories;

import com.whtl.antipn.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Restaurant findRestaurantByName(String name);
}
