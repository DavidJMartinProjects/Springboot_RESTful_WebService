package com.project.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.domain.datatable.LadderTableEntry;
import com.project.domain.ladder.Entries;
import com.project.domain.ladder.Ladder;

public class LeagueService {
	private static List<LadderTableEntry> tableEntries = new ArrayList<>();
	RestTemplate restTemplate = new RestTemplate();
	LadderTableEntry entry = new LadderTableEntry();

	public List<LadderTableEntry> getLeagueDetails(String league) {
		
		String url = "http://api.pathofexile.com/ladders/" + league + "?limit=200";
		System.out.println("URL : " +url);		
		ResponseEntity<Ladder> response = restTemplate.getForEntity(url, Ladder.class);
		
		for (Entries anEntry : response.getBody().getEntries()) {			
			if (anEntry.getAccount().getTwitch() != null) {
				anEntry.setTwitch(anEntry.getAccount().getTwitch().getName());
			} else {
				anEntry.setTwitch("");
			}
			tableEntries.add(entry);
		}
		return tableEntries;
	}

	// this method will be passed in the league and return that leagues dataset as a
	// List<LadderTableEntries> - it will call the Polling service class

	public List<LadderTableEntry> getPolleddata(String league) {
		return DatasetService.getCalculatedDataset(league);
	}

}
