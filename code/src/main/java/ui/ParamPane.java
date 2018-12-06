package ui;


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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import services.ParametresService;
import services.UserService;


public class ParamPane extends BorderPane  {

	public Contact current;
	private GridPane center = new GridPane();

	public ParamPane() {

		current =  UserService.getUserById(LoginController.a);

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
		Label paramLabel = new Label("Parametres");
		top.getChildren().add(paramLabel);

		Label textCryptLabel = new Label("Crypting Method (for Text)");
		GridPane.setConstraints(textCryptLabel, 0, 1);
		center.getChildren().add(textCryptLabel);

		ObservableList<String> textCryptOptions = 
				FXCollections.observableArrayList(
						"CEZAR",
						"XOR",
						"VERNAM",
						"RSA",
						"DES",
						"NONE"
						);
		final ComboBox textCryptComboBox = new ComboBox(textCryptOptions);
		textCryptComboBox.setValue(current.getParam().getTextCryptingType());
		GridPane.setConstraints(textCryptComboBox, 1, 1);
		center.getChildren().add(textCryptComboBox);
		
		Label imageCryptLabel = new Label("Crypting Method (for Image)");
		GridPane.setConstraints(imageCryptLabel, 0, 2);
		center.getChildren().add(imageCryptLabel);

		ObservableList<String> imageCryptOptions = 
				FXCollections.observableArrayList(
						"XOR",
						"VERNAM",
						"DES",
						"NONE"
						);
		final ComboBox imageCryptComboBox = new ComboBox(imageCryptOptions);
		imageCryptComboBox.setValue(current.getParam().getImageCryptingType());
		GridPane.setConstraints(imageCryptComboBox, 1, 2);
		center.getChildren().add(imageCryptComboBox);
		
		Label statusLabel = new Label("Status");
		GridPane.setConstraints(statusLabel, 0, 3);
		center.getChildren().add(statusLabel);

		ObservableList<String> statusOptions = 
				FXCollections.observableArrayList(
						"Online",
						"Away",
						"At Work",
						"hahaha"
						);
		final ComboBox statusComboBox = new ComboBox(statusOptions);
		statusComboBox.setValue(current.getParam().getStatus());
		GridPane.setConstraints(statusComboBox, 1, 3);
		center.getChildren().add(statusComboBox);
		
		//Defining the Clear button
		Button save = new Button("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Parametres p = new Parametres(textCryptComboBox.getValue().toString(), imageCryptComboBox.getValue().toString(), statusComboBox.getValue().toString());
				current.setParam(p);
				UserService.updateUser(current);
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
