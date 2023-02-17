package com.mycompany.JavaFX_Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PrimaryController implements Initializable {

	@FXML TextField txtMen;
	@FXML ScrollPane scroll;
	@FXML Button btnSend, btnClose;
	private Socket client;
	private User user;
	private Optional<String> userName;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// MOSTRAR DIALOGO PIDIENDO USUARIO
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Introducir usuario");
		dialog.setHeaderText("Introduzca su nombre de usuario para entrar al chat.");
		dialog.setContentText("Introduzca su nombre de usuario:");

		userName = dialog.showAndWait();

		// MODIFICAR EL ASPECTO DE BOTONES
		btnSend.setGraphic(new ImageView(getClass().getResource("img/play.png").toExternalForm()));
		btnClose.setGraphic(new ImageView(getClass().getResource("img/close.png").toExternalForm()));
		
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

		
		// BUTTON ONCLICK BTNSEND
		btnSend.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {

				if (!txtMen.getText().toString().equals("")) {

					// cargar mensaje
					String men = userName.get() + ": " + txtMen.getText().toString();

					// enviar
					user.send(men);

					// borrar de TextField
					txtMen.clear();
				}

			}
		});
		
		
		
		// BUTTON ONCLICK BTNCLOSE
		btnClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {

				// enviar
				user.send("*");
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

	
	// BUTTON ONMOUSEENTERED BTNSEND
	@FXML
	private void btnSendEntered() {
		btnSend.setStyle("-fx-background-color: #30b9d4; -fx-border-radius: 50");
	}

	// BUTTON ONMOUSEEXITED BTNSEND
	@FXML
	private void btnSendExited() {
		btnSend.setStyle("-fx-background-color: #66d1e7; -fx-border-radius: 50");
	}
	
	// BUTTON ONMOUSEENTERED BTNCLOSE
	@FXML
	public void btnCloseEntered() {
		btnClose.setStyle("-fx-background-color: #c91e1e; -fx-border-radius: 50");
	}
	
	// BUTTON ONMOUSEEXITED BTNCLOSE
	@FXML
	public void btnCloseExited() {
		btnClose.setStyle("-fx-background-color: #ef4343; -fx-border-radius: 50");
	}
}
