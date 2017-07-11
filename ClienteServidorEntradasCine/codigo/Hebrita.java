import java.net.Socket;
// Ejemplo de hebra para servidores concurrentes: Hebrita.java
//
// La clase creada debe extender "Thread".
public class Hebrita extends Thread{
	 // Creamos un objeto de la clase ProcesadorPeliculas, pasándole como 
	 // argumento el nuevo socket, para que realice el procesamiento
	 // Este esquema permite que se puedan usar hebras más fácilmente.
	 ProcesadorPeliculas procesador;

		
	 Hebrita(Socket socketConexion){
	 	procesador=new ProcesadorPeliculas(socketConexion);
	 }
	 // El contenido de este método se ejecutará tras llamar al
	 // método "start()". Se trata del procesamiento de la hebra.
	 public void run() {
		 procesador.procesa();
	 }
}
