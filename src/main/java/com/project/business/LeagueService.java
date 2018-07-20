package com.project.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.domain.Entries;
import com.project.domain.LadderTableEntry;
import com.project.domain.Ladder;

public class LeagueService {

	public List<LadderTableEntry> getLeagueDetails(String league) {		
		List<LadderTableEntry> tableEntries = new ArrayList<>();
		String url = "http://api.pathofexile.com/ladders/"+league+"?limit=250";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Ladder> response = restTemplate.getForEntity(url, Ladder.class);
		
		for (Entries anEntry : response.getBody().getEntries()) {
			LadderTableEntry entry = new LadderTableEntry();
			entry.setRank(anEntry.getRank());
			entry.setCharacter(anEntry.getCharacter().getName());
			entry.setDead(anEntry.getDead());
			entry.setAccount(anEntry.getAccount().getName());
			entry.setLevel(anEntry.getCharacter().getLevel());
			entry.setTheClass(anEntry.getCharacter().getTheClass());			
			entry.setChallenges(anEntry.getAccount().getChallenges().getTotal());
			entry.setExperience(anEntry.getCharacter().getExperience());
			tableEntries.add(entry);
		}		
		return tableEntries;
	}

}
