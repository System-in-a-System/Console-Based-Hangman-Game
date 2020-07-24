import java.util.Scanner;

/**
 * The Class GameRound
 * represents the functionality of the game round logic.
 */
public class GameRound {
	
	/** Current player. */
	private Player player;
	
	/** Current category. */
	private Category category;
	
	
	/** The recieved secret word. */
	private String secretWord;
	
	/** The recieved secret word scheme. */
	private String secretWordScheme;
	
	
	/** The number of tries. */
	private int numberOfTries = 0;
	
	/** The  score. */ 
	private int score = 0;
	
	
	/** Game round status. */
	private boolean roundIsWon = false;
	
	/** The round is lost. */
	private boolean roundIsLost = false;
	
	
	
	
	
	
	/**
	 * Instantiates a new game round.
	 *
	 * @param thePlayer is the current the player
	 * @param theCategory the current semantic category
	 */
	public GameRound(Player thePlayer, Category theCategory) { 
		setPlayer(thePlayer);
		setCategory(theCategory);
	}
	
	
	
	/**
	 * Sets the player.
	 *
	 * @param thePlayer is a new player
	 * @throws IllegalArgumentException
	 */
	public void setPlayer(Player thePlayer) throws IllegalArgumentException {
		if(thePlayer instanceof Player)
			player = thePlayer;
		else 
			throw new IllegalArgumentException ("The input parameters should be of type Player");
	}
	
	
	
	/**
	 * Sets the category.
	 *
	 * @param theCategory is a new category
	 * @throws IllegalArgumentException
	 */
	public void setCategory(Category theCategory) throws IllegalArgumentException {
		if(theCategory instanceof Category)
			category = theCategory;
		else 
			throw new IllegalArgumentException ("The input parameters should be of type Category");
	}
	
	
	
	
	
	
	
	
	/**
	 *  ---------------------------------Basic run method of the game round---------------------------------------
	 *
	 * @throws InterruptedException 
	 */
	public void run() throws InterruptedException {
				
		generateSecretWord();
		setSecretWordScheme();
		
		Scanner input = new Scanner(System.in);
		
		do {		
			// Basic game flow text-based user interface
			System.out.println("~~~~~~~~~~~~~~~~~Hangman 1.0~~~~~~~~~~~~~~~~~~");
			
			System.out.println("----------------------------------------------\n");
			System.out.println(currentHangmanStatus());
			System.out.println("\n----------------------------------------------\n");
			
			System.out.println("______________________________________________");
			System.out.println("SECRET WORD:  " + getSecretWordScheme());
			System.out.println("______________________________________________\n");
			
			
			System.out.println("Press L --> Suggest a letter");
			System.out.println("Press W --> Suggest a word");
			System.out.println("Press B --> Back to the main menu");
			
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			System.out.print("Your choice: ");
			char choice = input.next().toCharArray()[0];
			
			
			
			// If the user choses to suggest a letter
			if (choice == 'L' || choice == 'l') {
				System.out.print("Suggest a letter: ");
				char letter = input.next().toCharArray()[0];
				
				
				// If the letter is present in the secret word
				if (letterIsPresent(letter)) {
					System.out.println("You guessed correct!");
					updateSecretWordScheme(letter);
				
					
				// If the letter is not present in the secret word
				} else {
					System.out.println("Your guess was not correct!");
					
					// Update number of tries counter
					numberOfTries++;
					
					// Update round status to lost in case the player has used up all 8 tries
					if (numberOfTries == 8) {
						roundIsLost = true;
						finalizeRound();
					}
				}
					
			}
			
			
			// If the user choses to suggest a word
			else if (choice == 'W' || choice == 'w') {
				System.out.print("Suggest a word: ");
				String word = input.next();
				
				// If the user guessed the word
				if (isTheSecretWord(word)) {
					finalizeRound();
					break;
				
				
				// If the user did not guess the word	
				} else 
					System.out.println("The suggested word is incorrect");	
			}
			
			
			
			// If the user choses to go back to the main menu
			else if (choice == 'B' || choice == 'b') 
				roundIsLost = true;
			
			
			
			// If the user enterd invalid choice
			else 
				System.out.println("Please, enter 'l', 'w' or 'b for your choice");
			
			
			
			
			
			// A little pause
			Thread.sleep(1500);
			
			// Console clear imitation
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			
			
		} while (roundIsOn()); // = while the outcome of the round has not been settled yet
		
	}
	
	
	
	
	
