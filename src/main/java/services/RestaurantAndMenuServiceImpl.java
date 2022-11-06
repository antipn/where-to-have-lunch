package services;

import dto.MenuDto;
import dto.RestairantScoreDto;
import dto.RestaurantDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RestaurantAndMenuServiceImpl implements RestaurantAndMenuService{

    //RestaurantRepository restaurantRepository;
    //VotingRepository votingRepository;

    public List<RestaurantDto> findAllRestaurants() {
        //restRepo.findAll();
        return null;
    }

    public RestaurantDto findRestaurantById(int restId) {
        //restRepo.findById(id);
        return null;
    }

    public RestaurantDto saveRestaurant(RestaurantDto restaurantDto) {
        //just save to restRepo
        //checking is new? cause json with new restaurant came without id!!!
        //updating
        return null;
    }

    public RestaurantDto deleteRestaurant(int restId) {
        //cheking that id exists in repo and if ok ? delete : return error no id in repo
        return null;
    }

    public RestairantScoreDto findRestaurantsScores() {
        //find all rest in repo and return with scores
        //Scores are in repo
        return null;
    }

    public RestairantScoreDto findRestaurantsScoresOnDate(LocalDate localDate) {
        //find scores on date in repo
        //The scores are in repo
        return null;
    }

    public MenuDto findMenu(int restId) {
        // find menu by rest id in repo
        return null;
    }

    public MenuDto findMenuOnDate(int restId, LocalDate localDate) {
        // find menu by id and date in repo
        return null;
    }

    public MenuDto saveMenu(int id) {
        // save  menu by id in repo
        return null;
    }

    public MenuDto deleteMenu(int id) {
        // delete menu by id in repo
        return null;
    }
}
