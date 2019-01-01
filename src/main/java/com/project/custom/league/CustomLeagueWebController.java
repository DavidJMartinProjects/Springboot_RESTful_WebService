package com.project.custom.league;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.domain.datatable.LadderTableEntry;

@CrossOrigin
@RestController
@RequestMapping(value = "/custom-league")
public class CustomLeagueWebController {
	
	@Autowired
	CustomLeagueRequestService customLeagueRequestService;
	
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public List<LadderTableEntry> getLadderRankings(@RequestParam(value = "leagueId") String leagueId, @RequestParam(value = "leagueName") String leagueName) throws InterruptedException {
		return customLeagueRequestService.getCurrentDataset(leagueId, leagueName);
	}

}
