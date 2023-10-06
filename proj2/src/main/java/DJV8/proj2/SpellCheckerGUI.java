package DJV8.proj2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SpellCheckerGUI extends Application {
	
	private TextArea textArea;
	
	
	@Override
	public void start(Stage stage) {
		BorderPane borderPane = new BorderPane();
        
		
		// Create MenuBar
        MenuBar menuBar = new MenuBar();
 
        // Create menus
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        
		
        // Create MenuItems for File
        MenuItem openFileItem = openFileItem();  
        MenuItem saveFileItem = saveFileItem();
        MenuItem exitItem = createExitItem();
        
        // Create MenuItems for Edit
        MenuItem spellCheck = analyzeSpellCheck();
        
        // Add menuItems to the Menus
        fileMenu.getItems().addAll(openFileItem, saveFileItem, exitItem);
        editMenu.getItems().addAll(spellCheck);
        
        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(fileMenu, editMenu);
        
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        Scene scene = new Scene(root, 350, 200);
        
        TextArea textArea = new TextArea();
        root.setBottom(textArea);
        
        stage.setTitle("Spell Checker");
        stage.setScene(scene);
		stage.show();
		
	}
	
	
	private MenuItem openFileItem() {
		MenuItem openFileItem = new MenuItem("Open File");
		
		openFileItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override 
			public void handle(ActionEvent event) {
				System.out.println("Chose the file-new item");
			}
		});
		return openFileItem;
	}
	
	private MenuItem saveFileItem() {
		MenuItem saveFileItem = new MenuItem("Save File");
		// Set Accelerator for Exit MenuItem.
		saveFileItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		saveFileItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Chose the file-open item");
			}
		});
		return saveFileItem;
	}
	
	private MenuItem createExitItem() {
       MenuItem exitItem = new MenuItem("Exit");
        // Set Accelerator for Exit MenuItem.
       exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
       // When user click on the Exit item
       exitItem.setOnAction(new EventHandler<ActionEvent>() {

           @Override
           public void handle(ActionEvent event) {
               System.exit(0);
           }
       });
       return exitItem;
    }
	
	private MenuItem analyzeSpellCheck() {
		MenuItem spellCheck = new MenuItem("Spell Check");
	    // When user click on the Exit item
		spellCheck.setOnAction(new EventHandler<ActionEvent>() {

	           @Override
	           public void handle(ActionEvent event) {
	        	   System.out.println("Spell Check");
	           }
	       });
	       return spellCheck;
	}
	
	
	
	public void applicaitonLaunch(String[] args) {
		 Application.launch(args);
	}
	
	

}
