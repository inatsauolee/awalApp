package controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Base64;
import java.util.Date;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import bo.Contact;
import bo.Parametres;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.UserService;
import ui.ConversationPane;

public class RegisterController implements Initializable {

	@FXML
	private TextField usernameField;

	@FXML
	private GridPane centerPane;

	@FXML
	private TextField passwordField;

	@FXML
	private TextField repassField;

	@FXML
	private TextField fullnameField;

	@FXML
	private TextField emailField;

	@FXML
	private Label errorLabel;


	File file=null;
	private ImageView imageView;

	@FXML
	private void register(ActionEvent event) {
		//Contact j = UserService.getUserById(15);
		//UserService.removeUser(j);
		if(!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty() && !fullnameField.getText().isEmpty() && !emailField.getText().isEmpty() && !repassField.getText().isEmpty()){
			if(passwordField.getText().equals(repassField.getText())){
				Contact yooba = UserService.getUserById(0);
				Contact user = UserService.getUserByUsername(usernameField.getText());
				if(user==null){
					Contact u = new Contact();
					u.setDate(new Date());
					u.setName(fullnameField.getText());
					u.setPassword(passwordField.getText());
					u.setUsername(usernameField.getText());
					u.setEmail(emailField.getText());
					u.setParam(new Parametres());
					if(yooba!=null) u.addCyrcle(yooba);
					//add image to user:
					String base64Encoded = null;
					if (file != null) {
						Image image = new Image(file.toURI().toString());
						BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
						ByteArrayOutputStream s = new ByteArrayOutputStream();
						try {
							ImageIO.write(bImage, "png", s);
							s.close(); //especially if you are using a different output stream.
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						byte[] data = s.toByteArray();;
						byte[] encodeBase64 = Base64.getEncoder().encode(data);
						try {
							base64Encoded = new String(encodeBase64, "UTF-8");
							System.err.println(base64Encoded);
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						u.setBase64image(base64Encoded);
						u.setImage(data);

						try {
							UserService.saveUser(u);
							errorLabel.setText("Contact saved successfully..");
						} catch (Exception e) {
							errorLabel.setText("Error in registring..");
						}
					}else{
						errorLabel.setText("Image does not fit..");
					}

				}else{
					errorLabel.setText("Login used before..");
				}
			}else{
				errorLabel.setText("Password not match..");
			}
		}else{
			errorLabel.setText("Some Fields cannot be empty..");
		}
	}

	public void clearError() {
		errorLabel.setText("");
	}

	@FXML
	private void login(ActionEvent event){
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
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addError(String string) {
		errorLabel.setWrapText(true);
		errorLabel.setText(errorLabel.getText()+"\n"+string);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		FileChooser fileChooser = new FileChooser();

		Button browse = new Button();
		browse.getStyleClass().add("picture");
		browse.setOnAction(
				new EventHandler<ActionEvent>() {

					@Override
					public void handle(final ActionEvent e) {
						Stage stage = null;
						ConversationPane.configureFileChooser(fileChooser);
						file = fileChooser.showOpenDialog(stage);
						if (file != null) {

							Image image = new Image(file.toURI().toString());
							imageView = new ImageView(image);
							imageView.setImage(image);
							GridPane.setConstraints(imageView, 1, 6);
							centerPane.getChildren().add(imageView);
						}
					}
				});

		GridPane.setConstraints(browse, 0, 6);
		centerPane.getChildren().add(browse);
	}
}
