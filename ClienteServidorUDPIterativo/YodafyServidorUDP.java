import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorUDP {

	public static void main(String[] args) {
	
		// Puerto de escucha
		int puerto=8888;
		InetAddress direccion;

		DatagramPacket paquete=null;
		DatagramPacket paqueteEnvio=null;
		byte[] bufer = new byte[256];
		// Socket del servidor
		DatagramSocket socketServidor=null;

		try {
			socketServidor = new DatagramSocket(puerto);
			
			do{
				paquete = new DatagramPacket(bufer, bufer.length);
				socketServidor.receive(paquete);			
				// Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
				// argumento el nuevo socket, para que realice el procesamiento
				// Este esquema permite que se puedan usar hebras más fácilmente.
				ProcesadorYodafy procesador=new ProcesadorYodafy(socketServidor,paquete);
				paqueteEnvio=procesador.procesa();				
				socketServidor.send(paqueteEnvio);
				

			}while(true);

		}catch (UnknownHostException e) {
			System.err.println("Error: equipo desconocido");
		} catch (IOException e) {
			System.err.println("Error: no se pudo establecer la conexión");
		}
		socketServidor.close();

	}

}


