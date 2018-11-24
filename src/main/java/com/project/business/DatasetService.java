package com.project.business;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.domain.datatable.LadderTableEntry;
import com.project.domain.ladder.Entries;
import com.project.domain.ladder.Ladder;

public class DatasetService {

	private static final String LIMIT_200_OFFSET_200 = "?limit=200&offset=200";
	private static final String LIMIT_200 = "?limit=200";
	private static final String HTTP_API_PATHOFEXILE_COM_LADDERS = "http://api.pathofexile.com/ladders/";
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
	private static RestTemplate restTemplate = new RestTemplate();;
	private static HttpHeaders headers = new HttpHeaders();;
	private static List<LadderTableEntry> tableEntries;
	private static HttpEntity<String> entity;

	public DatasetService() throws InterruptedException {
		calculateDataSet();
	}

	public static List<List<LadderTableEntry>> getLatestDataSet() throws InterruptedException {
		prepareForApiRequest();
		int i = 0;
		for ( ;i < 4; i++) {
			getLeagueDataFromApiByLeagueName(i);
		}
		isCurrentDatasetEmpty();
		return newDataset;
	}

	private static void isCurrentDatasetEmpty() {
		if (currentDataset.size() == 0) {
			currentDataset = newDataset;
			latestDataset = newDataset;
		}
	}

	private static void prepareForApiRequest() {
		leagues = currentLeagueService.getLeagues();
		newDataset = new ArrayList<>();
        setupHttpEntityHeaders();
	}

	private static void getLeagueDataFromApiByLeagueName(int i) throws InterruptedException {
		tableEntries = new ArrayList<>();
		String url = HTTP_API_PATHOFEXILE_COM_LADDERS + leagues.get(i) + LIMIT_200;
		getLeagueApiResponse(url);
		url = HTTP_API_PATHOFEXILE_COM_LADDERS + leagues.get(i) + LIMIT_200_OFFSET_200;
		getLeagueApiResponse(url);
		newDataset.add(tableEntries);
	}

	private static void getLeagueApiResponse(String url) throws InterruptedException {
		response = restTemplate.exchange(url, HttpMethod.GET, entity, Ladder.class);
		System.out.println("response" + response.getBody().getEntries().toString());
		Ladder ladders =  response.getBody();
		List<Entries> entires = Arrays.asList(ladders.getEntries());
		entires.stream()
			.forEach(e -> mapResponseToEntity(e));
		Thread.sleep(500);
	}

	private static void setupHttpEntityHeaders() {
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        entity = new HttpEntity<String>("parameters", headers);		
	}

	private static void mapResponseToEntity(Entries responseEntry) {
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
		tableEntries.add(entry);
	}

	private static void formatTwitchInfo(Entries responseEntry) {
		if (responseEntry.getAccount().getTwitch() != null) {
			entry.setTwitch(responseEntry.getAccount().getTwitch().getName());
		} else {
			entry.setTwitch("");
		}
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
