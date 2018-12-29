package com.project.domain.topten.business;

import static com.project.business.Constants.USER_AGENT;
import static com.project.business.Constants.USER_AGENT_PARAM;
import static com.project.business.Constants.entity;
import static com.project.business.Constants.headers;
import static com.project.business.Constants.restTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.project.domain.ladder.Entries;
import com.project.domain.ladder.Ladder;
import com.project.topten.pojo.DelveLadderResponseEntry;
import com.project.topten.pojo.DelveLadderResponseLadder;
import com.project.topten.pojo.TopTenLadderResponse;
import com.project.topten.pojo.TopTenLeague;
import com.project.topten.pojo.UberLabLadderResponseLadder;
import com.project.topten.pojo.UberLabResponseEntry;
import com.project.topten.tabledata.TableDataDelveTopTen;
import com.project.topten.tabledata.TableDataRaceTo100;
import com.project.topten.tabledata.TableDataUberLabTopTen;

@Component
public class TopTenLaddersService {
	
	public static TopTenLadderResponse topTenLadderResponse = new TopTenLadderResponse();
	
	private static List<Object> topTenDelveTableEntries;
	private static List<Object> topTenRaceTo100Entries;
	private static List<Object> topTenUberLabEntries;
	
	public HttpEntity<String> entity;
	public HttpHeaders headers = new HttpHeaders();

	public TopTenLeague getLeagueTopTenData(String leagueName) throws InterruptedException {
		setupHttpEntityHeaders();
		topTenDelveTableEntries = new ArrayList<>();	
		topTenRaceTo100Entries = new ArrayList<>();
		topTenUberLabEntries = new ArrayList<>();

		Thread.sleep(1000);
		String url = "http://www.pathofexile.com/api/ladders?offset=0&limit=10&id=" + leagueName + "&type=league&sort=depthsolo";
		ResponseEntity<DelveLadderResponseLadder> delveLadderResponse = restTemplate.exchange(url, HttpMethod.GET,
				entity, DelveLadderResponseLadder.class);
		DelveLadderResponseLadder delveLadders = delveLadderResponse.getBody();
		List<DelveLadderResponseEntry> delveEntries = delveLadders.getEntries();
		AtomicInteger delveCounter = new AtomicInteger(1);
		
		for(DelveLadderResponseEntry delveLadderResponseLadder : delveEntries) {
			mapDelveResponseToEntity(delveLadderResponseLadder, delveCounter.getAndIncrement(), leagueName);
		}
		Thread.sleep(1000);
		url = new String("http://www.pathofexile.com/api/ladders?offset=0&limit=10&id=" + leagueName + "&type=labyrinth&difficulty=1");
		ResponseEntity<UberLabLadderResponseLadder> uberLabResponse = restTemplate.exchange(url, HttpMethod.GET, entity,
				UberLabLadderResponseLadder.class);
		UberLabLadderResponseLadder uberLabLadders = uberLabResponse.getBody();
		List<UberLabResponseEntry> uberLabEntires = uberLabLadders.getEntries();
		AtomicInteger uberLabCounter = new AtomicInteger(1);
		for(UberLabResponseEntry uberLabResponseEntry : uberLabEntires) {
			mapUberLabResponseToEntity(uberLabResponseEntry, uberLabCounter.getAndIncrement(), leagueName);
		}
		Thread.sleep(1000);
		url = new String("http://api.pathofexile.com/ladders/" + leagueName + "?limit=10");
		ResponseEntity<Ladder> raceTo100Response = restTemplate.exchange(url, HttpMethod.GET, entity, Ladder.class);
		Ladder raceTo100Ladders = raceTo100Response.getBody();
		List<Entries> raceTo100Entires = Arrays.asList(raceTo100Ladders.getEntries());
		AtomicInteger raceTo100Counter = new AtomicInteger(1);
		for(Entries entries : raceTo100Entires) {
			mapRaceTo100ResponseToEntity(entries, raceTo100Counter.getAndIncrement(), leagueName);
		}
		
		TopTenLeague topTenLeague = new TopTenLeague();
		topTenLeague.setTableDataDelve(topTenDelveTableEntries);
		topTenLeague.setTableDataRaceTo100(topTenRaceTo100Entries);
		topTenLeague.setTableDataUberLabTopTen(topTenUberLabEntries);
		return topTenLeague;

	}

	private void mapDelveResponseToEntity(DelveLadderResponseEntry delveLadderResponseEntry, int rankNumber, String league) {
		TableDataDelveTopTen entry = new TableDataDelveTopTen();
		entry.setLeague(league + " Delve");
		entry.setRank(String.valueOf(rankNumber));
		entry.setCharName(delveLadderResponseEntry.getCharacter().getName());
		entry.setAscendancy(delveLadderResponseEntry.getCharacter().getAscendancy());
		entry.setDept(String.valueOf(delveLadderResponseEntry.getCharacter().getDepth().getSolo()));
		entry.setDead(delveLadderResponseEntry.getDead());
		topTenDelveTableEntries.add(entry);
	}

	private void mapRaceTo100ResponseToEntity(Entries response, int rankNumber, String league) {
		TableDataRaceTo100 ladderEntry = new TableDataRaceTo100();
		ladderEntry.setLeague(league + " Race To 100");
		ladderEntry.setRank(String.valueOf(rankNumber));
		ladderEntry.setCharName(response.getCharacter().getName());
		ladderEntry.setAscendancy(response.getCharacter().getTheClass());
		ladderEntry.setLevel(response.getCharacter().getLevel());
		ladderEntry.setDead(Boolean.valueOf(response.getDead()));
		topTenRaceTo100Entries.add(ladderEntry);
	}

	private void mapUberLabResponseToEntity(UberLabResponseEntry response, int rankNumber, String league) {
		TableDataUberLabTopTen ladderEntry = new TableDataUberLabTopTen();
		ladderEntry.setLeague(league + " UberLab");
		ladderEntry.setRank(String.valueOf(rankNumber));
		ladderEntry.setCharName(response.getCharacter().getName());
		ladderEntry.setAscendancy(response.getCharacter().getTheClass());
		ladderEntry.setTime(formatUberLabTime(response.getTime()));
		topTenUberLabEntries.add(ladderEntry);
	}

	private String formatUberLabTime(Integer time) {
		return (time / 60) + "min " + (time % 60) + "sec";
	}

	private void setupHttpEntityHeaders() {
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add(USER_AGENT, USER_AGENT_PARAM);
		entity = new HttpEntity<String>("parameters", headers);
	}

}
