// andrew rios 
// ta : Shreya Nambi
// 04/10/2024
// C0 MultiCipher

/*
 * Extends Cipher and implements a multitude of given ciphers chaining these ciphers together. 
 * An input is passed through a list of ciphers one at a time
 * using the previous cipher's output as the input to the next. Repeating this through the entire 
 * list results. Vise versa with decrypting starting at the end of the list moving backward.
 */

 import java.util.*;

 public class MultiCipher extends Cipher {
     private List<Cipher> ciphers;
 
     // Constructs a new MultiCipher with the provided List of Ciphers
     // Should throw an IllegalArgumentException if the given list is null
     public MultiCipher(List<Cipher> ciphers) {
         if(ciphers == null) {
             throw new IllegalArgumentException();
         }
         this.ciphers = ciphers;
     }
 
     //   Behavior: Applies this Cipher's encryption scheme to 'input', returning the result
     // Exceptions: None
     //    Returns: The result of applying this Cipher's encryption scheme to `input`
     // Parameters: 'input' - the string to be encrypted
     @Override
     public String encrypt(String input) {
         String currOutput = input;
         for(Cipher cipher : ciphers) {
             String currInput = cipher.encrypt(currOutput);
             currOutput = currInput;
         }
         return currOutput;
     }
 
     //   Behavior: Applies this inverse of this Cipher's encryption scheme to 'input' (reversing
     //             a single round of encryption if previously applied), returning the result
     // Exceptions: None
     //    Returns: The result of applying the inverse of this Ciphers encryption scheme to `input`
     // Parameters: 'input' - the string to be decrypted
     @Override
     public String decrypt(String input) {
         String currOutput = input;
         for(int i = ciphers.size()-1; i>=0; i--) {
             Cipher currCipher = ciphers.get(i);
             String currInput = currCipher.decrypt(currOutput);
             currOutput = currInput;
         }
         return currOutput;
     }
 }