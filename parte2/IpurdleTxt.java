import java.util.Scanner;

/**
 * O objetivo do jogador é, a partir da pista recebida para cada palavra jogada, adivinhar a palavra;
 * O jogo termina quando a palavra é revelada ou após ter sido atingido o número máximo de tentativa;
 * 
 * @author Rodrigo Frutuoso 61865
 * @author Tiago Leite 61863
 *
 * Compilar: javac IpurdleTxt.java
 * Executar: java IpurdleTxt
 */
public class IpurdleTxt {
	public static void main(String[] args) {
		
		// por defeito o wordsize é 5 e o maxGuesses é 6
		int wordsize = 5;
		int maxGuesses = 6;
		
		/* 
		 * caso o utilizador introduza argumentos serão usados o primeiro argumento para o wordSize e o segudno para o maxGuesses,
		 * este código vai funcionar mesmo que o utilizdor só introduza um argumento.
		 */ 
		if (args.length > 0) {
			wordsize = Integer.parseInt(args[0]);
			if (args.length > 1) {
				maxGuesses = Integer.parseInt(args[1]);
			}
		}
		// criar o jogo
		IpurdleGame game = new IpurdleGame(wordsize, maxGuesses);
		Scanner sc = new Scanner(System.in);
		boolean error = false;

		// mensagens iniciais
		System.out.println("Bem vindo ao jogo Ipurdle!");
		System.out.println("Neste jogo as palavras têm tamanho " + game.wordLength() + ". O dicionário tem apenas palavras em inglês realcionadas com IP.");
		System.out.println("Tens apenas " + game.maxGuesses() +" tentativas para advinhar a palavra. Boa sorte!");
		System.out.println("tamanho da palavra: " + wordsize + " número de tentativas: " + maxGuesses);
		
		Clue clue = null;

		while(!game.isOver()) {
	
			System.out.print("Introduza uma palavra de " + wordsize + " letras: ");
			String guess = sc.nextLine().toUpperCase();
			if (guess.length() != wordsize){
				System.out.println("A palavra deve ter " + wordsize + " letras! Por favor tente novamente.");
				error = true;
			}
			if (!error && !game.isValid(guess)){
				System.out.println("A palavra deve ser composta apenas por letras, e tem de ser uma palavra do Dicionario!! Por favor tente novamente.");
				error = true;
			}
			if (!error)
			{
				clue = game.playGuess(guess);
				System.out.println(game.toString());
			}
			error = false;
		}

		// verificar se o jogador ganhou ou perdeu
		if(clue != null && clue.isMax()) {
			System.out.println("Parabéns, encontraste a palavra secreta!");
		} else {
			System.out.println("Ohh, Perdeste!");
		}

		sc.close();
	}
}