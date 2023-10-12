/*
 * Student name: Dillon Vaughan
 * File name: SpellCheckerGUI.java
 * Assignment number: Project 2
 * SpellCheckerGUI will create the entire window
 * for the entire spell check application
 */
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
	
	// Text area is the space where spell check will take the 
	// text to spell check against 
	private TextArea textArea;
	// textFromFile is the text received from file to set into the textArea
	private String textFromFile;
	//dialogBoxText is the text to input into the dialogBox for 
	private StringBuffer dialogBoxText;
	
	// constructor 
	public SpellCheckerGUI() {
		dialogBoxText = new StringBuffer();
	}
	
	// Method start is overridden 
	// @param stage is the primary stage for the application
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
        
        // set the border pane
        BorderPane root = new BorderPane();
        // setting the menuBar in the border pane 
        root.setTop(menuBar);
        
        // setting the size of the window 
        Scene scene = new Scene(root, 600, 600);
        
        // setting the text area in the border pane
        root.setCenter(textArea);
        
        // setting the title of the stage and setting the scene in the stage
        stage.setTitle("Spell Checker");
        stage.setScene(scene);
		stage.show();
		
	}
	
	// openFile item will open
	// @return will return the open file menu item
	private MenuItem openFileItem() {
		// new menuItem that will be returned
		MenuItem openFileItem = new MenuItem("Open File");
		// fileParser will read from the file that is opened
		FileParser fileParser = new FileParser();
		textFromFile = "";
		textArea = new TextArea();
		
		// setting the action event on openFile 
		openFileItem.setOnAction(new EventHandler<ActionEvent>() {
			
			// Handling the action event 
			@Override 
			public void handle(ActionEvent event) {
				
				FileChooser fileChooser = new FileChooser();
				// setting the default directory to where the program
				// is located currently on the users machine
                fileChooser.setInitialDirectory(new File(".")); 
                File file = fileChooser.showOpenDialog(null);
                // if the file is working, set the text to the text area 
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
	
	// save file item will save all text in the text box to a default location
	// of where the file is located. A default file name saveFile.txt will be 
	// loaded but the user can change the name and location of the file
	// @return MenuItem will return the menu item that can be added to the 
	// javaFx window later
	private MenuItem saveFileItem() {
		MenuItem saveFileItem = new MenuItem("Save File");
		
		// event handler for save file button
		saveFileItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// fileChooser will create the file saving object 
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
				fileChooser.setInitialDirectory(new File("./"));
				fileChooser.setInitialFileName("saveFile.txt");
				File file = fileChooser.showSaveDialog(null);
				
				if (file != null) {
					try {
						// writing the text area to the file 
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
	
	// createExitItem will create a menu item that will allow a user to exit the program
	private MenuItem createExitItem() {
       MenuItem exitItem = new MenuItem("Exit");
       // When user click on the Exit item
       exitItem.setOnAction(new EventHandler<ActionEvent>() {
    	   // program will end if exit item is selected
           @Override
           public void handle(ActionEvent event) {
               System.exit(0);
           }
       });
       return exitItem;
    }
	
	// method analyzeSpellCheck will create a menu item for spell checking
	// @return spellCheck will return the menu item for spell checking
	private MenuItem analyzeSpellCheck() {
		
		MenuItem spellCheck = new MenuItem("Spell Check");
		// creating a spellcheckaggregator object will allow all text in the text area to 
		// be checked against a dictionary of words, so that words can have suggestions created for them
		SpellCheckingAggregator spellCheckManager = new SpellCheckingAggregator();
		
		
	    // When user click on the Exit item
		spellCheck.setOnAction(new EventHandler<ActionEvent>() {

	           @Override
	           public void handle(ActionEvent event) {
	        	   // words from file is a string of words from the text area, spellcheckManager has a 
	        	   // method that will spilt each of the words off into an array of strings that will be
	        	   // returned to wordsFromFile
	        	   String[] wordsFromFile = spellCheckManager.splitWordsFromFile(textArea.getText());
	        	   // dialogBoxText is a string buffer that will populate the correct words to display recommendations 
	        	   dialogBoxText = spellCheckManager.allSuggestedWords(wordsFromFile);
	        	   StringBuffer noSuggestionsBuffer = spellCheckManager.getNoMatchesFound();
	        	   // If all words are correctly spelled in the text area, a dialog box shows up displaying that infromation
	        	   if (!spellCheckManager.areThereIncorrectWords(textFromFile, wordsFromFile)) {
	        		   dialogBoxText.append("All words are spelled correctly");
	        		   Alert allClearAlert = new Alert(AlertType.CONFIRMATION);
	        		   allClearAlert.setTitle("Spell Checker");
	        		   allClearAlert.setHeaderText("Good job");
	        		   allClearAlert.setContentText("Nothing to fix");
	        		   allClearAlert.show();
	        	   }
	        	   // If there are words and corrections to be displayed, that is displayed here
	        	   else {
	        		   Alert wordsToFix = new Alert(AlertType.CONFIRMATION);
	        		   wordsToFix.setTitle("Spell Checker");
	        		   wordsToFix.setHeaderText("Possible Corrections..");
	        		   wordsToFix.setContentText(dialogBoxText.toString());
	        		   
						wordsToFix.showAndWait();
					
	        	   }
	        	   // If there are words that are incorrectly spelled however there are no suggestions, those words are displayed here
	        	   if (noSuggestionsBuffer.length() != 0 && spellCheckManager.areThereIncorrectWords(textFromFile, wordsFromFile)) {
	        		   Alert wordsToFix = new Alert(AlertType.ERROR);
	        		   wordsToFix.setTitle("Spell Checker");
	        		   wordsToFix.setHeaderText("There are no possible matches for the following words");
	        		   wordsToFix.setContentText(noSuggestionsBuffer.toString());
	        		   wordsToFix.show();
	        	   }
	        	   // clear the buffers of dialog info each time you press spellcheck and you have closed all windows 
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
