package com.project.business;

import java.util.ArrayList;
import java.util.List;

public class CurrentLeagueService {

	private List<String> leagues = new ArrayList<>();

	public CurrentLeagueService() {
	}

	public List getLeagues() {
		leagues.add("Delve");
		leagues.add("Hardcore Delve");
		leagues.add("SSF Delve");
		leagues.add("SSF Delve HC");		
		return leagues;
	}
 
}
