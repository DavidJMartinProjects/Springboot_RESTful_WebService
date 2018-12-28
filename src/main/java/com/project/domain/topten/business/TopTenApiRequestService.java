package com.project.domain.topten.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.topten.pojo.TopTenLadderResponse;
import com.project.topten.pojo.TopTenLeague;
import com.project.topten.util.TopTenUtil;

@Component
public class TopTenApiRequestService {

	@Autowired
	TopTenLaddersService topTenLaddersService;

	@Autowired
	TopTenUtil topTenUtil;

	public static TopTenLadderResponse topTenLadderResponse = new TopTenLadderResponse();

	static TopTenLeague topTenLeague;

		public TopTenLadderResponse getTopTenLaddersDataSet() throws InterruptedException {
		return topTenLadderResponse;
	}

	public void getTopTenLadderData() throws InterruptedException {
		List<String> leaguesList = topTenUtil.getLeagueNamesAsList();
		leaguesList.forEach(System.out::println);

		for (String league : leaguesList) {
			TopTenLeague topTenLeague = topTenLaddersService.getLeagueTopTenData(league);
			mapObject(league, topTenLeague);
		}
	}

	private void mapObject(String league, TopTenLeague topTenLeague_) {
		TopTenLeague topTenLeague = new TopTenLeague();
		topTenLeague = topTenLeague_;
		if (league.equals("Betrayal")) {
			System.out.println("top-ten : adding Betrayal league data");
			topTenLadderResponse.addToLeagueStd(topTenLeague);
		} else if (league.equals("Hardcore Betrayal")) {
			System.out.println("top-ten : adding Hardcore Betrayal league data");
			topTenLadderResponse.addToLeagueHC(topTenLeague);
		} else if (league.equals("SSF Betrayal")) {
			System.out.println("top-ten : adding SFF Betrayal league data");
			topTenLadderResponse.addToLeagueSFF(topTenLeague);
		} else if (league.equals("SSF Betrayal HC")) {
			System.out.println("top-ten : adding SSF Betrayal HC league data");
			topTenLadderResponse.addToLeagueHCSFF(topTenLeague);
		}

	}

}
