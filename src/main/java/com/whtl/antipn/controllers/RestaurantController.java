package com.whtl.antipn.controllers;

import com.whtl.antipn.dto.EntityNotFoundResponse;
import com.whtl.antipn.dto.MenuDto;
import com.whtl.antipn.dto.RestaurantScoreDto;
import com.whtl.antipn.dto.RestaurantDto;
import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.services.RestaurantAndMenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;

@RestController
public class RestaurantController {

    private final RestaurantAndMenuServiceImpl restaurantService;

    @Autowired
    public RestaurantController(RestaurantAndMenuServiceImpl restaurantService) {
        this.restaurantService = restaurantService;
    }

    //restaurants

    @PostMapping("/api/v1/restaurants")// +
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) {
        System.out.println("add new restaurant");
        System.out.println("input jason " + restaurantDto);
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantDto));
    }

    @GetMapping("/api/v1/restaurants") // +
    public ResponseEntity<List<RestaurantDto>> readAllRestaurants() {
        List<RestaurantDto> result = restaurantService.findAllRestaurants();
        restaurantService.findAllRestaurants();
        //System.out.println("showing all restaurants");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/v1/restaurants/{rest_id}") // +
    public ResponseEntity<RestaurantDto> readOneRestaurant(@PathVariable(name = "rest_id") int restId) {
        System.out.println("showing one restaurants with id = " + restId);
        RestaurantDto result = restaurantService.findRestaurantById(restId);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            throw new EntityNotFoundException("Restaurant", restId, "Not found");
        }
    }

    @PutMapping("/api/v1/restaurants/{rest_id}") // +
    public ResponseEntity<RestaurantDto> updateRestaurant(@RequestBody RestaurantDto restaurantDto, @PathVariable(name = "rest_id") int restId) {
        System.out.println("updating restaurant by id = " + restId);
        System.out.println("input jason potentially with id" + restaurantDto);
        if (restaurantDto.getId() == restId) { //json id == restId
        } else {//json id is different from in path variable
            restaurantDto.setId(restId); //give right rest id in json and after saving
        }
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantDto));
    }

    @DeleteMapping("/api/v1/restaurants/{rest_id}") //+
    public ResponseEntity<Void> deleteRestaurant(@PathVariable(name = "rest_id") int restId) {

        restaurantService.deleteRestaurant(restId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //rating on today and on the date is the same
    //@GetMapping("/api/v1/restaurants/rating")
    @GetMapping("/api/v1/restaurants/rating")// +
    //http://localhost:8080/api/v1/restaurants/rating?date=02.11.2022
    public ResponseEntity<List<RestaurantScoreDto>> readRatingOnDate(@RequestParam(required = false) LocalDate date) {
        return ResponseEntity.ok(restaurantService.findRestaurantsScoresOnDate(date));
    }

    //menu

    @GetMapping("/api/v1/restaurants/{rest_id}/menu") //+
    public ResponseEntity<List<MenuDto>> readMenu(@PathVariable(name = "rest_id") int restId) {
        System.out.println("get menu for restaurant with id = " + restId);
        System.out.println("output json menu -> ");
        List<MenuDto> result = restaurantService.findMenu(restId);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            throw new EntityNotFoundException("MenuDto", restId, "There is no menu for restaurant with id = " + restId);
        }


    }
//? do we really need to keep old menu for restaurants???? discuss
    @GetMapping("/api/v1/restaurants/{rest_id}/menu/{menu_date}") // ---
    public ResponseEntity<MenuDto> getMenu(@PathVariable(name = "rest_id") int restId, @PathVariable(name = "menu_date") LocalDate localDate) {
        System.out.println("get menu for restaurant with id = " + restId + " on the date " + localDate);
        System.out.println("output json menu on the date " + localDate + " -> ");
        return ResponseEntity.ok(restaurantService.findMenuOnDate(restId, localDate));
    }

    @PostMapping("/api/v1/restaurants/{rest_id}/menu") //+
    public ResponseEntity<List<MenuDto>> createMenu(@RequestBody List<MenuDto> menuDtoList, @PathVariable(name = "rest_id") int restId) {
        System.out.println("add menu for rest id = " + restId);
        System.out.println("output json menu -> " + menuDtoList);
        return ResponseEntity.ok(restaurantService.saveMenu(menuDtoList, restId));
    }

    //there is no PUT method because we dont responsible for changing history of meal in our app
    //all errors in menu might be change in the same day by deleting and again new posting

    @DeleteMapping("/api/v1/restaurants/{rest_id}/menu") //+
    public ResponseEntity<Void> deleteMenu(@PathVariable(name = "rest_id") int restId) {
        System.out.println("deleting menu in rest by id = " + restId);
        restaurantService.deleteMenu(restId);
        return new ResponseEntity<Void>(HttpStatus.OK);
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
