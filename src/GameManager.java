import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameManager {

	public static void main(String[] args) throws InterruptedException, IllegalArgumentException, IOException {
		
		// Prepare semantic categories for the game: locate the paths for txt documents holding respective wordlists 
	    String continents = new File("src/continents.txt").getAbsolutePath();
	    String countries = new File("src/countries.txt").getAbsolutePath();
	    String capitals = new File("src/capitals.txt").getAbsolutePath();
	    
		
		
		// Display greetings & "log-in field" 
	    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
	    System.out.println("           Welcome to Hangman Game");
	    
	    System.out.print("\n           Enter your nickname: ");
		Scanner input = new Scanner(System.in);
		String nickname = input.next();
		
		
		
		// Clear console window imitation
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		
	
		
		// Instantiate a Player object (retrieved nickname in the constructor parameter field)
		Player player = new Player(nickname);
		
		
		
		
		// Menu "switcher"
		boolean menuSwitcherOn = true;
		
		// Open a menu loop
		do {
			
			// Print menu choices: 
			System.out.println("~~~~~~~~~~~~~~~~~Hangman 1.0~~~~~~~~~~~~~~~~~~~~~~~\n\n\n");
			System.out.println("1 --> Play continents (level 1)\n" + 
							   "2 --> Play countries (level 2)\n" + 
							   "3 --> Play capitals (level 3)\n" + 
							   "4 --> Display score list\n" + 
							   "0 --> Exit\n");
			System.out.println("\n\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.print("Your choice: ");					
			
			
			try {
				
				// Retrieve user menu choice
				Scanner inputChoice = new Scanner(System.in);
				int menuChoice = inputChoice.nextInt();
				Thread.sleep(1500);
				
				
				// Clear console window imitation
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			
				
				
				
				
				// Switch user choice and implement respective operations
				switch (menuChoice) {
				
					case 1:
						Category categoryContinents = new Category();
						categoryContinents.populateWith(continents);
				
						GameRound roundOnContinents = new GameRound(player, categoryContinents);
						roundOnContinents.run();
						updateScoreList(player);
						break;
			
					case 2:
						Category categoryCountries = new Category();
						categoryCountries.populateWith(countries);
					
						GameRound roundOnCountries = new GameRound(player, categoryCountries);
						roundOnCountries.run();
						updateScoreList(player);
						break;
				
					case 3: 
						Category categoryCapitals = new Category();
						categoryCapitals.populateWith(capitals);
					
						GameRound roundOnCapitals = new GameRound(player, categoryCapitals);
						roundOnCapitals.run();
						updateScoreList(player);
						break;
					
					case 4: 
						// Display score list
						System.out.println("~~~~~~~~~~~~~~~~~~~~Hangman 1.0~~~~~~~~~~~~~~~~~~~~~\n\n\n\n\n");
						displayScoreList();
						System.out.println("\n\n\n\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						
						// Option to go back to the main menu
						System.out.println("Press 'x' to go back to the main menu");
						String x = inputChoice.next();
						if (x.charAt(0) == 'X' || x.charAt(0) == 'x') {
							System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
							break;
						}
							
					
					case 0: 
						// Exit the game
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~Hangman 1.0~~~~~~~~~~~~~~~~~~\n\n\n\n\n");
						System.out.println("        Thank you for playing with us");
						System.out.println("\n\n\n\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						menuSwitcherOn = false;
						break;
					
			
					// Handle invalid user input
					default: 
						System.out.println("|Please, enter the menu choice: 1, 2, 3, 4 or 0|\n");
						break;
			
				}
				
				
			// Handle invalid user input	
			} catch (InputMismatchException ex) {
				
				// Clear console window imitation
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				
				// Instructing message
				System.out.println("|Please, enter the menu choice: 1, 2, 3, 4 or 0|\n");
				
				//input.next();
			}
			
			
			
		} while(menuSwitcherOn);

		
		input.close();
		
	}
	
	
	
	// Method to update the global score list that is kept in external file
	public static void updateScoreList(Player player) throws IOException, IllegalArgumentException {
		if (player instanceof Player) {
			
			if (player.retrieveScore() > 0) {
				
				String name = player.getName();
				int score = player.retrieveScore();
			
				BufferedWriter writer = new BufferedWriter(new FileWriter("src/scorelist.txt", true));
				writer.newLine();
				writer.append(name + ": " + score);
				writer.close();
			}
			
			
		} else {
			throw new IllegalArgumentException ("The input parameter should be of type Player");
		}
		
	}
	
	
	
	// Method to retrieve and print out the score list from the external file 
	public static void displayScoreList() throws IOException {
		
		File file = new File("src/scorelist.txt");
		
		if (file.exists()) {
			Scanner reader = new Scanner(file);
		
			while(reader.hasNextLine())
				System.out.println(reader.nextLine());
		
			reader.close();
		
		
			
		} else {
			System.out.println("No score records yet...");
		}
		
		
		
	}

}
