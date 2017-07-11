//
// ProcesadorPeliculas
// (CC) Emilio Chica Jiménez e Isabel Belmonte Parra , 2015
//
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class ProcesadorPeliculas {
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private Socket socketServicio;
	// stream de lectura (por aquí se recibe lo que envía el cliente)
	private InputStream inputStream;
	// stream de escritura (por aquí se envía los datos al cliente)
	private OutputStream outputStream;
	
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorPeliculas(Socket socketServicio) {
		this.socketServicio=socketServicio;
		random=new Random();
	}
	
	
	// Aquí es donde se realiza el procesamiento realmente:
	void procesa(){
		
		// Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
		String usuariopass;
		String peli;
		String respuesta;
		String sesion;
		
		try {
			// Obtiene los flujos de escritura/lectura
			PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
			BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));	
			do{
				outPrinter.println("usuario:contraseña\n");

				usuariopass = inReader.readLine();
			
				respuesta=compruebaUserPass(usuariopass);
			}while(respuesta.compareTo("verificado")!=0);
			//Mensaje 200: "Verificado"
			outPrinter.println("verificado\n");
			
			do{
				//Mensaje 201: "Elige la película: Star Wars VII, Moby Dick"
				outPrinter.println("Elige la película: Star Wars VII, Moby Dick\n");

				peli = inReader.readLine();
				respuesta =eligePeli(peli);
			}while(respuesta.compareTo("seleccionada")!=0);
			//Mensaje 202: “seleccionada”
			outPrinter.println("seleccionada");
			do{
				if(peli.compareTo("Star Wars VII")==0){
					//Mensaje 203: "Elige la sesión: 22:00, 23:00"
					outPrinter.println("Elige la sesión: 22:00, 23:00\n");
					sesion = inReader.readLine();
					respuesta =eligeSesionS(sesion);
				}else{
					//Mensaje 204: "Elige la sesión: 17:00, 19:00"
					outPrinter.println("Elige la sesión: 17:00, 19:00\n");
					sesion = inReader.readLine();
					respuesta =eligeSesionM(sesion);
				}
			}while(respuesta.compareTo("seleccionada")!=0);
			//Mensaje 205: "Seleccionada"
			outPrinter.println("seleccionada\n");
			//Mensaje 206: "Disfrute de su pelicula aqui tiene el codigo 123456789 de su entrada para:" + peli + " Sesión: " +sesion"
			outPrinter.println("Disfrute de su pelicula aqui tiene el codigo 123456789 de su entrada para:\n" + peli + "  Sesión: " +sesion);
			
		} catch (IOException e) {
			System.err.println("Error al obtener los flujso de entrada/salida.");
		}

	}
	public String eligeSesionS(String sesion){
		if(sesion.compareTo("22:00")==0 || sesion.compareTo("23:00")==0){
			return "seleccionada";
		}else
			return "sesion incorrecta";
		
	} 
	public String eligeSesionM(String sesion){
		if(sesion.compareTo("17:00")==0 || sesion.compareTo("19:00")==0){
			return "seleccionada";
		}else
			return "sesion incorrecta";
		
	} 
	public String eligePeli(String peli){
		//if(peli!=null){
		if(peli.compareTo("Star Wars VII")==0 || peli.compareTo("Moby Dick")==0){
			return "seleccionada";
		}else
			return "pelicula incorrecta";
		//}return "peli incorrect";
		
	} 

	public String compruebaUserPass(String usuariopass){
		if(usuariopass.compareTo("fr:2015")==0){
			return "verificado";
		}else
			return "no verificado";
		
	} 
}
