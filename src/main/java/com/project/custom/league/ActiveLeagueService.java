package com.project.custom.league;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ActiveLeagueService {
	
	Map<String, String> activeLeagueRequests = new HashMap<>();
	Map<String, String> redundantLeagueRequests = new HashMap<>();
	Map<String, String> allLeagueRequests = new HashMap<>();
	
	public void addLeagueRequest(String leagueId, String leagueName) {
		System.out.println("League Id : " +leagueId+ " added to active leagues map.");
		activeLeagueRequests.put(leagueId, leagueName);
		System.out.println("activeLeagueRequests : " + activeLeagueRequests);
	}
	
	// polled every 10 mins
	public Map removeRedundantLeagues() {
		for(Map.Entry<String, String> entry : allLeagueRequests.entrySet()) {
			if(!activeLeagueRequests.containsKey(entry.getKey())) {
				redundantLeagueRequests.put(entry.getKey(), entry.getValue());
			}
		}
		allLeagueRequests = activeLeagueRequests;
		activeLeagueRequests.clear();
		return redundantLeagueRequests;
	}
	

}
