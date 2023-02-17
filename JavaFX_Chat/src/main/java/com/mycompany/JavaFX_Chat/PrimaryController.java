package com.mycompany.JavaFX_Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import com.mycompany.conexion.User;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PrimaryController implements Initializable {

	@FXML TextField txtMen;
	@FXML ScrollPane scroll;
	@FXML Button btnSend;
	private Socket client;
	private User user;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// CONECTAMOS USUARIO CON SERVIDOR
		try {
			client = new Socket("localhost", 12345);
			user = new User(client);
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// RECIBIMOS LOS MENSAJES
		user.recieve(scroll);
		
		
		
		btnSend.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				
				// cargar mensaje
				String men = txtMen.getText().toString();	
				
				// enviar
				user.send(men);
				
				// borraar de TextField
				txtMen.clear();
			}
		});
	}

	
	public static void escribir(String men, ScrollPane scroll) {
		
		
		VBox content = new VBox();
		HBox contentMen = new HBox();
		
		Label lblMen = new Label();
		lblMen.setText(men);
		
		contentMen.getChildren().add(lblMen);
		content.getChildren().add(contentMen);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {

				scroll.setContent(content);
				
			}
		});
	}
}
