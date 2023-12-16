import java.util.Arrays;

/**
* A classe {@code IpurdleTest} permite testar os principais elementos  
* do seguindo projeto de IP2324.
* 
* @author Diogo Branco IP2324
* @author Antonia Lopes
* 
* Compilar: javac IpurdleTest.java
* Executar: java IpurdleTest
*/

public class IpurdleTest {

	public static void main(String[] args) {
		System.out.println("Testing Ipurdle classes \n");
		testLetterStatus();
		testClue();
		testBoard();
		testIpurdleGame();

	}

	static void testLetterStatus () {
		System.out.println("Testing LetterStatus enum");

		String name = "LetterStatus";
		boolean error = false;
		LetterStatus[] elements = LetterStatus.values();
		int expectedSize = 3;
		int obtainedSize = elements.length;
		if (expectedSize != obtainedSize) {
			failOnMethod(name);
			expectedObtainedValues(String.valueOf(expectedSize), String.valueOf(obtainedSize));
			error = true;
		}

		if (!testLetterStatusElement(LetterStatus.CORRECT_POS)) {
			failOnMethod(name);
			error = true;
		}

		if (!testLetterStatusElement(LetterStatus.WRONG_POS)) {
			failOnMethod(name);
			error = true;
		}

		if (!testLetterStatusElement(LetterStatus.INEXISTENT)) {
			failOnMethod(name);
			error = true;
		}

		if (testLetterStatusElement(null)) {
			failOnMethod(name);
			error = true;
		}
		passOrFail(error);
		System.out.println("-----------------------------------------------");
	}

	private static boolean testLetterStatusElement (LetterStatus status) {
		boolean exists = false;
		if (status == LetterStatus.CORRECT_POS )
			exists = true;
		if (status == LetterStatus.WRONG_POS )
			exists = true;
		if (status == LetterStatus.INEXISTENT )
			exists = true;
		return exists;
	}


	static void testClue() {
		System.out.println("Testing Clue class");
		testWordSize();
		testOrderNumber();
		testElements();
		testClueIsMax();
		System.out.println("-----------------------------------------------");
	}

	static void testElements () {
		String methodName = "Clue.letterStatus()";
		boolean error = false;

		LetterStatus[] expected = { LetterStatus.CORRECT_POS, LetterStatus.WRONG_POS, LetterStatus.INEXISTENT};
		Clue clue = new Clue(expected);
		LetterStatus[] obtained = clue.letterStatus();
		if (!equalsElements(expected, obtained)) {
			failOnMethod(methodName);
			expectedObtainedValues(Arrays.toString(expected), Arrays.toString(obtained));
			error = true;
		}

		clue = new Clue(22,3);
		obtained = clue.letterStatus();
		if (!equalsElements(expected, obtained)) {
			failOnMethod(methodName);
			expectedObtainedValues(Arrays.toString(expected), Arrays.toString(obtained));
			error = true;
		}

		LetterStatus[] expected2 = { LetterStatus.INEXISTENT, LetterStatus.INEXISTENT, LetterStatus.WRONG_POS, LetterStatus.WRONG_POS, LetterStatus.INEXISTENT};
		clue = new Clue(13,5);
		obtained = clue.letterStatus();
		if (!equalsElements(expected2, obtained)) {
			failOnMethod(methodName);
			expectedObtainedValues(Arrays.toString(expected), Arrays.toString(obtained));
			error = true;
		}

		passOrFail(error);
	}

	private static boolean equalsElements (LetterStatus[] elements1, LetterStatus[] elements2) {
		boolean error = elements1.length != elements2.length;
		int i =  0;
		while (!error && i < elements1.length){
			error = elements1[i] != elements2[i];
			i++;
		}
		return !error;
	}

	static void testWordSize() {
		String methodName = "Clue.length()";
		boolean error = false;

		int wordSize = 4;
		Clue clue = new Clue(2,wordSize);
		int obtainedSize = clue.length();
		if (wordSize != obtainedSize) {
			failOnMethod(methodName);
			expectedObtainedValues(String.valueOf(wordSize), String.valueOf(obtainedSize));
			error = true;
		}
		passOrFail(error);
	}

	static void testOrderNumber () {
		String methodName = "Clue.orderNumber()";
		boolean error = false;

		int orderNumber = 3;
		Clue clue = new Clue(orderNumber,5);
		int obtained = clue.orderNumber();
		if (obtained != orderNumber) {
			failOnMethod(methodName);
			expectedObtainedValues(String.valueOf(orderNumber), String.valueOf(obtained));
			error = true;
		}
		passOrFail(error);

		orderNumber = 13;
		clue = new Clue(orderNumber,5);
		obtained = clue.orderNumber();
		if (obtained != orderNumber) {
			failOnMethod(methodName);
			expectedObtainedValues(String.valueOf(orderNumber), String.valueOf(obtained));
			error = true;
		}
		passOrFail(error);
	}

