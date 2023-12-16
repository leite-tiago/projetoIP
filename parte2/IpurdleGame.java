import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IpurdleGame {
    
    private String[] ALL_WORDS;
    private boolean[] VALID_WORDS;
    private int wordSize;
    private int maxGuesses;
    private int guesses;
    private String guess;
    private String word;
    private Board board;
    private boolean over;

    /**
     * cria uma partida do jogo Ipurdle com os dados fornecidos no estado inicial 
     * @param wordSize
     * @param maxGuesses
     * @requires wordSize >= 1 && maxGuesses >= 1
     */
    public IpurdleGame (int wordSize, int maxGuesses) {
        // dar valores aos atributos
        this.wordSize = wordSize;
        this.maxGuesses = maxGuesses;
        this.board = new Board(wordSize, maxGuesses);
        
        try {
            // ler o ficheiro Dicionario.txt, se este ficheiro não for encontrado será lançada a exceção FileNotFoundException
            Scanner dicionarioSc = new Scanner(new File("Dicionario.txt"));
            // ciclo para descobrir quantas palavras estão no ficheiro Dicionario.txt
            int wordCounter = 0;
            while (dicionarioSc.hasNextLine()) {
                dicionarioSc.nextLine();
                wordCounter++;
            }

            // cria o array de String's ALL_WORDS com o tamanho correspondente ao número de palavras que estão no ficheiro Dicionario.txt
            ALL_WORDS = new String[wordCounter];
            dicionarioSc.close();
            dicionarioSc = new Scanner(new File("Dicionario.txt"));
            // coloca as palavras que estão no ficheiro  Dicionario.txt no array ALL_WORDS
            for(int i = 0; i < ALL_WORDS.length; i++){ 
                if(dicionarioSc.hasNextLine()){
                    ALL_WORDS[i] = dicionarioSc.nextLine();
                }
            }
            dicionarioSc.close();
            /* 
             * cria um vetor de Booleanos para representar as palavras que ainda podem ser a palavra a descobrir 
             * (que vão mudando à medida que vão sendo feitas jogadas).
             */ 
            Boolean[] VALID_WORDS = new Boolean[ALL_WORDS.length];

            /*
             * ciclo para percorrer ALL_WORDS e verificar se as palavras têm o tamanho que está a ser usado na partida,
             * caso isto se verifique é colocado o valor true no índice do VALID_WORDS correspondente ao índice que a palavra ocupa no ALL_WORDS,
             * caso contrário é colocado o valor false
             */
            for (int i = 0; i < ALL_WORDS.length; i++) {
                VALID_WORDS[i] = true;
            }

        } catch (FileNotFoundException e) {
            System.err.println("ficheiro não encontrado");
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
                if (guess.toUpperCase().equals(ALL_WORDS[i])) { //&& VALID_WORDS[i] == true) {
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

        LetterStatus[] elements = new LetterStatus[wordSize];

        for (int i = 0; i < wordSize; i++) {
            if (word.contains(String.valueOf(guess.charAt(i)))){
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
        Clue clue = new Clue(elements);

        /////////////////////// how many words with clue /////////////////////////////////////
        int howManyWordsWithClue = 0;
        // Ciclo para percorrer o dicionário
        for(int i = 0; i < ALL_WORDS.length; i++){
            word = ALL_WORDS[i];
            // Verifica se clue é válida para a palavra do dicionário no índice i
            if(clue == clueForGuessAndWord(guess, word)) {
                howManyWordsWithClue++;
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////



        ///////////////////////// better clue for guess ////////////////////////////////////////////////////
        howManyWordsWithClue = 0;
        LetterStatus[] MaxClueElems = new LetterStatus[guess.length()];
        for(int i = 0; i < guess.length(); i++) {
            MaxClueElems[i] = LetterStatus.CORRECT_POS;
        }   
        Clue MaxClue = new Clue(MaxClueElems);

        int clue = minClue(guess.length());
        while (!isMaxClue(clue, guess.length())) {
            // Verifica se a clue atual está presente em mais palavras do que a última clue guardada
            if (howManyWordsWithClue(dictionary, clue, guess) > howManyWordsWithClue) {
                howManyWordsWithClue = howManyWordsWithClue(dictionary, clue, guess);
                betterClueForGuess = clue;
                }
                clue=nextClue(clue, guess.length());
        }

        return betterClueForGuess;
        ////////////////////////////////////////////////////////////////////////////////////////////////////
    }


    }

    /**
     * faz a jogada (com tudo o que isso implica) devolvendo a pista para guess que 
     * serve para mais palavras. 
     * @param guess
     * @requires isValid(guess) && !isOver()
     * @return
     */
    public Clue playGuess(String guess) {
        /////////////////////// how many words with clue /////////////////////////////////////
        int howManyWordsWithClue = 0;
        // Ciclo para percorrer o dicionário
        for(int i = 0; i < ALL_WORDS.length; i++){
            word = ALL_WORDS[i];
            // Verifica se clue é válida para a palavra do dicionário no índice i
            if(clue == clueForGuessAndWord(guess, word)) {
                howManyWordsWithClue++;
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////
        





        
    //     int clue = betterClueForGuess(dictionary, guess);

    //     for (int i = 0; i < dictionary.lenght(); i++) {
    //         if (clue != clueForGuessAndWord(guess, dictionary.getWord(i)))
    //             dictionary.selectForRemove(i);
    //     }
    //     dictionary.removeSelected();
    //     return clue;
    // }






        int size = this.board.wordLength();
        Clue bestClue = new Clue ((int)Math.pow(3, size),size);
        Clue clue = new Clue(1,size);
        int bestWords = 0;
        int validWordCounter = 0;

        for(int i = 0; i < ALL_WORDS.length; i++){
            if(VALID_WORDS[i] &&  ALL_WORDS[i] != guess && ALL_WORDS[i] != null){
                clue = clueForGuessAndWord(guess, ALL_WORDS[i]);
                // ciclo para descobrir quantas palavras são válidas
                for (int j = 0; j < VALID_WORDS.length; j++) {
                    if (VALID_WORDS[i] == true) {
                        validWordCounter++;
                    }
                }
                int orderNumberClue = clue.orderNumber();
                int orderNumberBetterClue = bestClue.orderNumber();
                if (bestWords  <=  validWordCounter && orderNumberClue < orderNumberBetterClue){ 
                    bestWords = validWordCounter;
                    bestClue = clue;
                }
            }
        }
    
        this.board.insertGuessAndClue(guess, bestClue);
        if (maxGuesses() == guesses() || bestClue.isMax()){
            over = true;
        }
        return bestClue;
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
        return "Ipurdle with words of " + this.board.wordLength() + " letters\n" +
               "Remaining guesses: " + (this.board.maxGuesses() - this.board.guesses()) + "\n" +
               this.board.toString();
    }
}