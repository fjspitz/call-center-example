package ar.com.fjs.callcenter;

import java.time.LocalTime;

public class Call {
	
	private int id;
	
	private LocalTime incomingTime;
	
	private LocalTime startTime;
	
	private LocalTime endTime;
	
	private int duration;
	
	private Employee employeeAssigned;
	
	public Call(int id, LocalTime incomingTime) {
		this.id = id;
		this.incomingTime = incomingTime;
	}
	
	public Employee getEmployeeAssigned() {
		return employeeAssigned;
	}

	public void setEmployeeAssigned(Employee employeeAssigned) {
		this.employeeAssigned = employeeAssigned;
	}

	public int getId() {
		return id;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getIncomingTime() {
		return incomingTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
