package ui;

import java.net.HttpURLConnection;
import java.net.URL;

import controllers.LoginController;
import controllers.MainController;
import controllers.RegisterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	private BorderPane root;
	public static void main(String [] args){
		launch(args);
	}

	public static LoginController controller;

	@Override
	public void start(Stage fenetre) throws Exception {
		
		//System.out.println("is valid: "+checkIfURLExists("http://localhost:8080/awalApp/LoginServlet")); // does the extra checking required for validation of URI 

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
		root = loader.load();

		controller = (LoginController)loader.getController();
		
		Scene scene = new Scene(root);
		fenetre.setTitle("AWAL APP");
		fenetre.setScene(scene);
		fenetre.setResizable(false);
		fenetre.show();
	}
	
	 public static boolean checkIfURLExists(String targetUrl) {
	        HttpURLConnection httpUrlConn;
	        try {
	            httpUrlConn = (HttpURLConnection) new URL(targetUrl)
	                    .openConnection();
	 
	            // A HEAD request is just like a GET request, except that it asks
	            // the server to return the response headers only, and not the
	            // actual resource (i.e. no message body).
	            // This is useful to check characteristics of a resource without
	            // actually downloading it,thus saving bandwidth. Use HEAD when
	            // you don't actually need a file's contents.
	            httpUrlConn.setRequestMethod("HEAD");
	 
	            // Set timeouts in milliseconds
	            httpUrlConn.setConnectTimeout(30000);
	            httpUrlConn.setReadTimeout(30000);
	 
	            // Print HTTP status code/message for your information.
	            System.out.println("Response Code: "
	                    + httpUrlConn.getResponseCode());
	            System.out.println("Response Message: "
	                    + httpUrlConn.getResponseMessage());
	 
	            return (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_OK);
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	            return false;
	        }
	    }
}

