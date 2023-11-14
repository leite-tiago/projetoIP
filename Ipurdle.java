public class Ipurdle {
    /**
     * @param args
     */
    public static void main(String[] args) {
        int clue, size;
        String guess, word;
        int Maxattempts = 6;
        DictionaryIP gameWordsDictionary, puzzlesDictionary;

        System.out.println("Testing Ipurdle.java \n");

        IpurdleTest.testIsValidClue();
        System.out.println();

        IpurdleTest.testMinClue();
        System.out.println();

        IpurdleTest.testIsMaxClue();
        System.out.println();

        System.out.println(printClue( "ultimo", 133232, 6));

		IpurdleTest.testClueForGuessAndWord();
        System.out.println();

    }

    // dados dois números inteiros clue e size, e
    // assumindo que size é um número maior que zero, verifica se clue representa uma pista para uma
    // palavra com o tamanho size. Note que isto corresponde a verificar as seguintes condições:
    // clue é positivo, clue tem size dígitos, clue é composto apenas pelos dígitos 1, 2 e 3
    // e a retornar true se todas as condições se verificarem e false caso contrário.
    public static boolean validClue(int clue, int size) {
        boolean samelength = false;
        if (clue > 0 && size > 0) {
            int pista = clue, tamanho = 0;

            int digit;
            while (pista > 0) {
                digit = pista % 10;
                if (digit != 1 && digit != 2 && digit != 3) {
                    return false;
                }
                pista /= 10;
                tamanho++;
            }
            samelength = (tamanho == size);

            if (samelength == false) {
                System.out.println("Palavra inválida, tamanho errado.");
            }
        } else {
            System.out.println("Tamanho inválido.");
        }
        return samelength;
    }

    // dado um número inteiro size, e assumindo que
    // size é um número maior que zero, retorna a menor pista para palavras desse tamanho.
    public static int minClue(int size) {
        int clue = 1;
        for (int i = 1; i < size; i++) {
            clue = clue * 10 + 1;
        }
        return clue;
    }

    // dados dois números inteiros clue e size, e
    // assumindo que size é um número maior que zero e clue representa uma pista para palavras de
    // tamanho size, verifica se clue é a maior pista para palavras desse tamanho.
    public static boolean isMaxClue(int clue, int size) {
        int a = 3;
        for (int i = 1; i < size; i++) {
            a = a * 10 + 3;
        }
        boolean maxclue = clue == a;
        return maxclue;
    }

    // dados dois números inteiros clue e size, e
    // assumindo que
    // size é um número maior que zero, clue representa uma pista para palavras de tamanho size
    // e clue não é a maior pista para palavras de tamanho size, esta função
    // calcula o número que representa a pista imediatamente a seguir, ou seja, o menor número inteiro
    // maior que clue que representa uma pista para palavras de tamanho size.
    public static int nextClue(int clue, int size) {
        clue++;
        int position = 1;
        for (int i = 1; i <= size; i++) {
            if (clue / position % 10 == 4) {
                clue = clue - 3 * position;
                position *= 10;
                clue += position;
            }
        }
        int nextclue = clue;
        return nextclue;
    }

    //dada uma String guess que se assume
    // não ser null e um número inteiro clue que se assume representar uma pista para guess, imprime
    // guess com as suas letras coloridas de acordo com a clue. Devem ser coloridas a verde as letras
    // que na pista têm 3, a amarelo as letras que na pista têm 2 e a preto as letras que na pista têm 1.
    public static String printClue(String guess, int clue, int size) {
        StringBuilder guess_colored = new StringBuilder();
        // Ciclo para percorrer a clue
        for (int i = size; i > 0; i--) {
            if ((clue / (int)Math.pow(10, i - 1)) % 10 == 1) {
                guess_colored.append(
                    StringColouring.toColoredString(String.valueOf(guess.charAt(size - i)), StringColouring.BLACK));
            }
            if ((clue / (int)Math.pow(10, i - 1)) % 10 == 2){
                guess_colored.append(
                    StringColouring.toColoredString(String.valueOf(guess.charAt(size - i)), StringColouring.YELLOW));
            }
            if ((clue / (int)Math.pow(10, i - 1)) % 10 == 3) {
                guess_colored.append(
                    StringColouring.toColoredString(String.valueOf(guess.charAt(size - i)), StringColouring.GREEN));
            }
        }
        return guess_colored.toString();
    }

    // dadas duas Strings guess e word,
    // que se assumem ter o mesmo tamanho, retorna o inteiro que representa a pista a dar à jogada
    // guess se a palavra a adivinhar for word.
    // No caso de uma letra de word, que só ocorre uma vez nesta palavra, estar em várias posições
    // erradas de guess, apenas a letra na posição mais à esquerda é identificada como letra certa na
    // posição errada.
    public static int clueForGuessAndWord(String guess, String word) {
        int clue = 0;

        for(int i = 0; i < guess.length(); i++){
            clue *= 10;
            char character = guess.charAt(i);
            if(word.contains(String.valueOf(character))){
                if(character == word.charAt(i)){
                    clue += 3;
                } else {
                    clue += 2;
                    word = word.replaceFirst(String.valueOf(character), " ");
                }
            } else {
                clue += 1;
            }
        }
            
        return clue;
    }

    
    //  dado um objeto do tipo
    //  DictionaryIP que se assume não ser null, um número inteiro clue que se assume representar uma
    //  pista para palavras desse dicionário e uma String guess que se assume ter o tamanho certo, retorna
    //  o número de palavras válidas do dicionário que se fossem a palavra a adivinhar, dariam origem à
    //  pista clue para guess.
    public static boolean howManyWordsWithClue(DictionaryIP dictionary, String guess) {

        return false;
    }

    // dado um objeto do tipo
    // DictionaryIP, que se assume não ser null e uma String guess que se assume ter o tamanho certo,
    // retorna o inteiro que representa a pista para guess que serve para mais palavras do dicionário
    // dado. No caso de haver pistas empatadas, dentre estas, é escolhida a menor.
    // Note que as pistas podem ser percorridas recorrendo às funções minClue e a nextClue descritas
    // acima. A pista calculada diz-se que é a melhor porque é a que torna mais difícil o jogador acertar na
    // palavra.
    public static boolean betterClueForGuess(DictionaryIP dictionary, String guess) {

        return false;
    }

    // dado um objeto do tipo Dictionary que
    // se assume não ser null, e uma String guess que se assume ter o tamanho certo:
    // 1. calcula a melhor pista para guess face ao dicionário dado, recorrendo à função anterior,
    // betterClueForGuess
    // 2. remove do dicionário todas as palavras que não resultariam nessa pista
    // 3. retorna a pista
    public static boolean playGuess(DictionaryIP dictionary, String guess) {

        return false;
    }
}