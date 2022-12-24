//package com.whtl.antipn.controllers.inmemory;
//
//import com.whtl.antipn.dto.MenuDto;
//import com.whtl.antipn.dto.RestaurantDto;
//import com.whtl.antipn.dto.RestaurantScoreDto;
//import com.whtl.antipn.exception.EntityNotFoundException;
//import com.whtl.antipn.exception.EntityNotFoundResponse;
//import com.whtl.antipn.services.inmemory.RestaurantAndMenuServiceImplInMemoryRepo;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Tag(name = "Restaurants and menus controller", description = "The responsibilities of controller are: creating, updating and deleting information about restaurants and theirs menus. ")
//@RestController
//public class RestaurantControllerInMemoryRepo {
//
//    private final RestaurantAndMenuServiceImplInMemoryRepo restaurantService;
//
//    @Autowired
//    public RestaurantControllerInMemoryRepo(RestaurantAndMenuServiceImplInMemoryRepo restaurantService) {
//        this.restaurantService = restaurantService;
//    }
//
//    //restaurants
//    @Operation(
//            summary = "The providing possibility for administrators to save new restaurant",
//            description = "It allows for administrators to save restaurant at any time"
//    )
//
//    @PostMapping("/api/v1/restaurants")
//
//    //now we from this method we will use restId from restaurantDto
//    //we don't require restId
//    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody
//                                                          @Parameter(description = "Save restaurant by received Json object in format RestaurantDto", required = true)
//                                                          RestaurantDto restaurantDto) {
//
//        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantDto));
//    }
//
//    @Operation(
//            summary = "The providing possibility for administrators and users to see all restaurants in DB",
//            description = "It allows for administrators and users to see at all restaurants in DB"
//    )
//    @GetMapping("/api/v1/restaurants")
//    public ResponseEntity<List<RestaurantDto>> readAllRestaurants() {
//        return ResponseEntity.ok(restaurantService.findAllRestaurants());
//    }
//
//    @Operation(
//            summary = "The providing possibility for administrators and users to see the one restaurants by id in DB",
//            description = "It allows for administrators and users to see the one restaurants by id  in DB"
//    )
//    @GetMapping("api/v1/restaurants/") //discuss this path with "/" !!!!
//    public ResponseEntity<RestaurantDto> readOneRestaurant(@RequestParam(name = "id", required = true)
//                                                           @Parameter(description = "Look up value for looking for the restaurant", required = true)
//                                                           int restId) {
//
//        return ResponseEntity.ok(restaurantService.findRestaurantById(restId));
//    }
//
//    @Operation(
//            summary = "The providing possibility for administrators to update information about restaurants by id in DB",
//            description = "It allows for administrators to see the one restaurants by id in DB"
//    )
//    @PutMapping("/api/v1/restaurants")
//    public ResponseEntity<RestaurantDto> updateRestaurant(@RequestBody
//                                                          @Parameter(description = "Updating the restaurant by received Json object in format RestaurantDto", required = true)
//                                                          RestaurantDto restaurantDto,
//
//                                                          @RequestParam(name = "id", required = true)
//                                                          @Parameter(description = "For updating we will use this variable")
//                                                          int restId) {
//
//        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantDto, restId));
//    }
//
//    @Operation(
//            summary = "The providing possibility for administrators to delete restaurants by id in DB",
//            description = "It allows for administrators to delete restaurants by id in DB"
//    )
//    @DeleteMapping("/api/v1/restaurants")
//    public ResponseEntity<Void> deleteRestaurant(@RequestParam(name = "id", required = true)
//                                                 @Parameter(description = "Look up value for deleting restaurant by id", required = true)
//                                                 int restId) {
//
//        restaurantService.deleteRestaurant(restId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @Operation(
//            summary = "The providing possibility for administrators and users to see restaurants rating on today or on date",
//            description = "It allows for administrators and users to see restaurants rating on today or on date"
//    )
//    @GetMapping("/api/v1/restaurants/rating")
//    //http://localhost:8080/api/v1/restaurants/rating?date=02.11.2022
//    public ResponseEntity<List<RestaurantScoreDto>> readRating(@RequestParam(value = "date", required = false)
//                                                               @DateTimeFormat(pattern = "dd.MM.yyyy")
//                                                               @Parameter(description = "Look up date in format dd-MM-yyyy for getting rating", required = false)
//                                                               LocalDate localDate) {
//
//        return ResponseEntity.ok(restaurantService.findRestaurantsScoresOnDate(localDate));
//    }
//
//    //menu
//    @Operation(
//            summary = "The providing possibility for administrators and users to see restaurant menu on today or on date",
//            description = "It allows for administrators and users to see restaurant menu on today or on date"
//    )
//    @GetMapping("/api/v1/restaurants/{rest_id}/menu")
//    public ResponseEntity<List<MenuDto>> getMenu(@PathVariable(name = "rest_id")
//                                                 @Parameter(description = "Look up value for finding restaurant's menu by id", required = true)
//                                                 int restId,
//
//                                                 @RequestParam(name = "date", required = false)
//                                                 @Parameter(description = "Look up date in format dd-MM-yyyy")
//                                                 @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate localDate) {
//
//        return ResponseEntity.ok(restaurantService.findMenuOnDate(localDate, restId));
//    }
//
//    @Operation(
//            summary = "The providing possibility for administrators to save restaurant menu on today",
//            description = "It allows for administrators to save restaurant menu on today"
//    )
//    @PostMapping("/api/v1/restaurants/{rest_id}/menu")
//    public ResponseEntity<List<MenuDto>> createMenu(@RequestBody
//                                                    @Parameter(description = "Saving restaurant menu be receiving Json format List of menu", required = true)
//                                                    List<MenuDto> menuDtoList,
//
//                                                    @PathVariable(name = "rest_id")
//                                                    @Parameter(description = "Look up value for saving menu for restaurant", required = true)
//                                                    int restId) {
//
//        return ResponseEntity.ok(restaurantService.saveMenu(LocalDate.now(), restId, menuDtoList));
//    }
//
//    @Operation(
//            summary = "The providing possibility for administrators to update restaurant menu on today or on other date",
//            description = "It allows for administrators to update restaurant menu on today"
//    )
//    //http://localhost:8080/api/v1/restaurants/1/menu?date=02.11.2022
//    @PutMapping("/api/v1/restaurants/{rest_id}/menu")
//    public ResponseEntity<List<MenuDto>> updateMenu(@PathVariable(name = "rest_id")
//                                                    @Parameter(description = "Look up value for updating menu for restaurant")
//                                                    int restId,
//
//                                                    @RequestParam(name = "date", required = false)
//                                                    @DateTimeFormat(pattern = "dd.MM.yyyy")
//                                                    @Parameter(description = "Look up date in format dd-MM-yyyy for updating menu on date", required = false)
//                                                    LocalDate localDate,
//
//                                                    @RequestBody
//                                                    @Parameter(description = "Updating restaurant's menu by received Json object in format of list with MenuDto for updating")
//                                                    List<MenuDto> menuDtoList) {
//
//        return ResponseEntity.ok(restaurantService.updateMenu(localDate, restId, menuDtoList));
//    }
//
//    @Operation(
//            summary = "The providing possibility for administrators to delete restaurant menu on today or on other date",
//            description = "It allows for administrators to delete restaurant menu on today"
//    )
//    @DeleteMapping("/api/v1/restaurants/{rest_id}/menu")
//    public ResponseEntity<Void> deleteMenu(@PathVariable(name = "rest_id")
//                                           @Parameter(description = "Look up value for deleting menu for restaurant", required = true)
//                                           int restId,
//
//                                           @RequestParam(name = "date", required = false)
//                                           @DateTimeFormat(pattern = "dd.MM.yyyy")
//                                           @Parameter(description = "Look up date in format dd-MM-yyyy for deleting menu on date", required = false)
//                                           LocalDate localDate) {
//
//        restaurantService.deleteMenu(localDate, restId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<EntityNotFoundResponse> handleException(EntityNotFoundException ex) {
//        EntityNotFoundResponse response = new EntityNotFoundResponse();
//        response.setEntityName(ex.getEntityName());
//        response.setEntityId(ex.getEntityId());
//        response.setMessage(ex.getMessage());
//        response.setStatus(HttpStatus.NOT_FOUND.value());
//        response.setTimestamp(System.currentTimeMillis());
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//}
