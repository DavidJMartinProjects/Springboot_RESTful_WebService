package com.project.domain.topten.business;

import static com.project.business.Constants.entity;
import static com.project.business.Constants.restTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.project.domain.topten.pojo.DelveLadder;
import com.project.domain.topten.pojo.DelveLadderResponseEntry;
import com.project.domain.topten.pojo.DelveTopTenTableData;

@Component
public class TopTenLaddersService {
	
	private List<DelveTopTenTableData> topTenDelveTableEntries = new ArrayList<>();
	
	public List<DelveTopTenTableData> getTopTenDelveData(String url) throws InterruptedException {		
		ResponseEntity<DelveLadder> response = restTemplate.exchange(url, HttpMethod.GET, entity, DelveLadder.class);
		System.out.println("response" + response.getBody().getEntries().toString());
		DelveLadder ladders =  response.getBody();
		List<DelveLadderResponseEntry> entires = ladders.getEntries();
		topTenDelveTableEntries.clear();
		AtomicInteger counter = new AtomicInteger(1);
		entires.stream()
			.forEach(e -> {
				mapResponseToEntity(e, counter.getAndIncrement());
			});
		Thread.sleep(500);
		System.out.println("TopTen Size : " + topTenDelveTableEntries.size());
		System.out.println(topTenDelveTableEntries);
		return topTenDelveTableEntries;		
	}
	
	private void mapResponseToEntity(DelveLadderResponseEntry delveLadderResponseEntry, int rankNumber) {
		DelveTopTenTableData entry = new DelveTopTenTableData();
		entry.setRank(String.valueOf(rankNumber));
		entry.setCharName(delveLadderResponseEntry.getCharacter().getName());
		entry.setAscendancy(delveLadderResponseEntry.getCharacter().getAscendancy());
		entry.setDept(String.valueOf(delveLadderResponseEntry.getCharacter().getDepth().getSolo()));
		entry.setDead(delveLadderResponseEntry.getDead());
		topTenDelveTableEntries.add(entry);
	}

}
