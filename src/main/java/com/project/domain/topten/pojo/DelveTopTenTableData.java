package com.project.domain.topten.pojo;

public class DelveTopTenTableData {
	
	String rank;
	String charName;
	String ascendancy;
	String dept;
	boolean dead;
	
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
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
		return "DelveTopTenTableData [rank=" + rank + ", charName=" + charName + ", ascendancy=" + ascendancy
				+ ", dept=" + dept + ", dead=" + dead + "]";
	}
	
}
