package com.whtl.antipn.mapper;

import com.whtl.antipn.dto.RestaurantDto;
import com.whtl.antipn.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantMapper RESTAURANT_MAPPER = Mappers.getMapper(RestaurantMapper.class);

    RestaurantDto toDto(Restaurant restaurant);

    Restaurant toEntity(RestaurantDto restaurantDto);

    List<RestaurantDto> toDtoList(List<Restaurant> listRestaurant);
}
