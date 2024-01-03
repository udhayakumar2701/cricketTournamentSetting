package com.teamSelection.selction.database;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tournament")
public class Tournament {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String tournament ;

    
    String date;

}
