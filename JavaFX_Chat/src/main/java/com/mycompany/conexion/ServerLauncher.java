package com.mycompany.conexion;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerLauncher {

	public static void main(String[] args) {
		 
        ServerSocket servidor = null;
        Socket clients[] = new Socket[20];
        ComunHilos comun = new ComunHilos(20, 0, 0, clients);
        
        
		try {
			servidor = new ServerSocket(12345);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        System.out.println("servidor iniciado...");
        
        
        // mientras no haya más de 20 conexiones
        while(comun.getConexiones() < 20){
            Socket socket = new Socket();
            try {
				socket = servidor.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
            
            comun.addTabla(socket, comun.getConexiones());
            comun.setActuales(comun.getActuales()+1);
            comun.setConexiones(comun.getConexiones()+1);
            
            Server hilo = new Server(socket, comun);
            hilo.start();
            
            
        }
        try {
			servidor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
}
