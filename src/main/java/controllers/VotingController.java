package controllers;

import dto.EntityNotFoundResponse;
import dto.VoteDto;
import exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.VotingServiceImpl;

@RestController
public class VotingController {
    private final VotingServiceImpl votingService;

    @Autowired
    public VotingController(VotingServiceImpl votingService) {
        this.votingService = votingService;
    }

    //vote
    @PostMapping("/api/v1/vote")
    public ResponseEntity<VoteDto> createVote(@RequestBody VoteDto voteDto) {
        System.out.println("send vote for rest");
        System.out.println("input json -> " + voteDto);
        Integer userId = 2222;// get user id here
        return ResponseEntity.ok(votingService.saveVote(userId, voteDto));
    }

    @GetMapping("/api/v1/vote")
    public ResponseEntity<VoteDto> readVote() {
        System.out.println("get vote");
        Integer userId = 2222;// get user id here
        return ResponseEntity.ok(votingService.findVote(userId));
    }

    @PutMapping("/api/v1/vote")
    public ResponseEntity<VoteDto> updateVote(@RequestBody VoteDto voteDto) {
        System.out.println("some logic for checking time < or > 11 am will be processed in service layer");
        System.out.println("input json -> " + voteDto);
        int userId = 22222;// get user id here
        return ResponseEntity.ok(votingService.updateVote(userId, voteDto));
    }

    @DeleteMapping("/api/v1/vote")
    public ResponseEntity<Void> deleteVote() {
        System.out.println("deleting vote");
        int userId = 22222;// get user id here
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

}
