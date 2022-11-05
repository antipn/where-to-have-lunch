package controllers;

import dto.*;
import exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.RestaurantAndMenuService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class RestaurantController {

    private final RestaurantAndMenuService restaurantService;

    @Autowired
    public RestaurantController(RestaurantAndMenuService restaurantService) {
        this.restaurantService = restaurantService;
    }

    //restaurants

    //show all restaurants
    @RequestMapping(value = "app/v1/restaurants", method = RequestMethod.GET)
    //@GetMapping("/app/v1/restaurants")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<RestaurantDto> result = restaurantService.findAllRestaurants();
        System.out.println("showing all restaurants");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //showing one restaurant by id
    @RequestMapping(value = "api/v1/restaurants/{rest_id}", method = RequestMethod.GET)
    //@GetMapping("api/v1/restaurants/{rest_id}")
    public ResponseEntity<?> getOneRestaurant(@PathVariable(name = "rest_id") int restId) {
        System.out.println("showing one restaurants with id = " + restId);
        return ResponseEntity.ok(restaurantService.findRestaurantById(restId));
    }

    //input new restaurant input json
    @RequestMapping(value = "/api/v1/restaurants", method = RequestMethod.POST)
    //@PostMapping("/api/v1/restaurants")
    public ResponseEntity<RestaurantDto> addRestaurant(@RequestBody RestaurantDto restaurantDto) {
        System.out.println("add new restaurant");
        System.out.println("input jason " + restaurantDto);
        //checking is new? cause json with new restaurant came without id!!!
        //set id for json restaurant for saving
        restaurantDto.setRestId(1111); //some id check with Sasha what put in id now!!!!
        return ResponseEntity.ok(restaurantService.saveRestaurant(restaurantDto));
    }

    //update restaurant by id + input json
    @RequestMapping(value = "/api/v1/restaurants/{rest_id}", method = RequestMethod.PUT)
    //@PutMapping("/api/v1/restaurants/{rest_id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@RequestBody RestaurantDto restaurantDto, @PathVariable(name = "rest_id") int restId) {
        System.out.println("updating restaurant by id = " + restId);
        System.out.println("input jason potentially with id" + restaurantDto);
        if (restaurantDto.getRestId() == restId) {
            return ResponseEntity.ok(restaurantService.saveRestaurant(restaurantDto));
        } else {// we can not save
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //delete restaurant by id
    @RequestMapping(value = "/api/v1/restaurants/{rest_id}", method = RequestMethod.DELETE)
    //@DeleteMapping("/api/v1/restaurants/{rest_id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable(name = "rest_id") int restId) {
        System.out.println("deleting restaurant by id = " + restId);
        restaurantService.deleteRestaurant(restId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //rating for all restaurants
    @RequestMapping(value = "/api/v1/restaurants/rating", method = RequestMethod.GET)
    //@GetMapping("/api/v1/restaurants/rating")
    public ResponseEntity<RestairantScoreDto> getRating() {
        System.out.println("get rating for all restaurants");
        return ResponseEntity.ok(restaurantService.getRestaurantsScores());
    }

    //rating for all restaurants on the date
    @RequestMapping(value = "/api/v1/restaurants/rating?date={rating_date}", method = RequestMethod.GET)
    //@GetMapping("/api/v1/restaurants/rating?date={rating_date}")
    public ResponseEntity<RestairantScoreDto> getRatingOnDate(@PathVariable(name = "rating_date") LocalDate localDate) {
        System.out.println("get rating for all restaurants on the date " + localDate);
        return ResponseEntity.ok(restaurantService.getRestaurantsScoresOnDate(localDate));
    }

    //menu

    //showing by rest id the menu
    @RequestMapping(value = "/api/v1/restaurants/{rest_id}/menu", method = RequestMethod.GET)
    //@GetMapping("/api/v1/restaurants/{rest_id}/menu")
    public ResponseEntity<MenuDto> getMenu(@PathVariable(name = "rest_id") int restId) {
        System.out.println("get menu for restaurant with id = " + restId);
        System.out.println("output json menu -> ");
        return ResponseEntity.ok(restaurantService.findMenu(restId));
    }

    //showing by restid the menu on the date
    @RequestMapping(value = "/api/v1/restaurants/{rest_id}/menu/{menu_date}", method = RequestMethod.GET)
    //@GetMapping("/api/v1/restaurants/{rest_id}/menu/{menu_date}")
    public ResponseEntity<MenuDto> getMenu(@PathVariable(name = "rest_id") int restId, @PathVariable(name = "menu_date") LocalDate localDate) {
        System.out.println("get menu for restaurant with id = " + restId + " on the date " + localDate);
        System.out.println("output json menu on the date " + localDate + " -> ");
        return ResponseEntity.ok(restaurantService.findMenuOnDate(restId, localDate));
    }

    //add menu to restaurant by rest id
    @RequestMapping(value = "/api/v1/restaurants/{rest_id}/menu", method = RequestMethod.POST)
    //@PostMapping("/api/v1/restaurants/{rest_id}/menu")
    public ResponseEntity<MenuDto> addMenu(@RequestBody MenuDto menuDto, @PathVariable(name = "rest_id") int restId) {
        System.out.println("add menu for rest id = " + restId);
        System.out.println("output json menu -> " + menuDto);
        return ResponseEntity.ok(restaurantService.saveMenu(restId));
    }

    //there is no PUT method because we dont responsible for changing history of meal in our app
    //all errors in menu might be change in the same day by deleting and again new posting

    //delete menu in rest dy id
    @RequestMapping(value = "/api/v1/restaurants/{rest_id}/menu", method = RequestMethod.DELETE)
    //@DeleteMapping("/api/v1/restaurants/{rest_id}/menu")
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
