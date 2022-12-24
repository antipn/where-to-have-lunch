package com.whtl.antipn.services;

import com.whtl.antipn.dto.VoteDto;
import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.exception.VoteIsNotAllowedException;
import com.whtl.antipn.mapper.VoteMapper;
import com.whtl.antipn.model.Vote;
import com.whtl.antipn.repositories.RestaurantRepository;
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

    private final LocalTime acceptanceVoteTime = LocalTime.of(11, 0);

    VoteRepository voteRepository;
    UserRepository userRepository;
    RestaurantRepository restaurantRepository;
    VoteMapper voteMapper;

    @Autowired
    public VotingServiceImpl(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteMapper = voteMapper;
    }

    public VoteDto findVote(int userId) {
        Optional<Vote> vote = voteRepository.findVoteByDateAndUserId(LocalDate.now(), userId);

        if (vote.isEmpty()) {
            throw new EntityNotFoundException("VoteDto", 0, "There is not vote for user with id = " + userId + " on date " +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        return voteMapper.toDto(vote.get());
    }

    public VoteDto saveVote(int userId, int restId) {
        // Vote usersVoteToday = repository.findVote(userId);
        //if ((usersVoteToday == null) || (LocalTime.now().isBefore(LocalTime.of(11, 0)))) {
        Optional<Vote> voteInDb = voteRepository.findVoteByDateAndUserId(LocalDate.now(), userId);


        if (LocalTime.now().isBefore(acceptanceVoteTime)
                || (voteInDb.isEmpty())) {

            if (voteInDb.isPresent()) { //if we are updating vote
                voteRepository.delete(voteInDb.get());
            }
            Vote voteForSaving = new Vote();
            System.out.println("Входные данные " + userId + " / " + restId);
            System.out.println("Ресторан для сохранения" + restaurantRepository.getReferenceById(restId));
            voteForSaving.setRestaurant(restaurantRepository.getReferenceById(restId));
            voteForSaving.setDate(LocalDate.now());
            System.out.println("Пользователь для сохранения" + userRepository.getReferenceById(userId));
            voteForSaving.setUser(userRepository.getReferenceById(userId));
            voteRepository.save(voteForSaving);
        } else {
            throw new VoteIsNotAllowedException(userId, "The time for voting is over");
        }
        System.out.println("Голосование прошло успешно");
        return voteMapper.toDto((voteRepository.findVoteByDateAndUserId(LocalDate.now(), userId).get()));
    }

    public VoteDto updateVote(int userId, int restId) {
        saveVote(userId, restId);
        System.out.println("Обновление голоса прошло успешно");
        return voteMapper.toDto((voteRepository.findVoteByDateAndUserId(LocalDate.now(), userId)).get());
    }

    public void deleteVote(int userId) {
        Optional<Vote> vote = voteRepository.findVoteByDateAndUserId(LocalDate.now(), userId);

        if (LocalTime.now().isBefore(acceptanceVoteTime)) {
            if (vote.isPresent()) {
                voteRepository.delete(vote.get()); //discuss deleting and checkNotFoundWithId //ValidationUtil.checkNotFoundWithId(
            } else {
                throw new EntityNotFoundException("VoteDto", 0, "There is no vote for user with id " + userId);
            }
        } else {
            throw new VoteIsNotAllowedException(userId, "The time for voting is over");
        }
    }
}

