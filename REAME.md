# Call Center Example

Se pretende emular un **Call Center** que debe permitir la atención en simultáneo de al menos 10 llamadas telefónicas entre sus empleados  (operadores, supervisores y directores). 

Esto implica la ejecución con concurrencia de múltiples hilos de ejecución de tareas.

Desde el aspecto técnico, se puede pensar el planteo como un pool de workers fijo a la espera de trabajo por realizar. En este caso, el trabajo es atender una llamada telefónica que puede durar entre 5 y 10 segundos.

El ser necesaria la atención de los llamados entrantes de manera simultánea, se debe implementar un esquema de hilos para permitir la concurrencia de múltiples llamados.

Una manera de resolver esto, es utilizar el `Executor Framework`, basado en la interfaz y subinterfaz `Executor` y `ExecutorService`, respectivamente, y la clase que implementa ambas interfaces, **`ThreadPoolExecutor`** del paquete `java.util.concurrent`.

Esta clase permite definir diferentes implementaciones de pool de Threads.

La clase `Dispatcher` será la responsable de implementar el llamado al método `dispatchCall`, que deriva a un agente la atención de una llamada entrante.

Al momento de construir la clase `Dispatcher`, ésta debe ser inicializada con un parámetro, que es una implementación de la interfaz `Queue`.

Existen diferentes implementaciones. La que voy a utilizar es `PriorityQueue`, ya que ésta variante me permite aprovechar el ordenamiento natural de objetos que implementen la interfaz *Comparable*. 

Esto me permite definir de que manera se van “des-encolando” los elementos de la cola, en este caso, los agentes que responden a los llamados telefónicos. 

El orden en que dichos agentes van saliendo de la cola para atender los llamados, se concreta a partir de la comparación de la propiedad `role` de cada agente, siendo el orden de prioridad:  primero los operadores, luego los supervisores y por último los directores.

`Employee` es la clase que representa a un empleado, siendo que el mismo puede tener un rol, que es un `Enumeration`, para representar los roles de **Operador**, **Supervisor** o **Director**.

Se crea una clase de utilidad, `DispatcherUtils` para manejar el tiempo aleatorio para cada llamada.

## Extra/Plus

Se presenta como solución al problema, cuando no hay empleados libres, que la llamada quede en un ciclo de espera, donde se comprueba cada 1 segundo si se ha liberado algún empleado. En cuanto se encuentra un empleado libre, se le asigna la llamada.

La misma solución aplica cuando entran más de 10 llamados simultáneos.

## Aclaración

Dada la simplicidad del proyecto, se utiliza directamente la salida estándar para imprimir en la consola la salida del proceso. No se utiliza ningún sistema de por simplicidad.

Para definir las clases, interfaces, etc. se utilizó el idioma inglés. Los comentarios y salidas de consola están en castellano. 
