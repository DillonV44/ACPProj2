package DJV8.proj2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SpellCheckingAggregator {
	
	private String[] wordsFromFile;
	private ArrayList<String> incorrectWords;
	private ArrayList<String> suggestions;
	private String correctSpelling;
	private Set<String> set;
	private DictionaryLoader dl;
	private SpellCorrection spellCorrection;
	private StringBuffer wordSuggestions;
	
	
	
	public SpellCheckingAggregator() {
		
		suggestions = new ArrayList<>();
		incorrectWords = new ArrayList<>();
		set = new HashSet<>();
		dl = new DictionaryLoader();
		set = dl.getDictionarySet();
		correctSpelling = "";
		spellCorrection = new SpellCorrection();
		wordSuggestions = new StringBuffer();
	}
	
	public void splitWordsFromFile(String inputString) {
		String removedItems = inputString.replaceAll("[\\.,]", "");
		wordsFromFile = removedItems.split("\\s");
		for (int i = 0; i < wordsFromFile.length; i ++) {
			wordsFromFile[i] = wordsFromFile[i].toLowerCase();
		}

	}
	
	public StringBuffer allSuggestedWords() {
		
		ArrayList<String> returnedItems = new ArrayList<>();
		
		for (int i = 0; i < wordsFromFile.length; i ++) {
			if (!set.contains(wordsFromFile[i])) {
				incorrectWords.add(wordsFromFile[i]);
			}
			
		}
		
		for (int j = 0; j < incorrectWords.size(); j++) {
			// set the incorrect word into a string object for easier
			// manipulation of the word
			String incorretWordFromList = incorrectWords.get(j);
			
			wordSuggestions.append(incorretWordFromList + ": " );
			
			// one letter missing
			
			// Reset correctSpelling to empty 
			correctSpelling = "";
			
			returnedItems = spellCorrection.oneLetterMissing(incorretWordFromList);
			
			for (int x = 0; x < returnedItems.size(); x ++) {
				correctSpelling = returnedItems.get(x);
				if (correctSpelling != "" && !suggestions.contains(correctSpelling)) {
					suggestions.add(correctSpelling); 
					if (x == returnedItems.size() - 1) {
						wordSuggestions.append(correctSpelling);
					}
					else {
						wordSuggestions.append(correctSpelling + ", ");
					}
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
					if (y == returnedItems.size() - 1) {
						wordSuggestions.append(correctSpelling);
					}
					else {
						wordSuggestions.append(correctSpelling + ", ");
					}
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
					if (z == returnedItems.size() - 1) {
						wordSuggestions.append(correctSpelling);
					}
					else {
						wordSuggestions.append(correctSpelling + ", ");
					}
				}
				
			}
			
			wordSuggestions.append("\n");
			
			correctSpelling = "";
			returnedItems.clear();
			
		}
		
//		System.out.println(wordSuggestions.toString());
		
//		System.out.println(suggestions.size());
//		for (int i = 0; i < suggestions.size(); i ++) {
//			System.out.println(suggestions.get(i));
//		}
		return wordSuggestions;
	}
	
	public boolean areThereIncorrectWords(String inputString) {
		boolean missspelledWords = false;
		for (int i = 0; i < wordsFromFile.length; i ++) {
			if (!set.contains(wordsFromFile[i])) {
				missspelledWords = true;
				
			}
		}
		return missspelledWords;
	}
	

}
