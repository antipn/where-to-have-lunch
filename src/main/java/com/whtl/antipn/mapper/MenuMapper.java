package com.whtl.antipn.mapper;

import com.whtl.antipn.dto.IncomeMenuDto;
import com.whtl.antipn.dto.MenuDto;
import com.whtl.antipn.model.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuMapper MENU_MAPPER = Mappers.getMapper(MenuMapper.class);

    MenuDto toDto(Menu menu);

    Menu toEntity(MenuDto menuDto);

    List<MenuDto> toDtoList(List<Menu> menu);

    List<Menu> toEntityList(List<MenuDto> menuDtoList);

    Menu incomeDtoToEntity(IncomeMenuDto incomeMenuDto);

    default MenuDto incomeDtoToMenuDto(IncomeMenuDto incomeMenuDto, Integer restId, LocalDate localDate) {
        MenuDto result = new MenuDto();
        result.setRestaurant(restId);
        result.setDate(localDate);
        result.setName(incomeMenuDto.getName());
        result.setPrice(incomeMenuDto.getPrice());
        return result;
    }


}
