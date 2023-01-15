package com.whtl.antipn.services;

import com.whtl.antipn.dto.IncomeMenuDto;
import com.whtl.antipn.dto.MenuDto;
import com.whtl.antipn.dto.RestaurantScoreDto;
import com.whtl.antipn.dto.RestaurantDto;
import com.whtl.antipn.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RestaurantAndMenuService {
    //restaurant
    public List<RestaurantDto> findAllRestaurants();

    public RestaurantDto findRestaurantById(int restId);

    public RestaurantDto updateRestaurant(RestaurantDto restaurantDto);

    public RestaurantDto createRestaurant(RestaurantDto restaurantDto);

    public void deleteRestaurant(int restId);


    public Restaurant findRestaurantByName(String restName);

    //menu

    public List<MenuDto> findAllMenuOnDate(LocalDate date);

    public List<MenuDto> findMenuOnDate(int restId, LocalDate date);

    public List<MenuDto> saveMenu(int restId, LocalDate localDate, List<IncomeMenuDto> incomeMenuDtoList);

    public void deleteMenu(int restId, LocalDate localDate);

    public List<MenuDto> updateMenu(int restId, LocalDate localDate, List<IncomeMenuDto> incomeMenuDtoList);
}
