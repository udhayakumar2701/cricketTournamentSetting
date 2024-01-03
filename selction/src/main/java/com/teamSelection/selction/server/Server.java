package com.teamSelection.selction.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamSelection.selction.database.Tournament;
import com.teamSelection.selction.database.pointTables;
import com.teamSelection.selction.database.teamsDatabase;
import com.teamSelection.selction.repository.repoPointTables;
import com.teamSelection.selction.repository.repoTeams;
import com.teamSelection.selction.repository.repoTournament;

@Service
public class Server {
    
//instance for the RepoPointTables interface
@Autowired
repoPointTables repoPoint;


//instance for the RepoTeams interface
@Autowired
repoTeams   repoteam;


//instance for the RepoTournament interface
@Autowired
repoTournament repoTourna;


public Tournament findByTournamentAndDate(String tournamentName, String date) {
    return repoTourna.findByTournamentAndDate(tournamentName,date);
}


public List<Tournament> findBYTournament(String tournamentName) {
    return  repoTourna.findByTournament(tournamentName);
}




public void saveTournament(Tournament db) {
    repoTourna.save(db);
}


public void savePointTables(pointTables pointTablesDb) {
    repoPoint.save(pointTablesDb);
}




public void saveteamsDatabase(teamsDatabase n) {
   repoteam.save(n);  
}


public teamsDatabase findByTeamsandTournamentId(String team,String opp_teams, Long Tournament_id, String round) {
 return  repoteam.findByTeamAndOppteamsAndTournamentIdAndRound(team,opp_teams, Tournament_id,round);
//return new teamsDatabase();
}


public pointTables findByTeamAndTournamnetId(String team, long tournament_id) {
    return repoPoint.findByTeamAndTournament(team,tournament_id);
}


public List<pointTables> findBYTourn(long id) {
    return repoPoint.findAllByTournament(id);
}


public int countTheTeamsScheduleList() {
    return (int)repoteam.count();
}


public List<teamsDatabase> findAllOFTeamsDatabase(long id) {
    return repoteam.findAllByTournamentId(id);
}


public List<teamsDatabase> findByAllSemiWinner(String round, long id) {
    return repoteam.findAllByTournamentIdAndRound(id,round);
}




}
