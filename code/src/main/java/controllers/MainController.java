package controllers;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.apache.http.client.methods.CloseableHttpResponse;

import bo.Groupe;
import bo.Contact;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import services.GroupService;
import services.UserService;
import ui.ConversationPane;
import ui.ParamPane;
import ui.ProfilPane;

public class MainController implements Initializable {

	@FXML
	private ConversationPane panneau;
	
	@FXML
	private BorderPane general;
	
	@FXML
	private ParamPane parametres;
	
	@FXML
	private ProfilPane profil;
	
	@FXML
	private TextField widd;

	@FXML
	private TextArea label;

	@FXML
	private GridPane leftPanel;
	
	@FXML
	private GridPane outilsGridPane;
	
	@FXML
	private GridPane profilGridPane;
	
	@FXML
	private GridPane buttonsGridPane;
	
	@FXML
	private GridPane contactGridPane;
	
	@FXML
	private GridPane groupGridPane;

	@Override
	public void initialize(URL url, ResourceBundle rb){
		

		
		Contact u= UserService.getUserById(panneau.getIdEmet());
		List<Contact> listContacts = u.getContacts();
		List<Groupe> listGroups = u.getGroupes();
		
		Label username = new Label("@"+u.getUsername());
		username.getStyleClass().add("usernameLab");
		username.setAlignment(Pos.CENTER);
		username.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				profil(null);
			}
		});
		Label name = new Label(u.getName());
		name.getStyleClass().add("nameLab");
		name.setAlignment(Pos.CENTER);
		byte[] bt;
		try {
			bt = Base64.getDecoder().decode(u.getBase64image());
		} catch (Exception e) {
			bt =null;
		}
		
		Image img = panneau.convertToJavaFXImage(bt, 20, 20);
		
		ImageView image;
		
		if(img==null) {
			image = ImageViewBuilder.create().image(img).build();
		}
		else {
			image = new ImageView(img);
		}
		Circle circle = new Circle(60);
		ImagePattern pattern = new ImagePattern(img);
		circle.setFill(pattern);
		circle.setStrokeWidth(2);
		javafx.scene.paint.Color c = javafx.scene.paint.Color.LIGHTGRAY;
		switch (u.getParam().getStatus()) {
		case "Online":
			c = javafx.scene.paint.Color.LAWNGREEN;
			break;
		case "Away":
			c = javafx.scene.paint.Color.ORANGE;
			break;
		case "At Work":
			c = javafx.scene.paint.Color.RED;
			break;
		}
	    circle.setStroke(c);

	    Image im = new Image(getClass().getResourceAsStream("/images/fil.png"));
		Label fil1 = new Label("", new ImageView(im));
		Label pic = new Label("", circle);
		pic.getStyleClass().add("picLab");
		GridPane.setConstraints(name, 0, 1);
		GridPane.setConstraints(username, 0, 2);
		GridPane.setConstraints(fil1, 0, 3);
		GridPane.setConstraints(pic, 0, 0);
		profilGridPane.getChildren().addAll(name, username, pic, fil1);

		Label fil = new Label("", new ImageView(im));
		Label lab = new Label("Contacts:");
		lab.getStyleClass().add("contactLab");
		GridPane.setConstraints(fil, 0, 0);
		GridPane.setConstraints(lab, 0, 1);
		contactGridPane.getChildren().add(fil);
		contactGridPane.getChildren().add(lab);
		if(listContacts.size()==0){
			Label l = new Label("No Contact");
			l.getStyleClass().add("l");
			GridPane.setConstraints(l, 0, 2);
			contactGridPane.getChildren().add(l);
		}
		else{
			for (int i = 0; i < listContacts.size(); i++) {
				Label l = new Label(listContacts.get(i).getUsername());
				l.getStyleClass().add("l");
				int id = listContacts.get(i).getIdUser();
				l.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						panneau.setPrivacy(true);
						panneau.setDestination(id);
						panneau.refreshConversation();
						System.out.println(id);
					}
				});
				GridPane.setConstraints(l, 0, i+2);
				contactGridPane.getChildren().add(l);
			}
		}

		Label lab2 = new Label("Groupes:");
		lab2.getStyleClass().add("groupLab");
		GridPane.setConstraints(lab2, 0, 0);
		groupGridPane.getChildren().add(lab2);
		if(listGroups.size()==0){
			Label l = new Label("No Groupe");
			l.getStyleClass().add("l");
			GridPane.setConstraints(l, 0, 1);
			groupGridPane.getChildren().add(l);
		}
		else{
			for (int j = 0; j < listGroups.size(); j++) {
				Label l = new Label(listGroups.get(j).getUsername());
				l.getStyleClass().add("l");
				int id = listGroups.get(j).getIdGroupe();
				l.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						panneau.setPrivacy(false);
						panneau.setDestination(id);
						panneau.refreshConversation();
						System.out.println(id);
					}
				});
				GridPane.setConstraints(l, 0, j+1);
				groupGridPane.getChildren().add(l);
			}
		}
	}

	@FXML
	private void messages(ActionEvent event) {
		general.getChildren().remove(parametres);
		general.getChildren().remove(profil);
		general.setCenter(panneau);
	}

	@FXML
	private void parametres(ActionEvent event) {
		general.getChildren().remove(profil);
		parametres = new ParamPane();
		general.setCenter(parametres);
	}
	
	@FXML
	private void profil(ActionEvent event) {
		general.getChildren().remove(parametres);
		profil = new ProfilPane(panneau.getIdEmet());
		general.setCenter(profil);
	}
	
	@FXML
	private void createGroupe(ActionEvent event) {
		general.getChildren().remove(parametres);
		profil = new ProfilPane(panneau.getIdEmet());
		general.setCenter(profil);
	}
	
	@FXML
	private void about(ActionEvent event) {
		
	}
	
	@FXML
	private void logout(ActionEvent event) {
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
			root=loader.load();
			Stage stage = new Stage();
			stage.setTitle("Log in");
			stage.setScene(new Scene(root, 600, 350));
			stage.setResizable(false);
			stage.show();
			
			// Hide this current window (if this is what you want)
			(((Node) event.getSource())).getScene().getWindow().hide();
			LoginController.a=-1;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
