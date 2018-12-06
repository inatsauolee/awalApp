package ui;


import java.util.Base64;

import bo.Contact;
import bo.Parametres;
import controllers.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import services.ParametresService;
import services.UserService;


public class ProfilPane extends BorderPane  {

	public Contact current;
	public Contact profilContact;
	public boolean b;
	private GridPane center = new GridPane();

	public ProfilPane(int id){

		////
		current =  UserService.getUserById(LoginController.a);
		profilContact = UserService.getUserById(id);
		//Creating a GridPane container
		GridPane bottom = new GridPane();

		bottom.setPadding(new Insets(10, 10, 10, 10));
		bottom.setVgap(5);
		bottom.setHgap(5);

		GridPane top = new GridPane();

		top.setPadding(new Insets(10, 10, 10, 10));
		top.setVgap(5);
		top.setHgap(5);

		center.setPadding(new Insets(10, 10, 10, 10));
		center.setVgap(5);
		center.setHgap(5);

		//Dest info
		Label paramLabel = new Label("ProfilPane");
		top.getChildren().add(paramLabel);
		
		//Personal Information
		Label username = new Label(profilContact.getUsername());
		Label name = new Label(profilContact.getName());
		Label email = new Label(profilContact.getEmail());
		byte[] bt;
		try {
			bt = Base64.getDecoder().decode(profilContact.getBase64image());
		} catch (Exception e) {
			bt =null;
		}
		
		Image img = ConversationPane.convertToJavaFXImage(bt, 20, 20);
		
		ImageView image;
		
		if(img==null) {
			image = ImageViewBuilder.create().image(img).build();
		}
		else {
			image = new ImageView(img);
		}

		//button message
		Button message = new Button("Message");
		message.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				
			}
		});
		
		//button cyrcle
		Button cyrcle = new Button("Cyrcle");
		b = current.isCyrcle(profilContact);
		if(b) cyrcle.setText("Uncyrcle");
		cyrcle.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				if(b){
					current.removeCyrcle(profilContact);
					UserService.updateUser(current);
					cyrcle.setText("Cyrcle");
					b = false;
				}
				else{
					current.addCyrcle(profilContact);
					UserService.updateUser(current);
					cyrcle.setText("Uncyrcle");
					b = true;
				}
			}
		});

		Label pic = new Label("", image);
		GridPane.setConstraints(name, 1, 1);
		GridPane.setConstraints(username, 2, 1);
		GridPane.setConstraints(pic, 0, 1);
		GridPane.setConstraints(message, 3, 1);
		GridPane.setConstraints(cyrcle, 4, 1);
		top.getChildren().addAll(name, username, pic, message, cyrcle);

		//Defining the Clear button
		Button save = new Button("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				
			}
		});
		GridPane.setConstraints(save, 2, 2);
		bottom.getChildren().add(save); 
		
		//Defining the Clear button
		Button clear = new Button("Clear");
		clear.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				
			}
		});
		GridPane.setConstraints(clear, 3, 2);
		bottom.getChildren().add(clear);  
		
		this.setTop(top); 
		this.setCenter(center);
		this.setBottom(bottom);
	}
}
