package com.whtl.antipn.services;

import com.whtl.antipn.dto.MenuDto;
import com.whtl.antipn.dto.RestaurantDto;
import com.whtl.antipn.dto.RestaurantScoreDto;
import com.whtl.antipn.exception.EntityAlreadyExistsException;
import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.exception.NotFoundException;
import com.whtl.antipn.exception.SaveRestNotAllowedException;
import com.whtl.antipn.mapper.MenuMapper;
import com.whtl.antipn.mapper.RestaurantMapper;
import com.whtl.antipn.mapper.RestaurantScoreMapper;
import com.whtl.antipn.model.Menu;
import com.whtl.antipn.model.Restaurant;
import com.whtl.antipn.repositories.MenuRepository;
import com.whtl.antipn.repositories.RestaurantRepository;
import com.whtl.antipn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.now;

@Service
public class RestaurantsAndMenuServiceImpl implements RestaurantAndMenuService {

    MenuRepository menuRepository;
    RestaurantRepository restaurantRepository;
    UserRepository userRepository;

    RestaurantScoreMapper restaurantScoreMapper;

    @Autowired
    public RestaurantsAndMenuServiceImpl(MenuRepository menuRepository,
                                         RestaurantRepository restaurantRepository,
                                         UserRepository userRepository,
                                         RestaurantScoreMapper restaurantScoreMapper) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.restaurantScoreMapper = restaurantScoreMapper;
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
        System.out.println("Обновляем информацию о ресторане");
        //проверяем есть ли такой ресторан чтобы его обновить

        if (restaurantDto.getId() == null) {
            throw new EntityNotFoundException("RestaurantDto", 0, "There is no restaurant with null id");
        }

        Optional<Restaurant> restaurantForUpdate = restaurantRepository.findById(restaurantDto.getId());

        if (restaurantForUpdate.isPresent()) {
            //если ресторан найден то значит его можно обновить
            Restaurant entity = RestaurantMapper.RESTAURANT_MAPPER.toEntity(restaurantDto);
            try {
                restaurantRepository.save(entity);
            } catch (DataIntegrityViolationException e) {
                System.out.println("Невозможно обновить ресторан, такие данные уже есть в системе");
                throw new EntityAlreadyExistsException("RestaurantDto", "Can not update Such restaurant already exists " + restaurantDto);
            }
        } else {
            throw new EntityNotFoundException("RestaurantDto", restaurantDto.getId(), "There is no restaurant with id = " + restaurantDto.getId());
        }
//
//
//        Restaurant entity = RestaurantMapper.RESTAURANT_MAPPER.toEntity(restaurantDto);
//        restaurantRepository.save(entity);
        return restaurantDto;
    }

    @Transactional
    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        System.out.println("Пытаемся сохранить новый ресторан");

        if (restaurantDto.getId() == null) {//новый ресторан пришел без id! значит его можно сохранить
            System.out.println("Проверили что такого ресторана без id");
            Restaurant entity = RestaurantMapper.RESTAURANT_MAPPER.toEntity(restaurantDto);
            try {
                restaurantRepository.save(entity);
                System.out.println("Сохранили реcторан");
                return RestaurantMapper.RESTAURANT_MAPPER.toDto(entity);
            } catch (DataIntegrityViolationException e) {
                System.out.println("такой ресторан с таким названием и адресом уже есть в системе");
                throw new EntityAlreadyExistsException("RestaurantDto", "Such restaurant already exists");
            }
        } else {
            System.out.println("выкинуть ошибку что мы не сохраняем рестораны с id, только обновляем");
            throw new SaveRestNotAllowedException("Prohibited to save restaurants with ID, please exlude ID from request");
        }

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
        LocalDate localDate = now();
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
    public List<MenuDto> findMenuOnDate(int restId, LocalDate localDate) {

        if (localDate == null) {
            localDate = now();
        }
        findRestaurantById(restId);//checking that rest exists
        return MenuMapper.MENU_MAPPER.toDtoList(menuRepository.findMenuByRestaurantAndDate(restId, localDate));
    }

    @Transactional
    @Override
    public List<MenuDto> saveMenu(int restId, List<MenuDto> menuInput) {
        findRestaurantById(restId);
        for (MenuDto input : menuInput) {
            try {
                if (input.getRestaurant() == restId) {
                    menuRepository.save(MenuMapper.MENU_MAPPER.toEntity(input));
                } else {

                    throw new SaveRestNotAllowedException("MenuDto and restaurant Id are not equal, check it");
                }
            } catch (DataIntegrityViolationException e) {
                throw new EntityAlreadyExistsException("MenuDto", "Can not update menu, such menu already exists " + menuInput);
            }
        }
        return menuInput;
    }

    @Override
    public void deleteMenu(LocalDate localDate, int restId) {
        System.out.println("Удаляем меню по ресторану + по дате ");
        menuRepository.deleteMenuByRestaurantAndDate(restId, localDate);
    }

    @Override
    public List<MenuDto> updateMenu(LocalDate localDate, int restId, List<MenuDto> menuDtoList) {
        return null;
    }
}
