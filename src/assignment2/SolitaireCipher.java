/**
* Your name here: Ridwanur Rahman
* Your McGill ID here: 260828139
**/
package assignment2;
public class SolitaireCipher {
    public Deck key;

    public SolitaireCipher (Deck key) {
	this.key = new Deck(key); // deep copy of the deck
    }

    /* 
     * TODO: Generates a keystream of the given size
     */
    public int[] getKeystream(int size) {

        int[] keystreamValues = new int[size];

        this.key.shuffle();
        this.key.printDeck();
        for (int i=0; i<size; i++)    {
            keystreamValues[i] = this.key.generateNextKeystreamValue();
            this.key.printDeck();
            if (keystreamValues[i] == -1) {
                this.key.printDeck();
                keystreamValues[i] = this.key.generateNextKeystreamValue();
                System.out.println(-1);
                this.key.printDeck();
            }
            System.out.println("values " + keystreamValues[i]);
            //this.key.printDeck();
        }

        return keystreamValues;

    }

    /* 
     * TODO: Encodes the input message using the algorithm described in the pdf.
     */
    public String encode(String msg) {

        System.out.println(msg);
        String encoded = "";
        String toEncode = msg.replace(" ", "");
        toEncode = toEncode.replaceAll("[^A-Za-z0-9]","");
        toEncode = toEncode.toUpperCase();
        int length = toEncode.length();

        int[] keystreamValues = this.getKeystream(length);

        for (int i=0; i<length; i++)    {
            int shift = keystreamValues[i] % 26;
            char shifted = (char) (toEncode.charAt(i) + shift);
            if (shifted > 'Z')  {
                encoded += (char) (toEncode.charAt(i) - (26 - shift));
            }
            else {
                encoded += shifted;
            }
        }

        return encoded;

    }

    /* 
     * TODO: Decodes the input message using the algorithm described in the pdf.
     */
    public String decode(String msg) {

        String decoded = "";
        int length = msg.length();

        int[] keystreamValues = this.getKeystream(length);

        for (int i=0; i<length; i++)    {
            int shift = keystreamValues[i] % 26;
            char shifted = (char) (msg.charAt(i) - shift);
            if (shifted < 'A')  {
                decoded += (char) (msg.charAt(i) + (26 - shift));
            }
            else {
                decoded += shifted;
            }
        }

        return decoded;

    }

    public static void main (String[] args)	{
        Deck deck = new Deck(5, 2);
        SolitaireCipher s = new SolitaireCipher(deck);
        System.out.println(s.encode("Is that you, Bob?"));

    }

}

/*AD BJ 3C 3D 5D 4D 2C 4C 5C RJ 2D AC*/