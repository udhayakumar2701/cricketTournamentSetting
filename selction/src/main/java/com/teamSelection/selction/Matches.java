package com.teamSelection.selction;

public class Matches {
    
    String team;

    public String getTeam() {
        return this.team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getOppTeam() {
        return this.oppTeam;
    }

    public void setOppTeam(String oppTeam) {
        this.oppTeam = oppTeam;
    }
    String oppTeam;

    @Override
    public String toString() {
        return "{" +
            " team='" + getTeam() + "'" +
            ", oppTeam='" + getOppTeam() + "'" +
            "}";
    }



}
