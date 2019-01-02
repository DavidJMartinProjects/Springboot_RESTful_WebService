package com.project.custom.league;

import static com.project.business.Constants.USER_AGENT;
import static com.project.business.Constants.USER_AGENT_PARAM;
import static com.project.business.Constants.restTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.project.domain.datatable.LadderTableEntry;

@Component
public class CustomLeagueRequestService {

	@Autowired
	ActiveLeagueService activeLeagueService;

	Map<String, List<LadderTableEntry>> parsedLeagues = new HashMap<>();
	private List<LadderTableEntry> customLeagueEntries = new ArrayList<>();
	private LadderTableEntry entry = new LadderTableEntry();

	private HttpHeaders headers = new HttpHeaders();
	private HttpEntity<String> entity;

	public List<LadderTableEntry> getCustomLeagueData(String leagueId, String leagueName) throws InterruptedException {
		setupHttpEntityHeaders();
		System.out.println("getCustomLeagueData() Calling poe-db for custom league ladder.");
		String url = "https://poe-ladder-db.herokuapp.com/custom-league?leagueId=" + leagueId + "&leagueName="
				+ leagueName + "";
		ResponseEntity<List> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
		System.out.println("reponseEntity Size : " + responseEntity.getBody().size());
		customLeagueEntries.clear();
		customLeagueEntries = responseEntity.getBody();
		System.out.println("customLeagueEntries size : " + customLeagueEntries.size());
		return customLeagueEntries;
	}

	private void setupHttpEntityHeaders() {
		headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add(USER_AGENT, USER_AGENT_PARAM);
		entity = new HttpEntity<String>("parameters", headers);
	}

	public List<LadderTableEntry> getCurrentDataset(String leagueId, String leagueName) throws InterruptedException {
		String trimmedLeagueId = leagueId.replace("(", "").replace(")", "").trim();
		String trimmedLeagueName = leagueName.trim();
		activeLeagueService.addLeagueRequest(trimmedLeagueId, trimmedLeagueName);
		getCustomLeagueData(trimmedLeagueId, trimmedLeagueName);
		System.out.println("Sucessfully parsed league : returning " + trimmedLeagueId + " data");
		return customLeagueEntries;
	}

}
