package services;

import dto.MenuDto;
import dto.RestairantScoreDto;
import dto.RestaurantDto;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantAndMenuService {
    //restaurant
    public List<RestaurantDto> findAllRestaurants();

    public RestaurantDto findRestaurantById(int restId);

    public RestaurantDto saveRestaurant(RestaurantDto restaurantDto);

    public RestaurantDto deleteRestaurant(int restId);

    public RestairantScoreDto findRestaurantsScores();

    public RestairantScoreDto findRestaurantsScoresOnDate(LocalDate localDate);

    //menu
    public MenuDto findMenu(int restId);

    public MenuDto findMenuOnDate(int restId, LocalDate localDate);

    public MenuDto saveMenu(int id);

    public MenuDto deleteMenu(int id);
}
