package com.whtl.antipn.services;

import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.exception.EntityNotFoundResponse;
import com.whtl.antipn.mapper.RestaurantScoreMapper;
import com.whtl.antipn.dto.MenuDto;
import com.whtl.antipn.dto.RestaurantScoreDto;
import com.whtl.antipn.dto.RestaurantDto;
import com.whtl.antipn.mapper.MenuMapper;
import com.whtl.antipn.mapper.RestaurantMapper;
import com.whtl.antipn.model.Menu;
import com.whtl.antipn.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whtl.antipn.repositories.InMemoryRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RestaurantAndMenuServiceImpl implements RestaurantAndMenuService {

    InMemoryRepository repository;
    RestaurantMapper restaurantMapper;
    MenuMapper menuMapper;
    RestaurantScoreMapper restaurantScoreMapper;

    @Autowired
    public RestaurantAndMenuServiceImpl(InMemoryRepository repository, RestaurantMapper restaurantMapper, RestaurantScoreMapper restaurantScoreMapper, MenuMapper menuMapper) {
        this.repository = repository;
        this.restaurantMapper = restaurantMapper;
        this.restaurantScoreMapper = restaurantScoreMapper;
        this.menuMapper = menuMapper;
    }

    public List<RestaurantDto> findAllRestaurants() {
        List<Restaurant> restaurants = repository.findAllRestaurants();
        if (restaurants == null) {
            throw new EntityNotFoundException("RestaurantDto", 0, "There are not restaurants");
        }
        return restaurantMapper.toDtoList(restaurants);
    }

    public RestaurantDto findRestaurantById(int restId) {
        Restaurant restaurantById = repository.findRestaurantById(restId);
        if (restaurantById == null) {
            throw new EntityNotFoundException("RestaurantDto", 0, "There is no restaurant with id = " + restId);
        }
        return restaurantMapper.toDto(restaurantById);
    }

    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {//just dto, or dto without restId
        Restaurant entity = restaurantMapper.toEntity(restaurantDto);
        if (entity.getId() == null) {
            entity.setId(repository.findAllRestaurants().size() + 1);
            //пометка в лог что ресторан пришел без id поэтому добавили автоматически следующий номер в базе!
        }
        //do we need check possibility that rest id alredy in DB ?
        return restaurantMapper.toDto(repository.saveRestaurant(entity));
    }

    public RestaurantDto createRestaurant(RestaurantDto restaurantDto, int restId) {
        if (restaurantDto.getId() != restId) {
            restaurantDto.setId(restId);
        }
        Restaurant entity = restaurantMapper.toEntity(restaurantDto);
        return restaurantMapper.toDto(repository.saveRestaurant(entity));
    }

    public void deleteRestaurant(int restId) {
        if (!repository.deleteRestaurant(restId)) {
            throw new EntityNotFoundException("RestaurantDto", restId, "There is no restaurant with id = " + restId);
        } else {
            //do we need provide more info for user after deleting ?
        }
    }

    public List<RestaurantScoreDto> findRestaurantsScores() {
        return restaurantScoreMapper.toDto(repository.findAllRestaurantsScoreOnToday(LocalDate.now()));
    }

    public List<RestaurantScoreDto> findRestaurantsScoresOnDate(LocalDate localDate) {
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        return restaurantScoreMapper.toDto(repository.findAllRestaurantsScoreOnToday(localDate));
    }

    public List<MenuDto> findMenu(int restId) {
        return menuMapper.toDtoList(repository.findMenuByRestId(restId));
    }

    public List<MenuDto> findMenuOnDate(LocalDate localDate, int restId) {// do i need checking rest id ???
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        List<Menu> menuByRestIdAndDate = repository.findMenuByRestIdAndDate(localDate, restId);
        if (menuByRestIdAndDate == null) {
            throw new EntityNotFoundException("MenuDto", 0, "There is no menu for restaurant with id = " + restId + " on date " + localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        return menuMapper.toDtoList(menuByRestIdAndDate);
    }

    public List<MenuDto> saveMenu(LocalDate localDate, int restId, List<MenuDto> menuInput) {
        return menuMapper.toDtoList(repository.saveMenu(localDate, restId, menuMapper.toEntityList(menuInput)));
    }

    @Override
    public List<MenuDto> updateMenu(LocalDate localDate, int restId, List<MenuDto> menuDtoList) {
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        return saveMenu(localDate, restId, menuDtoList);
    }

    public void deleteMenu(LocalDate localDate, int restId) {
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        repository.deleteMenuByRestIdAndDate(localDate, restId); //ValidationUtil.checkNotFoundWithId(
    }
    //после перехода на jpa это пригодится
    //    private void checkById(@PathVariable int id, String entityName, String comment) {
    //        if(!repository.existsById(id)){
    //            throw new EntityNotFoundException(entityName, id, comment);
    //        }

}
