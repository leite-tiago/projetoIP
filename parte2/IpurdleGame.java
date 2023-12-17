import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IpurdleGame {
    
    private String[] ALL_WORDS;
    private Boolean[] VALID_WORDS;
    private int wordSize;
    private int maxGuesses;
    private int guesses;
    //private String guess;
    //private String word;
    private Board board;
    private boolean over;
    
    /**
     * cria uma partida do jogo Ipurdle com os dados fornecidos no estado inicial 
     * @param wordSize
     * @param maxGuesses
     * @requires wordSize >= 1 && maxGuesses >= 1
     */
    public IpurdleGame (int wordSize, int maxGuesses) {

       // System.out.println("wordSize = " + wordSize);

        // dar valores aos atributos
        this.wordSize = wordSize;
        this.maxGuesses = maxGuesses;
        this.board = new Board(wordSize, maxGuesses);
        this.over = false;
        
        final String fileName = "Dicionario.txt";
        // ler o ficheiro Dicionario.txt, se este ficheiro não for encontrado será lançada a exceção FileNotFoundException
        try {
            Scanner dicionarioSc = new Scanner(new File(fileName));
            Scanner dicionarioSc2 = new Scanner(new File(fileName));  
            
            // ciclo para descobrir quantas palavras estão no ficheiro Dicionario.txt
            int wordCounter = 0;
            while (dicionarioSc.hasNextLine()) {
                dicionarioSc.nextLine();
                wordCounter++;
            }
            dicionarioSc.close();
            
            // cria o array de String's ALL_WORDS com o tamanho correspondente ao número de palavras que estão no ficheiro Dicionario.txt
            ALL_WORDS = new String[wordCounter];
        
            // coloca as palavras que estão no ficheiro  Dicionario.txt no array ALL_WORDS
            for(int i = 0; i < ALL_WORDS.length; i++){ 
                if(dicionarioSc2.hasNextLine()){
                    ALL_WORDS[i] = dicionarioSc2.nextLine();
                    //System.out.println(ALL_WORDS[i]);
                }
            }
            dicionarioSc2.close();

        } catch (FileNotFoundException e) {
            System.err.println("O ficheiro" + fileName + "não foi encontrado");
        }
        
        /* 
         * cria um vetor de Booleanos para representar as palavras que ainda podem ser a palavra a descobrir 
         * (que vão mudando à medida que vão sendo feitas jogadas).
         */ 
        VALID_WORDS = new Boolean[ALL_WORDS.length];

        /*
         * ciclo para percorrer ALL_WORDS e verificar se as palavras têm o tamanho que está a ser usado na partida,
         * caso isto se verifique é colocado o valor true no índice do VALID_WORDS correspondente ao índice que a palavra ocupa no ALL_WORDS,
         * caso contrário é colocado o valor false
         */
        for (int i = 0; i < ALL_WORDS.length; i++) {
            if (ALL_WORDS[i].length() == wordSize) {
                VALID_WORDS[i] = true;
            } else {
                VALID_WORDS[i] = false;
            }
        }
    }

    /**
     * 
     * @return o tamanho das palavras que podem ser jogadas
     */
    public int wordLength () {
        return wordSize;
    }

    /**
     * 
     * @return o número máximo de tentativas
     */
    public int maxGuesses () {
        return maxGuesses;
    }

    /**
     * 
     * @return quantas tentativas já foram realizadas
     */
    public int guesses () {
        return guesses;
    }

    /**
     * indica se a palavra é válida, ou seja, tem o tamanho certo e pertence ao dicionário
     * @param guess
     * @requires guess != null
     * @return true se tiver o tamanho certo e pertencer ao dicionário
     */
    public boolean isValid (String guess) {
        // verifica se a guess tem o tamanho certo (wordSize)
        if(guess.length() == wordSize){
            // ciclo para verificar se a guess está no dicionario e se é válida
            for (int i = 0; i < ALL_WORDS.length; i++) {
                if (guess.toUpperCase().equals(ALL_WORDS[i])/*&& VALID_WORDS[i] == true*/) {
                    return true;
                }
            }
        }
        // se as condições anteriores não se verificarem a guess não é válida
        return false;
    }

    /**
     * indica se a partida já terminou, ou seja, a palavra foi descoberta ou 
     * foram esgotadas as tentativas 
     * @return true if (guess == word || guesses == maxGuesses)
     */
    public boolean isOver () {
        return over;
    }

    /**
     * 
     * @param guess
     * @param word
     * @requires guess.length() == word.length()
     * @return pista a dar a guess se a palavra a adivinhar for word
     */
    private Clue clueForGuessAndWord(String guess, String word) {
        //this.guess = guess;
        //this.word = word;

        LetterStatus[] elements = new LetterStatus[word.length()];

       //System.out.println("method: clueForGuessAndWord, wordSize = " + wordSize + ", word.length() = " + word.length() + ", guess.length() = " + guess.length());

        for (int i = 0; i < word.length(); i++) {
           // System.out.println("guess = " + guess + ", word = " + word + ", i = " + i);
            if (word.indexOf(guess.charAt(i), 0) != -1) {

                if (guess.charAt(i) == word.charAt(i)) {
                    elements[i] = LetterStatus.CORRECT_POS;
                } else {
                    elements[i] = LetterStatus.WRONG_POS;
                    /* 
                     * Subsitui o caractere situado no index i da guess por um espaço em branco.
                     * Isto serve para, no caso de uma letra de word, que só ocorre uma vez nesta palavra,
                     * estar em várias posições erradas de guess, apenas a letra na posição mais à esquerda 
                     * é identificada como letra certa na posição errada 
                    */
                    word = word.replaceFirst(String.valueOf(guess.charAt(i)), " ");
                }
            } else {
                elements[i] = LetterStatus.INEXISTENT;
            }
        }
        return new Clue(elements);
    }
    ///////////////////////////////////////////////////// how Many Words With Clue ///////////////////////////////////////////////////////////////////////
    private int howManyWordsWithClue(Clue clue, String guess) {
        //System.out.println("howManyWordsWithClue:");
        int howManyWordsWithClue = 0;
        // Ciclo para percorrer o dicionário
        for (int i = 0; i < ALL_WORDS.length; i++){
            String word = ALL_WORDS[i];
            if (VALID_WORDS[i] == true) {
                // Verifica se clue é válida para a palavra do dicionário no índice i
                Clue aux = clueForGuessAndWord(guess, word);
                //System.out.println("guess = " + guess + ", word = " + word + ", clue = " + clue.toString() + ", clueForGuessAndWord = " + aux);
                if (Clue.equalsElements(clue.letterStatus(), aux.letterStatus())) {
                    howManyWordsWithClue++;
                }
            }
            
        }

        //System.out.println("howManyWordsWithClue = " + howManyWordsWithClue);
        // ///
        // System.out.println("Press any key to continue");
        // Scanner in = new Scanner(System.in);
        // in.nextLine();
        // ///
        return howManyWordsWithClue;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////// better clue for guess ////////////////////////////////////////////////////
    public Clue betterClueForGuess(String guess) {  
        
       // System.out.println("betterClueForGuess");
    // criar uma clue em que todos os elementos sejam CORRECT_POS, ou seja, criar a maxClue
        Clue betterClueForGuess = new Clue((int)Math.pow(3, wordSize), wordSize); // 3 elevado ao tamanho da guess vai ser o maior order number
       
        // criar uma clue em que todos os elementos sejam INEXISTENT, ou seja, criar a minClue
        Clue currentClue = new Clue(1, wordSize);

        int howManyWordsWithClue, maxWordsWithClue = 0;
         
        //System.out.println("betterClueForGuess: guess = " + guess);

        while (!currentClue.isMax()) {
            // Verifica se a clue atual está presente em mais palavras do que a última clue guardada
            howManyWordsWithClue = howManyWordsWithClue(currentClue, guess);
            //System.out.println("howManyWordsWithClue = " + howManyWordsWithClue);

            if (howManyWordsWithClue > maxWordsWithClue) {
                maxWordsWithClue = howManyWordsWithClue;
                betterClueForGuess = currentClue;
            }
            currentClue = new Clue(currentClue.orderNumber() + 1, wordSize);

            if (currentClue.isMax()) {
                // Verifica se a clue atual está presente em mais palavras do que a última clue guardada
                howManyWordsWithClue = howManyWordsWithClue(currentClue, guess);
                //System.out.println("howManyWordsWithClue = " + howManyWordsWithClue);

                if (howManyWordsWithClue > maxWordsWithClue) {
                    maxWordsWithClue = howManyWordsWithClue;
                    betterClueForGuess = currentClue;
                }
            }
        } 

        System.out.println("maxWordsWithClue = " + maxWordsWithClue + ", betterClueForGuess = " + betterClueForGuess);
        
        return betterClueForGuess;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * faz a jogada (com tudo o que isso implica) devolvendo a pista para guess que 
     * serve para mais palavras. 
     * @param guess
     * @requires isValid(guess) && !isOver()
     * @return
     */
    public Clue playGuess(String guess) {
        ++guesses;

       //        System.out.println("guess = " + guess + ", wordSize = " + wordSize + ", word = " + word);

        // Check if guess exists in ALL_WORDS array 
        boolean hasGuess = false;
        for (int i = 0; !hasGuess && i < ALL_WORDS.length; ++i) {
            if (ALL_WORDS[i].equals(guess)) {
                hasGuess = true;
            }
        }
        if (!hasGuess) {
            System.out.println("hasGuess = false");

            this.over = (guesses == maxGuesses);
            return new Clue(1, guess.length());
        }
        
        System.out.println("hasGuess = true");
        
        Clue betterClue = betterClueForGuess(guess);
        ///
        System.out.println("VALID_WORDS: ");
        for (int i = 0; i < VALID_WORDS.length; i++) {
            if (VALID_WORDS[i] == true) {
                System.out.println("ALL_WORDS[i] = " + ALL_WORDS[i]);
            }
        }
        System.out.println("betterClue for guess " + guess + ": " + betterClue);
        ///


        // Remove all words that don't have this clue
        for (int i = 0; i < ALL_WORDS.length; i++) {
            if (VALID_WORDS[i] == true) {
                if (!Clue.equalsElements(betterClue.letterStatus(), clueForGuessAndWord(guess, ALL_WORDS[i]).letterStatus())) {
                    VALID_WORDS[i] = false;
                }
            }
            
        }
        for (int i = 0; i < VALID_WORDS.length; i++) {
            if (VALID_WORDS[i] == true) {
                System.out.println("ALL_WORDS[i] = " + ALL_WORDS[i]);
            }
        }
        this.over = (betterClue.isMax() || guesses == maxGuesses);

        this.board.insertGuessAndClue(guess, betterClue);

        return betterClue;
    }

    /** 
     * que dá uma representação textual do estado da partida como ilustrado
     * 
     * Ipurdle with words of 5 letters.
     * Remaining guesses: 4
     * +---------------+
     * | WHILE | ____* |
     * +---------------+
     * | FIELD | __o__ |
     * +---------------+
     * 
     * @return representação textual do estado da partida
    */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Ipurdle with words of " + this.board.wordLength() + " letters\n");
        sb.append("Remaining guesses: " + (this.board.maxGuesses() - this.board.guesses()) + "\n");
        sb.append(this.board.toString());
        return sb.toString();
    }
}