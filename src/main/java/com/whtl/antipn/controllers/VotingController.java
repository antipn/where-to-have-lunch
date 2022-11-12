package com.whtl.antipn.controllers;

import com.whtl.antipn.dto.VoteDto;
import com.whtl.antipn.exception.VoteIsNotAllowedResponse;
import com.whtl.antipn.dto.input.VoteDtoIncome;
import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.exception.VoteIsNotAllowedException;
import com.whtl.antipn.services.VotingServiceImpl;
import com.whtl.antipn.exception.EntityNotFoundResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Vote controller", description = "The responsibilities of Vote controller are providing voting API: " +
        "to vote for the restaurant, " +
        "getting know user's vote, " +
        "deleting user's vote")
@RestController
public class VotingController {
    private final VotingServiceImpl votingService;

    @Autowired
    public VotingController(VotingServiceImpl votingService) {
        this.votingService = votingService;
    }

    //vote
@Operation(
        summary = "The providing possibility for user to vote for some restaurant",
        description="It allows user to vote for restaurant until 11 o'clock"
)
    @PostMapping("/api/v1/vote")
    public ResponseEntity<VoteDto> createVote(@RequestBody @Parameter(description = "The vote for restaurant by json",required = true) VoteDtoIncome voteDtoIncome) {
        int userId = 1;// please get userId after applying spring security
        VoteDto voteDto = votingService.saveVote(userId, voteDtoIncome);
        if (voteDto != null && voteDtoIncome.getRestaurantId() == voteDto.getRestaurantId()) {
            return ResponseEntity.ok(voteDto);
        } else {
            throw new VoteIsNotAllowedException(userId, "The vote can not be processed due to time for voting is over");
        }
    }
    @Operation(
            summary = "The providing possibility getting to know what for restaurant voted user",
            description="It allows user to get to know his vote"
    )
    @GetMapping("/api/v1/vote")
    public ResponseEntity<VoteDto> readVote() {
        int userId = 1;// // please get userId after applying spring security
        return ResponseEntity.ok(votingService.findVote(userId));
    }

    @Operation(
            summary = "The providing possibility update his vote",
            description="It allows user to update his vote until 11 o'clock"
    )
    @PutMapping("/api/v1/vote")
    public ResponseEntity<VoteDto> updateVote(@RequestBody @Parameter(description = "The vote for the restaurant", required = true) VoteDtoIncome voteDtoIncome) {
        int userId = 1;// please get userId after applying spring security
        VoteDto voteDto = votingService.updateVote(userId, voteDtoIncome);
        if (voteDto != null) {
            return ResponseEntity.ok(voteDto);
        } else {
            throw new VoteIsNotAllowedException(userId, "The vote can not  be processed due to time for voting is over");
        }
    }
    @Operation(
            summary = "The providing possibility delete his vote",
            description="It allows user to delete his vote until 11 o'clock"
    )
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
