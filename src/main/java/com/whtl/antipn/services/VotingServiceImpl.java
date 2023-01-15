package com.whtl.antipn.services;

import com.whtl.antipn.dto.VoteDto;
import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.exception.VoteIsNotAllowedException;
import com.whtl.antipn.mapper.RestaurantMapper;
import com.whtl.antipn.mapper.VoteMapper;
import com.whtl.antipn.model.Vote;
import com.whtl.antipn.repositories.RestaurantRepository;
import com.whtl.antipn.repositories.UserRepository;
import com.whtl.antipn.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class VotingServiceImpl implements VotingService {

    private final LocalTime ACCEPT_VOTE_TIME = LocalTime.of(23, 55);
    VoteRepository voteRepository;
    UserRepository userRepository;
    RestaurantAndMenuService restaurantAndMenuService;
    RestaurantRepository restaurantRepository;

    @Autowired
    public VotingServiceImpl(VoteRepository voteRepository,
                             UserRepository userRepository,
                             RestaurantAndMenuService restaurantAndMenuService,
                             RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantAndMenuService = restaurantAndMenuService;
        this.restaurantRepository = restaurantRepository;
    }

    public VoteDto findVote(int userId) {
        Optional<Vote> vote = voteRepository.findVoteByDateAndUserId(LocalDate.now(), userId);

        if (vote.isEmpty()) {
            throw new EntityNotFoundException("VoteDto", 0, "There is not vote for user with id = " + userId + " on date " +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        return VoteMapper.VOTE_MAPPER.toDto(vote.get());
    }

    @Transactional
    public VoteDto saveVote(int userId, int restId) {
        Optional<Vote> voteInDb = voteRepository.findVoteByDateAndUserId(LocalDate.now(), userId);

        if (voteInDb.isEmpty()) {

            if (LocalTime.now().isBefore(ACCEPT_VOTE_TIME)) {
                restaurantAndMenuService.findRestaurantById(restId);
                Vote voteForSaving = new Vote();
                voteForSaving.setRestaurant(RestaurantMapper.RESTAURANT_MAPPER.toEntity(restaurantAndMenuService.findRestaurantById(restId)));
                voteForSaving.setDate(LocalDate.now());
                voteForSaving.setUser(userRepository.getReferenceById(userId));
                return VoteMapper.VOTE_MAPPER.toDto(voteRepository.save(voteForSaving));

            } else {
                throw new VoteIsNotAllowedException(userId, "The time for voting is over");
            }
        } else {
            return updateVote(userId, restId);
        }
    }

    @Transactional
    public VoteDto updateVote(int userId, int restId) {
        restaurantAndMenuService.findRestaurantById(restId);
        Optional<Vote> voteInDb = voteRepository.findVoteByDateAndUserId(LocalDate.now(), userId);
        if (voteInDb.isPresent() && voteInDb.get().getUser().getId() == userId) {
            if (LocalTime.now().isBefore(ACCEPT_VOTE_TIME)) {
                voteInDb.get().setRestaurant(restaurantRepository.getReferenceById(restId));
                voteRepository.save(voteInDb.get());
                return VoteMapper.VOTE_MAPPER.toDto(voteInDb.get());

            } else {
                throw new VoteIsNotAllowedException(userId, "The time for voting is over");
            }
        } else {
            return saveVote(userId, restId);
            // or we can call exception to ask to vote instead updating
            //throw new EntityNotFoundException("VoteDto", 0, "You can not update vote cause you did not vote yet");
        }
    }

    @Transactional
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

