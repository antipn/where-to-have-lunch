package controllers;

import dto.EntityNotFoundResponse;
import dto.VoteDto;
import exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VotingController {

    //get to know vote
    @RequestMapping(value = "/api/v1/vote", method = RequestMethod.GET)
    //@GetMapping("/api/v1/vote")
    public ResponseEntity<VoteDto> getVote() {
        System.out.println("get vote");
        return ResponseEntity.ok(new VoteDto());
    }

    //to vote
    @RequestMapping(value = "/api/v1/vote", method = RequestMethod.POST)
    //@PostMapping("/api/v1/vote")
    public ResponseEntity<VoteDto> toVote(@RequestBody VoteDto voteDto) {
        System.out.println("send vote for rest");
        System.out.println("input json -> " + voteDto);
        return ResponseEntity.ok(new VoteDto());
    }

    @RequestMapping(value = "/api/v1/vote", method = RequestMethod.DELETE)
    //@DeleteMapping("/api/v1/vote")
    public void deleteVote() {
        System.out.println("deleting vote");
    }

    @RequestMapping(value = "/api/v1/vote", method = RequestMethod.PUT)
    //@PutMapping("/api/v1/vote")
    public ResponseEntity<VoteDto> updateVote(@RequestBody VoteDto voteDto) {
        System.out.println("some logic for checking time < or > 11 am");
        System.out.println("input json -> " + voteDto);
        System.out.println("updating vote if possible");
        return ResponseEntity.ok(new VoteDto());
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
