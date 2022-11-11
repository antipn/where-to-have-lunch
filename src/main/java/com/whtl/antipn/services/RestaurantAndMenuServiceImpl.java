package com.whtl.antipn.services;

import com.whtl.antipn.mapper.RestaurantScoreMapper;
import com.whtl.antipn.dto.MenuDto;
import com.whtl.antipn.dto.RestaurantScoreDto;
import com.whtl.antipn.dto.RestaurantDto;
import com.whtl.antipn.mapper.MenuMapper;
import com.whtl.antipn.mapper.RestaurantMapper;
import com.whtl.antipn.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whtl.antipn.repositories.InMemoryRepository;

import java.time.LocalDate;
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
        return restaurantMapper.toDtoList(restaurants);
    }

    public RestaurantDto findRestaurantById(int restId) {
        return restaurantMapper.toDto(repository.findRestaurantById(restId));
    }

    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) { //discus this
        Restaurant entity = restaurantMapper.toEntity(restaurantDto);
        repository.saveRestaurant(entity);
        return restaurantMapper.toDto(repository.saveRestaurant(entity));
    }

    public void deleteRestaurant(int restId) {
        repository.deleteRestaurant(restId); //ValidationUtil.checkNotFoundWithId(
    }

    public List<RestaurantScoreDto> findRestaurantsScores() {
        LocalDate localDate = LocalDate.now(); //#1
        return restaurantScoreMapper.toDto(repository.findAllRestaurantsScoreOnToday(localDate));
    }

    public List<RestaurantScoreDto> findRestaurantsScoresOnDate(LocalDate localDate) {
        return restaurantScoreMapper.toDto(repository.findAllRestaurantsScoreOnToday(localDate));
    }

    public List<MenuDto> findMenu(int restId) {
        return menuMapper.toDtoList(repository.findMenuByRestId(restId));
    }

    public List<MenuDto> findMenuOnDate(LocalDate localDate, int restId) {
        return menuMapper.toDtoList(repository.findMenuByRestIdAndDate(localDate, restId));
    }

    public List<MenuDto> saveMenu(LocalDate localDate, int restId, List<MenuDto> menuInput) {
        return menuMapper.toDtoList(repository.saveMenu(localDate, restId, menuMapper.toEntityList(menuInput)));
    }

    @Override
    public List<MenuDto> updateMenu(LocalDate localDate, int restId, List<MenuDto> menuDtoList) {
        return saveMenu(localDate, restId, menuDtoList);
    }

    public void deleteMenu(LocalDate localDate, int restId) {
        repository.deleteMenuByRestIdAndDate(localDate, restId); //ValidationUtil.checkNotFoundWithId(
    }
    //после перехода на jpa это пригодится
    //    private void checkById(@PathVariable int id, String entityName, String comment) {
    //        if(!repository.existsById(id)){
    //            throw new EntityNotFoundException(entityName, id, comment);
    //        }

}
