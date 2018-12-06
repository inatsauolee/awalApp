package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import bo.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.UserService;

public class LoginController implements Initializable {
	
	public static int a = -1;

    @FXML
	private TextField usernameField;
    
    @FXML
   	private TextField passwordField;
    
    @FXML
    private Label errorLabel;
    
    @FXML
	private GridPane centerPane;

    @FXML
    private void login(ActionEvent event) throws Exception {
    	if(!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()){
        	Contact user = UserService.getUser(usernameField.getText(), passwordField.getText());
        	if(user==null){
        		errorLabel.setText("Login not found");
        	}else{
        		errorLabel.setText("Welcome <3");
        		a = user.getIdUser();
        		Parent root;
        		try {
        			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
        			root=loader.load();
        			Stage stage = new Stage();
        			stage.setTitle("Awal App..");
        			stage.setScene(new Scene(root, 1000, 700));
        			stage.show();
        			
        			// Hide this current window (if this is what you want)
        			(((Node) event.getSource())).getScene().getWindow().hide();
        		}
        		catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
    	}else{
    		errorLabel.setText("Login or Password is empty..");
    	}
    }
    

public static String convertStreamToString(InputStream is) {

    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line = null;
    try {
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return sb.toString();
}
    @FXML
    private void register(ActionEvent event){
    	Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/register.fxml"));
			root=loader.load();
			Stage stage = new Stage();
			stage.setTitle("Sign up");
			stage.setScene(new Scene(root, 620, 600));
			stage.setResizable(false);
			stage.show();
			// Hide this current window (if this is what you want)
			(((Node) event.getSource())).getScene().getWindow().hide();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void clearError() {
    	errorLabel.setText("");
    }

    public void addError(String string) {
    	errorLabel.setWrapText(true);
    	errorLabel.setText(errorLabel.getText()+"\n"+string);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  }
    
    public static CloseableHttpResponse postRequest(String url, Map<String, String> params) throws Exception {
		CloseableHttpResponse response = null;

		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			for (Map.Entry<String, String> entry : params.entrySet()) {

				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

			}

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			response = httpclient.execute(httpPost);

		} finally

		{
			if (response != null) {
				response.close();

			}
		}
		
		return response;
	}
}

