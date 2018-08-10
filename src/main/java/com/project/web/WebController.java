package com.project.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.business.ChartService;
import com.project.business.PollingService;
import com.project.domain.chart.LevelChartData;
import com.project.domain.datatable.LadderTableEntry;

@CrossOrigin
@RestController
@RequestMapping(value = "/ladders")
public class WebController {

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public List<LadderTableEntry> getLadderRankings(@RequestParam(value = "league") String league) {
		// return new LeagueService().getLeagueDetails(league);
		return PollingService.getPollingService().getLeagueDataSet(league);
	}

	@RequestMapping(value = "/charts", method = RequestMethod.GET, produces = { "application/json" })
	public List<LevelChartData> getlevelChartData(@RequestParam(value = "league") String league) {
		return new ChartService().getLevelChartData(league);
	}

}
