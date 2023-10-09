package DJV8.proj2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FileParser {
	String textFromFile;
	
	public FileParser() {
		textFromFile = "";
	}
	
	public void setStringFromFile(String filePath) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			textFromFile = reader.readLine();
//			System.out.println(textFromFile);
//			textFromFile.append(data.split(/[ ,]+/).filter(Boolean));

			reader.close();
		} catch (IOException e) {
			System.out.println("Error reading selected file");
		}
		
	}
	
	public String getStringFromFile() {
		return textFromFile;
	}

}
