// andrew rios 
// ta : Shreya Nambi
// 04/10/2024
// C0 Substitution

/*
 * Extends Cipher and represents an encryption cipher called Substitution 
 * assigning each input character a unique output character and replacing all characters 
 * from an input with the output equivalent when encrypting (and vice-versa when decrypting).
 */

public class Substitution extends Cipher {
    private String shifter;

    // Constructs a new Substitution Cipher with an empty shifter
    public Substitution() {
        shifter = "";
    }

    // constructs a new Substitution Cipher with the provided shifter
     /*
     * IllegalArgumentException : The length of the shifter doesn't match the number of characters
     *                              within our Cipher's encodable range.
     * 
     * IllegalArgumentException : shifter contains a duplicate character.
     * 
     * IllegalArgumentException : Any individual character in the shifter falls outside the 
     *                              encodable range .
     */
    public Substitution(String shifter) {
        setShifter(shifter);
    }

    //   Behavior: Applies this Cipher's encryption scheme to 'input', returning the result
    // Exceptions: IllegalStateException if the shifter was never set
    //    Returns: The result of applying this Cipher's encryption scheme to `input`
    // Parameters: 'input' - the string to be encrypted
    @Override
    public String encrypt(String input) {
        if(shifter.isEmpty()) {
            throw new IllegalStateException();
        }
        String output = "";
        String range = encodableRange();
        for(int i = 0; i<input.length(); i++) {
            char currChar = input.charAt(i);
            int index = range.indexOf(currChar);
            char newChar = shifter.charAt(index);
            output += newChar;
        }
        return output;
    }

    //   Behavior: Applies this inverse of this Cipher's encryption scheme to 'input' (reversing
    //             a single round of encryption if previously applied), returning the result
    // Exceptions: IllegalStateException if the shifter was never set
    //    Returns: The result of applying the inverse of this Ciphers encryption scheme to `input`
    // Parameters: 'input' - the string to be decrypted
    @Override
    public String decrypt(String input) {
        if(shifter.isEmpty()) {
            throw new IllegalStateException();
        }
        String output = "";
        String range = encodableRange();
        for(int i = 0; i<input.length(); i++) {
            char currChar = input.charAt(i);
            int index = shifter.indexOf(currChar);
            char newChar = range.charAt(index);
            output += newChar;
        }
        return output;
    }

    // Sets the shifter for this Substitution Cipher with the given shifter
    /*
     * IllegalArgumentException : The length of the shifter doesn't match the number of characters
     *                              within the Cipher's encodable range.
     * 
     * IllegalArgumentException : shifter contains a duplicate character.
     * 
     * IllegalArgumentException : Any individual character in the shifter falls outside the 
     *                              encodable range .
     */
    public void setShifter(String shifter) {
        if(shifter.length() != Cipher.TOTAL_CHARS || containsDuplicateChar(shifter)
                                                || !isInEncodableRange(shifter)){
            throw new IllegalArgumentException();
        }
        this.shifter = shifter;
    }

    // creates and returns a String representation of the encodable range
    private String encodableRange() {
        String output = "";
        for(int i = MIN_CHAR; i<=MAX_CHAR; i++) {
            output += (char)i;
        }
        return output;
    }

    // with the given shifter this method checks for any duplicate characters
    // returns true if there are any dupicate characters within the shifter and otherwise false
    public boolean containsDuplicateChar(String shifter) {
        boolean containsDuplicate = false;
        for(int i = shifter.length()-1; i>=0; i--) {
            char currChar = shifter.charAt(i);
            String temp = shifter.substring(0,i);
            if(temp.contains(currChar+"")) {
                return true;
            }
        }
        return containsDuplicate;
    }

    // with the given shifter this method checks if any individual character  
    // falls outside the encodable range
    // returns true if all characters are in range and false if any indivudual character falls out
    public boolean isInEncodableRange(String shifter) {
        boolean inRange = true;
        for(int i = shifter.length()-1; i >= 0; i--) {
            if(shifter.charAt(i) < Cipher.MIN_CHAR || shifter.charAt(i) > Cipher.MAX_CHAR) {
                return false;
            }
        }
        return inRange;
    }
}