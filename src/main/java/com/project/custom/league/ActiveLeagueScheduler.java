package com.project.custom.league;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ActiveLeagueScheduler {
	
	@Autowired
	ActiveLeagueService activeLeagueService;

	@Scheduled(initialDelay = 600000, fixedRate = 600000)
	public void getRedundantLeagues() {
		System.out.println(activeLeagueService.removeRedundantLeagues());
		// pass map to new leagueMaintenance entrypoint
	}

}
