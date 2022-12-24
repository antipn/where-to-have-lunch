package com.whtl.antipn.services;

import com.whtl.antipn.dto.MenuDto;
import com.whtl.antipn.dto.RestaurantDto;
import com.whtl.antipn.dto.RestaurantScoreDto;
import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.mapper.MenuMapper;
import com.whtl.antipn.mapper.RestaurantMapper;
import com.whtl.antipn.mapper.RestaurantScoreMapper;
import com.whtl.antipn.model.Restaurant;
import com.whtl.antipn.repositories.MenuRepository;
import com.whtl.antipn.repositories.RestaurantRepository;
import com.whtl.antipn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantsAndMenuServiceImpl implements RestaurantAndMenuService {

    MenuRepository menuRepository;
    RestaurantRepository restaurantRepository;
    UserRepository userRepository;

    MenuMapper menuMapper;
    RestaurantMapper restaurantMapper;
    RestaurantScoreMapper restaurantScoreMapper;

    @Autowired
    public RestaurantsAndMenuServiceImpl(MenuRepository menuRepository, RestaurantRepository restaurantRepository,
                                         UserRepository userRepository, MenuMapper menuMapper, RestaurantMapper restaurantMapper,
                                         RestaurantScoreMapper restaurantScoreMapper) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.menuMapper = menuMapper;
        this.restaurantMapper = restaurantMapper;
        this.restaurantScoreMapper = restaurantScoreMapper;
    }


    @Override
    public List<RestaurantDto> findAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants == null) {
            throw new EntityNotFoundException("RestaurantDto", 0, "There are not restaurants");
        }
        return restaurantMapper.toDtoList(restaurants);
    }

    @Override
    public RestaurantDto findRestaurantById(int restId) {
        Restaurant restaurantById = restaurantRepository.getReferenceById(restId);
        if (restaurantById == null) {
            throw new EntityNotFoundException("RestaurantDto", 0, "There is no restaurant with id = " + restId);
        }
        return restaurantMapper.toDto(restaurantById);
    }

    @Override
    //when saving we need to return object from DB ? when looking on first time when filling variable with that object
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto, int restId) {
        if (restaurantDto.getId() != restId) {
            restaurantDto.setId(restId);
        }
        Restaurant entity = restaurantMapper.toEntity(restaurantDto);
        restaurantRepository.save(entity);
        return restaurantMapper.toDto(restaurantRepository.getReferenceById(restId));
    }

    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        Restaurant entity = restaurantMapper.toEntity(restaurantDto);
        restaurantRepository.save(entity);
        return restaurantMapper.toDto(restaurantRepository.getReferenceById(entity.getId()));
    }

    @Override
    public void deleteRestaurant(int restId) { //проверить удаление ресторана которого нет
        try {
            restaurantRepository.deleteById(restId);
        } catch (Exception e) {
            throw new EntityNotFoundException("RestaurantDto", restId, "There is no restaurant with id = " + restId);
        }

    }

    @Override
    public List<RestaurantScoreDto> findRestaurantsScores() {
        LocalDate localDate = LocalDate.now();
        return null;
    }

    @Override
    public List<RestaurantScoreDto> findRestaurantsScoresOnDate(LocalDate localDate) {
        return null;
    }

    @Override
    public List<MenuDto> findMenu(int restId) {
        return null;
    }

    @Override
    public List<MenuDto> findMenuOnDate(LocalDate localDate, int restId) {
        return null;
    }

    @Override
    public List<MenuDto> saveMenu(LocalDate localDate, int restId, List<MenuDto> menuInput) {
        return null;
    }

    @Override
    public void deleteMenu(LocalDate localDate, int restId) {

    }

    @Override
    public List<MenuDto> updateMenu(LocalDate localDate, int restId, List<MenuDto> menuDtoList) {
        return null;
    }
}
