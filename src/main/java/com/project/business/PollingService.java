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
	public static Runtime r;

	PollingService() {
		pollLatestDataset();	
	}

	public void pollLatestDataset() {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
		try {
			r = Runtime.getRuntime();
			r.gc();
			System.out.println("======== Poll Request Recieved. ========");
			DatasetService.calculateDataSet();
			System.out.println("======== Poll Request Complete. ========");
			System.out.println("Sleeping..");			
			r.gc();
			java.lang.Thread.sleep(10000);
			System.out.println("Awake..");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			}
		}, 5000, 5 * 60 * 1000);
	}

	public static List<LadderTableEntry> getLeagueDataSet(String selectedLeague) {		
		return DatasetService.getCalculatedDataset(selectedLeague);
	}

}
