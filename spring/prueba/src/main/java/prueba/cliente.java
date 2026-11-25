package prueba;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class cliente {

	public static void main(String[] args) {

		try {

			int puerto = 5000;

			String ip = "130.10.1.54";

			System.out.println("Conectando con el servidor");

			Socket socket = new Socket(ip, puerto);

			BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			Thread escucharSocketServidor = new Thread(new Runnable() {
				@Override
				public void run() {

					try {
						boolean die = true;

						String linea = "";

						while ((linea = entrada.readLine()) != null) {
							System.out.println(linea);
							if (linea.startsWith("/logout"))
								break;
						}

						while (die) {
							while ((linea = entrada.readLine()) != null) {

								System.out.println(Cifrado.decriptar("ClaveSecreta2026", linea));
							}
						}
					} catch (Exception e) {
						System.out.println("Hubo un error." + e);
					}

				}

			});

			escucharSocketServidor.start();

			Thread escrituraSocketServidor = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						boolean die = true;

						while (die) {
							String linea = "";
							while ((linea = br.readLine()) != null) {

								salida.println(Cifrado.encriptar("ClaveSecreta2026", linea));

							}
						}

					} catch (Exception e) {
						System.out.println("Hubo un error." + e);
					}
				}
			});

			escrituraSocketServidor.start();

		} catch (Exception e) {

			System.out.println("Hubo un error." + e);

			// BufferedReader entrada = new BufferedReader(new
			// InputStreamReader(sockect.getInputStream()) )

		}
	}

}