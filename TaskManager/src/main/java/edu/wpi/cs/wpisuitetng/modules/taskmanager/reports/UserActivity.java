package edu.wpi.cs.wpisuitetng.modules.taskmanager.reports;

public class UserActivity {
	
	private int importance;
	private String name;
	
	public UserActivity(String username) {
		this.importance = 0;
		this.name = username;
	}
	
	public int getImportance() {
		return this.importance;
	}
	
	public void setImportance(int importance) {
		this.importance = importance;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String username) {
		this.name = username;
	}
	
	@Override
	public String toString() {
		return (this.name + ": " + this.importance);
	}
	
	@Override 
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof UserActivity) {
			if (this.name.equals(((UserActivity)o).getName())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	public void copyFrom(UserActivity user) {
		this.importance = user.importance;
	}
	
}

