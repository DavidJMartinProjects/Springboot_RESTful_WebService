package com.project.business;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.domain.datatable.LadderTableEntry;
import com.project.domain.ladder.Entries;
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

		for (int i = 0; i < leagues.size(); i++) {
			tableEntries = new ArrayList<>();
			String url = "http://api.pathofexile.com/ladders/" + leagues.get(i) + "?limit=200";
			restTemplate = new RestTemplate();
			response = restTemplate.getForEntity(url, Ladder.class);			

			for (Entries anEntry : response.getBody().getEntries()) {
				entry = new LadderTableEntry();
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
			newDataset.add(tableEntries);
			Thread.sleep(1000);
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

		for (int i = 0; i < latestDataset.size(); i++) {
			for (int j = 0; j < latestDataset.get(i).size(); j++) {
				for (int k = 0; k < 200; k++) {
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
		switch (selectedLeague) {
		
			case "Incursion Event (IRE001)": {
				System.out.println("getCalculatedDataset() : Incursion Event (IRE001)");
				return latestDataset.get(0);
			}
			case "Incursion Event HC (IRE002)": {
				System.out.println("getCalculatedDataset() : Incursion Event HC (IRE002)");
				return latestDataset.get(1);
			}
			case "SSF Incursion Event (IRE003)": {
				System.out.println("getCalculatedDataset() : SSF Incursion Event (IRE003)");
				return latestDataset.get(2);
			}
			case "SSF Incursion Event HC (IRE004)": {
				System.out.println("getCalculatedDataset() : SSF Incursion Event HC (IRE004)");
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
