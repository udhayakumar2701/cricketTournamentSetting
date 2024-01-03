package com.teamSelection.selction.Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teamSelection.selction.Matches;
import com.teamSelection.selction.database.Tournament;
import com.teamSelection.selction.database.pointTables;
import com.teamSelection.selction.database.teamsDatabase;
import com.teamSelection.selction.server.Server;


@RestController
public class homeController {

  @Autowired
  Server server;

  
  @GetMapping("/")
  public String Tournament() {
    return "Home";
  }

  // this is Gobal variable for the Tournament dataBase
  Tournament db;

  int match_no=1; //This is  matches no  count the total matches in the tournament

  @PostMapping("/show-tournament")
  public String showingTournament(@RequestParam("tournament-name") String tournamentName,
      @RequestParam(required = false) String Date) {

    // It will show all the Tournament in same Name
    if (Date == null) { // If the user doesn't enter the date it will show the all the tournament which
                        // having the same name
      List<Tournament> ListDb = server.findBYTournament(tournamentName);
      return ListDb.toString();
    } else {
       db = server.findByTournamentAndDate(tournamentName, Date);
      return db.toString();
    }

  }

  @PostMapping("/new-tournament")
  public String TournamentDetials(@RequestParam("tournament-name") String tournamentName,
      @RequestParam String Date) {

    if (Date == null)
      return "Plz Enter the date";

    // this get only the one user from the database
    db = server.findByTournamentAndDate(tournamentName, Date);

    // If the user doesn't exist it will create the new user
    if (db == null) {
      Tournament db1 = new Tournament();
      db1.setTournament(tournamentName);
      db1.setDate(Date);
      server.saveTournament(db1);
      db = db1;
    }
    return "teams" + tournamentName + Date; 
  }

  // It will get the count of the teams that are need to add in the database
  @PostMapping("/teams-Count")
    public String postMethodName(@RequestParam String count) {
        try {
            // Your existing logic here
            return count;
        } catch (Exception e) {
            // Handle other exceptions if needed
            return "An error occurred";
        }catch(ExceptionInInitializerError e){
   return "e";
        }
      }

  // It will get all the teams name from the fornt End
  @PostMapping("/teams-Name-in-leagueStage")
  public String postMethodName(@RequestParam List<String> teamName) {

   // db = server.findByTournamentAndDate("virat", "2003");
    for (String str : teamName) {
      // Start of the pointTables Database
      pointTables pointTablesDb = new pointTables();
      pointTablesDb.setLoss(0);
      pointTablesDb.setNet_runRate(0);
      pointTablesDb.setTeam(str);
      pointTablesDb.setWin(0);
      pointTablesDb.setTotal_match(0);
      pointTablesDb.setTournament(db.getId());
      System.out.println(str);
       server.savePointTables(pointTablesDb);
      // end of the set the attributes to the database for the point tables
    }
    List<teamsDatabase> matchesSchedule = setMactches(teamName, "league Stage"); // This setMatches method is used to
                                                                                 // schudels the matches for teams
    String result = matchesSchedule.stream()
        .map(teamsDatabase::toString)
        .collect(Collectors.joining("\n"));

    return result;

  }

  List<Matches> matchesForTheTournament = new ArrayList<>();
  List<teamsDatabase> matchesSchedule = new ArrayList<>();

