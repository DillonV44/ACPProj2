package DJV8.proj2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SpellCheckerGUI extends Application {
	
	private TextArea textArea;
	private String textFromFile;
	private StringBuffer dialogBoxText;
	
	
	public SpellCheckerGUI() {
		dialogBoxText = new StringBuffer();
	}
	
	@Override
	public void start(Stage stage) {
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
        
        Scene scene = new Scene(root, 600, 600);
        
        
        root.setCenter(textArea);
        
        stage.setTitle("Spell Checker");
        stage.setScene(scene);
		stage.show();
		
	}
	
	
	private MenuItem openFileItem() {
		MenuItem openFileItem = new MenuItem("Open File");
		FileParser fileParser = new FileParser();
		textFromFile = "";
		textArea = new TextArea();
		
		openFileItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override 
			public void handle(ActionEvent event) {
				
				FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File(".")); 
                File file = fileChooser.showOpenDialog(null);
                if(file != null) {
                  fileParser.setStringFromFile(file.getPath());
                  textFromFile = fileParser.getStringFromFile();
                  textArea.setWrapText(true);
                  textArea.setText(textFromFile);
                }
                
			}
		});
		return openFileItem;
	}
	
	private MenuItem saveFileItem() {
		MenuItem saveFileItem = new MenuItem("Save File");
		// Set Accelerator for Exit MenuItem.
	
		saveFileItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
				fileChooser.setInitialDirectory(new File("./"));
				fileChooser.setInitialFileName("saveFile.txt");
				File file = fileChooser.showSaveDialog(null);

				if (file != null) {
					try {
						PrintWriter writer;
						writer = new PrintWriter(file);
						writer.println(textArea.getText().toString());
						writer.close();
					} catch (IOException ex) {
						System.out.println(ex);
					}
				}
			}
		});
		return saveFileItem;
	}
	
	private MenuItem createExitItem() {
       MenuItem exitItem = new MenuItem("Exit");
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
		SpellCheckingAggregator spellCheckManager = new SpellCheckingAggregator();
		
		
	    // When user click on the Exit item
		spellCheck.setOnAction(new EventHandler<ActionEvent>() {

	           @Override
	           public void handle(ActionEvent event) {
	        	   String[] wordsFromFile = spellCheckManager.splitWordsFromFile(textArea.getText());
	        	   dialogBoxText = spellCheckManager.allSuggestedWords(wordsFromFile);
	        	   StringBuffer noSuggestionsBuffer = spellCheckManager.getNoMatchesFound();
	        	   if (!spellCheckManager.areThereIncorrectWords(textFromFile, wordsFromFile)) {
	        		   dialogBoxText.append("All words are spelled correctly");
	        		   Alert allClearAlert = new Alert(AlertType.CONFIRMATION);
	        		   allClearAlert.setTitle("Spell Checker");
	        		   allClearAlert.setHeaderText("Good job");
	        		   allClearAlert.setContentText("Nothing to fix");
	        		   allClearAlert.show();
	        	   }
	        	   
	        	   else {
	        		   Alert wordsToFix = new Alert(AlertType.CONFIRMATION);
	        		   wordsToFix.setTitle("Spell Checker");
	        		   wordsToFix.setHeaderText("Possible Corrections..");
	        		   wordsToFix.setContentText(dialogBoxText.toString());
	        		   
						wordsToFix.showAndWait();
					
	        	   }
	        	   if (noSuggestionsBuffer.toString() != "") {
	        		   Alert wordsToFix = new Alert(AlertType.ERROR);
	        		   wordsToFix.setTitle("Spell Checker");
	        		   wordsToFix.setHeaderText("There are no possible matches for the following words");
	        		   System.out.println(noSuggestionsBuffer.toString());
	        		   wordsToFix.setContentText(noSuggestionsBuffer.toString());
	        		   wordsToFix.show();
	        	   }
	        	   spellCheckManager.clearBuffer();
//	        	   System.out.println(textFromFile);
	           }
	       });
	       return spellCheck;
	}
	
	
	
	public void applicaitonLaunch(String[] args) {
		 Application.launch(args);
	}
	
	

}
