package com.project.business;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.domain.datatable.LadderTableEntry;
import com.project.domain.ladder.Ladder;

public class Constants {
	public static final String LIMIT_200_OFFSET_200 = "?limit=200&offset=200";
	public static final String LIMIT_200 = "?limit=200";
	public static final String HTTP_API_PATHOFEXILE_COM_LADDERS = "http://api.pathofexile.com/ladders/";
	public static CurrentLeagueService currentLeagueService = new CurrentLeagueService();
	public static List<String> leagues = new ArrayList<>();
	public static List<List<LadderTableEntry>> currentDataset = new ArrayList<>();
	public static List<List<LadderTableEntry>> latestDataset = new ArrayList<>();
	public static List<List<LadderTableEntry>> newDataset = new ArrayList<>();
	public static DecimalFormat formatter = new DecimalFormat("#,###");
	public static double amount;	
	public static String number;	
	public static String latest;
	public static String current;
	public static String difference;
	public static String rankDifference;
	public static String xpPerHour;
	public static String theExperience;
	public static String timeStamp;
	public static String latestRank;
	public static String currentRank;
	public static Long newXPPH, oldXPPH;
	public static Long newRank, oldRank;
	public static String levelProgressBar;
	public static ResponseEntity<Ladder> response;
	public static LadderTableEntry entry;
	public static RestTemplate restTemplate = new RestTemplate();;
	public static HttpHeaders headers = new HttpHeaders();;
	public static List<LadderTableEntry> tableEntries;
	public static HttpEntity<String> entity;
	public static final String USER_AGENT_PARAM = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
	public static final String USER_AGENT = "user-agent";

}