  private List<teamsDatabase> setMactches(List<String> teamName, String string) {//String=round


    matchesForTheTournament=new ArrayList<>();
    matchesSchedule=new ArrayList<>();
    // teamsDatabase db=new teamsDatabase();// teams database Instances
    /*
     * In the below method we find the total matches of the tournament
     * Example team1,team2,team3 // there is total of the 3 team so we need to
     * arrange the matches
     * team1 vs team2
     * team1 vs team3
     * team2 vs team3 // Each teams is paly with each other one matches
     */
    int Teams_Count = teamName.size();
    for (int i = 0; i < Teams_Count; i++) {
      for (int j = i + 1; j < Teams_Count; j++) {
        Matches obj = new Matches();
        obj.setTeam(teamName.get(i));
        obj.setOppTeam(teamName.get(j));
       // System.out.println(obj.toString());
        matchesForTheTournament.add(obj);
      }
    }
    /*
     * In the below lines are used to scchudles the team matches
     * like team1 vs team2 is the first matches then the team1 and team2 are not
     * have the matches for
     * the next days.....
     * 
     * 
     * That logic only apply below.............*
     * 
     */
      
    while(!matchesForTheTournament.isEmpty()){
      String roundName="leage_stage";
      upadateTeamDatabase(roundName, 0,match_no++);
      System.out.println(matchesForTheTournament.remove(0)+"------------------------------------------------------");
      if(matchesForTheTournament.isEmpty()){
       break;
      }
       upadateTeamDatabase(roundName, matchesForTheTournament.size()-1, match_no++);
    
       System.out.println( matchesForTheTournament.remove(matchesForTheTournament.size()-1)+"------------------------------------");
      
    }

    /*
     * End of that logic
     */

   // matchesSchedule.stream().forEach(n -> server.saveteamsDatabase(n));// used to store in database
   
    return matchesSchedule;

  }
// this mwthod this used to update the teams in the teamsdatabase  
//  this save the teams match detials in the database
  void upadateTeamDatabase(String round  ,int position,int match_no){ //String=round
    teamsDatabase db1 = new teamsDatabase();
        db1.setTeam(matchesForTheTournament.get(position).getTeam());
        db1.setOppteams(matchesForTheTournament.get(position).getOppTeam());
        db1.setTournamentId(db.getId());
        db1.setRound(round);
        db1.setMatch_no(match_no);
        matchesSchedule.add(db1);
        System.out.println(db1+"__--------------------------------------------------------------__ ");
        server.saveteamsDatabase(db1); 
  }

  @PostMapping("/teams-score")
  public String teamsScore(@RequestParam List<String> team, @RequestParam List<String> oppTeam) {
//db = server.findByTournamentAndDate("virat", "2003");
    // Iterate through the teams and update statistics
    for (int i = 0; i < team.size(); i++) {
        teamsDatabase obj = matchesSchedule.get(i);
        System.out.println(obj+"________________________________________________");
        teamsDatabase teamsObj = server.findByTeamsandTournamentId(obj.getTeam(), obj.getOppteams(), db.getId(),obj.getRound());
        System.out.println(teamsObj+"______________________________________________");

        // Determine the winning team and update win status
        String winTeam = Integer.parseInt(team.get(i)) > Integer.parseInt(oppTeam.get(i)) ? obj.getTeam() : obj.getOppteams(); 
        teamsObj.setWin_status(winTeam);
        server.saveteamsDatabase(teamsObj);
       System.out.println(); 
        System.out.println("team:"+teamsObj.toString());
        System.out.println();
       
        if(teamsObj.getRound().equals("leage_stage")){
        // Update pointTables for the current team and the opponent team
        updatePointTables( db.getId(), teamsObj.getTeam(), winTeam, team, oppTeam, i);
        updatePointTables( db.getId(), teamsObj.getOppteams(), winTeam, team, oppTeam, i);}
    }

    return " ";
}

// Helper method to update pointTables for a team
private void updatePointTables( Long tournamentId, String teamName, String winTeam,
                                List<String> team, List<String> oppTeam, int index) {
    // Retrieve the pointTables object for the specified team and tournament ID
    pointTables pointTablesDb = server.findByTeamAndTournamnetId(teamName, tournamentId);
    if(pointTablesDb==null ){
      System.out.println(teamName + tournamentId);
    }
    else{
    // Update statistics based on the outcome of the match
    pointTablesDb.setLoss(pointTablesDb.getLoss() + (!pointTablesDb.getTeam().equals(winTeam) ? 1 : 0));
   
    pointTablesDb.setWin(pointTablesDb.getWin() + (!pointTablesDb.getTeam().equals(winTeam) ? 0 : 1));
   
    pointTablesDb.setTotal_match(pointTablesDb.getTotal_match() +1);

    // Update the net run rate based on the outcome of the match
    pointTablesDb.setNet_runRate(pointTablesDb.getNet_runRate()
            + (net_runRatemethod(Integer.parseInt(team.get(index)), Integer.parseInt(oppTeam.get(index)))
            * (winTeam.equals(pointTablesDb.getTeam()) ? 1 : -1)));
            System.out.println("________________________________________");
        System.out.println("pointTablesDb :"+pointTablesDb.toString());
        System.out.println("________________________________________________________");

    // Save the updated pointTables object
    server.savePointTables(pointTablesDb);}
    //System.out.println(pointTablesDb.toString());
}

// This method is used to find the netrunrate
  public double net_runRatemethod(int teamOneScore, int teamTwoScore) {
    int score = (teamOneScore > teamTwoScore) ? teamOneScore - teamTwoScore : teamTwoScore - teamOneScore;
    System.out.println("Score:"+score);
    if (score >= 150) {
      return 0.750;
    }
    if (score < 150 && score >= 100)
      return 0.500;
    if (score < 100 && score >= 50)
      return 0.250;
    if (score < 50 && score > 0)
      return 0.100;

    return 0.000;
  }

