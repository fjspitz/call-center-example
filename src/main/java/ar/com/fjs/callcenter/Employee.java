package ar.com.fjs.callcenter;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class Employee implements Runnable, Comparable<Employee> {
	
	private String name;
	
	private Role role;
	
	private Call assignedCall;
	
	private int attendedCalls;
	
	public Employee(String name, Role role) {
		this.name = name;
		this.role = role;
	}

	@Override
	public void run() {
		if (assignedCall != null) {
			answer();
		}
	}
	
	private void answer() {
		assignedCall.setStartTime(LocalTime.now());
		assignedCall.setDuration(DispatcherUtils.randomDurationCall());
		assignedCall.setEmployeeAssigned(this);
		
		System.out.format(">> | Call ID: %3d | Hora: %s | Duración: %3s seg. | Inicio: %s | Fin: %12s | Atendida por: %s%n",
				assignedCall.getId(), assignedCall.getIncomingTime(), "-", assignedCall.getStartTime(), "-", this.toString());
		
		try {
			TimeUnit.SECONDS.sleep(assignedCall.getDuration());
			
			assignedCall.setEndTime(LocalTime.now());
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public String toString() {
		return name + " [" + role + "]";
	}

	@Override
	public int compareTo(Employee o) {
		return this.role.compareTo(o.role);
	}

	public int getAttendedCalls() {
		return attendedCalls;
	}

	public Call getAssignedCall() {
		return assignedCall;
	}

	public void setAssignedCall(Call assignedcall) {
		this.assignedCall = assignedcall;
		attendedCalls++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (role != other.role)
			return false;
		return true;
	}

	public Role getRole() {
		return role;
	}
}
