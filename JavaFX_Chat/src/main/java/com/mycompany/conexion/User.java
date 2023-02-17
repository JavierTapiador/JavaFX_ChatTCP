package com.mycompany.conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import com.mycompany.JavaFX_Chat.PrimaryController;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class User {

	private Socket client;
	private DataInputStream entrada;
	private DataOutputStream salida;
	
	
	// CONSTRUCTOR
	public User(Socket client) {
		this.client = client;
		
		try {
			entrada = new DataInputStream(client.getInputStream());
			salida = new DataOutputStream(client.getOutputStream());
			
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
	
	
	// METHODS
	
	// METODO ENVIAR
	public void send(String men) {
		
		try {
			salida.writeUTF(men);
			salida.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// METODO RECIBIR
	public void recieve(ScrollPane scroll){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(client.isConnected()){


                    try {
                        String men = entrada.readUTF();
                        
                        PrimaryController.escribir(men, scroll);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }
}
