//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.SocketException;

public class YodafyClienteUDP {

	public static void main(String[] args) {
		
		int puerto=8888;
		InetAddress direccion;
		DatagramPacket paquete=null;
		byte[] bufer = new byte[256];
		DatagramSocket socket=null;
		try{
			socket = new DatagramSocket();

			direccion = InetAddress.getByName("localhost");
			bufer="Al monte del volcan debes ir sin demora".getBytes();
			paquete = new DatagramPacket(bufer, bufer.length, direccion, puerto);
			socket.send(paquete);
			bufer = new byte[256];
			paquete = new DatagramPacket(bufer, bufer.length);
			socket.receive(paquete);

		}catch (UnknownHostException e) {
			System.err.println("Error: equipo desconocido");
		} catch (IOException e) {
			System.err.println("Error: no se pudo establecer la conexión");
		}
		System.out.println("Mensaje: "+paquete.getData());
		System.out.println("Direccion: "+paquete.getAddress());	
		System.out.println("Puerto: "+paquete.getPort());			
		socket.close();

	}
}