  @ExceptionHandler(Exception.class)
  String handleException(Exception ex) {
      System.out.println("Error: " + ex);
      return "404 Not Found";
  }
  

@GetMapping("/next-Round")
public String setNextRound() {

  List<pointTables> teams=server.findBYTourn(db.getId());
 
    teams = new ArrayList<>( teams.stream()
              .sorted(Comparator.comparing(pointTables::getNet_runRate).reversed())
              .limit(teams.size()<=3?2:4)
              .collect(Collectors.toList()));
  
              List<String> nextRoundSelecteTeams= new ArrayList<>(teams.stream().map(pointTables::getTeam).toList());
              
        List<teamsDatabase> selectAllTheTeams=server.findAllOFTeamsDatabase(db.getId());
        boolean check=false;
        for(teamsDatabase list:selectAllTheTeams){
          if(list.getRound().equals("semi-final")){
            check=true;
          }
          else if(list.getRound().equals("Final")){
            return  "Winner is :"+list.getWin_status();
          }
        }
       if(check==false){
          nextRound(nextRoundSelecteTeams);
          matchesSchedule=new ArrayList<>();

          for(int i=0;i<matchesForTheTournament.size();i++){
          if(teams.size()==2)
          upadateTeamDatabase("Final", i, server.countTheTeamsScheduleList()+1);
          else
          upadateTeamDatabase("semi-final", i, server.countTheTeamsScheduleList()+1);
          System.out.println( "  ______________________________In the false condition_____________________________________");
          }
        }else{
         List<teamsDatabase> semiFinalWinTeams=new ArrayList<>(server.findByAllSemiWinner("semi-final",db.getId()));
         List<String>semiWinner=new ArrayList<>(semiFinalWinTeams.stream().map(teamsDatabase::getWin_status).toList());
         nextRound(semiWinner);
         upadateTeamDatabase("Final", 0, server.countTheTeamsScheduleList()+1);
         System.out.println("_____________________________________In the true condition___________________________________");
        }
  System.out.println(teams);
    return matchesSchedule.toString();
}

// thios method get the top four teams of the tournament and create the next round matches for that teams 
void  nextRound(List<String>nextRound){
 matchesForTheTournament=new ArrayList<>();
 matchesSchedule=new ArrayList<>();
  while(!nextRound.isEmpty()){
    newMatches(nextRound.remove(0),nextRound.remove(nextRound.size()-1));
  }

}

// create the matchs for next round 
void newMatches(String team1 ,String team2){
  
  Matches obj = new Matches();
        obj.setTeam(team1);
        obj.setOppTeam(team2);
      
        matchesForTheTournament.add(obj);
}
  

}
