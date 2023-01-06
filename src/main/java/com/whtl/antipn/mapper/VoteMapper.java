package com.whtl.antipn.mapper;

import com.whtl.antipn.dto.VoteDto;
import com.whtl.antipn.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    VoteMapper VOTE_MAPPER = Mappers.getMapper(VoteMapper.class);

    default VoteDto toDto(Vote vote) {
        VoteDto result = new VoteDto();
        result.setRestaurant(vote.getRestaurant().getId());
        result.setUser(vote.getUser().getId());

        return result;
    }
}
