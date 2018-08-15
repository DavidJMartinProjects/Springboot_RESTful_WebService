package com.project.business;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.tomcat.jni.Thread;
import org.springframework.stereotype.Service;

import com.project.domain.datatable.LadderTableEntry;

// this will be a singleton class
@Service
public class PollingService {

	private static Timer timer = new Timer();	
	private static int counter = 9;

	PollingService() {
		pollLatestDataset();
	}

	public void pollLatestDataset() {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					counter++;
					Runtime.getRuntime().gc();					
					if(counter == 10) {
						System.out.println("======== Poll Request Recieved. ========");
						DatasetService.calculateDataSet();
						System.out.println("======== Poll Request Complete. ======== Counter : " +counter);
						System.out.println("Sleeping..");
						java.lang.Thread.sleep(10000);
						System.out.println("Awake..");
						counter = 0;
					}
					Runtime.getRuntime().gc();	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 5000, 1 * 30 * 1000);
	}

	public static List<LadderTableEntry> getLeagueDataSet(String selectedLeague) {
		return DatasetService.getCalculatedDataset(selectedLeague);
	}

}
