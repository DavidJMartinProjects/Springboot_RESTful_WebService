package com.project.business;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.domain.datatable.LadderTableEntry;
import com.project.domain.ladder.Ladder;

public class DatasetService {

	private static CurrentLeagueService currentLeagueService = new CurrentLeagueService();
	private static List<String> leagues = new ArrayList<>();
	private static List<List<LadderTableEntry>> currentDataset = new ArrayList<>();
	private static List<List<LadderTableEntry>> latestDataset = new ArrayList<>();
	private static List<List<LadderTableEntry>> newDataset = new ArrayList<>();
	private static DecimalFormat formatter = new DecimalFormat("#,###");
	private static double amount;	
	private static String number;
	
	private static String latest;
	private static String current;
	private static String difference;
	private static String rankDifference;
	private static String xpPerHour;
	private static String theExperience;
	private static String timeStamp;
	private static String latestRank;
	private static String currentRank;
	private static Long newXPPH, oldXPPH;
	private static Long newRank, oldRank;
	private static String levelProgressBar;
	private static ResponseEntity<Ladder> response;
	private static LadderTableEntry entry;
	private static RestTemplate restTemplate;
	private static List<LadderTableEntry> tableEntries;

	public DatasetService() throws InterruptedException {
		calculateDataSet();
	}

	public static List<List<LadderTableEntry>> getLatestDataSet() throws InterruptedException {
		leagues = currentLeagueService.getLeagues();	
		newDataset = new ArrayList<>();
		int i = 0;
		for ( ;i < 4; i++) {
			tableEntries = new ArrayList<>();
//			String url = "http://api.pathofexile.com/ladders/" + leagues.get(i) + "?limit=200";
//			System.out.println("url : " +url);
//			restTemplate = new RestTemplate();
//			response = restTemplate.getForEntity(url, Ladder.class);
			
	        RestTemplate rt = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
	        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	        String url = "http://api.pathofexile.com/ladders/" + leagues.get(i) + "?limit=200";
	        ResponseEntity<Ladder> response = rt.exchange(url, HttpMethod.GET, entity, Ladder.class);
	        System.out.println("response" + response.getBody().getEntries().toString());
			
			int j=0; int length = 200;//response.getBody().getEntries().length;
			for (; j < length; j++) {
				entry = new LadderTableEntry();
				entry.setRank(response.getBody().getEntries()[j].getRank());
				entry.setOnline(response.getBody().getEntries()[j].getOnline());
				entry.setCharacter(response.getBody().getEntries()[j].getCharacter().getName());
				entry.setDead(response.getBody().getEntries()[j].getDead());
				entry.setAccount(response.getBody().getEntries()[j].getAccount().getName());
				entry.setLevel(response.getBody().getEntries()[j].getCharacter().getLevel());
				entry.setTheClass(response.getBody().getEntries()[j].getCharacter().getTheClass());
				entry.setChallenges(response.getBody().getEntries()[j].getAccount().getChallenges().getTotal());
				entry.setExperience(response.getBody().getEntries()[j].getCharacter().getExperience());
				if (response.getBody().getEntries()[j].getAccount().getTwitch() != null) {
					entry.setTwitch(response.getBody().getEntries()[j].getAccount().getTwitch().getName());
				} else {
					entry.setTwitch("");
				}
				tableEntries.add(entry);
			}
			newDataset.add(tableEntries);
			Thread.sleep(500);
	        response = rt.exchange(url, HttpMethod.GET, entity, Ladder.class);
	        System.out.println("response" + response.getBody().getEntries().toString());

			j=0; length = 200; //response.getBody().getEntries().length;
			for (; j < length; j++) {
			entry = new LadderTableEntry();
			entry.setRank(response.getBody().getEntries()[j].getRank());
			entry.setOnline(response.getBody().getEntries()[j].getOnline());
			entry.setCharacter(response.getBody().getEntries()[j].getCharacter().getName());
			entry.setDead(response.getBody().getEntries()[j].getDead());
			entry.setAccount(response.getBody().getEntries()[j].getAccount().getName());
			entry.setLevel(response.getBody().getEntries()[j].getCharacter().getLevel());
			entry.setTheClass(response.getBody().getEntries()[j].getCharacter().getTheClass());
			entry.setChallenges(response.getBody().getEntries()[j].getAccount().getChallenges().getTotal());
			entry.setExperience(response.getBody().getEntries()[j].getCharacter().getExperience());
			if (response.getBody().getEntries()[j].getAccount().getTwitch() != null) {
				entry.setTwitch(response.getBody().getEntries()[j].getAccount().getTwitch().getName());
			} else {
				entry.setTwitch("");
			}
				tableEntries.add(entry);
			}
			Thread.sleep(500);
		}
		if (currentDataset.size() == 0) {
			currentDataset = newDataset;
			latestDataset = newDataset;
		}
		return newDataset;
	}

