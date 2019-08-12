package ar.com.fjs.callcenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.PriorityQueue;

/**
 * Clase principal que ejecuta la aplicación.
 *
 * @author Fernando J. Spitz
 */
public class App {
	
	private static final int TOTAL_CALLS = 1;
	
	public static void main(String[] args) throws InterruptedException {
		Dispatcher dispatcher;
		
		DateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
		
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		
		PriorityQueue<Employee> employees = asignarEmpleadosAlPool();
		dispatcher = new Dispatcher(employees);
		
		for (int i = 0; i < TOTAL_CALLS; i++) {
			Call call = new Call((i+1), LocalTime.now());
            dispatcher.dispatchCall(call);
        }
		
		dispatcher.shutdown();
        while (!dispatcher.isTerminated()) { }
        
        endTime = System.currentTimeMillis();
        
        System.out.println("Inicio  : " + sdf.format(startTime));
        System.out.println("Fin     : " + sdf.format(endTime));
        System.out.println("Duración: " + ((endTime - startTime) / 1000) + " seg.");
        
        int atendidas = dispatcher.getEmployees().stream().mapToInt(Employee::getAttendedCalls).sum();
        
        System.out.format("Total llamadas: %d, Atendidas: %d, Perdidas: %d", 
        		dispatcher.getCompletedTaskCount(),
        		atendidas,
        		dispatcher.getLostCalls());
	}
	
	/**
	 * Método estático que permite cargar un pool de empleados para la ejecución
	 * del programa. En este caso se cargan diez empleados, con sus diferentes 
	 * roles, para emular la atención del call center.
	 * 
	 * @return Una cola de tipo PriorityQueue.
	 */
	private static PriorityQueue<Employee> asignarEmpleadosAlPool() {
		
		PriorityQueue<Employee> employees = new PriorityQueue<>(10);
		
		Employee fernando = new Employee("Fernando", Role.OPERADOR);
		Employee luis = new Employee("Luis", Role.OPERADOR);
		Employee alejandro = new Employee("Alejandro", Role.DIRECTOR);
		Employee alfonso = new Employee("Diego", Role.SUPERVISOR);
		Employee alberto = new Employee("Alberto", Role.OPERADOR);
		Employee stella = new Employee("Stella Maris", Role.OPERADOR);
		Employee cristian = new Employee("Cristian", Role.OPERADOR);
		Employee norberto = new Employee("Norberto", Role.OPERADOR);
		Employee nicolas = new Employee("Nico", Role.OPERADOR);
		Employee pablo = new Employee("Pablito", Role.OPERADOR);
		
		employees.add(alfonso);
		employees.add(luis);
		employees.add(fernando);
		employees.add(alejandro);
		employees.add(alberto);
		employees.add(stella);
		employees.add(cristian);
		employees.add(norberto);
		employees.add(nicolas);
		employees.add(pablo);
		
		return employees;
	}
}
