package com.project.topten.pojo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TopTenLeague {
	
	private List tableDataDelve = new ArrayList<>();
	private List tableDataRaceTo100 = new ArrayList<>();
	private List tableDataUberLabTopTen = new ArrayList<>();
	
	public List getTableDataDelve() {
		return tableDataDelve;
	}
	public void setTableDataDelve(List topTenDelveTableEntries) {
		this.tableDataDelve = topTenDelveTableEntries;
	}
	public List getTableDataRaceTo100() {
		return tableDataRaceTo100;
	}
	public void setTableDataRaceTo100(List tableDataRaceTo100) {
		this.tableDataRaceTo100 = tableDataRaceTo100;
	}
	public List getTableDataUberLabTopTen() {
		return tableDataUberLabTopTen;
	}
	public void setTableDataUberLabTopTen(List tableDataUberLabTopTen) {
		this.tableDataUberLabTopTen = tableDataUberLabTopTen;
	}
	@Override
	public String toString() {
		return "TopTenLeague [tableDataDelve=" + tableDataDelve + ", tableDataRaceTo100=" + tableDataRaceTo100
				+ ", tableDataUberLabTopTen=" + tableDataUberLabTopTen + "]";
	}

	
	
	
}
