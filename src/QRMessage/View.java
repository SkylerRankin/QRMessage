package QRMessage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class View extends Application {
	
	private Scene get_main_scene(Stage stage) {
		
		TabPane tp = new TabPane();
		
		//----------------Tab for Encrypting and Generating Image-----------------------------
		
		Tab encrypt = new Tab("Encrypt");
		encrypt.setClosable(false);
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text message_title = new Text("Message");
		message_title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(message_title, 0, 0, 2, 1);
		
		TextArea area = new TextArea();
		area.setWrapText(true);
		grid.add(area, 0, 1, 4, 3);
		
		Text destination = new Text("Destination");
		grid.add(destination, 0, 4);
		TextField destination_input = new TextField();
		grid.add(destination_input, 1, 4, 2, 1);
		
		Button dir_browse = new Button("Browse");
		dir_browse.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				DirectoryChooser dc = new DirectoryChooser();
				dc.setTitle("Select Save Location");
				File f = dc.showDialog(stage);
				if (f != null) {
					destination_input.setText(f.toString());
				}
			}
			
		});
		grid.add(dir_browse, 3, 4);
		
		Text filename = new Text("File Name");
		grid.add(filename, 0, 5);
		TextField filename_input = new TextField("output.jpg");
		grid.add(filename_input, 1, 5, 2, 1);
		
		Text debug = new Text();
		grid.add(debug, 0, 6, 6, 1);
		
		HBox hb1 = new HBox(200);
		hb1.setAlignment(Pos.BOTTOM_RIGHT);
		Button encrypt_btn = new Button("Generate QR");
		encrypt_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Path file_path = Paths.get(destination_input.getText(), filename_input.getText());
				Event result = EncryptionManager.SaveImage(area.getText(), file_path.toString());
				System.out.println(file_path.toString());
				debug.setFill(result.color);
				debug.setText(result.message);
			}
			
		});
		hb1.getChildren().add(encrypt_btn);
		grid.add(hb1, 0, 6, 6, 1);
		
		encrypt.setContent(grid);
		
		//-----------------------Tab for Decrypting Images and creating texts-------------------------
		
		Tab decrypt = new Tab("Decrypt");
		decrypt.setClosable(false);
		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(10);
		grid2.setVgap(10);
		grid2.setPadding(new Insets(25, 25, 25, 25));
		
		Text decrypt_title = new Text("Decyrpt QR Message");
		decrypt_title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid2.add(decrypt_title, 0, 0, 2, 1);
		
		TextArea result = new TextArea();
		result.setWrapText(true);
		result.setEditable(false);
		grid2.add(result, 0, 1, 4, 3);
		
		grid2.add(new Text("Location"), 0, 5);
		TextField location_input = new TextField();
		grid2.add(location_input, 1, 5, 2, 1);
		
		Button loc_browse = new Button("Browse");
		loc_browse.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				FileChooser dc = new FileChooser();
				dc.setTitle("Select Image");
				File f = dc.showOpenDialog(stage);
				if (f != null) {
					location_input.setText(f.toString());
				}
			}
			
		});
		grid2.add(loc_browse, 3, 5);
		
		Text debug2 = new Text();
		grid2.add(debug2, 0, 6, 1, 1);
		
		HBox hb2 = new HBox(200);
		hb2.setAlignment(Pos.BOTTOM_RIGHT);
		Button decrypt_btn = new Button("Decrypt");
		decrypt_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Event s = EncryptionManager.getText(location_input.getText());
				debug2.setFill(s.color);
				debug2.setText(s.message);
				result.setText(s.getData());
			}
			
		});
		hb2.getChildren().add(decrypt_btn);
		grid2.add(hb2, 0, 6, 6, 1);
		
		decrypt.setContent(grid2);
		
		tp.getTabs().addAll(encrypt, decrypt);
		
		Scene scene = new Scene(tp, 800, 600);
		return scene;
	}
	
	private Scene get_login_scene(Stage stage) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text login_title = new Text("Login");
		login_title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(login_title, 0, 0, 2, 1);
		
		Text username = new Text("Username");
		grid.add(username, 0, 1);
		
		TextField username_field = new TextField();
		grid.add(username_field, 1, 1);
		
		Text password = new Text("Password");
		grid.add(password, 0, 2);
		
		PasswordField password_field = new PasswordField();
		grid.add(password_field, 1, 2);
		
		Button login = new Button();
		login.setText("Login");
		login.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				stage.setScene(get_main_scene(stage));
			}
			
		});
		
		HBox hb = new HBox(20);
		hb.setAlignment(Pos.BOTTOM_RIGHT);
		hb.getChildren().add(login);
		grid.add(hb, 1, 3);
		
		Scene scene = new Scene(grid, 400, 300);
		return scene;
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("QR Message");
		stage.setScene(get_login_scene(stage));
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
