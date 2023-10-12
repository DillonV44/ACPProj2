/*
 * Student name: Dillon Vaughan
 * File name: SpellCorrection.java
 * Assignment number: Project 2
 * class SpellCorrection received strings of incorrect words and 
 * alters those strings looking for possible solutions to misspelled 
 * words
 */
package DJV8.proj2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SpellCorrection {
	// string correct spelling is returned if there is a match in 
	// the dictionary to the new word created
	private String correctSpelling;
	// set dictionarySet is a container of dictionary words to compare to
	private Set<String> dictionarySet;
	// dictionary loader allows the reading of dictionary words
	private DictionaryLoader dl;
	// new words is appended when creating new words
	private StringBuffer newWord;
	
	public SpellCorrection() {
		correctSpelling = "";
		dictionarySet = new HashSet<>();
		dl = new DictionaryLoader();
		dictionarySet = dl.getDictionarySet();
		newWord = new StringBuffer();
	}
	
	// clear string buffer will clear newWord at the beginning of each check
	public void clearStringBuffer(String inputString) {
		newWord.setLength(0);
		newWord.insert(0, inputString);
	}
	
	// one letter missing will return an array of suggestions after taking the incorrect word 
	// and deleting every character and then checking it against each word in the dictionary. 
	// @param inputString accepts the word that is misspelled for checking
	// @return returnList will return an array list of suggestions found
	public ArrayList<String> oneLetterMissing(String inputString) {
		// correct spelling is cleared
		correctSpelling = "";
		// return list is populated if matches are found
		ArrayList<String> returnList = new ArrayList<>();
		// Beginning and ending of the ASCII representation of lower case characters
		final int ASCII_START = 97;
		final int ASCII_END = 122;
		
		// for each letter in the alphabet lower case
		for (Character letter = ASCII_START; letter <= ASCII_END; letter++) {
			
			// for each character in the input string replace with currect character
			for (int i = 0; i < inputString.length() + 1; i ++) {
				
				clearStringBuffer(inputString);
				newWord.insert(i, letter);
				if (dictionarySet.contains(newWord.toString())) {
					// if found a correct word add it to the returnList array list
					correctSpelling = newWord.toString();
					returnList.add(correctSpelling);
					correctSpelling = "";
				}
			}
		}
		
		return returnList;
	}
	
	// oneletterAdded takes in a misspelled word and deleted each character in the string
	// and then checks the dictionary for matches
	// @param inputString accepts the word that is misspelled for checking
	// @return returnList will return an array list of suggestions found
	public ArrayList<String> oneLetterAdded(String inputString) {
		
		ArrayList<String> returnList = new ArrayList<>();
		
		// for each character in the string delete one character and check for matches
		for (int j = 0; j < inputString.length(); j ++) {
			
			clearStringBuffer(inputString);
			
			// deleting the character at the specific place in the string
			newWord.deleteCharAt(j);
			// checking to see if the word exist, if it does it is added to the return list
			if (dictionarySet.contains(newWord.toString())) {
				correctSpelling = newWord.toString();
				returnList.add(correctSpelling);
				correctSpelling = "";
			}
		}
		return returnList;
	}
	
	// twoLettersReversed takes a misspelled word and swaps two characters next to each other,
	// for every pair of characters in the string and searched for matches
	// @param inputString accepts the word that is misspelled for checking
	// @return returnList will return an array list of suggestions found
	public ArrayList<String> twoLettersReversed(String inputString) {
		correctSpelling = "";
		
		ArrayList<String> returnList = new ArrayList<>();
		
		// for each pair of characters next to each other, swap them and check for matches
		for (int i = 0; i < inputString.length() -1 ; i ++) {
			
			clearStringBuffer(inputString);
			
			// setting the characters to switch 
			char leftChar = newWord.charAt(i);
			char rightChar = newWord.charAt(i + 1);
			
			// switching the characters
			newWord.setCharAt(i, rightChar);
			newWord.setCharAt(i + 1, leftChar);
			
			// checking to see if the new word exist or not and
			// then adding it to the return list if it does
			if (dictionarySet.contains(newWord.toString())) {
				correctSpelling = newWord.toString();
				returnList.add(correctSpelling);
				correctSpelling = "";
			}
		}
		return returnList;
	}

}
