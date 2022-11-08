package com.whtl.antipn.services;

import com.whtl.antipn.dto.VoteDto;

public interface VotingService {
    public VoteDto findVote(int userId);

    public VoteDto saveVote(int userId, VoteDto voteDto);

    public VoteDto updateVote(int userId, VoteDto voteDto);

    public void deleteVote(int userId);
}
