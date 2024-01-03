package com.teamSelection.selction.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="mini-task-2-Matchs")
public class teamsDatabase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String team;
   
    private String oppteams;
    private String win_status;
    private String round;
    private int match_no;
   
    private Long tournamentId;
   

    
    
}
