package com.project.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
						Date date1 = new Date();
						System.out.println("****************************************");
						System.out.println("======== Poll Request Recieved. ========");
						DatasetService.calculateDataSet();
						Date date2 = new Date();
						computeDiff(date1, date2);						
						System.out.println("======== Poll Request Complete. ========");						
						System.out.println("Processing Time : "+computeDiff(date1, date2).toString());
						System.out.println("****************************************");						
						java.lang.Thread.sleep(10000);						
						counter = 0;
					}
					Runtime.getRuntime().gc();	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 5000, 1 * 2 * 1000);
	}

	public static List<LadderTableEntry> getLeagueDataSet(String selectedLeague) {
		return DatasetService.getCalculatedDataset(selectedLeague);
	}
	
	public static Map<TimeUnit,Long> computeDiff(Date date1, Date date2) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
	    Collections.reverse(units);
	    Map<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
	    long milliesRest = diffInMillies;
	    for ( TimeUnit unit : units ) {
	        long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
	        long diffInMilliesForUnit = unit.toMillis(diff);
	        milliesRest = milliesRest - diffInMilliesForUnit;
	        result.put(unit,diff);
	    }
	    return result;
	}

}
