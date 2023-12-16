import java.util.Scanner;
public class IpurdleTxt{
    public static void main(String[] args) {
        {
            IpurdleGame jogo = new IpurdleGame(5,6);
            Board tabela = new Board(5, 6);
            Scanner scanner = new Scanner(System.in);
            boolean error = false;
            while(!jogo.isOver())
            {
                System.out.println("Introduza uma palavra de 5 letras:");
                String guess = scanner.nextLine().toUpperCase();
                if(guess.length() != 5){
                    System.out.println("A palavra deve ter "+ jogo.wordLength() +" letras!");
                    error = true;
                    continue;
                }
                if (!jogo.isValid(guess)){
                    System.out.println("A palavra deve ser composta apenas por letras, e tem de ser uma palavra do Dicionario!!");
                    error = true;
                    continue;
                }
                if (!error)
                {
                    jogo.playGuess(guess);
                    System.out.println(jogo.toString());
                    System.out.println(tabela.toString());
                }
                error = false;
            }
            
            scanner.close();
        }
}
}