	/**
	 *----------------------------  Help methods used in the game logic: -----------------------------------------
	 */
	
	/**
	 * Assigns secret word randomly picked out within the respective Category class
	 * 
	 * @throws IndexOutOfBoundsException
	 */
	public void generateSecretWord() throws IndexOutOfBoundsException {
		
		if (category.size() > 0) 
			secretWord = category.provideRandomWord();
			
		else 
			throw new IndexOutOfBoundsException ("The category is empty. You should populate the category first");
		
	}
	
	
	
	/**
	 * Constructs the secret word scheme.
	 *
	 * @return the string
	 */
	public void setSecretWordScheme() {
		
		if (secretWord.length() > 0) {
			
			String scheme = "";
			for (int i = 0; i < secretWord.length(); i++)
				scheme += ".";
			
			secretWordScheme = scheme;
		
		} else {
			secretWordScheme = "";
		}
	}
	
	
	
	/**
	 * Gets the secret word scheme.
	 *
	 * @return the secret word scheme
	 */
	public String getSecretWordScheme() {
		return secretWordScheme;
	}
	
	
	
	/**
	 * Updates secret word scheme.
	 *
	 * @param theLetter = the letter to update the secret word with
	 * @throws IllegalArgumentException
	 */
	public void updateSecretWordScheme(char theLetter) {
		
		if (theLetter != '\0') {
			
			// Uppercase the letter before the search
			theLetter = Character.toUpperCase(theLetter);
			
			// Locate matched letters
			for(int i = 0; i < secretWord.length(); i++) {
			
				if(secretWord.charAt(i) == theLetter) 
				
					// Update the word scheme
					secretWordScheme = secretWordScheme.substring(0, i) 
								   	   + theLetter 
								   	   + secretWordScheme.substring(i + 1);	
			
			
			}			
		
			
		// Catch possible illegal argument exceptions	
		} else {
			throw new IllegalArgumentException ("Error: No letter has been provided");
		}
	}
	
	
	
	/**
	 * Checks if the suggested letter is present in the secret word.
	 *
	 * @param theLetter = the suggested letter
	 * @return true, if the suggested letter is present in the word
	 * 
	 * @throws IllegalArgumentException
	 */
	public boolean letterIsPresent (char theLetter) throws IllegalArgumentException {
		
		if (theLetter != '\0') {
			
			// Uppercase the letter before the search
			theLetter = Character.toUpperCase(theLetter);
			
			
			// Check the uppercased letter against the uppercased secret word
			// If indexOf returns >= 0, the letter is present in the word
			if (secretWord.indexOf(theLetter) >= 0)			
				return true;
						
			// If indexOf returns "-1", the letter is NOT present in the word
			else				
				return false;
			
				
				
		// Catch possible illegal argument exceptions		
		} else {
			throw new IllegalArgumentException ("Error: No letter has been provided");
		}
				 		
	}
	
	
	
	/**
	 * Checks if the suggested word corresponds to the secret word
	 *
	 * @param theWord = the suggested word
	 * @return true, if the suggested word corresponds to the secret word
	 * 
	 * @throws IllegalArgumentException
	 */
	public boolean isTheSecretWord(String theWord) throws IllegalArgumentException {
		
		if (theWord != "") {
			
			// Uppercase the input word before the check
			theWord = theWord.toUpperCase();
			
			
			// If the word corresponds to the secret word
			if (theWord.equals(secretWord)) {
				roundIsWon = true;
				return true;
			
			// If the word does not correspond to the secret word	
			} else {
				numberOfTries++;
				return false;
			}
				
		
		// Catch possible Illegal Argument Exceptions	
		} else {
			throw new IllegalArgumentException ("Error: No word has been provided");
		}
	}
	
	
	
