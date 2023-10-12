package DJV8.proj2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SpellCorrection {
	private String correctSpelling;
	private Set<String> dictionarySet;
	private DictionaryLoader dl;
	private StringBuffer newWord;
	
	public SpellCorrection() {
		correctSpelling = "";
		dictionarySet = new HashSet<>();
		dl = new DictionaryLoader();
		dictionarySet = dl.getDictionarySet();
		newWord = new StringBuffer();
	}
	
	
	public void clearStringBuffer(String inputString) {
		newWord.setLength(0);
		newWord.insert(0, inputString);
	}
	
	public ArrayList<String> oneLetterMissing(String inputString) {
		correctSpelling = "";
		ArrayList<String> returnList = new ArrayList<>();
		final int ASCII_START = 97;
		final int ASCII_END = 122;
		
		for (Character letter = ASCII_START; letter <= ASCII_END; letter++) {
			
			for (int i = 0; i < inputString.length() + 1; i ++) {
				
				clearStringBuffer(inputString);
				newWord.insert(i, letter);
				if (dictionarySet.contains(newWord.toString())) {
					
					correctSpelling = newWord.toString();
					returnList.add(correctSpelling);
					correctSpelling = "";
				}
			}
		}
		
		return returnList;
	}
	
	
	public ArrayList<String> oneLetterAdded(String inputString) {
		
		ArrayList<String> returnList = new ArrayList<>();
		
		
		for (int j = 0; j < inputString.length(); j ++) {
			
			clearStringBuffer(inputString);
			newWord.deleteCharAt(j);
			if (dictionarySet.contains(newWord.toString())) {
		
				correctSpelling = newWord.toString();
				returnList.add(correctSpelling);
				correctSpelling = "";
				
			}
		}
	
		
		return returnList;
	}
	
	public ArrayList<String> twoLettersReversed(String inputString) {
		correctSpelling = "";
		
		ArrayList<String> returnList = new ArrayList<>();
		
		
		for (int i = 0; i < inputString.length() -1 ; i ++) {
			
			clearStringBuffer(inputString);
			
			char leftChar = newWord.charAt(i);
			char rightChar = newWord.charAt(i + 1);
			
			newWord.setCharAt(i, rightChar);
			newWord.setCharAt(i + 1, leftChar);
			
			
			if (dictionarySet.contains(newWord.toString())) {
				
				correctSpelling = newWord.toString();
				returnList.add(correctSpelling);
				correctSpelling = "";
				
			}
			
		}
		
		
		
		return returnList;
	}

}
