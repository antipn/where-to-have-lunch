package com.whtl.antipn.services;

import com.whtl.antipn.dto.VoteDto;
import org.springframework.stereotype.Service;

@Service
public class VotingServiceImpl implements VotingService{
    public VoteDto findVote(int userId) {
        //check the vote of user
        //find vote of user in repo
        return null;
    }

    public VoteDto saveVote(int userId, VoteDto voteDto) {
        //user is voting
        //save vote of user in repo
        return null;
    }

    public VoteDto updateVote(int userId, VoteDto voteDto) {
        //some logic about chance to re-vote
        //if user did not vote -> this is possible in any time today...
        //if user have voted -> revote only untill 11
        //store vote of user with date (date-> today always, there is no vote in past or future)
        return null;
    }

    public void deleteVote(int userId) {
        //delete vote if possible
        //chance to delete only if time < 11, after 11 no chance
    }
}
