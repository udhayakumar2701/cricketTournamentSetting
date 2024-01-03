package com.teamSelection.selction.database;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="PointTables")
public class pointTables {
    

  
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;
    String team;
    int total_match;
    int win;
    int loss;
private double net_runRate;
   
    Long tournament;

}
