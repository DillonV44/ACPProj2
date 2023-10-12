/*
 * Student name: Dillon Vaughan
 * File name: SpellCheckingAggregator.java
 * Assignment number: Project 2
 * class SpellCheckingAggregator will take info from sent 
 * from the GUI and distribute words out to find matches
 * for the words that are misspelled 
 */
package DJV8.proj2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SpellCheckingAggregator {
	
	// correctSpelling is a temporary string used to extract 
	// strings from return items of suggestions 
	private String correctSpelling;
	// dictionary set is what the words are checked against 
	private Set<String> dictionarySet;
	// dictionary loader is where the dictionary set data comes from
	private DictionaryLoader dl;
	// spell correction is the class words are sent to be checked for suggestions
	private SpellCorrection spellCorrection;
	// word suggestions is the string buffer that is populated with suggestions
	// and sent to the GUI for presentation 
	private StringBuffer wordSuggestions;
	// no suggestions is the string buffer that is populated with words that don't
	// have suggestions and sent to the GUI for presentation 
	private StringBuffer noSuggestionsBuffer;
	
	
	// constructor 
	public SpellCheckingAggregator() {
		dictionarySet = new HashSet<>();
		dl = new DictionaryLoader();
		dictionarySet = dl.getDictionarySet();
		correctSpelling = "";
		spellCorrection = new SpellCorrection();
		wordSuggestions = new StringBuffer();
		noSuggestionsBuffer = new StringBuffer();
		
	}
	
	// clearBuffer with clear the buffer of each 
	// string buffer after all dialog boxes have 
	// been closed
	public void clearBuffer() {
		this.wordSuggestions.setLength(0);
		this.noSuggestionsBuffer.setLength(0);
	}
	
	// splitWordsFromFile will take an entire string from
	// file and turn it into an array of strings 
	// @reutrn wordsFromFile will return an arraylist of strings
	public String[] splitWordsFromFile(String inputString) {
		// remove characters . and , 
		String removedItems = inputString.replaceAll("[\\.,]", "");
		// split on whitespace
		String[] wordsFromFile = removedItems.split("\\s");
		// turn all words to lower case for comparison
		for (int i = 0; i < wordsFromFile.length; i ++) {
			wordsFromFile[i] = wordsFromFile[i].toLowerCase();
		}
		return wordsFromFile;

	}
	
	// method allSuggestedWords will take all incorrect words and will 
	// send them to the SpellCorrection class to check for possible suggestions
	// @return wordSuggestions will return a stringbuffer of all word suggestions
	public StringBuffer allSuggestedWords(String[] wordsFromFile) {
		// returnedItems are items returned from the SpellCorrection class
		ArrayList<String> returnedItems = new ArrayList<>();
		// incorrectWords are words that are not found in the dictionary
		ArrayList<String> incorrectWords = new ArrayList<>();
		// suggestions is an array list of suggestions
		ArrayList<String> suggestions = new ArrayList<>();
		// boolean matchFound is set to true if a suggestion exist for the word
		Boolean matchFound = false;
		
		// finding which words from the text area are incorrectly spelled
		for (int i = 0; i < wordsFromFile.length; i ++) {
			if (!dictionarySet.contains(wordsFromFile[i])) {
				incorrectWords.add(wordsFromFile[i]);
			}
		}
		
		// sending each word to be checked for possible mathces 
		for (int j = 0; j < incorrectWords.size(); j++) {
			// set the incorrect word into a string object for easier
			// manipulation of the word
			String incorretWordFromList = incorrectWords.get(j);
			
			// adding the incorrect word to the string buffer
			wordSuggestions.append(incorretWordFromList + ": " );
			
			matchFound = false;
			
			// ------------------- logic for one letter missing -----------------
			
			// Reset correctSpelling to empty 
			correctSpelling = "";
			
			returnedItems = spellCorrection.oneLetterMissing(incorretWordFromList);
			
			for (int x = 0; x < returnedItems.size(); x ++) {
				correctSpelling = returnedItems.get(x);
				if (correctSpelling != "" && !suggestions.contains(correctSpelling)) {
					suggestions.add(correctSpelling); 
					wordSuggestions.append(correctSpelling + ", ");
					matchFound = true;
				}
			}
			
			// Reset correctSpelling to empty and returnedItems to clear
			correctSpelling = "";
			returnedItems.clear();
			
			// ------------------- logic for one letter added -----------------
			
			// one letter added
			
			returnedItems = spellCorrection.oneLetterAdded(incorretWordFromList);
			
			for (int y = 0; y < returnedItems.size(); y ++) {
				correctSpelling = returnedItems.get(y);
				
				if (correctSpelling != "" && !suggestions.contains(correctSpelling)) {
					suggestions.add(correctSpelling);
					wordSuggestions.append(correctSpelling + ", ");
					matchFound = true;
				}
			}
			
			// Reset correctSpelling to empty and returnedItems to clear
			correctSpelling = "";
			returnedItems.clear();
			
			// ------------------- logic for two letters reversed -----------------
			
			returnedItems = spellCorrection.twoLettersReversed(incorretWordFromList);
			
			for (int z = 0; z < returnedItems.size(); z ++) {
				correctSpelling = returnedItems.get(z);
				
				if (correctSpelling != "" && !suggestions.contains(correctSpelling)) {
					suggestions.add(correctSpelling);
					wordSuggestions.append(correctSpelling + ", ");
					matchFound = true;
				}
			}
			
			// if there are no matches found populate the word in the incorrectWordList
			if (!matchFound) {
				populateNoMatchesFoundStringBuffer(incorretWordFromList);
				wordSuggestions.append("No suggestions for this miss spelled word");
				
			}
			wordSuggestions.append("\n");
			
			// Reset correctSpelling to empty and returnedItems to clear
			correctSpelling = "";
			returnedItems.clear();	
		}
		return wordSuggestions;
	}
	
	// areThereIncorrectWords will return of boolean of if there are misspelled words or not
	// @param inputString is the input word that will be checked
	// @param wordsFromFile is the string array of dictionary of words that will be checked against
	// @return missspelledWords will return true or false if there are words to check
	public boolean areThereIncorrectWords(String inputString, String[] wordsFromFile) {
		boolean missspelledWords = false;
		for (int i = 0; i < wordsFromFile.length; i ++) {
			if (!dictionarySet.contains(wordsFromFile[i])) {
				missspelledWords = true;
			}
		}
		return missspelledWords;
	}
	
	// populateNoMatchesFoundStringBuffer will receive words to populate in the string buffer for no 
	// suggestions found
	// @param noSuggestions takes in a string of a word that has no suggestions
	public void populateNoMatchesFoundStringBuffer(String noSuggestions) { 
		noSuggestionsBuffer.append(noSuggestions + ", ");
	}
	
	// getNoMatchesFound returns the string buffer of words that have no suggestions
	// @return noSuggestionsBuffer will return the string buffer of words that have no suggestions
	public StringBuffer getNoMatchesFound() {
		return noSuggestionsBuffer; 
	}
	

}
