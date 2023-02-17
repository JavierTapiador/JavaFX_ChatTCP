/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conexion;

import java.net.Socket;

public class ComunHilos {
	
    int conexiones;
    int actuales;
    int maximo;
    Socket clients[] = new Socket[maximo];
    String mensajes;

    // CONSTRUCTOR
    public ComunHilos(int conexciones, int actuales, int maximo,Socket[] tabla) {
        this.conexiones = conexiones;
        this.actuales = actuales;
        this.maximo = maximo;
        this.clients = tabla;
        this.mensajes = "";
    }

    
    // GETTERS & SETTERS
    public int getConexiones() {
        return conexiones;
    }

    public synchronized void setConexiones(int conexiones) {
        this.conexiones = conexiones;
    }

    public int getActuales() {
        return actuales;
    }

    public synchronized void setActuales(int actuales) {
        this.actuales = actuales;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public Socket[] getClients() {
        return clients;
    }

    public void setClients(Socket[] tabla) {
        this.clients = tabla;
    }

    public String getMensajes() {
        return mensajes;
    }

    public synchronized void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }
    
    public synchronized void addTabla(Socket s,int i){
        clients[i]=s;
    }
    public Socket getElementoTabla(int i){
        return clients[i];
    }
    
}
