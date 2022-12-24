package com.whtl.antipn.mapper;

import com.whtl.antipn.dto.RestaurantScoreDto;
//import com.whtl.antipn.repositories.inmemory.InMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RestaurantScoreMapper {

    //InMemoryRepository repository;

//    @Autowired
//    public RestaurantScoreMapper(InMemoryRepository repository) {
//        this.repository = repository;
//    }

    public List<RestaurantScoreDto> toDto(Map<Integer, Integer> input) { // map -> restId,restId repeats
//        List<RestaurantScoreDto> result = new ArrayList<>();
//        for (Map.Entry<Integer, Integer> entry : input.entrySet()) {
//
//            result.add(new RestaurantScoreDto(entry.getKey(), repository.findRestaurantById(entry.getKey()).getName(), entry.getValue()));
//        }
//
//        return result;
        return null;
    }
}
