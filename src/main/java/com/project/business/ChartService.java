package com.project.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.domain.chart.LevelChartData;
import com.project.domain.ladder.Entries;
import com.project.domain.ladder.Ladder;

public class ChartService {
	
	public Map<String, String> getLevelChartData(String league) {
		
		List<LevelChartData> theDataList = new ArrayList<>();		
		Map<String, String> levelCount = new TreeMap<>();	
		
		String url = "http://api.pathofexile.com/ladders/"+league+"?limit=200";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Ladder> response = restTemplate.getForEntity(url, Ladder.class);				
		
		for (Entries anEntry : response.getBody().getEntries()) {					
			if(levelCount.containsKey(anEntry.getCharacter().getLevel())) {
				Integer theCount = Integer.parseInt(levelCount.get(anEntry.getCharacter().getLevel()));
				theCount++;
				levelCount.put(anEntry.getCharacter().getLevel(),Integer.toString(theCount));
			} else {
				levelCount.put(anEntry.getCharacter().getLevel(), "1");
			}
		}		
		
		//convert to ArrayList for javascript		
		for(Entry<String, String> entry : levelCount.entrySet()) {
			LevelChartData levelChartData = new LevelChartData();
			levelChartData.setLevel(entry.getKey().toString());
			levelChartData.setFrequency((entry.getValue().toString()));
			theDataList.add(levelChartData);
		}
		return levelCount;
	}	
	
}
	



