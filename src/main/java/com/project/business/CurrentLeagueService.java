package com.project.business;

import java.util.ArrayList;
import java.util.List;

public class CurrentLeagueService {

	private List<String> leagues = new ArrayList<>();

	public CurrentLeagueService() {
	}

	public List getLeagues() {
		leagues.add("Betrayal");
		leagues.add("Hardcore Betrayal");
		leagues.add("SSF Betrayal");
		leagues.add("SSF Betrayal HC");		
		return leagues;
	}
 
}