	/**
	 * Gets the number of tries.
	 *
	 * @return the number of tries
	 */
	public int getNumberOfTries() {
		return numberOfTries;
	}
	
	
	
	/**
	 * Updates hangman image based on the number of failed tries
	 *
	 * @return the string
	 */
	public String currentHangmanStatus() {
		
		String hangmanStatus = "";
		
		if (numberOfTries == 1) 
			hangmanStatus = " =========\n |\n |\n |\n |\n_|_";
		
		if (numberOfTries == 2)
			hangmanStatus = " =========\n |\t |\n |\n |\n |\n_|_";
			
		if (numberOfTries == 3)
			hangmanStatus = " =========\n |\t |\n |\t O\n |\n |\n_|_";
		
		if (numberOfTries == 4)
			hangmanStatus = " =========\n |\t |\n |\t O\n |\t | \n |\n_|_";
		
		if (numberOfTries == 5)
			hangmanStatus = " =========\n |\t |\n |\t O\n |\t/| \n |\n_|_";
		
		if (numberOfTries == 6)
			hangmanStatus = " =========\n |\t |\n |\t O\n |\t/|\\ \n |\n_|_";
		
		if (numberOfTries == 7)
			hangmanStatus = " =========\n |\t |\n |\t O\n |\t/|\\ \n |\t/ \n_|_";
		
		if (numberOfTries == 8)
			hangmanStatus = " =========\n |\t |\n |\t O\n |\t/|\\ \n |\t/ \\ \n_|_";
				
		
		
		return hangmanStatus;
	}
	
	
	
	/**
	 * Calculates score based on the number of failed tries.
	 *
	 * @return player's score
	 */
	public int calculateScore() {
		if (roundIsWon) {
			score = ((8 - numberOfTries) * 10) + 20;
			player.recordScore(score);
			return score;
			
		} else {
			return 0;
		}
		
	}
	
	
	
	/**
	 * Game round "switcher"
	 *
	 * @return true, if the game round outcome has not yet been established = the round is still on
	 */
	public boolean roundIsOn() {
		if (!roundIsWon && !roundIsLost)
			return true;
		else 
			return false;
	}
	
	
	
	/**
	 * Finalizes the game round & Outputs round outcome message
	 *
	 * @throws InterruptedException 
	 */
	public void finalizeRound() throws InterruptedException {
		
		// Clear console window imitation
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		
		
		
		// Output round outcome message in case when the round is won
		if (roundIsWon) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~Hangman 1.0~~~~~~~~~~~~~~~~~~~~~\n\n\n\n");
			System.out.println("   Well done, " + player.getName() + "! You saved the man!");
			System.out.println("           Your score is " + calculateScore() + "!");
			System.out.println("\n\n\n\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		
		// Output round outcome message in case when the round is lost	
		} else if (roundIsLost) {
			// Add dramatic effect to the hangman image
			System.out.println("~~~~~~~~~~~~~~~~~~~~Hangman 1.0~~~~~~~~~~~~~~~~~~~~~\n\n\n");
			System.out.println("TA DA DA DAM");
			System.out.println(currentHangmanStatus());
			System.out.println("\n\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
		
			// A little pause for the user to comprehend the gravity of the situation
			Thread.sleep(4000);
			
			
			// Clear console window imitation
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			
			System.out.println("~~~~~~~~~~~~~~~~~~~~Hangman 1.0~~~~~~~~~~~~~~~~~~~~~\n\n\n");
			System.out.println("          The man has been hanged             ");
			System.out.println("                   X X                        ");
			System.out.println("                    0                       \n");
			System.out.println("      The secret word was : " + secretWord);
			System.out.println("\n\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
		
		
		// Provide an option to go back to the main menu
		System.out.println("Press 'x' to go back to the main menu");
		Scanner keypress = new Scanner(System.in);
		String x = keypress.next();
		if (x.charAt(0) == 'X' || x.charAt(0) == 'x')
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	
	}
	

}
