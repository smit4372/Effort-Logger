package dataclass;

public class Log {

	private String name;
	private String date;
	private String status;
	private String startTime;
	private String endTime;
	private String projectName;
	private String category;
	private Integer storyPoints;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setStoryPoints(int storyPoints) {
		this.storyPoints = storyPoints;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getStatus() {
		return this.status;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public String getProjectName() {
		return this.projectName;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public Integer getStoryPoints() {
		return this.storyPoints;
	}

}
