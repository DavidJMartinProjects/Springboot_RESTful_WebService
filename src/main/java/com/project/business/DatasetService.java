package com.project.business;

import static com.project.business.Constants.HTTP_API_PATHOFEXILE_COM_LADDERS;
import static com.project.business.Constants.*;
import static com.project.business.Constants.LIMIT_200;
import static com.project.business.Constants.LIMIT_200_OFFSET_200;
import static com.project.business.Constants.amount;
import static com.project.business.Constants.current;
import static com.project.business.Constants.currentDataset;
import static com.project.business.Constants.currentLeagueService;
import static com.project.business.Constants.currentRank;
import static com.project.business.Constants.difference;
import static com.project.business.Constants.entity;
import static com.project.business.Constants.entry;
import static com.project.business.Constants.formatter;
import static com.project.business.Constants.headers;
import static com.project.business.Constants.latest;
import static com.project.business.Constants.latestDataset;
import static com.project.business.Constants.latestRank;
import static com.project.business.Constants.leagues;
import static com.project.business.Constants.levelProgressBar;
import static com.project.business.Constants.newDataset;
import static com.project.business.Constants.newRank;
import static com.project.business.Constants.newXPPH;
import static com.project.business.Constants.number;
import static com.project.business.Constants.oldRank;
import static com.project.business.Constants.oldXPPH;
import static com.project.business.Constants.rankDifference;
import static com.project.business.Constants.response;
import static com.project.business.Constants.restTemplate;
import static com.project.business.Constants.tableEntries;
import static com.project.business.Constants.theExperience;
import static com.project.business.Constants.timeStamp;
import static com.project.business.Constants.xpPerHour;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.project.domain.datatable.LadderTableEntry;
import com.project.domain.ladder.Entries;
import com.project.domain.ladder.Ladder;

public class DatasetService {
	
	public DatasetService() throws InterruptedException {
		calculateDataSet();
	}

	public static List<List<LadderTableEntry>> getLatestDataSet() throws InterruptedException {
		return requestLatestDataFromApi();
	}

	private static List<List<LadderTableEntry>> requestLatestDataFromApi() throws InterruptedException {
		prepareForApiRequest();
		makeApiRequests();
		isCurrentDatasetEmpty();
		return newDataset;
	}

	private static void makeApiRequests() throws InterruptedException {
		int numberOfLeagues = 0;
		for ( ;numberOfLeagues < 4; numberOfLeagues++) {
			getLeagueDataFromApiByLeagueName(numberOfLeagues);
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
//		System.out.println("url : " +url);
		url = HTTP_API_PATHOFEXILE_COM_LADDERS + leagues.get(i) + LIMIT_200_OFFSET_200;
		getLeagueApiResponse(url);
		newDataset.add(tableEntries);
	}
	
	private static void isCurrentDatasetEmpty() {
		if (currentDataset.size() == 0) {
			currentDataset = newDataset;
			latestDataset = newDataset;
		}
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
        headers.add(USER_AGENT, USER_AGENT_PARAM);
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
			case "Betrayal": {
				System.out.println("getCalculatedDataset() : " +selectedLeague);
				Runtime.getRuntime().gc();
				return latestDataset.get(0);
			}
			case "Hardcore Betrayal": {
				System.out.println("getCalculatedDataset() : " +selectedLeague);
				Runtime.getRuntime().gc();
				return latestDataset.get(1);
			}
			case "SSF Betrayal": {
				System.out.println("getCalculatedDataset() : " +selectedLeague);
				Runtime.getRuntime().gc();
				return latestDataset.get(2);
			}
			case "SSF Betrayal HC": {
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
