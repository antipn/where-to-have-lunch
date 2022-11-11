package com.whtl.antipn.services;

import com.whtl.antipn.dto.VoteDto;
import com.whtl.antipn.dto.input.VoteDtoIncome;

public interface VotingService {
    public VoteDto findVote(int userId);

    public VoteDto saveVote(int userId, VoteDtoIncome voteDtoIncome);

    public VoteDto updateVote(int userId, VoteDtoIncome voteDtoIncome);

    public void deleteVote(int userId);
}
