package com.project.topten.tabledata;

import com.project.topten.pojo.LadderResponse;

public class TableDataUberLabTopTen extends LadderResponse  {
		
	String charName;
	String ascendancy;
	String time;
	
	public String getCharName() {
		return charName;
	}
	public void setCharName(String charName) {
		this.charName = charName;
	}
	public String getAscendancy() {	
		return ascendancy;
	}
	public void setAscendancy(String ascendancy) {
		this.ascendancy = ascendancy;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "TableDataUberLabTopTen [charName=" + charName + ", ascendancy=" + ascendancy + ", time=" + time
				+ ", league=" + league + ", rank=" + rank + "]";
	}
}
