package com.project.business;

import java.util.ArrayList;
import java.util.List;

public class CurrentLeagueService {

	private List<String> leagues = new ArrayList<>();

	public CurrentLeagueService() {
	}

	public List getLeagues() {
		leagues.add("Incursion");
		leagues.add("Hardcore Incursion");
		leagues.add("SSF Incursion");
		leagues.add("SSF Incursion HC");
		return leagues;
	}

}
