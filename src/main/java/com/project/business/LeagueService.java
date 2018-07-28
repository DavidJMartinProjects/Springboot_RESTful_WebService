package com.project.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.domain.datatable.LadderTableEntry;
import com.project.domain.ladder.Entries;
import com.project.domain.ladder.Ladder;

public class LeagueService {

	public List<LadderTableEntry> getLeagueDetails(String league) {
		List<LadderTableEntry> tableEntries = new ArrayList<>();
		String url = "http://api.pathofexile.com/ladders/" + league + "?limit=200";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Ladder> response = restTemplate.getForEntity(url, Ladder.class);

		for (Entries anEntry : response.getBody().getEntries()) {
			LadderTableEntry entry = new LadderTableEntry();
			entry.setRank(anEntry.getRank());
			entry.setOnline(anEntry.getOnline());
			entry.setCharacter(anEntry.getCharacter().getName());
			entry.setDead(anEntry.getDead());
			entry.setAccount(anEntry.getAccount().getName());
			entry.setLevel(anEntry.getCharacter().getLevel());
			entry.setTheClass(anEntry.getCharacter().getTheClass());
			entry.setChallenges(anEntry.getAccount().getChallenges().getTotal());
			entry.setExperience(anEntry.getCharacter().getExperience());
			if (anEntry.getAccount().getTwitch() != null) {
				entry.setTwitch(anEntry.getAccount().getTwitch().getName());
			} else {
				entry.setTwitch("");
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
