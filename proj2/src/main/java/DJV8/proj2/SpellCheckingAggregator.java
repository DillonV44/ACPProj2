package DJV8.proj2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SpellCheckingAggregator {
	
	
	
	
	private String correctSpelling;
	private Set<String> dictionarySet;
	private DictionaryLoader dl;
	private SpellCorrection spellCorrection;
	private StringBuffer wordSuggestions;
	private StringBuffer noSuggestionsBuffer;
	
	
	
	public SpellCheckingAggregator() {
		
		
		dictionarySet = new HashSet<>();
		dl = new DictionaryLoader();
		dictionarySet = dl.getDictionarySet();
		correctSpelling = "";
		spellCorrection = new SpellCorrection();
		wordSuggestions = new StringBuffer();
		noSuggestionsBuffer = new StringBuffer();
		
	}
	
	public void clearBuffer() {
		this.wordSuggestions.setLength(0);
		noSuggestionsBuffer.setLength(0);
	}
	
	public String[] splitWordsFromFile(String inputString) {
		
		String removedItems = inputString.replaceAll("[\\.,]", "");
		String[] wordsFromFile = removedItems.split("\\s");
		for (int i = 0; i < wordsFromFile.length; i ++) {
			wordsFromFile[i] = wordsFromFile[i].toLowerCase();
		}
		return wordsFromFile;

	}
	
	public StringBuffer allSuggestedWords(String[] wordsFromFile) {
		
		ArrayList<String> returnedItems = new ArrayList<>();
		ArrayList<String> incorrectWords = new ArrayList<>();
		ArrayList<String> suggestions = new ArrayList<>();
		Boolean matchFound = false;
		
		
		for (int i = 0; i < wordsFromFile.length; i ++) {
			if (!dictionarySet.contains(wordsFromFile[i])) {
				incorrectWords.add(wordsFromFile[i]);
			}
			
		}
		
		for (int j = 0; j < incorrectWords.size(); j++) {
			// set the incorrect word into a string object for easier
			// manipulation of the word
			String incorretWordFromList = incorrectWords.get(j);
			
			wordSuggestions.append(incorretWordFromList + ": " );
			
			matchFound = false;
			// one letter missing
			
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
			
			
			correctSpelling = "";
			returnedItems.clear();
			
			
			
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
			
			
			correctSpelling = "";
			returnedItems.clear();
			
			// two letters reversed
			
			returnedItems = spellCorrection.twoLettersReversed(incorretWordFromList);
			
			for (int z = 0; z < returnedItems.size(); z ++) {
				correctSpelling = returnedItems.get(z);
				
				if (correctSpelling != "" && !suggestions.contains(correctSpelling)) {
					suggestions.add(correctSpelling);
					wordSuggestions.append(correctSpelling + ", ");
					matchFound = true;
				}
				
			}
			
			if (!matchFound) {
				populateNoMatchesFoundStringBuffer(incorretWordFromList);
				wordSuggestions.append("No suggestions for this miss spelled word");
				
			}
			
			wordSuggestions.append("\n");
			
			
			
			correctSpelling = "";
			returnedItems.clear();
			
		}
		

		return wordSuggestions;
	}
	
	public boolean areThereIncorrectWords(String inputString, String[] wordsFromFile) {
		boolean missspelledWords = false;
		for (int i = 0; i < wordsFromFile.length; i ++) {
			if (!dictionarySet.contains(wordsFromFile[i])) {
				missspelledWords = true;
				
			}
		}
		return missspelledWords;
	}
	
	public void populateNoMatchesFoundStringBuffer(String noSuggestions) { 
		System.out.println("Input this word " + noSuggestions);
		noSuggestionsBuffer.append(noSuggestions + ", ");
	}
	
	public StringBuffer getNoMatchesFound() {
		return noSuggestionsBuffer; 
	}
	

}
