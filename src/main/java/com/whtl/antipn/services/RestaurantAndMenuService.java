package com.whtl.antipn.services;

import com.whtl.antipn.dto.MenuDto;
import com.whtl.antipn.dto.RestaurantScoreDto;
import com.whtl.antipn.dto.RestaurantDto;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantAndMenuService {
    //restaurant
    public List<RestaurantDto> findAllRestaurants();

    public RestaurantDto findRestaurantById(int restId);

    public RestaurantDto createRestaurant(RestaurantDto restaurantDto);

    public void deleteRestaurant(int restId);

    public List<RestaurantScoreDto> findRestaurantsScores();

    public List<RestaurantScoreDto> findRestaurantsScoresOnDate(LocalDate localDate);

    //menu
    public List<MenuDto> findMenu(int restId);

    public MenuDto findMenuOnDate(int restId, LocalDate localDate);

    public List<MenuDto> saveMenu(List<MenuDto> menuInput, int restId);

    public void deleteMenu(int id);
}
