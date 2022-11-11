package com.whtl.antipn.services;

import com.whtl.antipn.Utils.ValidationUtil;
import com.whtl.antipn.dto.VoteDto;
import com.whtl.antipn.dto.input.VoteDtoIncome;
import com.whtl.antipn.exception.VoteIsNotAllowedException;
import com.whtl.antipn.mapper.VoteMapper;
import com.whtl.antipn.model.Vote;
import com.whtl.antipn.repositories.InMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Service
public class VotingServiceImpl implements VotingService {

    InMemoryRepository repository;
    VoteMapper voteMapper;

    @Autowired
    public VotingServiceImpl(InMemoryRepository repository, VoteMapper voteMapper) {
        this.repository = repository;
        this.voteMapper = voteMapper;
    }

    public VoteDto findVote(int userId) {
        return voteMapper.toDto(repository.findVote(userId));
    }

    public VoteDto saveVote(int userId, VoteDtoIncome voteDtoIncome) { //we use 01-11-2022 date
        Vote vote = new Vote(userId, voteDtoIncome.getRestaurantId());
        Vote usersVoteToday = repository.findVote(userId);

        if ((usersVoteToday == null) || (LocalTime.now().isBefore(LocalTime.of(11, 0)))) {
            repository.deleteVoteSpecial(usersVoteToday);
            repository.saveVoteSpecial(vote);
        } else {
            throw new VoteIsNotAllowedException(userId, "The time for voting is over " + java.time.Duration.between(LocalTime.of(11, 00), LocalTime.now())); //discuss where to throw Exception
        }
        return voteMapper.toDto(repository.findVote(userId));
    }

    public VoteDto updateVote(int userId, VoteDtoIncome voteDtoIncome) {
        saveVote(userId, voteDtoIncome);
        return voteMapper.toDto(repository.findVote(userId));
    }

    public void deleteVote(int userId) {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            repository.deleteVoteSpecial(repository.findVote(userId)); //discuss deleting and checkNotFoundWithId //ValidationUtil.checkNotFoundWithId(
        } else {
            throw new VoteIsNotAllowedException(userId, "The time for voting is over" + java.time.Duration.between(LocalTime.of(11, 00), LocalTime.now())); //discuss where to throw Exception
        }
    }
}
