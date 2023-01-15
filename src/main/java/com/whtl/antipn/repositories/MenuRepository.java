package com.whtl.antipn.repositories;

import com.whtl.antipn.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findMenuByRestaurantAndDate(int restId, LocalDate date);

    List<Menu> findAllByDate(LocalDate date);

    void deleteMenusByRestaurantAndDate(int restId, LocalDate date);


}
