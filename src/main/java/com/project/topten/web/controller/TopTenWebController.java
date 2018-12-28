package com.project.topten.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.domain.topten.business.TopTenApiRequestService;
import com.project.topten.pojo.TopTenLadderResponse;

@CrossOrigin
@RestController
@RequestMapping(value = "/top-ten")
public class TopTenWebController {
		
	private String urlTopTepDelve = "http://www.pathofexile.com/api/ladders?offset=0&limit=10&id=SSF+Betrayal+HC&type=league&sort=depthsolo";
	private String urlTopTenUberLab = "http://www.pathofexile.com/api/ladders?offset=0&limit=20&id=SSF+Betrayal+HC&type=labyrinth&difficulty=1&start=1545782400&_=1545794104445";
	
	@RequestMapping(value = "/delve", method = RequestMethod.GET, produces = { "application/json" })
	public TopTenLadderResponse getDelveLadderData() throws InterruptedException {
		System.out.println("Processing request to to-ten data");
		System.out.println("Web Controller Response : ");
		return TopTenApiRequestService.topTenLadderResponse;
	}

}
	