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
//	
//	public static TopTenLadderResponse topTenLadderResponse = new TopTenLadderResponse();
//	
//	private static List<Object> topTenDelveTableEntries;
//	private static List<Object> topTenRaceTo100Entries;
//	private static List<Object> topTenUberLabEntries;
//	
//	public HttpEntity<String> entity;
//	public HttpHeaders headers = new HttpHeaders();
//
//	public TopTenLeague getLeagueTopTenData(String leagueName) throws InterruptedException {
//		setupHttpEntityHeaders();
//		topTenDelveTableEntries = new ArrayList<>();	
//		topTenRaceTo100Entries = new ArrayList<>();
//		topTenUberLabEntries = new ArrayList<>();
//
//		Thread.sleep(1000);
//		String url = "http://www.pathofexile.com/api/ladders?offset=0&limit=10&id=" + leagueName + "&type=league&sort=depthsolo&_=1546137921952";
//		ResponseEntity<DelveLadderResponseLadder> delveLadderResponse = restTemplate.exchange(url, HttpMethod.GET,
//				entity, DelveLadderResponseLadder.class);
//		DelveLadderResponseLadder delveLadders = delveLadderResponse.getBody();
//		List<DelveLadderResponseEntry> delveEntries = delveLadders.getEntries();
//		AtomicInteger delveCounter = new AtomicInteger(1);
//		
//		for(DelveLadderResponseEntry delveLadderResponseLadder : delveEntries) {
//			mapDelveResponseToEntity(delveLadderResponseLadder, delveCounter.getAndIncrement(), leagueName);
//		}
//		Thread.sleep(1000);
//		url = new String("http://www.pathofexile.com/api/ladders?offset=0&limit=10&id=" + leagueName + "&type=labyrinth&difficulty=4");
//		ResponseEntity<UberLabLadderResponseLadder> uberLabResponse = restTemplate.exchange(url, HttpMethod.GET, entity,
//				UberLabLadderResponseLadder.class);
//		UberLabLadderResponseLadder uberLabLadders = uberLabResponse.getBody();
//		List<UberLabResponseEntry> uberLabEntires = uberLabLadders.getEntries();
//		AtomicInteger uberLabCounter = new AtomicInteger(1);
//		for(UberLabResponseEntry uberLabResponseEntry : uberLabEntires) {
//			mapUberLabResponseToEntity(uberLabResponseEntry, uberLabCounter.getAndIncrement(), leagueName);
//		}
//		Thread.sleep(1000);
//		url = new String("http://api.pathofexile.com/ladders/" + leagueName + "?limit=10");
//		ResponseEntity<Ladder> raceTo100Response = restTemplate.exchange(url, HttpMethod.GET, entity, Ladder.class);
//		Ladder raceTo100Ladders = raceTo100Response.getBody();
//		List<Entries> raceTo100Entires = Arrays.asList(raceTo100Ladders.getEntries());
//		AtomicInteger raceTo100Counter = new AtomicInteger(1);
//		for(Entries entries : raceTo100Entires) {
//			mapRaceTo100ResponseToEntity(entries, raceTo100Counter.getAndIncrement(), leagueName);
//		}
//		
//		TopTenLeague topTenLeague = new TopTenLeague();
//		topTenLeague.setTableDataDelve(topTenDelveTableEntries);
//		topTenLeague.setTableDataRaceTo100(topTenRaceTo100Entries);
//		topTenLeague.setTableDataUberLabTopTen(topTenUberLabEntries);
//		return topTenLeague;
//
//	}
//
//	private void mapDelveResponseToEntity(DelveLadderResponseEntry delveLadderResponseEntry, int rankNumber, String league) {
//		TableDataDelveTopTen entry = new TableDataDelveTopTen();
//		entry.setLeague(league);
//		entry.setRank(String.valueOf(rankNumber));
//		entry.setCharName(delveLadderResponseEntry.getCharacter().getName());
//		entry.setAscendancy(delveLadderResponseEntry.getCharacter().getAscendancy());
//		entry.setDept(String.valueOf(delveLadderResponseEntry.getCharacter().getDepth().getSolo()));
//		entry.setDead(delveLadderResponseEntry.getDead());
//		topTenDelveTableEntries.add(entry);
//	}
//
//	private void mapRaceTo100ResponseToEntity(Entries response, int rankNumber, String league) {
//		TableDataRaceTo100 ladderEntry = new TableDataRaceTo100();
//		ladderEntry.setLeague(league);
//		ladderEntry.setRank(String.valueOf(rankNumber));
//		ladderEntry.setCharName(response.getCharacter().getName());
//		ladderEntry.setAscendancy(response.getCharacter().getTheClass());
//		ladderEntry.setLevel(response.getCharacter().getLevel());
//		ladderEntry.setDead(Boolean.valueOf(response.getDead()));
//		topTenRaceTo100Entries.add(ladderEntry);
//	}
//
//	private void mapUberLabResponseToEntity(UberLabResponseEntry response, int rankNumber, String league) {
//		TableDataUberLabTopTen ladderEntry = new TableDataUberLabTopTen();
//		ladderEntry.setLeague(league);
//		ladderEntry.setRank(String.valueOf(rankNumber));
//		ladderEntry.setCharName(response.getCharacter().getName());
//		ladderEntry.setAscendancy(response.getCharacter().getTheClass());
//		ladderEntry.setTime(formatUberLabTime(response.getTime()));
//		topTenUberLabEntries.add(ladderEntry);
//	}
//
//	private String formatUberLabTime(Integer time) {
//		return (time / 60) + "min " + (time % 60) + "sec";
//	}
//
//	private void setupHttpEntityHeaders() {
//		headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		headers.add(USER_AGENT, USER_AGENT_PARAM);
//		headers.add("Accept-Encoding",  "gzip deflate");
//		headers.add("Accept-Language",  "en-US en");
//		headers.add("Upgrade-Insecure-Requests",  "1");
//		headers.add("Cookie",  "__cfduid=d26f0e1ca08b926e10a926dcda9302cfa1546206260");
//		headers.add("Cookie",  "POESESSID=3a4fa1c3e399804bf49bef466270e484");
//		headers.add("Cookie",  "cf_clearance=b32a2a1c3b51d4bfde7de0cbf7fbc5015de28b0d-1546218127-300-150");
//		headers.add("Cookie",  "_ga=GA1.2.1564561921.1546218143");
//		headers.add("Cookie",  "_gid=GA1.2.350283217.1546218143");
//		entity = new HttpEntity<String>("parameters", headers);
//	}

}
