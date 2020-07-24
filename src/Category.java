import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * The Class Category 
 * that will serve as functional blueprints 
 * for Hangman semantic categories management
 */
public class Category {
	
	/** Representatives of the semantic category. */
	protected ArrayList<String> representatives = new ArrayList<String>();
		
	
	
	/**
	 * Method to populate semantic category with respective content
	 * taken from an indicated external file
	 *
	 * @param path = the path for the text file that contains the wordlist
	 * @throws FileNotFoundException 
	 */
	public void populateWith(String path) throws FileNotFoundException {
		
		File file = new File(path);
		Scanner input = new Scanner(file);
		
		while(input.hasNext()) 
			representatives.add(input.next().toUpperCase());
										   // uppercase the words for further "search material unification"
		input.close();
	}
	
	
	
	/**
	 * Method to retireve the size of representatives ArrayList
	 *
	 * @return the int value of the size of representatives ArrayList
	 */
	public int size() {
		return representatives.size();
	}
	
	
	
	/**
	 * Method to provide a random word form the respective category.
	 *
	 * @return the string representation of the secretWord
	 */
	public String provideRandomWord() {
		// Retrieve a random number within the range of the ArrayList size
		int randomIndex = (int)(Math.random() * (representatives.size() - 1));

		
		// Use this number as an index to retrieve a respective element from the ArrayList
		// Retrieved String will be the secret Word
		String secretWord = representatives.get(randomIndex);
		
		return secretWord;
	}
		
	
	
	/**
	 * Method that converts the content of "representatives" ArrayList to string.
	 *
	 * @return the string representation of "representatives" ArrayList
	 */
	public String contentToString() {
		
		String content = "[ ";
		
		for (String representative :representatives)
			content += representative + " ";
			
		content += "]";
		
		return content;
	}
	
	

}
