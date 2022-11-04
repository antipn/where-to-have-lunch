package controllers;

import dto.EntityNotFoundResponse;
import dto.MenuDto;
import dto.RestaurantDto;
import exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class RestaurantController {

    //restaurants

    //show all restaurants
    @GetMapping("/app/v1/restaurants")
    public Collection<RestaurantDto> getAllrestaurants() {
        List<RestaurantDto> result = new ArrayList<>();
        System.out.println("showing all restaurants");
        return result;
    }

    //showing one restaurant by id
    @GetMapping("api/v1/restaurants/{rest_id}")
    public RestaurantDto getOneRestaurant(@PathVariable(name = "rest_id") int restId) {
        System.out.println("showing one restaurants with id = " + restId);
        return new RestaurantDto();
    }

    //input new restaurant input json
    @PostMapping("/api/v1/restaurants")
    public RestaurantDto addRestaurant(@RequestBody RestaurantDto restaurantDto) {
        System.out.println("add new restaurant");
        System.out.println("input jason " + restaurantDto);
        return new RestaurantDto();
    }

    //update restaurant by id + input json
    @PutMapping("/api/v1/restaurants/{rest_id}")
    public RestaurantDto updateRestaurant(@RequestBody RestaurantDto restaurantDto, @PathVariable(name = "rest_id") int restId) {
        System.out.println("updating restaurant by id = " + restId);
        System.out.println("input jason " + restaurantDto);
        return new RestaurantDto();
    }

    //delete restaurant by id
    @DeleteMapping("/api/v1/restaurants/{rest_id}")
    public void deleteRestaurant(@PathVariable(name = "rest_id") int restId) {
        System.out.println("deleting restaurant by id = " + restId);
    }

    //rating for all restaurants
    @GetMapping("/api/v1/restaurants/rating")
    public void getRating() {
        System.out.println("get rating for all restaurants");
    }


    //rating for all restaurants on the date
    @GetMapping("/api/v1/restaurants/rating?date={rating_date}")
    public void getRatingOnDate(@PathVariable(name = "rating_date") LocalDate localDate) {
        System.out.println("get rating for all restaurants on the date " + localDate);
    }

    //menu

    //showing by rest id the menu
    @GetMapping("/api/v1/restaurants/{rest_id}/menu")
    public MenuDto getMenu(@PathVariable(name = "rest_id") int restId) {
        MenuDto result = new MenuDto();
        System.out.println("get menu for restaurant with id = " + restId);
        System.out.println("output json menu -> " + result);
        return new MenuDto();
    }

    //showing by rest id the menu on the date
    @GetMapping("/api/v1/restaurants/{rest_id}/menu/{menu_date}")
    public MenuDto getMenu(@PathVariable(name = "rest_id") int restId, @PathVariable(name = "menu_date") LocalDate localDate) {
        MenuDto result = new MenuDto();
        System.out.println("get menu for restaurant with id = " + restId + " on the date " + localDate);
        System.out.println("output json menu on the date " + localDate + " -> " + result);
        return new MenuDto();
    }

    //add menu to restaurant by rest id
    @PostMapping("/api/v1/restaurants/{rest_id}/menu")
    public MenuDto addMenu(@RequestBody MenuDto menuDto, @PathVariable(name = "rest_id") int restId) {
        System.out.println("add menu for rest id = " + restId);
        System.out.println("output json menu -> " + menuDto);
        return menuDto;
    }

    //delete menu in rest dy id
    @DeleteMapping("/api/v1/restaurants/{rest_id}/menu")
    public void deleteMenu(@PathVariable(name = "rest_id") int restId) {
        System.out.println("deleting menu in rest by id = " + restId);
    }


    @ExceptionHandler
    public ResponseEntity<EntityNotFoundResponse> handleException(EntityNotFoundException ex) {
        EntityNotFoundResponse response = new EntityNotFoundResponse();
        response.setEntityName(ex.getEntityName());
        response.setEntityId(ex.getEntityId());
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
