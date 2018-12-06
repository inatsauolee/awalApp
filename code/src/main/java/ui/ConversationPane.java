package ui;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import bo.Groupe;
import bo.Message;
import controllers.LoginController;
import bo.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.GroupService;
import services.MessageService;
import services.UserService;


public class ConversationPane extends BorderPane  {
	
	public Contact current;
	public File file=null;
	private ImageView imageView;
	private int emet=LoginController.a;
	private int dest=0;
	private boolean privacy = true;
	private String textCryptingType = "NONE";
	private String imageCryptingType = "NONE";
	private TableView<Message> table = new TableView<Message>();
	private ListView<Message> listView = new ListView<Message>();
	private Label usernameDest = new Label();
	private Label nameDest = new Label();
	private Label picDest = new Label();

	private FlowPane p = new FlowPane();
	private GridPane center = new GridPane();
	
	private ObservableList<Message> data = FXCollections.observableArrayList(MessageService.readMessages(emet, dest, privacy));
	private Contact destContact;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ConversationPane() {

		current =  UserService.getUserById(emet);
		textCryptingType = current.getParam().getTextCryptingType();
		imageCryptingType = current.getParam().getImageCryptingType();
		//Dest info
		setDestination(0);
		
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

		top.getChildren().add(p);

		//Search text
		TextField search = new TextField();
		search.setPromptText("search a message");
		search.setPrefColumnCount(10);
		search.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("textfield changed from " + oldValue + " to " + newValue);
			List<Message> lr = new ArrayList<Message>();
			for (int i = 0; i < data.size(); i++) {
				if(data.get(i).getText().startsWith(search.getText())) lr.add(data.get(i));
			}
			data = FXCollections.observableArrayList(lr);
			table.setItems(data);
			table.refresh();
		});

		GridPane.setConstraints(search, 0, 0);
		top.getChildren().add(search);
		
		// Refresh Button
		Image imageOk = new Image(getClass().getResourceAsStream("/images/refresh.jpg"));
		Button refresh = new Button("Refresh");
		//	Button refresh = new Button("Refresh", new ImageView(imageOk));
		refresh.resize(100, 40);
		refresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				refreshConversation();
			}
		});

		String css = getClass().getResource("/css/panneau.css").toExternalForm(); 
		this.getStylesheets().add(css);

		GridPane.setConstraints(refresh, 1, 0);
		top.getChildren().add(refresh); 

		//		// Table of conversation 
		final Label label = new Label("Conversation..");
		label.setFont(new Font("Arial", 20));

		table.setEditable(true);
		table.setPrefSize(2000, 1000);
		table.getStyleClass().add("noheader");
		//		table.setMouseTransparent( true );
		//		table.setFocusTraversable( false );

		TableColumn emetCol = new TableColumn("Emetteur");
		emetCol.setMinWidth(150);
		emetCol.setCellValueFactory(
				new PropertyValueFactory<Message, Integer>("idEmet"));
		
		emetCol.setCellFactory(new Callback<TableColumn<Message, Integer>, TableCell<Message, Integer>>(){

			@Override
			public TableCell<Message, Integer> call(TableColumn<Message, Integer> p) {
				return new TableCell<Message, Integer>(){
					@Override
					protected void updateItem(Integer id, boolean empty) {
						super.updateItem(id, empty);
						if(id!=null){
							System.out.println(id);
							Contact u = UserService.getUserById(id);
							if(u!=null){
									System.out.println("************************"+id);
									setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
									Label username = new Label("@"+u.getUsername());
									username.getStyleClass().add("usernameLab2");
									username.setAlignment(Pos.CENTER);
									byte[] bt;
									try {
										bt = Base64.getDecoder().decode(u.getBase64image());
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
									
									Circle circle = new Circle(30);
									ImagePattern pattern = new ImagePattern(img);
									circle.setFill(pattern);
									circle.setStrokeWidth(1);
								    circle.setStroke(javafx.scene.paint.Color.rgb(0, 250, 250));
								    
								    VBox hb = new VBox();
								    hb.setPadding(new Insets(0, 0, 0, 0));
								    hb.setSpacing(5);
								    hb.getChildren().add(circle);
								    hb.getChildren().add(username);
									setGraphic(hb);
								  
							}
						}
					}
				};
			}
		});

		TableColumn dateCol = new TableColumn<>("Date");
		dateCol.setMinWidth(50);
		dateCol.setCellValueFactory(
				new PropertyValueFactory<Message, String>("date"));

		TableColumn<Message, String> textCol = new TableColumn<Message, String>("Text");
		textCol.setMinWidth(300);
		
		textCol.setCellValueFactory(
				new PropertyValueFactory<Message, String>("text"));

		textCol.setCellFactory(new Callback<TableColumn<Message, String>, TableCell<Message, String>>(){

			@Override
			public TableCell<Message, String> call(TableColumn<Message, String> p) {
				return new TableCell<Message, String>(){
					@Override
					protected void updateItem(String i, boolean empty) {
						super.updateItem(i, empty);
						if(i!=null){
							if(i.startsWith("I")){
								String s = i.substring(1);
								System.out.println("************************"+s);
								setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
								byte[] bt;
								try {
									bt = Base64.getDecoder().decode(s);
								} catch (Exception e) {
									bt =null;
								}
								Image img = convertToJavaFXImage(bt, 20, 20);
								ImageView image;
								if(img==null) {
									image = ImageViewBuilder.create().image(img).build();
								}
								else {
									image = new ImageView(img);
								}
								setGraphic(image);
							}
							else if(i.startsWith("T")){
								System.out.println(i);
								String s = i.substring(1);
								setText(s);
							}
							else{
								setText(i);
							}
						}
					}
				};
			}
		});
		
		table.setItems(data);
		
		table.getColumns().addAll(emetCol, dateCol, textCol);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));

		GridPane.setConstraints(table, 0, 1);
		GridPane.setConstraints(label, 0, 0);
		center.getChildren().addAll(table, label); 
		
		//TextField chat
		
		//Defining the Message text field
		final TextField text = new TextField();
		text.setPromptText("Enter your message");
		text.setPrefColumnCount(10);
		text.getText();
		GridPane.setConstraints(text, 0, 2);
		bottom.getChildren().add(text);

		//Image Viewer

		FileChooser fileChooser = new FileChooser();

		Button browse = new Button("Picture");

		browse.setOnAction(
				new EventHandler<ActionEvent>() {

					@Override
					public void handle(final ActionEvent e) {
						Stage stage = null;
						configureFileChooser(fileChooser);
						file = fileChooser.showOpenDialog(stage);
						if (file != null) {

							Image image = new Image(file.toURI().toString());
							imageView = new ImageView(image);
							imageView.setImage(image);
							GridPane.setConstraints(imageView, 0, 2);
							bottom.getChildren().add(imageView);
							text.setVisible(false);
						}
					}
				});

		GridPane.setConstraints(browse, 1, 2);
		bottom.getChildren().add(browse);

		//Defining the Send button
		Button send = new Button("Send");
		send.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
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
					byte[] bytes = s.toByteArray();;
					byte[] encodeBase64 = Base64.getEncoder().encode(bytes);
					String base64Encoded = null;
					try {
						base64Encoded = new String(encodeBase64, "UTF-8");
						System.err.println(base64Encoded);
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Message message = new Message('I'+base64Encoded, emet, dest, new Date(), privacy, imageCryptingType, BigInteger.ZERO, BigInteger.ZERO,BigInteger.ZERO);
					MessageService.sendMessage(message);
					text.setVisible(true);
					file=null;
				}else {
					if(text.getText().equals("")){
						Message message = new Message('T'+text.getText(), emet, dest, new Date(), privacy, textCryptingType, BigInteger.ZERO, BigInteger.ZERO,BigInteger.ZERO);
						MessageService.sendMessage(message);
					}
				}
				text.clear();
				if(!destContact.isCyrcle(current)){
					System.err.println("hhhhhhhhhhhhhhhhhhhhhbb: "+destContact);
					List<Contact> newList = new ArrayList<Contact>();
					newList.add(current);
					destContact.setContacts(newList);
					System.err.println("hhhhhhhhhhhhhhhhhhhhhbb: "+destContact.getContacts().get(0));
					UserService.saveUser(destContact);
				}
				
				refreshConversation();
				bottom.getChildren().remove(imageView);
			}
		});
		GridPane.setConstraints(send, 2, 2);
		bottom.getChildren().add(send);

		//Defining the Clear button
		Button clear = new Button("Clear");
		clear.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				text.clear();
			}
		});
		GridPane.setConstraints(clear, 3, 2);
		bottom.getChildren().add(clear);  

		this.setTop(top); 
		this.setCenter(center);
		this.setBottom(bottom);
	}
	
	public void refreshConversation() {
		ObservableList<Message> daa = FXCollections.observableArrayList(MessageService.readMessages(emet, dest, privacy));
		table.setItems(daa);
		table.refresh();
	}

	public void setDestination(int id) {
		this.dest = id;
		if(privacy) {
			destContact = UserService.getUserById(dest);
			usernameDest = new Label("@"+destContact.getUsername());
			usernameDest.getStyleClass().add("usernameLab");
			usernameDest.setAlignment(Pos.CENTER);
			nameDest = new Label(destContact.getName());
			nameDest.getStyleClass().add("nameLab");
			nameDest.setAlignment(Pos.CENTER);
			byte[] bt;
			try {
				bt = Base64.getDecoder().decode(destContact.getBase64image());
			} catch (Exception e) {
				bt =null;
			}
			
			Image img = this.convertToJavaFXImage(bt, 20, 20);
			
			ImageView image;
			
			if(img==null) {
				image = ImageViewBuilder.create().image(img).build();
			}
			else {
				image = new ImageView(img);
			}
			Circle circle = new Circle(40);
			ImagePattern pattern = new ImagePattern(img);
			circle.setFill(pattern);
			circle.setStrokeWidth(1);
			javafx.scene.paint.Color c = javafx.scene.paint.Color.LIGHTGRAY;
			switch (destContact.getParam().getStatus()) {
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
			picDest = new Label("", circle);
			p.getChildren().add(nameDest);
			p.getChildren().add(usernameDest);
			p.getChildren().add(picDest);
			GridPane.setConstraints(nameDest, 1, 1);
			GridPane.setConstraints(usernameDest, 2, 1);
			GridPane.setConstraints(picDest, 0, 0);
			p.getChildren().clear();
			p.getChildren().addAll(nameDest, usernameDest, picDest);
			GridPane.setConstraints(p, 0, 1);
		}
		else {
			Groupe destGroupe = GroupService.getGroupById(dest);
			usernameDest = new Label("@"+destGroupe.getUsername());
			usernameDest.getStyleClass().add("usernameLab");
			usernameDest.setAlignment(Pos.CENTER);
			nameDest = new Label(destGroupe.getName());
			nameDest.getStyleClass().add("nameLab");
			nameDest.setAlignment(Pos.CENTER);
			byte[] bt;
			try {
				bt = Base64.getDecoder().decode(destGroupe.getBase64image());
			} catch (Exception e) {
				bt =null;
			}
			
			Image img = this.convertToJavaFXImage(bt, 20, 20);
			
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
		    circle.setStroke(javafx.scene.paint.Color.rgb(0, 250, 0));

		    Image im = new Image(getClass().getResourceAsStream("/images/fil.png"));
			Label fil1 = new Label("", new ImageView(im));
			picDest = new Label("", circle);
		}	

	}
	
	public int getIdEmet() {
		return emet;
	}
	
	public void setIdEmet(int i){
		this.emet=i;
	}

	public void setPrivacy(boolean b) {
		this.privacy = b;
	}
	
	public static Image convertToJavaFXImage(byte[] raw, final int width, final int height) {
        WritableImage image = new WritableImage(width, height);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(raw);
            BufferedImage read = ImageIO.read(bis);
            image = SwingFXUtils.toFXImage(read, null);
        } catch (IOException ex) {
        	
           // Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }	
	
	public static void configureFileChooser(
			final FileChooser fileChooser) {      
		fileChooser.setTitle("View Pictures");
		fileChooser.setInitialDirectory(
				new File(System.getProperty("user.home"))
				);                 
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png")
				);
	}
}