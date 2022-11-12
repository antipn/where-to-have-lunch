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

    public RestaurantDto createRestaurant(RestaurantDto restaurantDto, int restId);

    public void deleteRestaurant(int restId);

    public List<RestaurantScoreDto> findRestaurantsScores();

    public List<RestaurantScoreDto> findRestaurantsScoresOnDate(LocalDate localDate);

    //menu
    public List<MenuDto> findMenu(int restId);

    public List<MenuDto> findMenuOnDate(LocalDate localDate, int restId);

    public List<MenuDto> saveMenu(LocalDate localDate, int restId, List<MenuDto> menuInput);

    public void deleteMenu(LocalDate localDate, int restId);

    public List<MenuDto> updateMenu(LocalDate localDate, int restId, List<MenuDto> menuDtoList);
}
