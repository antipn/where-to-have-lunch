package com.whtl.antipn.services;

import com.whtl.antipn.Utils.ValidationUtil;
import com.whtl.antipn.dto.VoteDto;
import com.whtl.antipn.dto.input.VoteDtoIncome;
import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.exception.VoteIsNotAllowedException;
import com.whtl.antipn.mapper.VoteMapper;
import com.whtl.antipn.model.Vote;
import com.whtl.antipn.repositories.InMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        Vote vote = repository.findVote(userId);
        if (vote == null) {
            throw new EntityNotFoundException("VoteDto", 0, "There is not vote for user with id = " + userId + " on date " +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        return voteMapper.toDto(repository.findVote(userId));
    }

    public VoteDto saveVote(int userId, int restId) {
        // Vote usersVoteToday = repository.findVote(userId);
        //if ((usersVoteToday == null) || (LocalTime.now().isBefore(LocalTime.of(11, 0)))) {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            repository.deleteVoteSpecial(repository.findVote(userId)); //used for updating the vote
            repository.saveVoteSpecial(new Vote(userId, restId));
        } else {
            throw new VoteIsNotAllowedException(userId, "The time for voting is over " + java.time.Duration.between(LocalTime.of(11, 0),
                    LocalDate.now()));
        }
        return voteMapper.toDto(repository.findVote(userId));
    }

    public VoteDto updateVote(int userId, int restId) {
        saveVote(userId, restId);
        return voteMapper.toDto(repository.findVote(userId));
    }

    public void deleteVote(int userId) {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            repository.deleteVoteSpecial(repository.findVote(userId)); //discuss deleting and checkNotFoundWithId //ValidationUtil.checkNotFoundWithId(
        } else {
            throw new VoteIsNotAllowedException(userId, "The time for voting is over" + java.time.Duration.between(LocalTime.of(11, 0),
                    LocalDate.now()));
        }
    }
}

