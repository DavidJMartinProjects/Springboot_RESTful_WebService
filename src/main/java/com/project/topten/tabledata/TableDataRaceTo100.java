package com.project.topten.tabledata;

import com.project.topten.pojo.LadderResponse;

public class TableDataRaceTo100 extends LadderResponse  {
	
	String charName;
	String ascendancy;
	String level;	
	boolean dead;
	
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	@Override
	public String toString() {
		return "TableDataRaceTo100 [charName=" + charName + ", ascendancy=" + ascendancy + ", level=" + level
				+ ", dead=" + dead + ", league=" + league + ", rank=" + rank + "]";
	}

}
