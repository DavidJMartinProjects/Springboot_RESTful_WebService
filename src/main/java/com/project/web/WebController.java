package com.project.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.domain.Entries;
import com.project.domain.MyPojo;

public class WebController {

	public void getPlayerDetails() {
		String url = "http://api.pathofexile.com/ladders/SSF Incursion HC?offset=1&limit=10";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<MyPojo> response = restTemplate.getForEntity(url, MyPojo.class);

		List<Entries> entries = new ArrayList<>();

		for (Entries anEntry : response.getBody().getEntries()) {
			entries.add(anEntry);
		}

		System.out.println(entries.size());

		for (Entries anEntry : entries) {
			System.out.println("Character Name : " +anEntry.getCharacter().getName());
			System.out.println("Level : " +anEntry.getCharacter().getLevel());
			System.out.println("Challenges : " +anEntry.getAccount().getChallenges().getTotal());
			System.out.println("Experience : " +anEntry.getCharacter().getExperience());
			System.out.println();
		}
	}

}
