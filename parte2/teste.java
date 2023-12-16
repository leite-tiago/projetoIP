public class teste {
    public static void main(String[] args) {
        String guess = "WHILE";
        String clue = "___*_";
        int guesses = 4;

        // This corresponds to the amount od characters in "| " + " | " + " |" 
        final int CHARACTHERS_IN_THE_LINE = 7;

        for(int i = 0; i < guesses; i++) {
            System.out.print("+");
            // Cicle to fill the lines between guesses with "-",
            // the amount of "-" is the size of the clue + the
            // size of the guess + CHARACTHERS_IN_THE_LINE - 2,
            // 2 is the two "+" on the beggining and on the end
            for (int k = 0; k < guess.length() + clue.length() + CHARACTHERS_IN_THE_LINE - 2; k++) {
                System.out.print("-");
            }
            System.out.print("+" + "\n");
            System.out.println("| " + guess + " | " + clue + " |");
            if (i == guesses - 1){
                System.out.print("+");
                for (int k = 0; k < guess.length() + clue.length() + CHARACTHERS_IN_THE_LINE - 2; k++) {
                    System.out.print("-");
                }
                System.out.print("+" + "\n");
            }
        }
    }
}

