/*
 * Student name: Dillon Vaughan
 * File name: DictionaryLoader.java
 * Assignment number: Project 2
 * Dictionary loader takes the words out of the text file
 * provided and stores them all in a hash set for quick
 * reading
 */
package DJV8.proj2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DictionaryLoader {
	// hash set to store all dictionary words
	private Set<String> dictionary;
	// file name to pull dictionary words from
	private final String dictionaryFile = "Words.txt";
	
	// constructor to initialize the set
	// and launch the main method to set 
	// the dictionary 
	public DictionaryLoader() {
		dictionary = new HashSet<>();
		setDictionarySet();
	}
	
	// setDictionarySet will read from the file 
	// provided and add each word to the dictionary 
	public void setDictionarySet() {
		// BufferedReader object for file reading
		BufferedReader readFile;
		try {
			// readFile will read from the provided file
			readFile = new BufferedReader(new FileReader(dictionaryFile));
			// this string will capture each line of the file
			String newLine = readFile.readLine();
			// as long as the file is not empty, keep reading
			while (newLine != null) {
				// add each word in lower case format
				dictionary.add(newLine.toLowerCase());
				// newLine will let us know when we have reached the end of the file
				newLine = readFile.readLine();
			}
		} catch (IOException e) {
			System.out.println("Failed dictionary file reading");
		}
	}
	
	// external classes can call this method to 
	// retrieve the dictionary set 
	// @returns dictionary set<String>
	public Set<String> getDictionarySet() {
		return dictionary;
	}
}
