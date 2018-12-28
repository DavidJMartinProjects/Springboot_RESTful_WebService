package com.project.topten.pojo;

import org.springframework.stereotype.Component;

@Component
public class TopTenLadderResponse {

	TopTenLeague leagueStd;

	TopTenLeague leagueHC;

	TopTenLeague leagueSFF;
	
	TopTenLeague leagueHCSFF;
	
	public TopTenLeague getLeagueStd() {
		return leagueStd;
	}
	public void addToLeagueStd(TopTenLeague leagueStd) {
		this.leagueStd = leagueStd;
	}
	public TopTenLeague getLeagueHC() {
		return leagueHC;
	}
	public void addToLeagueHC(TopTenLeague leagueHC) {
		this.leagueHC = leagueHC;
	}
	public TopTenLeague getLeagueSFF() {
		return leagueSFF;
	}
	public void addToLeagueSFF(TopTenLeague leagueSFF) {
		this.leagueSFF = leagueSFF;
	}
	public TopTenLeague getLeagueHCSFF() {
		return leagueHCSFF;
	}
	public void addToLeagueHCSFF(TopTenLeague leagueHCSFF) {
		this.leagueHCSFF = leagueHCSFF;
	}
	
	@Override
	public String toString() {
		return "TopTenLadderResponse [leagueStd=" + leagueStd + ", leagueHC=" + leagueHC + ", leagueSFF=" + leagueSFF
				+ ", leagueHCSFF=" + leagueHCSFF + "]";
	}

}
