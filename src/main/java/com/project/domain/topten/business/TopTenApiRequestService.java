package com.project.domain.topten.business;

import static com.project.business.Constants.USER_AGENT;
import static com.project.business.Constants.USER_AGENT_PARAM;
import static com.project.business.Constants.restTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.project.topten.pojo.DelveLadderResponseEntry;
import com.project.topten.pojo.DelveLadderResponseLadder;
import com.project.topten.pojo.TopTenLadderResponse;
import com.project.topten.pojo.TopTenLeague;
import com.project.topten.util.TopTenUtil;

@Component
public class TopTenApiRequestService {

	@Autowired
	TopTenLaddersService topTenLaddersService;

	@Autowired
	TopTenUtil topTenUtil;
	
	public HttpEntity<String> entity;
	public HttpHeaders headers = new HttpHeaders();

	public static TopTenLadderResponse topTenLadderResponse = new TopTenLadderResponse();

	static TopTenLeague topTenLeague;

	public TopTenLadderResponse getTopTenLaddersDataSet() throws InterruptedException {
		return topTenLadderResponse;
	}

	public void getTopTenLadderData() throws InterruptedException {
		System.out.println("Calling poe-db for top-ten tables.");
		String url = "https://poe-ladder-db.herokuapp.com/top-ten/delve";
		ResponseEntity<TopTenLadderResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				entity, TopTenLadderResponse.class);
		topTenLadderResponse = responseEntity.getBody();
	}

//	private void mapObject(String league, TopTenLeague topTenLeague_) {
//		TopTenLeague topTenLeague = new TopTenLeague();
//		topTenLeague = topTenLeague_;
//		if (league.equals("Betrayal")) {
//			System.out.println("top-ten : adding Betrayal league data");
//			topTenLadderResponse.addToLeagueStd(topTenLeague);
//		} else if (league.equals("Hardcore Betrayal")) {
//			System.out.println("top-ten : adding Hardcore Betrayal league data");
//			topTenLadderResponse.addToLeagueHC(topTenLeague);
//		} else if (league.equals("SSF Betrayal")) {
//			System.out.println("top-ten : adding SFF Betrayal league data");
//			topTenLadderResponse.addToLeagueSFF(topTenLeague);
//		} else if (league.equals("SSF Betrayal HC")) {
//			System.out.println("top-ten : adding SSF Betrayal HC league data");
//			topTenLadderResponse.addToLeagueHCSFF(topTenLeague);
//		}
//
//	}
	
	private void setupHttpEntityHeaders() {
		headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add(USER_AGENT, USER_AGENT_PARAM);
		entity = new HttpEntity<String>("parameters", headers);
	}

}
