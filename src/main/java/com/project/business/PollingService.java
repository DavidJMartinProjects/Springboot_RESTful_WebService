package com.project.business;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Service;

import com.project.domain.datatable.LadderTableEntry;

// this will be a singleton class
@Service
public class PollingService {

	private static PollingService pollingService = new PollingService();
	private Timer timer = new Timer();
	
	

	PollingService() {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Poll Request Recieved.");
				pollLatestDataset();
				System.out.println("Poll Request Complete.");
			}
		}, 2000, 5 * 60 * 1000);

	}
	
	public static PollingService getPollingService() {
		return pollingService;
	}

	public static void pollLatestDataset() {
		try {
			DatasetService.calculateDataSet();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static List<LadderTableEntry> getLeagueDataSet(String selectedLeague) {
		return DatasetService.getCalculatedDataset(selectedLeague);
	}

}
