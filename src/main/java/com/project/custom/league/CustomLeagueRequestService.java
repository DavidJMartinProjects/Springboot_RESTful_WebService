package com.project.custom.league;

import static com.project.business.Constants.USER_AGENT;
import static com.project.business.Constants.USER_AGENT_PARAM;
import static com.project.business.Constants.entry;
import static com.project.business.Constants.response;
import static com.project.business.Constants.restTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.project.domain.datatable.LadderTableEntry;
import com.project.domain.ladder.Entries;
import com.project.domain.ladder.Ladder;

@Component
public class CustomLeagueRequestService {
	
	Map<String, List<LadderTableEntry>> parsedLeagues = new HashMap<>();
	private List<LadderTableEntry> newDataset = new ArrayList<>();
	private List<LadderTableEntry> currentDataset = new ArrayList<>();
	private List<LadderTableEntry> customLeagueEntries = new ArrayList<>();
	private LadderTableEntry entry = new LadderTableEntry();
	
	private HttpHeaders headers = new HttpHeaders();
	private HttpEntity<String> entity;

	public List<LadderTableEntry> getCustomLeagueData(String leagueId, String leagueName) throws InterruptedException {
		String urlPostfix = leagueName + " " + leagueId;
		System.out.println("getCurrentDataset() urlPostfix : " + urlPostfix);
		setupHttpEntityHeaders();		
		String url = "http://www.pathofexile.com/api/ladders?offset=0&limit=200&id=" + urlPostfix;
		System.out.println("custom league api request url : " + url);
		response = restTemplate.exchange(url, HttpMethod.GET, entity, Ladder.class);
		System.out.println("response size : " + response.getBody().getEntries().length);
		Ladder ladders =  response.getBody();
		List<Entries> entires = Arrays.asList(ladders.getEntries());
		entires.stream()		
			.forEach(e -> mapResponseToEntity(e));
		Thread.sleep(500);
		isCurrentDatasetEmpty();
		newDataset = customLeagueEntries;
		if(parsedLeagues.containsKey(leagueId)) {
			parsedLeagues.replace(leagueId, newDataset);
		} else {
			parsedLeagues.put(leagueId, newDataset);
		}
		
		return newDataset;
	}
	
	private void mapResponseToEntity(Entries responseEntry) {
		entry = new LadderTableEntry();
		entry.setRank(responseEntry.getRank());
		entry.setOnline(responseEntry.getOnline());
		entry.setCharacter(responseEntry.getCharacter().getName());
		entry.setDead(responseEntry.getDead());
		entry.setAccount(responseEntry.getAccount().getName());
		entry.setLevel(responseEntry.getCharacter().getLevel());
		entry.setTheClass(responseEntry.getCharacter().getTheClass());
		entry.setChallenges(responseEntry.getAccount().getChallenges().getTotal());
		entry.setExperience(responseEntry.getCharacter().getExperience());
		formatTwitchInfo(responseEntry);
		customLeagueEntries.add(entry);
	}
	
	private void formatTwitchInfo(Entries responseEntry) {
		if (responseEntry.getAccount().getTwitch() != null) {
			entry.setTwitch(responseEntry.getAccount().getTwitch().getName());
		} else {
			entry.setTwitch("");
		}
	}
	
	private void setupHttpEntityHeaders() {
		headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add(USER_AGENT, USER_AGENT_PARAM);
		headers.add("Accept-Encoding",  "gzip deflate");
		headers.add("Accept-Language",  "en-US en");
		headers.add("Upgrade-Insecure-Requests",  "1");
		headers.add("Cookie",  "__cfduid=d26f0e1ca08b926e10a926dcda9302cfa1546206260");
		headers.add("Cookie",  "POESESSID=3a4fa1c3e399804bf49bef466270e484");
		headers.add("Cookie",  "cf_clearance=b32a2a1c3b51d4bfde7de0cbf7fbc5015de28b0d-1546218127-300-150");
		headers.add("Cookie",  "_ga=GA1.2.1564561921.1546218143");
		headers.add("Cookie",  "_gid=GA1.2.350283217.1546218143");
		entity = new HttpEntity<String>("parameters", headers);
	}
	
	private void isCurrentDatasetEmpty() {
		if (currentDataset.size() == 0) {
			currentDataset = newDataset;
		}
	}
	
	public List<LadderTableEntry> getCurrentDataset(String leagueId, String leagueName) throws InterruptedException {
		if(!parsedLeagues.containsKey(leagueId)) {
			System.out.println("League not already parsed : requesting league data from api");
			getCustomLeagueData(leagueId, leagueName);

		} 
		System.out.println("League already parsed : returning data");
		return parsedLeagues.get(leagueId);
	}

}
