import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * 
 * @author Rodrigo Frutuoso 61865
 * @author Tiago Leite 61863
 *
 * Compilar: javac IpurdleGame.java
 * Executar: java IpurdleGame
 */
public class IpurdleGame {
    
    private String[] ALL_WORDS;
    private Boolean[] VALID_WORDS;
    private int wordSize;
    private int maxGuesses;
    private int guesses;
    private Board board;
    private boolean over;
    
    /**
     * cria uma partida do jogo Ipurdle com os dados fornecidos no estado inicial 
     * @param wordSize
     * @param maxGuesses
     * @requires wordSize >= 1 && maxGuesses >= 1
     */
    public IpurdleGame (int wordSize, int maxGuesses) {

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
     * @return o tamanho das palavras que podem ser jogadas
     */
    public int wordLength () {
        return wordSize;
    }

    /**
     * @return o número máximo de tentativas
     */
    public int maxGuesses () {
        return maxGuesses;
    }

    /**
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
            // ciclo para verificar se a guess está no dicionario
            for (int i = 0; i < ALL_WORDS.length; i++) {
                if (guess.toUpperCase().equals(ALL_WORDS[i])) {
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
     * @param guess
     * @param word
     * @requires guess.length() == word.length()
     * @return pista a dar a guess se a palavra a adivinhar for word
     */
    private Clue clueForGuessAndWord(String guess, String word) {

        LetterStatus[] elements = new LetterStatus[wordSize];

        for (int i = 0; i < word.length(); i++) {
            // verifica se o caractere situado no indice i da guess está presente na word, se não estiver o método indexOf retorna -1
            if (word.indexOf(guess.charAt(i), 0) != -1) {
                // verifica se o caractere situado no indice i da guess está na mesma posição na word
                if (guess.charAt(i) == word.charAt(i)) {
                    // se isto se verificar significa que o caractere já está na posição correta
                    elements[i] = LetterStatus.CORRECT_POS;
                } else {
                    // o caractere está presente na word mas não está na posição correta
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
                // se não estiver presente na word é INEXISTENT
                elements[i] = LetterStatus.INEXISTENT;
            }
        }
        return new Clue(elements);
    }
    
    /**
     * dado uma Clue clue que se assume representar uma pista para palavras contidas no ALL_WORDS e uma String guess 
     * que se assume ter o tamanho certo, retorna o número de palavras válidas do dicionário que se fossem a palavra 
     * a adivinhar, dariam origem à pista clue para guess. 
     * @param clue representa a pista para guess de tamanho wordSize
     * @param guess palavra que o jogador escolheu
     * @return o número de palavras válidas do ALL_WORDS que se fossem a palavra a adivinhar, dariam origem à pista clue para guess
     * @requires {@code ALL_WORDS != null && clue != null && guess.length() == wordSize}
     */
    private int howManyWordsWithClue(Clue clue, String guess) {
        int howManyWordsWithClue = 0;
        // Ciclo para percorrer as palavras presentes no ALL_WORDS
        for (int i = 0; i < ALL_WORDS.length; i++){
            String word = ALL_WORDS[i];
            // verifica se a palavra ainda é válida
            if (VALID_WORDS[i] == true) {
                // Verifica se clue é válida para a palavra do dicionário no índice i
                if (Clue.equalsElements(clue.letterStatus(), clueForGuessAndWord(guess, word).letterStatus())) {
                    howManyWordsWithClue++;
                }
            }
        }
        return howManyWordsWithClue;
    }

    /**
     * dada uma String guess que se assume ter o tamanho certo,
     * retorna a clue que representa a pista para guess que serve para mais palavras do ALL_WORDS.
     * No caso de haver pistas empatadas, dentre estas, é escolhida a menor.
     * A pista calculada diz-se que é a melhor porque é a que torna mais difícil o jogador acertar na
     * palavra. 
     * @param guess palavra que o jogador escolheu
     * @return a clue que representa a pista para guess que serve para mais palavras do ALL_WORDS
     * @requires {@code ALL_WORDS != null && guess.length() == wordSize}
     */
    public Clue betterClueForGuess(String guess) {  
        // criar uma clue em que todos os elementos sejam CORRECT_POS, ou seja, criar a maxClue
        Clue betterClueForGuess = new Clue((int)Math.pow(3, wordSize), wordSize); // 3 elevado ao tamanho da guess vai ser o maior order number
       
        // criar uma clue em que todos os elementos sejam INEXISTENT, ou seja, criar a minClue
        Clue currentClue = new Clue(1, wordSize); // orderNumber 1 corresponde à minClue 

        int howManyWordsWithClue, maxWordsWithClue = 0;
        
        // ciclo para percorrer as clues
        while (!currentClue.isMax()) {
            // Verifica se a clue atual está presente em mais palavras do que a última clue guardada
            howManyWordsWithClue = howManyWordsWithClue(currentClue, guess);

            /* 
             * verificar se a currentClue serve para mais palavras do que a antiga currentClue
             * e registar tanto a clue como a quantidade de palavras para que ela serve.
             */ 
            if (howManyWordsWithClue > maxWordsWithClue) {
                maxWordsWithClue = howManyWordsWithClue;
                betterClueForGuess = currentClue;
            }
            currentClue = new Clue(currentClue.orderNumber() + 1, wordSize);

            /*  
             * este bloco de código apenas serve para verificar se a maxClue é a betterClue, 
             * caso contrário sairia do while e não chegava a testar a maxClue.             
             */ 
            if (currentClue.isMax()) {
                // Verifica se a clue atual está presente em mais palavras do que a última clue guardada
                howManyWordsWithClue = howManyWordsWithClue(currentClue, guess);

                /* 
                 * verificar se a currentClue serve para mais palavras do que a antiga currentClue
                 * e registar tanto a clue como a quantidade de palavras para que ela serve.
                 */ 
                if (howManyWordsWithClue > maxWordsWithClue) {
                    maxWordsWithClue = howManyWordsWithClue;
                    betterClueForGuess = currentClue;
                }
            }
        } 
        return betterClueForGuess;
    }

    /**
     * faz a jogada (com tudo o que isso implica) devolvendo a pista para guess que 
     * serve para mais palavras. 
     * 1. conta que foi feita uma jogada
     * 2. calcula a melhor pista para guess face às VALID_WORDS, recorrendo à função anterior, betterClueForGuess
     * 3. coloca a false no VALID_WORDS todas as palavras que não resultariam na melhor pista
     * 4. atualiza a variável over, esta variável vai ser usada pelo método isOver para ver se o jogo já acabou
     * 5. insere a guess e a clue no board
     * 6. retorna a pista
     * @param guess
     * @requires isValid(guess) && !isOver()
     * @return pista para guess que serve para mais palavras. 
     */
    public Clue playGuess(String guess) {
        // 1.
        ++guesses;

        // 2.
        Clue betterClue = betterClueForGuess(guess);

        // 3.
        for (int i = 0; i < ALL_WORDS.length; i++) {
            if (VALID_WORDS[i] == true) {
                if (!Clue.equalsElements(betterClue.letterStatus(), clueForGuessAndWord(guess, ALL_WORDS[i]).letterStatus())) {
                    VALID_WORDS[i] = false;
                }
            }    
        }

        // 4.
        this.over = (betterClue.isMax() || guesses == maxGuesses);
        
        // 5.
        this.board.insertGuessAndClue(guess, betterClue);
        
        // 6.
        return betterClueForGuess(guess); 
    }

    /** 
     * dá uma representação textual do estado da partida como ilustrado:
     * 
     * Ipurdle com palavras de 5 letras.
     * Tentativas restantes: 4
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

        sb.append("Ipurdle com palavras de " + this.board.wordLength() + " letras\n");
        sb.append("Tentativas restantes: " + (this.board.maxGuesses() - this.board.guesses()) + "\n");
        sb.append(this.board.toString());

        return sb.toString();
    }
}