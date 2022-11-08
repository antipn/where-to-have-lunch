package com.whtl.antipn.mapper;

import com.whtl.antipn.dto.RestaurantDto;
import com.whtl.antipn.model.Restaurant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantDto toDto(Restaurant restaurant);
    Restaurant toEntity(RestaurantDto restaurantDto);

    List<RestaurantDto> toDtoList (List<Restaurant> listRestaurant);
}
