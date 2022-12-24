//package com.whtl.antipn.services.inmemory;
//
//import com.whtl.antipn.dto.VoteDto;
//import com.whtl.antipn.exception.EntityNotFoundException;
//import com.whtl.antipn.exception.VoteIsNotAllowedException;
//import com.whtl.antipn.mapper.VoteMapper;
//import com.whtl.antipn.model.Vote;
//import com.whtl.antipn.repositories.inmemory.InMemoryRepository;
//import com.whtl.antipn.services.VotingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
//@Service
//public class VotingServiceImplInMemoryRepo implements VotingService {
//
//    LocalTime acceptanceVoteTime = LocalTime.of(11, 0);
//    InMemoryRepository repository;
//    VoteMapper voteMapper;
//
//    @Autowired
//    public VotingServiceImplInMemoryRepo(InMemoryRepository repository, VoteMapper voteMapper) {
//        this.repository = repository;
//        this.voteMapper = voteMapper;
//    }
//
//    public VoteDto findVote(int userId) {
//        Vote vote = repository.findVote(userId);
//        if (vote == null) {
//            throw new EntityNotFoundException("VoteDto", 0, "There is not vote for user with id = " + userId + " on date " +
//                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        }
//        return voteMapper.toDto(repository.findVote(userId));
//    }
//
//    public VoteDto saveVote(int userId, int restId) {
//        // Vote usersVoteToday = repository.findVote(userId);
//        //if ((usersVoteToday == null) || (LocalTime.now().isBefore(LocalTime.of(11, 0)))) {
//        if (LocalTime.now().isBefore(acceptanceVoteTime)
//                || (repository.findVote(userId) == null)) {
//            repository.deleteVoteSpecial(repository.findVote(userId)); //used for updating the vote
//            Vote inputedVote = new Vote();
//            inputedVote.setRestaurant(repository.findRestaurantById(restId));
//            inputedVote.setDate(LocalDate.now());
//            inputedVote.setUser(repository.findUserById(userId));
//            repository.saveVoteSpecial(inputedVote);
//        } else {
//            throw new VoteIsNotAllowedException(userId, "The time for voting is over");
//        }
//        return voteMapper.toDto(repository.findVote(userId));
//    }
//
//    public VoteDto updateVote(int userId, int restId) {
//        saveVote(userId, restId);
//        return voteMapper.toDto(repository.findVote(userId));
//    }
//
//    public void deleteVote(int userId) {
//        Vote vote = repository.findVote(userId);
//
//        if (LocalTime.now().isBefore(acceptanceVoteTime)) {
//            if (vote != null) {
//                repository.deleteVoteSpecial(repository.findVote(userId)); //discuss deleting and checkNotFoundWithId //ValidationUtil.checkNotFoundWithId(
//            } else {
//                throw new EntityNotFoundException("VoteDto", 0, "There is no vote for user with id " + userId);
//            }
//        } else {
//            throw new VoteIsNotAllowedException(userId, "The time for voting is over");
//        }
//    }
//}
//
