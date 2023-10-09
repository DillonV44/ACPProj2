package DJV8.proj2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DictionaryLoader {
	private Set<String> dictionary;
	private final String dictionaryFile = "Words.txt";
	
	public DictionaryLoader() {
		dictionary = new HashSet<>();
		setDictionarySet();
	}
	
	public void setDictionarySet() {
		BufferedReader readFile;
		try {
			readFile = new BufferedReader(new FileReader(dictionaryFile));
			String newLine = readFile.readLine();
			while (newLine != null) {
				dictionary.add(newLine.toLowerCase());
				newLine = readFile.readLine();
			}
		} catch (IOException e) {
			System.out.println("Failed dictionary file reading");
		}
	}
	
	public Set<String> getDictionarySet() {
		return dictionary;
	}
}
