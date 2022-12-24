package com.whtl.antipn.services;

import com.whtl.antipn.dto.VoteDto;
import org.springframework.stereotype.Service;

@Service
public interface VotingService {
    public VoteDto findVote(int userId);

    public VoteDto saveVote(int userId, int vote);

    public VoteDto updateVote(int userId, int vote);

    public void deleteVote(int userId);
}
