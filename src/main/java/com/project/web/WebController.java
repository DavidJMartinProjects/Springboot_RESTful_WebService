package com.project.web;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.business.LeagueService;
import com.project.domain.LadderTableEntry;

@RestController
@RequestMapping(value = "/ladders")
public class WebController {	

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })	
    public List<LadderTableEntry> greeting(@RequestParam(value="league", defaultValue="Standard") String league) {
        return new LeagueService().getLeagueDetails(league);
    }

}
