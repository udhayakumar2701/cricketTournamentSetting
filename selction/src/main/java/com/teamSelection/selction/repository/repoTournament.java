package com.teamSelection.selction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teamSelection.selction.database.Tournament;




@Repository
public interface repoTournament extends JpaRepository<Tournament,Long> {

    Tournament findByTournamentAndDate(String tournamentName, String date);

    List<Tournament> findByTournament(String tournamentName);

    
}