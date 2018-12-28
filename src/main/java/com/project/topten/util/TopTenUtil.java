package com.project.topten.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopTenUtil {
	
	@Autowired
	LeagueNameService leagueNameService;
	
	private List<String> leagueNamesList;
	
	public List<String> getLeagueNamesAsList() {
		leagueNamesList = new ArrayList<>();
		for(Entry<String, String> entry : leagueNameService.getProperties().entrySet()) {
			leagueNamesList.add(entry.getValue());
		}
		return leagueNamesList;
	}

}
