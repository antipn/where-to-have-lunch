package com.whtl.antipn.services;

import com.whtl.antipn.dto.IncomeMenuDto;
import com.whtl.antipn.dto.MenuDto;
import com.whtl.antipn.dto.RestaurantDto;
import com.whtl.antipn.exception.EntityAlreadyExistsException;
import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.mapper.MenuMapper;
import com.whtl.antipn.mapper.RestaurantMapper;
import com.whtl.antipn.model.Restaurant;
import com.whtl.antipn.repositories.MenuRepository;
import com.whtl.antipn.repositories.RestaurantRepository;
import com.whtl.antipn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.now;

@Service
public class RestaurantsAndMenuServiceImpl implements RestaurantAndMenuService {

    MenuRepository menuRepository;
    RestaurantRepository restaurantRepository;
    UserRepository userRepository;


    @Autowired
    public RestaurantsAndMenuServiceImpl(MenuRepository menuRepository,
                                         RestaurantRepository restaurantRepository,
                                         UserRepository userRepository
    ) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<RestaurantDto> findAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) {
            throw new EntityNotFoundException("RestaurantDto", 0, "There are not restaurants");
        }
        return RestaurantMapper.RESTAURANT_MAPPER.toDtoList(restaurants);
    }

    @Override
    public RestaurantDto findRestaurantById(int restId) {
        Optional<Restaurant> restaurantById = restaurantRepository.findById(restId);

        if (restaurantById.isEmpty()) {
            throw new EntityNotFoundException("RestaurantDto", 0, "There is no restaurant with id = " + restId);
        }
        return RestaurantMapper.RESTAURANT_MAPPER.toDto(restaurantById.get());
    }

    @Transactional
    @Override
    public RestaurantDto updateRestaurant(RestaurantDto restaurantDto) {

        if (restaurantDto.getId() == null) {
            throw new EntityNotFoundException("RestaurantDto", 0, "There is no restaurant with null id");
        }
        Optional<Restaurant> restaurantForUpdate = restaurantRepository.findById(restaurantDto.getId());
        if (restaurantForUpdate.isPresent()) {
            Restaurant entity = RestaurantMapper.RESTAURANT_MAPPER.toEntity(restaurantDto);
            restaurantRepository.save(entity);
        } else {
            throw new EntityNotFoundException("RestaurantDto", restaurantDto.getId(), "There is no restaurant with id = " + restaurantDto.getId());
        }

        return restaurantDto;
    }

    @Transactional
    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        Restaurant entity = RestaurantMapper.RESTAURANT_MAPPER.toEntity(restaurantDto);
        restaurantRepository.save(entity);
        return restaurantDto;
    }

    @Override
    public Restaurant findRestaurantByName(String restName) {
        return restaurantRepository.findRestaurantByName(restName);
    }

    @Transactional
    @Override
    public void deleteRestaurant(int restId) {
        try {
            restaurantRepository.deleteById(restId);
        } catch (Exception e) {
            throw new EntityNotFoundException("RestaurantDto", restId, "There is no restaurant with id = " + restId);
        }

    }

    //MENU

    @Override
    public List<MenuDto> findAllMenuOnDate(LocalDate localDate) {
        if (localDate == null) {
            localDate = now();
        }
        return MenuMapper.MENU_MAPPER.toDtoList(menuRepository.findAllByDate(localDate));
    }


    @Override
    public List<MenuDto> findMenuOnDate(int restId, LocalDate localDate) {
        if (localDate == null) {
            localDate = now();
        }
        findRestaurantById(restId);
        return MenuMapper.MENU_MAPPER.toDtoList(menuRepository.findMenuByRestaurantAndDate(restId, localDate));
    }

    @Transactional
    @Override
    public List<MenuDto> saveMenu(int restId, LocalDate localDate, List<IncomeMenuDto> incomeMenuDtoList) {
        findRestaurantById(restId);
        if (!findMenuOnDate(restId, localDate).isEmpty()) {
            throw new EntityAlreadyExistsException("MenuDto", "Menu on date " + localDate + " already exists for restaurant " + restId);
        }
        List<MenuDto> result = new ArrayList<>();

        for (IncomeMenuDto income : incomeMenuDtoList) {
            MenuDto menu = MenuMapper.MENU_MAPPER.incomeDtoToMenuDto(income, restId, localDate);
            result.add(menu);
            menuRepository.save(MenuMapper.MENU_MAPPER.toEntity(menu));
        }
        return result;
    }

    @Transactional
    @Override
    public List<MenuDto> updateMenu(int restId, LocalDate localDate, List<IncomeMenuDto> incomeMenuDtoList) {
        deleteMenu(restId, localDate);
        saveMenu(restId, localDate, incomeMenuDtoList);

        return incomeMenuDtoList.stream()
                .map(item -> MenuMapper.MENU_MAPPER.incomeDtoToMenuDto(item, restId, localDate))
                .toList();
    }

    @Transactional
    @Override
    public void deleteMenu(int restId, LocalDate localDate) {
        findRestaurantById(restId);
        menuRepository.deleteMenusByRestaurantAndDate(restId, localDate);
    }
}
