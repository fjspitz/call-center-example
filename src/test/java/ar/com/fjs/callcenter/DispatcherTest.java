package ar.com.fjs.callcenter;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.PriorityQueue;

import org.junit.Before;
import org.junit.Test;

public class DispatcherTest {
	private static final String INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_PERDIDAS = "Incorrecta la cantidad de llamadas perdidas";
	private static final String INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_ATENDIDAS = "Incorrecta la cantidad de llamadas atendidas";
	private static final String INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_TOTALES = "Incorrecta la cantidad de llamadas totales";
	private Dispatcher dispatcher;
	
	@Test
	public void shouldAttendOneCall() throws InterruptedException {
		Call call = new Call(1, LocalTime.now());
		
		dispatcher.dispatchCall(call);
		
		dispatcher.shutdown();
        while (!dispatcher.isTerminated()) { }
		
		assertEquals(INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_TOTALES, 1, dispatcher.getCompletedTaskCount());
		assertEquals(INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_ATENDIDAS, 1, dispatcher.getEmployees().stream().mapToInt(Employee::getAttendedCalls).sum());
		assertEquals(INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_PERDIDAS, 0, dispatcher.getLostCalls());
	}
	
	@Test
	public void shouldAttendTenCallsWhenThereAreTenEmployees() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			dispatcher.dispatchCall(new Call((i+1), LocalTime.now()));
		}
		
		dispatcher.shutdown();
		while (!dispatcher.isTerminated()) { }
		
		assertEquals(INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_TOTALES, 10, dispatcher.getCompletedTaskCount());
		assertEquals(INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_ATENDIDAS, 10, dispatcher.getEmployees().stream().mapToInt(Employee::getAttendedCalls).sum());
		assertEquals(INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_PERDIDAS, 0, dispatcher.getLostCalls());
	}
	
	@Test
	public void shouldWaitForTheEleventhCallWhenThereAreTenAgentsAndElevenCalls() throws InterruptedException {
		for (int i = 0; i < 11; i++) {
			dispatcher.dispatchCall(new Call((i+1), LocalTime.now()));
		}
		
		dispatcher.shutdown();
		while (!dispatcher.isTerminated()) { }
		
		assertEquals(INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_TOTALES, 11, dispatcher.getCompletedTaskCount());
		assertEquals(INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_ATENDIDAS, 11, dispatcher.getEmployees().stream().mapToInt(Employee::getAttendedCalls).sum());
		assertEquals(INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_PERDIDAS, 0, dispatcher.getLostCalls());
	}
	
	@Test
	public void shouldProcessOkFiftyCalls() throws InterruptedException {
		for (int i = 0; i < 50; i++) {
			dispatcher.dispatchCall(new Call((i+1), LocalTime.now()));
		}
		
		dispatcher.shutdown();
		while (!dispatcher.isTerminated()) { }
		
		assertEquals(INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_TOTALES, 50, dispatcher.getCompletedTaskCount());
		assertEquals(INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_ATENDIDAS, 50, dispatcher.getEmployees().stream().mapToInt(Employee::getAttendedCalls).sum());
		assertEquals(INCORRECTA_LA_CANTIDAD_DE_LLAMADAS_PERDIDAS, 0, dispatcher.getLostCalls());
	}
	
	@Before
	public void setUp() {
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
		
		PriorityQueue<Employee> employees = new PriorityQueue<>(10);
		
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
		
		dispatcher = new Dispatcher(employees);
	}
}
