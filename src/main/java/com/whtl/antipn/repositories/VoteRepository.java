package com.whtl.antipn.repositories;

import com.whtl.antipn.model.User;
import com.whtl.antipn.model.Vote;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Integer> {


    @Query("SELECT  v FROM Vote v WHERE v.date= :localDate AND v.user.id= :userId")
    Optional<Vote> findVoteByDateAndUserId(@Param("localDate") LocalDate localDate, @Param("userId") Integer userId);
}
