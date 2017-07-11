import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorConcurrente {

	public static void main(String[] args) {
	
		// Puerto de escucha
		int port=8989;
		// array de bytes auxiliar para recibir o enviar datos.
		byte []buffer=new byte[256];
		// Número de bytes leídos
		int bytesLeidos=0;
		// Socket del servidor
		ServerSocket serverSocket;
		
		try {
			// Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
			serverSocket= new ServerSocket(port);
			
			// Conexion del servidor
			Socket socketConexion = null;

			// Mientras ... siempre!
			do {
				
				// Aceptamos una nueva conexión con accept()
				try {
				 	socketConexion = serverSocket.accept();
				} catch (IOException e) {
				 	System.out.println("Error: no se pudo aceptar la conexión solicitada");
				}
				new Hebrita(socketConexion).start();
				
				
			} while (true);
			
		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

}


