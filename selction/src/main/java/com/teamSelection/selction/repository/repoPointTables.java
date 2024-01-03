package com.teamSelection.selction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teamSelection.selction.database.pointTables;

@Repository
public interface repoPointTables extends JpaRepository<pointTables,Long>{

    pointTables findByTeamAndTournament(String team, long tournament_id);

    List<pointTables> findAllByTournament(long id);

  

    
}