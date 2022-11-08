package com.whtl.antipn.services;

import com.whtl.antipn.Utils.Converters;
import com.whtl.antipn.Utils.ValidationUtil;
import com.whtl.antipn.dto.MenuDto;
import com.whtl.antipn.dto.RestaurantScoreDto;
import com.whtl.antipn.dto.RestaurantDto;
import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.mapper.MenuMapper;
import com.whtl.antipn.mapper.RestaurantMapper;
import com.whtl.antipn.model.Restaurant;
import com.whtl.antipn.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whtl.antipn.repositories.InMemoryRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RestaurantAndMenuServiceImpl implements RestaurantAndMenuService {

    InMemoryRepository repository;
    RestaurantMapper restaurantMapper;
    MenuMapper menuMapper;
    Converters converters;

    @Autowired
    public RestaurantAndMenuServiceImpl(InMemoryRepository repository, RestaurantMapper restaurantMapper, Converters converters, MenuMapper menuMapper) {
        this.repository = repository;
        this.restaurantMapper = restaurantMapper;
        this.converters = converters;
        this.menuMapper = menuMapper;
    }
    //RestaurantRepository restaurantRepository;
    //VotingRepository votingRepository;

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
        return restaurantMapper.toDto(repository.saveRestaurant(entity));//what saved in entity that return in dto
    }

    public void deleteRestaurant(int restId) {
        ValidationUtil.checkNotFoundWithId(repository.deleteRestaurant(restId), restId);
    }

    public List<RestaurantScoreDto> findRestaurantsScores() {
        LocalDate localDateToday = LocalDate.of(2022, 11, 3);
        return converters.toDto(repository.findAllRestaurantsScoreOnToday(localDateToday));
    }

    public List<RestaurantScoreDto> findRestaurantsScoresOnDate(LocalDate localDate) {
        return converters.toDto(repository.findAllRestaurantsScoreOnToday(localDate));

    }

    public List<MenuDto> findMenu(int restId) {
        return menuMapper.toDtoList(repository.findMenuByRestId(restId));
    }

    public MenuDto findMenuOnDate(int restId, LocalDate localDate) {
        // find menu by id and date in repo
        return null;
    }

    public List<MenuDto> saveMenu(List<MenuDto> menuInput, int restId) {

        return menuMapper.toDtoList(repository.saveMenu(menuMapper.toEntityList(menuInput),restId));
    }

    public void deleteMenu(int restId) {
        ValidationUtil.checkNotFoundWithId(repository.deleteMenuByRestId(restId), restId);
    }
    //после перехода на jpa это пригодится
    //    private void checkById(@PathVariable int id, String entityName, String comment) {
    //        if(!repository.existsById(id)){
    //            throw new EntityNotFoundException(entityName, id, comment);
    //        }

}
