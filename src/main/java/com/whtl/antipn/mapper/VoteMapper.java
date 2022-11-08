package com.whtl.antipn.mapper;

import com.whtl.antipn.dto.VoteDto;
import com.whtl.antipn.model.Vote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    VoteDto toDto(Vote vote);

    Vote toEntity(VoteDto voteDto);
}
