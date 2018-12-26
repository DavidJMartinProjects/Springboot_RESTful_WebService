package com.project.domain.topten.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.domain.topten.pojo.DelveTopTenTableData;

@CrossOrigin
@RestController
@RequestMapping(value = "/top-ten")
public class TopTenWebController {

	@Autowired
	TopTenLaddersService topTenLaddersService;
	
	private String urlTopTepDelve = "http://www.pathofexile.com/api/ladders?offset=0&limit=10&id=SSF+Betrayal+HC&type=league&sort=depthsolo";
	private String urlTopTenUberLab = "http://www.pathofexile.com/api/ladders?offset=0&limit=20&id=SSF+Betrayal+HC&type=labyrinth&difficulty=1&start=1545782400&_=1545794104445";
	
	@RequestMapping(value = "/delve", method = RequestMethod.GET, produces = { "application/json" })
	public List<DelveTopTenTableData> getDelveLadderData() throws InterruptedException {
		return topTenLaddersService.getTopTenDelveData(urlTopTepDelve);
	}

}
	