	static void testClueIsMax () {
		String methodName = "Clue.isMax()";
		boolean error = false;

		LetterStatus[] correctElements = { LetterStatus.CORRECT_POS, LetterStatus.CORRECT_POS,
				LetterStatus.CORRECT_POS };
		Clue correctClue = new Clue(correctElements);
		if (!correctClue.isMax()) {
			failOnMethod(methodName);
			error = true;
		}

		LetterStatus[] partialCorrectElements = { LetterStatus.CORRECT_POS, LetterStatus.INEXISTENT,
				LetterStatus.CORRECT_POS };
		Clue partialCorrectClue = new Clue(partialCorrectElements);
		if (partialCorrectClue.isMax()) {
			failOnMethod(methodName);
			error = true;
		}

		LetterStatus[] wrongElements = { LetterStatus.INEXISTENT, LetterStatus.INEXISTENT, LetterStatus.INEXISTENT };
		Clue wrongClue = new Clue(wrongElements);
		if (wrongClue.isMax()) {
			failOnMethod(methodName);
			error = true;
		}
		passOrFail(error);
	}


	static void testBoard() {
		System.out.println("Testing Board class");
		testWordLength();
		testMaxGuesses();
		testGuesses();
		testBoardInsertGuessAndClue();
		System.out.println("-----------------------------------------------");
	}

	static void testMaxGuesses () {
		String methodName = "Board.maxGuesses()";
		boolean error = false;

		int maxAttempts = 5;
		Board board = new Board(5,maxAttempts);
		int obtained = board.maxGuesses();
		if (obtained != maxAttempts) {
			failOnMethod(methodName);
			expectedObtainedValues(String.valueOf(maxAttempts), String.valueOf(obtained));
			error = true;
		}
		passOrFail(error);
	}

	static void testGuesses () {
		String methodName = "Board.guesses()";
		boolean error = false;

		Board board = new Board(5,5);
		int expected = 0;
		int obtained = board.guesses();
		if (obtained != expected) {
			failOnMethod(methodName);
			expectedObtainedValues(String.valueOf(expected), String.valueOf(obtained));
			error = true;
		}

		board.insertGuessAndClue("null", new Clue(0, 0));
		expected = 1;
		obtained = board.guesses();
		if (obtained != expected) {
			failOnMethod(methodName);
			expectedObtainedValues(String.valueOf(expected), String.valueOf(obtained));
			error = true;
		}
		passOrFail(error);
	}

	static void testWordLength () {
		String methodName = "Board.wordLength()";
		boolean error = false;

		int wordSize = 5;
		Board board = new Board(wordSize,4);
		int obtained = board.wordLength();
		if (obtained != wordSize) {
			failOnMethod(methodName);
			expectedObtainedValues(String.valueOf(wordSize), String.valueOf(obtained));
			error = true;
		}
		passOrFail(error);
	}

	static void testBoardInsertGuessAndClue () {
		String methodName = "Board.insertGuessAndClue()";
		boolean error = false;
		Board board = new Board(5, 6);

		String guess = "WORD";
		LetterStatus[] elements = { LetterStatus.CORRECT_POS, LetterStatus.CORRECT_POS, LetterStatus.INEXISTENT,
				LetterStatus.INEXISTENT };
		Clue clue = new Clue(elements);
		board.insertGuessAndClue(guess, clue);
		int expected = 1;
		int obtained = board.guesses();
		if (obtained != expected) {
			failOnMethod(methodName);
			expectedObtainedValues(String.valueOf(expected), String.valueOf(obtained));
			error = true;
		}
		passOrFail(error);
	}


	static void testIpurdleGame() {
		System.out.println("Testing IpurdleGame class");
		testGameWordLength();
		testGameMaxGuesses();
		testIsValid();
		testIsOver();
		testGameGuesses();
		testPlayGuess();
		System.out.println("-----------------------------------------------");
	}

