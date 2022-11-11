package com.whtl.antipn.mapper;

import com.whtl.antipn.dto.RestaurantScoreDto;
import com.whtl.antipn.model.Restaurant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantScoreMapperDeleteLater {
    RestaurantScoreDto toDto(Restaurant restaurant);
}
