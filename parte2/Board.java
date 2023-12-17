/**
 * The objects of this type represent the state of the board of an Ipurdle game
 */
public class Board {

    private int wordSize;
    private int maxGuesses;
    private String guess;
    private Clue clue;
    private int guesses;
    private String[] guessesArray;
    private Clue[] cluesArray;
    
    /**
     * build a board for the Ipurdle game with the data provided in the initial state (empty)
     * @param wordSize
     * @param maxGuesses
     * @requires wordSize >= 1 e maxGuesses >= 1
     */
    public Board(int wordSize, int maxGuesses) {
        this.wordSize = wordSize;
        this.maxGuesses = maxGuesses;
        this.guessesArray = new String[maxGuesses];
        this.cluesArray = new Clue[maxGuesses];
    }
    /**
     * @return size of the words that can be stored in the board
     */
    public int wordLength() {
        return wordSize;
    }
    /**
     * @return maximum number of guesses that can be made
     */
    public int maxGuesses() {
        return maxGuesses;
    }
    /**
     * @return number of guesses that have been made
     * @requires 0 <= guesses() <= maxGuesses()
     */
    public int guesses() {
        return guesses;
    }
    /**
     * regist the word and the clues that have been given
     * @param guess
     * @param clue
     * @requires guess.length() == clue.length() == wordLength() && guesses() < maxGuesses()
     */
    public void insertGuessAndClue(String guess, Clue clue) {
        //this.guess = guess;
        //this.clue = clue;
        this.guessesArray[guesses] = guess;
        this.cluesArray[guesses] = clue;
        ++guesses;
    }
    /**
     * @return String representation of the board
     */
    @Override
    public String toString() {
        // This corresponds to the amount od characters in "| " + " | " + " |" 
        final int CHARACTERS_IN_THE_LINE = 7;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < guesses; i++) {
            sb.append("+");
            // Cicle to fill the lines between guesses with "-",
            // the amount of "-" is the size of the clue + the
            // size of the guess + CHARACTERS_IN_THE_LINE - 2,
            // 2 is the two "+" on the beggining and on the end
            for (int k = 0; k < wordSize + wordSize + CHARACTERS_IN_THE_LINE - 2; k++) {
                sb.append("-");
            }
            sb.append("+" + "\n");
            sb.append("| " + guessesArray[i] + " | " + cluesArray[i] + " |\n");
            if (i == guesses - 1) {
                sb.append("+");
                for (int k = 0; k < wordSize + wordSize + CHARACTERS_IN_THE_LINE - 2; k++) {
                    sb.append("-");
                }
                sb.append("+" + "\n");
            }
        }
        return sb.toString();
    }
}