	static void testPlayGuess () {
		String methodName = "IpurdleGame.testPlayGuess()";
		boolean error = false;
		IpurdleGame game = new IpurdleGame(4,5);

		String guess = "ZZZZ";
		Clue expectedClue = new Clue(new LetterStatus[] {LetterStatus.INEXISTENT,LetterStatus.INEXISTENT, LetterStatus.INEXISTENT,LetterStatus.INEXISTENT});
		Clue obtainedClue = game.playGuess(guess);
		if (!equalsElements(expectedClue.letterStatus(),obtainedClue.letterStatus())) {
			failOnMethod(methodName);
			expectedObtainedValues(expectedClue.toString(), obtainedClue.toString());
			error = true;
		}
		
		IpurdleGame game2 = new IpurdleGame(5,5);
		guess = "WHILE";
		expectedClue = new Clue(new LetterStatus[] {LetterStatus.INEXISTENT,LetterStatus.INEXISTENT, LetterStatus.INEXISTENT,LetterStatus.INEXISTENT, LetterStatus.CORRECT_POS});
		obtainedClue = game2.playGuess(guess);
		if (!equalsElements(expectedClue.letterStatus(),obtainedClue.letterStatus())) {
			failOnMethod(methodName);
			expectedObtainedValues(expectedClue.toString(), obtainedClue.toString());
			error = true;
		}

		guess = "FIELD";
		expectedClue = new Clue(new LetterStatus[] {LetterStatus.INEXISTENT,LetterStatus.INEXISTENT, LetterStatus.WRONG_POS,LetterStatus.INEXISTENT, LetterStatus.INEXISTENT});
		obtainedClue = game2.playGuess(guess);
		if (!equalsElements(expectedClue.letterStatus(),obtainedClue.letterStatus())) {
			failOnMethod(methodName);
			expectedObtainedValues(expectedClue.toString(), obtainedClue.toString());
			error = true;
		}

		guess = "ABOVE";
		expectedClue = new Clue(new LetterStatus[] {LetterStatus.CORRECT_POS,LetterStatus.CORRECT_POS, LetterStatus.CORRECT_POS,LetterStatus.CORRECT_POS, LetterStatus.CORRECT_POS});
		obtainedClue = game2.playGuess(guess);
		if (!equalsElements(expectedClue.letterStatus(),obtainedClue.letterStatus())) {
			failOnMethod(methodName);
			expectedObtainedValues(expectedClue.toString(), obtainedClue.toString());
			error = true;
		}

		passOrFail(error);
	}

	static void testIsOver () {
		String methodName = "IpurdleGame.isOver()";
		boolean error = false;

		IpurdleGame game = new IpurdleGame(5,5);
		if (game.isOver()) {
			failOnMethod(methodName);
			error = true;
		}

		IpurdleGame game2 = new IpurdleGame(5,5);
		game2.playGuess("WHILE");
		if (game2.isOver()) {
			failOnMethod(methodName);
			error = true;
		}

		IpurdleGame game3 = new IpurdleGame(5,1);
		game3.playGuess("WHILE");
		if (!game3.isOver()) {
			failOnMethod(methodName);
			error = true;
		}
		passOrFail(error);
	}

	static void testIsValid () {
		String methodName = "IpurdleGame.isValid()";
		boolean error = false;
		IpurdleGame game = new IpurdleGame(5,5);

		String validGuess = "WHILE";
		if (!game.isValid(validGuess)) {
			failOnMethod(methodName);
			error = true;
		}

		String invalidGuess = "SCANNER";
		if (game.isValid(invalidGuess)) {
			failOnMethod(methodName);
			error = true;
		}
		passOrFail(error);
	}
	
	static void testGameMaxGuesses () {
		String methodName = "IpurdleGame.maxGuesses()";
		boolean error = false;

		int maxAttempts = 5;
		IpurdleGame game = new IpurdleGame(5,maxAttempts);
		int obtained = game.maxGuesses();
		if (obtained != maxAttempts) {
			failOnMethod(methodName);
			expectedObtainedValues(String.valueOf(maxAttempts), String.valueOf(obtained));
			error = true;
		}
		passOrFail(error);
	}

	static void testGameGuesses () {
		String methodName = "IpurdleGame.guesses()";
		boolean error = false;

		IpurdleGame game = new IpurdleGame(5,5);
		int expected = 0;
		int obtained = game.guesses();
		if (obtained != expected) {
			failOnMethod(methodName);
			expectedObtainedValues(String.valueOf(expected), String.valueOf(obtained));
			error = true;
		}

		game.playGuess("JAVA");
		expected = 1;
		obtained = game.guesses();
		if (obtained != expected) {
			failOnMethod(methodName);
			expectedObtainedValues(String.valueOf(expected), String.valueOf(obtained));
			error = true;
		}
		passOrFail(error);
	}

	static void testGameWordLength () {
		String methodName = "IpurdleGame.wordLength()";
		boolean error = false;

		int wordSize = 5;
		IpurdleGame game = new IpurdleGame(wordSize,4);
		int obtained = game.wordLength();
		if (obtained != wordSize) {
			failOnMethod(methodName);
			expectedObtainedValues(String.valueOf(wordSize), String.valueOf(obtained));
			error = true;
		}
		passOrFail(error);
	}

	private static void expectedObtainedValues(String expected, String obtained) {
		System.out.println(StringColouring.toColoredString(">>> expected: " + expected + " obtained: " + obtained,StringColouring.RED));
	}
	
	private static void failOnMethod(String s) {
		System.out.println(StringColouring.toColoredString(">>> failed " + s, StringColouring.RED));
	}

	private static void passOrFail(boolean error) {
		System.out.println(error ? StringColouring.toColoredString("FAIL", StringColouring.RED) : "PASS");
	}
}
