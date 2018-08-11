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

	public DatasetService() throws InterruptedException {
		calculateDataSet();
	}

	public static List<List<LadderTableEntry>> getLatestDataSet() throws InterruptedException {
		leagues = currentLeagueService.getLeagues();	
		newDataset = new ArrayList<>();
		ResponseEntity<Ladder> response;
		LadderTableEntry entry;
		RestTemplate restTemplate;
		List<LadderTableEntry> tableEntries;

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
		// iterate
		String latest;
		String current;
		String difference;
		String rankDifference;
		String xpPerHour;
		String theExperience;
		String timeStamp;
		String latestRank;
		String currentRank;
		Long newXPPH, oldXPPH;
		Long newRank, oldRank;
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
						
						newDataset.get(i).get(j).setXph(xpPerHour);
						newDataset.get(i).get(j).setXphDifference(difference);
						newDataset.get(i).get(j).setRankDifference(rankDifference);
						newDataset.get(i).get(j).setExperience(theExperience);
						// set polling timestamp for current time
						timeStamp = new SimpleDateFormat(" MMM d hh:mm a").format(new Date());
//						System.out.println("timeStamp" +timeStamp);
						newDataset.get(i).get(j).setTimeStamp(timeStamp);
					}
				}
			}
		}
		latestDataset = newDataset;
	}

	public static List<LadderTableEntry> getCalculatedDataset(String selectedLeague) {
		switch (selectedLeague) {
			case "Incursion": {
				System.out.println("getCalculatedDataset() : Incursion");
				return latestDataset.get(0);
			}
			case "Hardcore Incursion": {
				System.out.println("getCalculatedDataset() : Hardcore Incursion");
				return latestDataset.get(1);
			}
			case "SSF Incursion": {
				System.out.println("getCalculatedDataset() : SSF Incursion");
				return latestDataset.get(2);
			}
			case "SSF Incursion HC": {
				System.out.println("getCalculatedDataset() : SSF Incursion HC");
				return latestDataset.get(3);
			}			
			case "Incursion Event (IRE001)": {
				System.out.println("getCalculatedDataset() : Incursion Event (IRE001)");
				return latestDataset.get(4);
			}
			case "Incursion Event HC (IRE002)": {
				System.out.println("getCalculatedDataset() : Incursion Event HC (IRE002)");
				return latestDataset.get(5);
			}
			case "SSF Incursion Event (IRE003)": {
				System.out.println("getCalculatedDataset() : SSF Incursion Event (IRE003)");
				return latestDataset.get(6);
			}
			case "SSF Incursion Event HC (IRE004)": {
				System.out.println("getCalculatedDataset() : SSF Incursion Event HC (IRE004)");
				return latestDataset.get(7);
			}
			
			default: {
				return new ArrayList<LadderTableEntry>();
			}
		}
	}
	
	public static String formatNumber(String theNumber) {	
			String number = theNumber;
			number = number.replaceAll(",", "");
			double amount = Double.parseDouble(number);
			DecimalFormat formatter = new DecimalFormat("#,###");
			return formatter.format(amount).replaceAll(",", "");
	}
	
	public static String formatXp(String theNumber) {	
		String number = theNumber;
		number = number.replaceAll(",", "");
		double amount = Double.parseDouble(number);
		DecimalFormat formatter = new DecimalFormat("#,###");
		return formatter.format(amount);
}

}
