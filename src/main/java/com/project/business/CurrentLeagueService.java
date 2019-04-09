package com.project.business;

import java.util.ArrayList;
import java.util.List;

public class CurrentLeagueService {

	private List<String> leagues = new ArrayList<>();

	public CurrentLeagueService() {
	}

	public List getLeagues() {
		leagues.add("Synthesis");
		leagues.add("Synthesis Betrayal");
		leagues.add("SSF Synthesis");
		leagues.add("SSF Synthesis HC");		
		return leagues;
	}
 
}
