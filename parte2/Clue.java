/** 
 * The objects of this type represent clues for words of a certain size
 */
public class Clue {

    private int orderNumber;
    private LetterStatus[] elements;
    private int wordSize;

    
    ////////////////////////////////// comparar se dois LetterStatus são iguais ////////////////////////
        public static boolean equalsElements (LetterStatus[] elements, LetterStatus[] elements1) {
            boolean error = elements.length != elements1.length;
            int i =  0;
            while (!error && i < elements.length){
                error = elements[i] != elements1[i];
                i++;
            }
            return !error;
        }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * build a clue with the given elements
     * 
     * @param elements
     * @requires elements != nul
     */
    public Clue(LetterStatus[] elements) {
        this.elements = elements;
        //System.out.println(Arrays.toString(elements));
        wordSize = elements.length;
        orderNumber = 1;
        boolean carry; 
        LetterStatus[] elements1 = new LetterStatus[wordSize];
        for (int i = 0; i < wordSize; i++) {
            elements1[i] = LetterStatus.INEXISTENT;
        }
        // Auxiliar variable that contains the LetterStatus enum values
        final LetterStatus[] values = LetterStatus.values();
        
        while (equalsElements(elements, elements1)) {
            /// Build the next clue ///////////////////////////////////////////
            carry = true;
            for (int j = elements1.length - 1; j >= 0 && carry; j--) {
                if (carry) {
                    if (elements1[j]== LetterStatus.CORRECT_POS) {
                        elements1[j] = LetterStatus.INEXISTENT;
                        carry = true;
                    } else {
                        elements1[j] = values[elements1[j].ordinal() + 1];
                        carry = false;
                    }
                }
            }
            ++orderNumber;
        }
       ///////////////////////////////////////////////////////////////////
    }
    /**
     * build a clue with the size of the word and the order number
     * @param orderNumber
     * @param wordSize
     * @requires wordSize > 0 e 1 ≤ orderNumber ≤ 3 ^(wordSize)
     */
    public Clue(int orderNumber, int wordSize) {
        this.orderNumber = orderNumber;
        this.wordSize = wordSize;
        this.elements = new LetterStatus[wordSize];
        for (int i = 0; i < wordSize; i++) {
            elements[i] = LetterStatus.INEXISTENT;
        }
        // Auxiliar variable that contains the LetterStatus enum values
        final LetterStatus[] values = LetterStatus.values();
        boolean carry;
        // Starting from the clue 0, advance to deisred order number
        for (int i = 0; i < orderNumber - 1; i++) {
            /// Build the next clue ///////////////////////////////////////////
            carry = true;
            for (int j = elements.length - 1; j >= 0 && carry; j--) {
                if (carry) {
                    if (elements[j]== LetterStatus.CORRECT_POS) {
                        elements[j] = LetterStatus.INEXISTENT;
                        carry = true;
                    } else {
                        elements[j] = values[elements[j].ordinal() + 1];
                        carry = false;
                    }
                }
            }
            ///////////////////////////////////////////////////////////////////
        }

    }
    /**
     * @return the length of the clue
     */
    public int length() {
        return elements.length;
    }
    /**
     * @return the order number of a clue
     */
    public int orderNumber() {
        return this.orderNumber;
    }
    /**
     * @return a vector with the elements of the clue
     */
    public LetterStatus[] letterStatus() {
        return this.elements;
    }
    /**
     * @return true if the clue is complete
     */
    public boolean isMax() {
        for (int i = 0; i < this.length(); i++) {
            if (letterStatus()[i] != LetterStatus.CORRECT_POS) {
                return false;
            }
        }
        return true;
    }
    /**
     * @return string representation of a list where the symbols *, o and _ 
     * are used to represent letter in the correct position, letter in the wrong
     * position and non-existent letter
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
           if (elements[i] == LetterStatus.CORRECT_POS) {
               sb.append("*");
           } else if (elements[i] == LetterStatus.WRONG_POS) {
               sb.append("o");
           } else {
               sb.append("_");
           }
        }
        return sb.toString();
    }

    // public boolean hasElementsInSamePosition(Clue aux) {
    //     int count = 0;
    //     int countRef = 0;
    //     for (int i = wordSize - 1; i >= 0; --i ) {
    //         if (this.elements[i] != LetterStatus.INEXISTENT) {
    //             ++countRef;
    //             if (this.elements[i] == aux.elements[i]) {
    //                 ++count;
    //             }
    //         }
    //     }
    //     return count != 0 && count == countRef;
    // }


}