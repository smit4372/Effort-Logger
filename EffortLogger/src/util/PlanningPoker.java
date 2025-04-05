package util;

import java.util.List;

public class PlanningPoker {
	
	public static Integer getScore(String startTime, String endTime) {
		Integer score = calculateScore(startTime, endTime);
		List<Integer> allScores = Database.getCategoryScores();
		
		
		for (int i = 0; i < allScores.size(); i++) {
			score += allScores.get(i);
		}
		
		return score / (allScores.size() + 1);
	}
	
	private static int calculateScore(String startTime, String endTime) {
		int currScore = calculateTotalMinutes(startTime, endTime);
		double percentage = (double) currScore / (24 * 60) * 100;
        int score = (int) (percentage / 10);

        return Math.min(Math.max(score, 1), 10);
	}

	
	private static int calculateTotalMinutes(String startTime, String endTime) {
		return convertToMinutes(endTime) - convertToMinutes(startTime);
	}
	
	private static int convertToMinutes(String timestamp) {
		String[] parts = timestamp.split(":");
		
		int hours = Integer.parseInt(parts[0]);
		int minutes = Integer.parseInt(parts[1]);
		
		return (hours * 60) + minutes;
	}

	public static void main(String[] args) {
		System.out.print(getScore("10:30", "18:45"));
	}

}
