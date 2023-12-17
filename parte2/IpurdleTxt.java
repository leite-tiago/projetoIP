
 /*
 * Classe com um método main que deverá criar uma partida de Ipurdle com palavras de 5
 * letras e 6 tentativas. O método deve suportar a interação com o jogador: obter uma palavra do utilizador
 * que seja válida, jogar essa palavra e imprimir o estado do jogo; repetir isto enquanto o jogo não terminar. 
 */

import java.util.Scanner;


public class IpurdleTxt {
	public static void main(String[] args)
	{
		int wordsize = 5;
		int maxGuesses = 6;
		if (args.length > 0)
		{
			wordsize = Integer.parseInt(args[0]);
			if (args.length > 1)
				maxGuesses = Integer.parseInt(args[1]);
		}
		System.out.println("tamanho da palavra: " + wordsize + " numero de tentativas: " + maxGuesses);
		IpurdleGame game = new IpurdleGame(wordsize, maxGuesses);
		Scanner sc = new Scanner(System.in);
		boolean error = false;
		
		while(!game.isOver())
		{
			System.out.println("Introduza uma palavra de " + wordsize + " letras:");
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
				game.playGuess(guess);
				System.out.println(game.toString());
			}
			error = false;
		}
		
		sc.close();
	}

}


/*IpurdleTxt. Classe com um método main que deverá criar uma partida de Ipurdle com palavras de 5 
letras e 6 tentativas. O método deve suportar a interação com o jogador: obter uma palavra do utilizador 
que seja válida, jogar essa palavra e imprimir o estado do jogo; repetir isto enquanto o jogo não terminar. */