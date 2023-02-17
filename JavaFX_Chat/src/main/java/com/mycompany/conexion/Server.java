package com.mycompany.conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{

	private ComunHilos comun;
	private Socket client;
	private DataInputStream entrada;
	private DataOutputStream salida;
	private String men;
	
	// CONSTRUCTOR
	public Server(Socket client, ComunHilos comun) {
		this.client = client;
		this.comun = comun;
		
		try {
			entrada = new DataInputStream(client.getInputStream());
			
			
		} catch (IOException e) {
			e.printStackTrace();
			
			try {
				entrada.close();
				salida.close();
				client.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	// GETTERS & SETTERS
	public String getMen() {
		return men;
	}
	

	// METHODS
	
	// METODO ENVIO
	public void send(String men) {

		Thread hilo = new Thread(new Runnable() {

			@Override
			public void run() {

				for(int cont = 0; cont < comun.getConexiones(); cont++) {
					
					// seleccionar cada usuario guardado
					Socket cliente = comun.getElementoTabla(cont);
					
					// si está conectado
					if (!cliente.isClosed()) {
						try {
							
							// enviar todos los mensajes guardados
							salida = new DataOutputStream(cliente.getOutputStream());
							salida.writeUTF(men);
							salida.flush();

						} catch (IOException e) {
							e.printStackTrace();
						}
					
				}
			}

			}
		});

		hilo.start();
		try {
			hilo.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	
	// EJECUCION HILO
	public void run() {

		// cargar todos los mensajes
        String texto = comun.getMensajes();
        send(texto);

        while (true) {
            String cadena = "";
            try {
            	
            	// cargar el mensaje recibido
                cadena = entrada.readUTF();
                
                // si se recibe un * restar una conexion 
                if (cadena.trim().equals("*")) {
                    comun.setActuales(comun.getActuales() - 1);
                    break;
                }
                
                // guardar los mensajes cargados + el nuevo mensaje recibido
                comun.setMensajes(comun.getMensajes() + cadena + "\n");
                
                // enviar mensaje
                send(comun.getMensajes());
                
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }

        }

    }
}
