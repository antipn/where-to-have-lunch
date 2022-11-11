package com.whtl.antipn.controllers;

import com.whtl.antipn.dto.VoteDto;
import com.whtl.antipn.exception.VoteIsNotAllowedResponse;
import com.whtl.antipn.dto.input.VoteDtoIncome;
import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.exception.VoteIsNotAllowedException;
import com.whtl.antipn.services.VotingServiceImpl;
import com.whtl.antipn.exception.EntityNotFoundResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VotingController {
    private final VotingServiceImpl votingService;

    @Autowired
    public VotingController(VotingServiceImpl votingService) {
        this.votingService = votingService;
    }

    //vote
    @PostMapping("/api/v1/vote")
    public ResponseEntity<VoteDto> createVote(@RequestBody VoteDtoIncome voteDtoIncome) {
        int userId = 1;// please get userId after applying spring security
        VoteDto voteDto = votingService.saveVote(userId, voteDtoIncome);
        if (voteDto != null && voteDtoIncome.getRestaurantId() == voteDto.getRestaurantId()) {
            return ResponseEntity.ok(voteDto);
        } else {
            throw new VoteIsNotAllowedException(userId, "The vote can not be processed due to time for voting is over");
        }
    }

    @GetMapping("/api/v1/vote")
    public ResponseEntity<VoteDto> readVote() {
        int userId = 1;// // please get userId after applying spring security
        return ResponseEntity.ok(votingService.findVote(userId));
    }

    @PutMapping("/api/v1/vote")
    public ResponseEntity<VoteDto> updateVote(@RequestBody VoteDtoIncome voteDtoIncome) {
        int userId = 1;// please get userId after applying spring security
        VoteDto voteDto = votingService.updateVote(userId, voteDtoIncome);
        if (voteDto != null) {
            return ResponseEntity.ok(voteDto);
        } else {
            throw new VoteIsNotAllowedException(userId, "The vote can not  be processed due to time for voting is over");
        }
    }

    @DeleteMapping("/api/v1/vote")
    public ResponseEntity<Void> deleteVote() {
        int userId = 1;// please get userId after applying spring security
        votingService.deleteVote(userId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<EntityNotFoundResponse> handleException(EntityNotFoundException ex) {
        EntityNotFoundResponse response = new EntityNotFoundResponse();
        response.setEntityName(ex.getEntityName());
        response.setEntityId(ex.getEntityId());
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<VoteIsNotAllowedResponse> handleException(VoteIsNotAllowedException ex) {
        VoteIsNotAllowedResponse response = new VoteIsNotAllowedResponse();
        response.setMessage(ex.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        response.setUserId(ex.getUserId());
        response.setStatus(HttpStatus.LOCKED.value());
        return new ResponseEntity<>(response, HttpStatus.LOCKED);
    }
}
