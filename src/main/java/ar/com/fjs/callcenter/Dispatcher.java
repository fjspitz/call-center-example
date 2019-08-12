package ar.com.fjs.callcenter;

import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * La clase Dispatcher es la responsable de asignar 
 * las llamadas entrantes a empleados disponibles. 
 * 
 * @author Fernando J. Spitz
 *
 */
public class Dispatcher extends ThreadPoolExecutor {
	
	private static final long KEEP_ALIVE_TIME = 60;
	
	private int lostCalls = 0;
	
	/**
	 * Cola para uso interno. Este tipo de cola permite que los empleados
	 * sean extraidos de la cola según su role, dándole prioridad primero 
	 * a los operadores, luego supervisores y finalmente los directores.
	 */
	private PriorityQueue<Employee> employees;
	
	public Dispatcher(PriorityQueue<Employee> employees) {
		super(employees.size(), employees.size(), 
				KEEP_ALIVE_TIME, 
				TimeUnit.SECONDS, 
				new ArrayBlockingQueue<Runnable>(employees.size()), 
				Executors.defaultThreadFactory());
		this.employees = employees;
	}

	/**
	 * Este método es el encargado de tomar un worker 
	 * de la cola y asignarlo a una llamada.
	 * 
	 * @throws InterruptedException 
	 */
	public void dispatchCall(Call call) throws InterruptedException {
		
		Employee e = employees.poll();
		
		if (e != null) {
			e.setAssignedCall(call);
	
			execute(e);
		} else {
			System.out.format(">> | Call ID: %3d | Hora: %s | %-17s  |%n", call.getId(), call.getIncomingTime(), "*** EN ESPERA ***");
			
			while (employees.isEmpty()) {
				TimeUnit.SECONDS.sleep(1);
			}
			
			e = employees.poll();
			
			e.setAssignedCall(call);
	
			execute(e);
		}
	}
	
	@Override
	protected synchronized void afterExecute(Runnable r, Throwable t) {
		Employee e = (Employee) r;
		employees.add(e);
		
		Call call = e.getAssignedCall();
		
		System.out.format("   | Call ID: %3d | Hora: %s | Duracion: %3d seg. | Inicio: %s | Fin: %s | Liberado    : %s%n", 
				call.getId(), call.getIncomingTime(), call.getDuration(), call.getStartTime(), call.getEndTime(), call.getEmployeeAssigned());
	}
	
	public int getLostCalls() {
		return lostCalls;
	}
	
	public PriorityQueue<Employee> getEmployees() {
		return employees;
	}
}
