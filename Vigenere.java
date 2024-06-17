// andrew rios 
// ta : Shreya Nambi
// 04/10/2024
// C0 Extension

/*
 * Represents a Vigenère cipher which is a hybrid between the CaesarKey and CaesarShift. 
 * It is created with a key that is repeated such that its length matches that of the input
 */

 public class Vigenere extends Substitution {
    //private int shift;
    private String key;

    // Constructs a new Vigenere Cipher with the provided key
    // IllegalArgumentException - if the key is empty or is not in the encodable range
    public Vigenere(String key) {
        if(key.isEmpty() || !isInEncodableRange(key)) {
            throw new IllegalArgumentException();
        }
        this.key = key;
    }

    //   Behavior: Applies this Cipher's encryption scheme to 'input', returning the result
    // Exceptions: None
    //    Returns: The result of applying this Cipher's encryption scheme to `input`
    // Parameters: 'input' - the string to be encrypted
    @Override
    public String encrypt(String input) {
        String repeatedKey = setKey(input);
        String output = "";
        int shift = 0;
        for(int i = 0; i<repeatedKey.length(); i++){
            shift = repeatedKey.charAt(i) - Cipher.MIN_CHAR;
            int charToAdd = input.charAt(i) + shift;
            if(charToAdd > Cipher.MAX_CHAR) {
                charToAdd = Cipher.MIN_CHAR + (charToAdd - Cipher.MAX_CHAR - 1);
            }
            output += (char)charToAdd;
        }
        return output;
    }

    //   Behavior: Applies this inverse of this Cipher's encryption scheme to 'input' (reversing
    //             a single round of encryption if previously applied), returning the result
    // Exceptions: None
    //    Returns: The result of applying the inverse of this Cipher's encryption scheme to `input`
    // Parameters: 'input' - the string to be decrypted
    @Override
    public String decrypt(String input) {
        String repeatedKey = setKey(input);
        String output = "";
        int shift = 0;
        for(int i = 0; i<repeatedKey.length(); i++) {
            shift = repeatedKey.charAt(i) - Cipher.MIN_CHAR;
            int charToAdd = input.charAt(i) - shift;
            if(charToAdd < Cipher.MIN_CHAR) {
                charToAdd = Cipher.MAX_CHAR - (Cipher.MIN_CHAR - charToAdd - 1);
            }
            output += (char) charToAdd;
        }
        return output;
    }

    // sets the current key to match the length of the given input
    // parameters: a string input to update the current key
    // return the updated key as a new string
    public String setKey(String input) {
        String repeatedKey = "";
        if(input.length() <= key.length()) {
            for(int i = 0; i<input.length(); i++){
                repeatedKey += key.charAt(i);
            }
            return repeatedKey;
        }
        repeatedKey = key;
        int index = 0;
        while(repeatedKey.length() != input.length()) {
            if(index == key.length()) {
                index = 0;
            }
            repeatedKey += key.charAt(index);
            index ++;
        }
        return repeatedKey;
    }
}