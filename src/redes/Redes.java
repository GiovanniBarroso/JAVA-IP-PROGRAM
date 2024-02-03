package redes;
import java.util.*;
public class Redes {

	public static void main(String[] args) {
		Scanner sn = new Scanner(System.in);

		boolean salir = false;

		// Switch para escoger una opción
		while (!salir) {
			try {
				System.out.println("PROGRAMA PARA CALCULAR PUERTOS IP:");
				System.out.println("1. Calcular tus direcciones IP");
				System.out.println("2. Salir del programa");
				System.out.print("Seleccione una opción: ");

				int opcion = Integer.parseInt(sn.next());
				sn.nextLine();

				switch (opcion) {
				case 1:
					procesarIP(sn);
					break;
				case 2:
					salir = true;
					System.out.println("Cerrando el programa. Ciao!");
					break;
				default:
					System.out.println("Error. Selecciona una opción válida.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Error. Debes ingresar un número entero. \n");
				sn.nextLine();
			}
		}

		sn.close();
	}

	// Función para comprobar dirección IP
	private static void procesarIP(Scanner scanner) {
		boolean entradaValida = false;

		while (!entradaValida) {
			System.out.print("Ingrese la dirección IP con formato ejemplo : 123.123.123.123/22 ");
			String direccionIP = scanner.nextLine();

			try {
				String[] partes = direccionIP.split("/");
				String ip = partes[0];
				int prefijo = Integer.parseInt(partes[1]);

				String direccionRed = getDireccionRed(ip, prefijo);
				System.out.println("La dirección de red es: " + direccionRed);

				String mascaraSubred = getMascaraSubred(prefijo);
				System.out.println("La máscara de subred es: " + mascaraSubred);

				String direccionBroadcast = getDireccionBroadcast(ip, prefijo);
				System.out.println("La dirección de broadcast es: " + direccionBroadcast);

				String red = getRedCompleta(ip, prefijo);
				System.out.println("La red sería: " + red);

				entradaValida = true;

			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				System.out.println("Error, este formato no es válido para la IP, intenta de nuevo.");
			}
		}
	}

	// Función para obtener la dirección de red
	private static String getDireccionRed(String ip, int prefijo) {
		int direccion = ipToDecimal(ip);
		int mascara = 0xffffffff << (32 - prefijo);
		int direccionRed = direccion & mascara;
		return decimalToIp(direccionRed);
	}

	// Función para obtener la máscara de subred
	private static String getMascaraSubred(int prefijo) {
		int mascara = 0xffffffff << (32 - prefijo);
		return decimalToIp(mascara);
	}

	// Función para obtener la dirección de broadcast
	private static String getDireccionBroadcast(String ip, int prefijo) {
		int direccion = ipToDecimal(ip);
		int mascara = 0xffffffff << (32 - prefijo);
		int direccionBroadcast = direccion | (~mascara);
		return decimalToIp(direccionBroadcast);
	}

	// Función para obtener la red completa
	private static String getRedCompleta(String ip, int prefijo) {
		return getDireccionRed(ip, prefijo) + " /" + prefijo;
	}

	// Función para convertir una IP de formato decimal a binario
	private static int ipToDecimal(String ip) {
		String[] partes = ip.split("\\.");
		return (Integer.parseInt(partes[0]) << 24) |
				(Integer.parseInt(partes[1]) << 16) |
				(Integer.parseInt(partes[2]) << 8) |
				Integer.parseInt(partes[3]);
	}

	// Función para convertir una IP de formato binario a decimal
	private static String decimalToIp(int decimal) {
		return String.format("%d.%d.%d.%d",
				(decimal >> 24) & 0xff,
				(decimal >> 16) & 0xff,
				(decimal >> 8) & 0xff,
				decimal & 0xff);
	}
}
