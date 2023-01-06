package com.whtl.antipn.services;

import com.whtl.antipn.dto.VoteDto;
import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.exception.VoteIsNotAllowedException;
import com.whtl.antipn.mapper.RestaurantMapper;
import com.whtl.antipn.mapper.VoteMapper;
import com.whtl.antipn.model.Vote;
import com.whtl.antipn.repositories.UserRepository;
import com.whtl.antipn.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class VotingServiceImpl implements VotingService {

    private final LocalTime ACCEPT_VOTE_TIME = LocalTime.of(23, 00);
    VoteRepository voteRepository;
    UserRepository userRepository;
    RestaurantAndMenuService restaurantAndMenuService;

    @Autowired
    public VotingServiceImpl(VoteRepository voteRepository,
                             UserRepository userRepository,
                             RestaurantAndMenuService restaurantAndMenuService) {

        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantAndMenuService = restaurantAndMenuService;
    }

    public VoteDto findVote(int userId) {
        Optional<Vote> vote = voteRepository.findVoteByDateAndUserId(LocalDate.now(), userId);

        if (vote.isEmpty()) {
            throw new EntityNotFoundException("VoteDto", 0, "There is not vote for user with id = " + userId + " on date " +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        return VoteMapper.VOTE_MAPPER.toDto(vote.get());
    }

    public VoteDto saveVote(int userId, int restId) {
        Optional<Vote> voteInDb = voteRepository.findVoteByDateAndUserId(LocalDate.now(), userId);
        if (LocalTime.now().isBefore(ACCEPT_VOTE_TIME) || (voteInDb.isEmpty())) { //если не голосовал и еще есть для этого время
            restaurantAndMenuService.findRestaurantById(restId);
            //if we are updating vote
            voteInDb.ifPresent(vote -> voteRepository.delete(vote));
            Vote voteForSaving = new Vote();
            voteForSaving.setRestaurant(RestaurantMapper.RESTAURANT_MAPPER.toEntity(restaurantAndMenuService.findRestaurantById(restId)));
            voteForSaving.setDate(LocalDate.now());
            voteForSaving.setUser(userRepository.getReferenceById(userId));
            voteRepository.save(voteForSaving);
        } else {
            throw new VoteIsNotAllowedException(userId, "The time for voting is over");
        }

        return VoteMapper.VOTE_MAPPER.toDto((voteRepository.findVoteByDateAndUserId(LocalDate.now(), userId).get()));
    }

    public VoteDto updateVote(int userId, int restId) {
        saveVote(userId, restId);

        return VoteMapper.VOTE_MAPPER.toDto((voteRepository.findVoteByDateAndUserId(LocalDate.now(), userId)).get());
    }

    public void deleteVote(int userId) {
        Optional<Vote> vote = voteRepository.findVoteByDateAndUserId(LocalDate.now(), userId);

        if (LocalTime.now().isBefore(ACCEPT_VOTE_TIME) || vote.isEmpty()) {
            if (vote.isPresent()) {
                voteRepository.delete(vote.get());
            } else {
                throw new EntityNotFoundException("VoteDto", 0, "There is no vote for user with id " + userId);
            }
        } else {
            throw new VoteIsNotAllowedException(userId, "The time for voting is over");
        }
    }
}