	public static void calculateDataSet() throws InterruptedException {
		// copy latest to current dataset
		currentDataset = latestDataset;
		// get the latest dataset
		newDataset = DatasetService.getLatestDataSet();
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 400; j++) {
				for (int k = 0; k < 400; k++) {
					if (newDataset.get(i).get(j).getCharacter().equals(currentDataset.get(i).get(k).getCharacter())) {
						// character match then calculate xph
						
						latest = (newDataset.get(i).get(j).getExperience()).replaceAll(",", "");
						current = (currentDataset.get(i).get(k).getExperience()).replaceAll(",", "");
						
						if (latest.equals("")) {
							newXPPH = new Long(0);
						} else {
							newXPPH = Long.parseLong(latest);
						}

						if (current.equals("")) {
							oldXPPH = new Long(0);
						} else {
							oldXPPH = Long.parseLong(current);
						}
						
						latestRank = (newDataset.get(i).get(j).getRank()).replaceAll(",", "");
						currentRank = (currentDataset.get(i).get(k).getRank()).replaceAll(",", "");
						
						
						if (latestRank.equals("")) {
							newRank = new Long(0);
						} else {
							newRank = Long.parseLong(latestRank);
						}

						if (currentRank.equals("")) {
							oldRank = new Long(0);
						} else {
							oldRank = Long.parseLong(currentRank);
						}
												
						difference = String.valueOf(newXPPH - oldXPPH);						
						rankDifference = String.valueOf(oldRank - newRank);
						xpPerHour = String.valueOf((newXPPH - oldXPPH) * 12);
						theExperience = formatXp(newDataset.get(i).get(k).getExperience());
						difference = formatNumber(difference);
						xpPerHour = formatNumber(xpPerHour);
						levelProgressBar = ProgressBarService.getProgressPercentage(Integer.parseInt(newDataset.get(i).get(j).getLevel()), newDataset.get(i).get(j).getExperience().replaceAll(",", ""));
						
						newDataset.get(i).get(j).setXph(xpPerHour);
						newDataset.get(i).get(j).setXphDifference(difference);
						newDataset.get(i).get(j).setLevelProgressBar(levelProgressBar);
						newDataset.get(i).get(j).setRankDifference(rankDifference);
						newDataset.get(i).get(j).setExperience(theExperience);
						// set polling timestamp for current time
						timeStamp = new SimpleDateFormat(" MMM d hh:mm a").format(new Date());						
						newDataset.get(i).get(j).setTimeStamp(timeStamp);
					}
				}
			}
		}
		latestDataset = newDataset;
	}

	public static List<LadderTableEntry> getCalculatedDataset(String selectedLeague) {
		Runtime.getRuntime().gc();	
		switch (selectedLeague) {		
			case "Delve": {
				System.out.println("getCalculatedDataset() : " +selectedLeague);
				Runtime.getRuntime().gc();
				return latestDataset.get(0);
			}
			case "Hardcore Delve": {
				System.out.println("getCalculatedDataset() : " +selectedLeague);
				Runtime.getRuntime().gc();
				return latestDataset.get(1);
			}
			case "SSF Delve": {
				System.out.println("getCalculatedDataset() : " +selectedLeague);
				Runtime.getRuntime().gc();
				return latestDataset.get(2);
			}
			case "SSF Delve HC": {
				System.out.println("getCalculatedDataset() : " +selectedLeague);
				Runtime.getRuntime().gc();
				return latestDataset.get(3);
			}
			
			default: {
				return new ArrayList<LadderTableEntry>();
			}
		}
	}
	
	public static String formatNumber(String theNumber) {	
			number = theNumber;
			number = number.replaceAll(",", "");
			amount = Double.parseDouble(number);
			return formatter.format(amount).replaceAll(",", "");
	}
	
	public static String formatXp(String theNumber) {	
		number = theNumber;
		number = number.replaceAll(",", "");
		amount = Double.parseDouble(number);	
		return formatter.format(amount);
}

}
