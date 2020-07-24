
/**
 * The Class Player.
 */
public class Player {

	/** Player's name. */
	private String name;
	
	
	/** Player's score. */
	private int score = 0;
	
	
	
	/**
	 * Instantiates a new player.
	 *
	 * @param nameInput - the name of the player 
	 */
	public Player(String nameInput) {
		setName(nameInput);
	}
	
	
	
	/** Getters and Setters for private data fields: */
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Sets the name.
	 *
	 * @param nameInput the new name
	 */
	public void setName(String nameInput) {
		if (nameInput != "") 
			name = nameInput;
		else 
			name = "Unknown hero";
	}
	
	
	
	/**
	 * Records player's score (establishes player - score pair)
	 *
	 * @param theScore = the current score of the current player within the current round
	 * @throws IllegalArgumentException 
	 */
	public void recordScore(int theScore) throws IllegalArgumentException {
		if (theScore > 0) 
			score = theScore;
		else 
			throw new IllegalArgumentException ("Only positive score can be recorded");
	}
	
	
	
	/**
	 * Retrieves player's score (established in player - score pair)
	 *
	 * @return the int value of the player's score
	 */
	public int retrieveScore() {
		if (score > 0) 
			return score;
		else 
			return 0;
	}
		
}
