package com.whtl.antipn.controllers;

import com.whtl.antipn.exception.EntityNotFoundResponse;
import com.whtl.antipn.dto.MenuDto;
import com.whtl.antipn.dto.RestaurantScoreDto;
import com.whtl.antipn.dto.RestaurantDto;
import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.services.RestaurantAndMenuServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Restaurants and menus controller", description = "The responsibilities of controller are: creating, updating and deleting information about restaurants and theirs menus. ")
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
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantDto));
    }

    @GetMapping("/api/v1/restaurants")
    public ResponseEntity<List<RestaurantDto>> readAllRestaurants() {
        List<RestaurantDto> result = restaurantService.findAllRestaurants();
        restaurantService.findAllRestaurants();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/v1/restaurants/")
    public ResponseEntity<RestaurantDto> readOneRestaurant(@RequestParam(name = "id", required = true) int restId) {
        RestaurantDto result = restaurantService.findRestaurantById(restId);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            throw new EntityNotFoundException("Restaurant", restId, "Not found");
        }
    }

    @PutMapping("/api/v1/restaurants/{rest_id}") // +
    public ResponseEntity<RestaurantDto> updateRestaurant(@RequestBody RestaurantDto restaurantDto,
                                                          @PathVariable(name = "rest_id") int restId) {
        if (restaurantDto.getId() != restId) {
            restaurantDto.setId(restId);
        }
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantDto));
    }

    @DeleteMapping("/api/v1/restaurants/{rest_id}") //+
    public ResponseEntity<Void> deleteRestaurant(@PathVariable(name = "rest_id") int restId) {
        restaurantService.deleteRestaurant(restId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/v1/restaurants/rating")// +
    //http://localhost:8080/api/v1/restaurants/rating?date=02.11.2022
    public ResponseEntity<List<RestaurantScoreDto>> readRating(@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        if (date == null) {
            date = LocalDate.now();// #!
        }
        return ResponseEntity.ok(restaurantService.findRestaurantsScoresOnDate(date));
    }

    //menu
    @GetMapping("/api/v1/restaurants/{rest_id}/menu")
    public ResponseEntity<List<MenuDto>> getMenu(@PathVariable(name = "rest_id") int restId,
                                                 @RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate localDate) {
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        List<MenuDto> result = restaurantService.findMenuOnDate(localDate, restId);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            throw new EntityNotFoundException("MenuDto", restId, "There is no menu for restaurant with id = " + restId + " on date " + localDate);
        }
    }

    @PostMapping("/api/v1/restaurants/{rest_id}/menu")
    public ResponseEntity<List<MenuDto>> createMenu(@RequestBody List<MenuDto> menuDtoList,
                                                    @PathVariable(name = "rest_id") int restId) {
        LocalDate localDate = LocalDate.now();
        return ResponseEntity.ok(restaurantService.saveMenu(localDate, restId, menuDtoList));
    }

    //http://localhost:8080/api/v1/restaurants/1/menu?date=02.11.2022
    @PutMapping("/api/v1/restaurants/{rest_id}/menu")
    public ResponseEntity<List<MenuDto>> updateMenu(@PathVariable(name = "rest_id") int restId,
                                                    @PathVariable(name = "menu_date", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate localDate,
                                                    @RequestBody List<MenuDto> menuDtoList) {
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        restaurantService.updateMenu(localDate, restId, menuDtoList);
        return null;
    }

    @DeleteMapping("/api/v1/restaurants/{rest_id}/menu")
    public ResponseEntity<Void> deleteMenu(@PathVariable(name = "rest_id") int restId,
                                           @PathVariable(name = "menu_date", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate localDate) {
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        restaurantService.deleteMenu(localDate, restId);
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
