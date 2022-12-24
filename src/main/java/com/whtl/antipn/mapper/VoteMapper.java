package com.whtl.antipn.mapper;

import com.whtl.antipn.dto.VoteDto;
import com.whtl.antipn.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") //mapping doest work well due to difference in field's type integer vs User
public interface VoteMapper {

    VoteMapper VOTE_MAPPER = Mappers.getMapper(VoteMapper.class);

    VoteDto toDto(Vote vote);

    Vote toEntity(VoteDto voteDto);
}
