// andrew rios 
// ta : Shreya Nambi
// 04/10/2024
// C0 Caesar Key

/*
 * Represents an encryption cipher called CaesarKey that involves placing
 * a key at the front of the substitution, with the rest of the encodable range following normally
 * (minus the characters included in the key). Then assigning each input character a unique
 * output character. 
 * Extends Substitution 
 */

 public class CaesarKey extends Substitution {
    
    // constructs a new CaesarKey Cipher with the provided key
    /*
    * IllegalArgumentException : if the key is empty
    * 
    * IllegalArgumentException : if the key contains a duplicate character.
    * 
    * IllegalArgumentException : Any individual character in the key falls outside the 
    *                              encodable range.
    *
    * IllegalArgumentException : length of the shifter created doesn't match the number of
    *                             characters within the Cipher's encodable range.
    * 
    * IllegalArgumentException : shifter created contains a duplicate character.
    * 
    * IllegalArgumentException : Any individual character in the shifter created falls outside 
    *                              the encodable range .
    */
   public CaesarKey(String key) {
       if(key.isEmpty() || containsDuplicateChar(key) || !isInEncodableRange(key)) {
           throw new IllegalArgumentException();
       }
       setShifter(getShifter(key));
   }

   // Builds a shifter in relation to the given key
   // returns the built shifter as a String
   public String getShifter(String key) {
       String output = "";

       for(int i = 0; i<key.length(); i++) {
           output += key.charAt(i);
       }

       for(int i = MIN_CHAR; i <= MAX_CHAR; i++) {
           if(!key.contains((char)i + "")) {
               output += (char)i;
           }
       }
       return output;
   }
}