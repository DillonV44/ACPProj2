/*
 * Student name: Dillon Vaughan
 * File name: FileParser.java
 * Assignment number: Project 2
 * FileParser.java will read a file from a filePath
 */
package DJV8.proj2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FileParser {
	// string text from file will be returned to 
	// reflect the contents of a file
	String textFromFile;
	
	// setting text from file to empty
	public FileParser() {
		textFromFile = "";
	}
	
	// setting the seting text from file
	// @param filePath takes the filePath of the file
	// to read from
	public void setStringFromFile(String filePath) {
		BufferedReader reader;
		try {
			// read from the file
			reader = new BufferedReader(new FileReader(filePath));
			// read the entire file
			textFromFile = reader.readLine();
			// close the connection
			reader.close();
		} catch (IOException e) {
			System.out.println("Error reading selected file");
		}
		
	}
	
	// return the string from the file
	// @return textFromFile will return all text from file
	public String getStringFromFile() {
		return textFromFile;
	}

}
