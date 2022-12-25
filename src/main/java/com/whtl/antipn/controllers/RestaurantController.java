package com.whtl.antipn.controllers;

import com.whtl.antipn.exception.*;
import com.whtl.antipn.dto.MenuDto;
import com.whtl.antipn.dto.RestaurantScoreDto;
import com.whtl.antipn.dto.RestaurantDto;
import com.whtl.antipn.services.RestaurantAndMenuService;
import com.whtl.antipn.services.RestaurantsAndMenuServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    RestaurantAndMenuService restaurantService;

    @Autowired
    public RestaurantController(RestaurantsAndMenuServiceImpl restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(
            summary = "The providing possibility for administrators to save new restaurant without id",
            description = "It allows for administrators to save restaurant at any time, remember that name+address must be unique"
    )

    @PostMapping//tested 25 12 2022
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody
                                                          @Parameter(description = "Save restaurant by received Json object in format RestaurantDto without id", required = true)
                                                          RestaurantDto restaurantDto) {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantDto));
    }

    @Operation(
            summary = "The providing possibility for administrators and users to see all restaurants in DB",
            description = "It allows for administrators and users to see at all restaurants in DB"
    )
    @GetMapping//tested 25012 2022
    public ResponseEntity<List<RestaurantDto>> getRestaurants() {
        return ResponseEntity.ok(restaurantService.findAllRestaurants());
    }

    @Operation(
            summary = "The providing possibility for administrators and users to see the one restaurants by id in DB",
            description = "It allows for administrators and users to see the one restaurants by id  in DB"
    )
    @GetMapping("/{restId}")//tested 25 12 2022
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable(name = "restId", required = true)
                                                       @Parameter(description = "Look up value for looking up for the restaurant", required = true)
                                                       Integer restId) {
        return ResponseEntity.ok(restaurantService.findRestaurantById(restId));
    }

    @Operation(
            summary = "The providing possibility for administrators to update information about restaurants by id in DB",
            description = "It allows for administrators to see the one restaurants by id in DB"
    )
    @PutMapping//tested 25 12 2022
    public ResponseEntity<RestaurantDto> updateRestaurant(@RequestBody
                                                          @Parameter(description = "Updating the restaurant by received Json object in format RestaurantDto with ID", required = true)
                                                          RestaurantDto restaurantDto
//            ,
//
//                                                          @RequestParam(name = "id", required = true)
//                                                          @Parameter(description = "For updating we will use this variable")
//                                                          int restId
    ) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantDto));
    }

    @Operation(
            summary = "The providing possibility for administrators to delete restaurants by id in DB",
            description = "It allows for administrators to delete restaurants by id in DB"
    )
    @DeleteMapping//tested 25 12 2022
    public ResponseEntity<Void> deleteRestaurant(@RequestParam(name = "id", required = true)
                                                 @Parameter(description = "Look up value for deleting restaurant by id", required = true)
                                                 int restId) {

        restaurantService.deleteRestaurant(restId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "The providing possibility for administrators and users to see restaurants rating on today or on date",
            description = "It allows for administrators and users to see restaurants rating on today or on date"
    )
    @GetMapping("/rating")// расчет рейтингов нужно расчитывать в реальном времени
    //http://localhost:8080/api/v1/restaurants/rating?date=25.12.2022
    public ResponseEntity<List<RestaurantScoreDto>> getRating(@RequestParam(value = "date", required = false)
                                                              @DateTimeFormat(pattern = "dd.MM.yyyy")
                                                              @Parameter(description = "Look up date in format dd-MM-yyyy for getting rating", required = false)
                                                              LocalDate localDate) {
        System.out.println("Выводим рейтинг ресторанов");
        return ResponseEntity.ok(restaurantService.findRestaurantsScoresOnDate(localDate));
    }

    //menu
    @Operation(
            summary = "The providing possibility for administrators and users to see restaurant menu on today (default )or on date",
            description = "It allows for administrators and users to see restaurant menu on today or on date"
    )
    @GetMapping("/{rest_id}/menu") //tested 25 12 2022
    public ResponseEntity<List<MenuDto>> getMenu(@PathVariable(name = "rest_id")
                                                 @Parameter(description = "Look up value for finding restaurant's menu by id", required = true)
                                                 int restId,

                                                 @RequestParam(name = "date", required = false)
                                                 @Parameter(description = "Look up date in format dd-MM-yyyy")
                                                 @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate localDate) {

        return ResponseEntity.ok(restaurantService.findMenuOnDate(restId, localDate));
    }

    @Operation(
            summary = "The providing possibility for administrators to save restaurant's menu",
            description = "It allows for administrators to save restaurant menu on today"
    )
    @PostMapping("/{rest_id}/menu")//tested 25 12 2022
    public ResponseEntity<List<MenuDto>> createMenu(@RequestBody
                                                    @Parameter(description = "Saving restaurant menu be receiving Json format List of menu", required = true)
                                                    List<MenuDto> menuDtoList,
//                                                    @RequestParam(name = "date", required = false)
//                                                    @Parameter(description = "Date for saving menu")
//                                                    @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate localDate,
                                                    @PathVariable(name = "rest_id")
                                                    @Parameter(description = "Look up value for saving menu for restaurant", required = true)
                                                    int restId) {
               return ResponseEntity.ok(restaurantService.saveMenu(restId, menuDtoList));
    }

    @Operation(
            summary = "The providing possibility for administrators to update restaurant menu on today or on other date",
            description = "It allows for administrators to update restaurant menu on today"
    )
    //http://localhost:8080/api/v1/restaurants/1/menu?date=02.11.2022
    @PutMapping("/{rest_id}/menu")
    public ResponseEntity<List<MenuDto>> updateMenu(@PathVariable(name = "rest_id")
                                                    @Parameter(description = "Look up value for updating menu for restaurant")
                                                    int restId,

                                                    @RequestParam(name = "date", required = false)
                                                    @DateTimeFormat(pattern = "dd.MM.yyyy")
                                                    @Parameter(description = "Look up date in format dd-MM-yyyy for updating menu on date", required = false)
                                                    LocalDate localDate,

                                                    @RequestBody
                                                    @Parameter(description = "Updating restaurant's menu by received Json object in format of list with MenuDto for updating")
                                                    List<MenuDto> menuDtoList) {

        return ResponseEntity.ok(restaurantService.updateMenu(localDate, restId, menuDtoList));
    }

    @Operation(
            summary = "The providing possibility for administrators to delete restaurant menu on today or on other date",
            description = "It allows for administrators to delete restaurant menu on today"
    )
    @DeleteMapping("/{rest_id}/menu")
    public ResponseEntity<Void> deleteMenu(@PathVariable(name = "rest_id")
                                           @Parameter(description = "Look up value for deleting menu for restaurant", required = true)
                                           int restId,

                                           @RequestParam(name = "date", required = false)
                                           @DateTimeFormat(pattern = "dd.MM.yyyy")
                                           @Parameter(description = "Look up date in format dd-MM-yyyy for deleting menu on date", required = false)
                                           LocalDate localDate) {

        restaurantService.deleteMenu(localDate, restId);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @ExceptionHandler
    public ResponseEntity<EntityAlreadyExistsResponse> handleException(EntityAlreadyExistsException ex) {
        EntityAlreadyExistsResponse response = new EntityAlreadyExistsResponse();
        response.setMessage(ex.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        response.setStatus(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<SaveRestNotAllowedResponse> handleException(SaveRestNotAllowedException ex) {
        SaveRestNotAllowedResponse response = new SaveRestNotAllowedResponse();
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
