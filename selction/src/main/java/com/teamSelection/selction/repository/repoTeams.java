package com.teamSelection.selction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.teamSelection.selction.database.teamsDatabase;



@Repository
public interface repoTeams extends JpaRepository<teamsDatabase,Long>  {

    teamsDatabase findByTeamAndOppteamsAndTournamentIdAndRound(String team, String opp_teams, Long Tournament_id,String round);

    List<teamsDatabase> findAllByTournamentId(long id);

    List<teamsDatabase> findAllByTournamentIdAndRound(long id, String round);




}