// andrew rios 
// ta : Shreya Nambi
// 04/10/2024
// C0 CaesarShift

/*
 * Represents an encryption cipher called CaesarShift that involves shifting all encodable 
 * characters to the left by some provided shift amount. Replacing each input character 
 * with the corresponding character in shifter at the same relative position.
 * Extends Substitution
 */

 import java.util.*;

 public class CaesarShift extends Substitution {
  
     // Constructs a new CaesarShift with the provided shift value
     // An IllegalArgumentException should be thrown in the case that shift <= 0 
     // An IllegalArgumentException should be thrown if 
     /*
      * The length of the shifter created doesn't match the number of characters
      *                         within the Cipher's encodable range.
      * The shifter created contains a duplicate character.
      * Any individual character in the shifter falls outside the encodable range.
      */
     public CaesarShift(int shift) {
         if(shift <= 0) {
             throw new IllegalArgumentException();
         }
         setShifter(getShifter(shift));
     }
  
     // Builds a shifter in relation to the given key
     // returns the built shifter as a String
     public String getShifter(int shift) {
         String result = "";
         Queue<Character> encodableRange = queueOfRange();
         for(int i = 0; i<shift; i++) {
             encodableRange.add(encodableRange.remove());
         }
  
         for(char character : encodableRange) {
             result += "" + character;
         }
         return result;
     }
  
     // creates and returns a Queue of characters within the encodable range
     private Queue<Character> queueOfRange() {
         Queue<Character> result = new LinkedList<>();
         for(int i = MIN_CHAR; i<=MAX_CHAR; i++) {
             result.add((char)i);
         }
         return result;
     }
 }