package com.project.topten.tabledata;

import com.project.topten.pojo.LadderResponse;

public class TableDataDelveTopTen extends LadderResponse {
	

	String charName;
	String ascendancy;
	String dept;
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
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	@Override
	public String toString() {
		return "TableDataDelveTopTen [charName=" + charName + ", ascendancy=" + ascendancy + ", dept=" + dept
				+ ", dead=" + dead + ", league=" + league + ", rank=" + rank + "]";
	}
	
}
