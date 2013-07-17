package com.ytz.punditunited;

public class Match {
	
	private String homeTeam;
	private String awayTeam;
	
	public Match(String home, String away){
		homeTeam = home;
		awayTeam = away;
	}
	
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	
	

}
