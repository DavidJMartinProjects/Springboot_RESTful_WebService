package com.project.business;

public class Main {

	public static void main(String[] args) {
		LeagueService webController = new LeagueService();
		webController.getLeagueDetails("SSF Incursion HC");
	}
}
