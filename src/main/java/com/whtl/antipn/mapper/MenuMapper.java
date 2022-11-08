package com.whtl.antipn.mapper;

import com.whtl.antipn.dto.MenuDto;
import com.whtl.antipn.dto.RestaurantDto;
import com.whtl.antipn.model.Menu;
import com.whtl.antipn.model.Restaurant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper (componentModel = "spring")
public interface MenuMapper {
    MenuDto toDto(Menu menu);
    Menu toEntity(MenuDto menuDto);

    List<MenuDto> toDtoList (List<Menu> menu);

    List<Menu> toEntityList(List<MenuDto> menuDtoList);
}