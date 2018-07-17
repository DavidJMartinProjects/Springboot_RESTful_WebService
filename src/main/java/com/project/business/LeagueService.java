package com.project.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.domain.Entries;
import com.project.domain.LadderTableEntry;
import com.project.domain.MyPojo;

public class LeagueService {

	public List<LadderTableEntry> getLeagueDetails(String league) {
		
		String url = "http://api.pathofexile.com/ladders/"+league+"?offset=1&limit=10";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<MyPojo> response = restTemplate.getForEntity(url, MyPojo.class);

		List<LadderTableEntry> tableEntries = new ArrayList<>();

		for (Entries anEntry : response.getBody().getEntries()) {
			LadderTableEntry entry = new LadderTableEntry();
			entry.setName(anEntry.getCharacter().getName());
			entry.setLevel(anEntry.getCharacter().getLevel());
			entry.setChallenges(anEntry.getAccount().getChallenges().getTotal());
			entry.setExperience(anEntry.getCharacter().getExperience());
			tableEntries.add(entry);
		}

		for (LadderTableEntry anEntry : tableEntries) {
			System.out.println(anEntry);
		}
		
		return tableEntries;
	}

}
