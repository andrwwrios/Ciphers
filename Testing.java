import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assume.assumeTrue;
import java.util.*;

public class Testing {

    @Test
    @DisplayName("EXAMPLE TEST CASE - 'A'-'G' Spec Example")
    public void firstCaseTest() {
        // Remember that you can change MIN_CHAR AND MAX_CHAR 
        // in Cipher.java to make testing easier! For this 
        // example test, we are using MIN_CHAR = A and MAX_CHAR = G

        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('G'));

        Cipher testSubstitution = new Substitution("GCBEAFD");
        assertEquals("FGE", testSubstitution.encrypt("FAD"));
        assertEquals("BAD", testSubstitution.decrypt("CGE"));
        
        // Per the spec, we should throw an IllegalArgumentException if 
        // the length of the shifter doesn't match the number of characters
        // within our Cipher's encodable range
        assertThrows(IllegalArgumentException.class, () -> {
            new Substitution("GCB");
        });
    }

    @Test
    @DisplayName("EXAMPLE TEST CASE - 'A'-'Z' Shifter")
    public void secondCaseTest() {
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('Z'));

        // Reverse alphabetic
        Cipher testSubstitution = new Substitution(
            "ZYXWVUTSRQPONMLKJIHGFEDCBA"
        );
        assertEquals("UZW", testSubstitution.encrypt("FAD"));
        assertEquals("BAD", testSubstitution.decrypt("YZW"));
    }

    @Test
    @DisplayName("EXAMPLE TEST CASE - ' '-'}' Shifter")
    public void thirdCaseTest() {
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int)(' ') && Cipher.MAX_CHAR == (int)('}'));
        
        // Swapping lowercase a<->b
        Cipher testSubstitution = new Substitution(
            " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`bacdefghijklmnopqrstuvwxyz{|}"
        );
        assertEquals("FAD", testSubstitution.encrypt("FAD"));
        assertEquals("fbd", testSubstitution.encrypt("fad"));
        assertEquals("BAD", testSubstitution.decrypt("BAD"));
        assertEquals("bad", testSubstitution.decrypt("abd"));
    }

    @Test
    @DisplayName("Substitution Encrypt - 'A' - 'Z' Reverse alphabet shifter")
    public void testSubEncrypt() {
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('Z'));

        String shifter = "";
        for(int i = Cipher.MAX_CHAR; i>= Cipher.MIN_CHAR; i--){
            shifter += (char)i;
        }
        Cipher testObject = new Substitution(shifter);

        assertFalse(((Substitution)testObject).containsDuplicateChar(shifter));
        assertTrue(((Substitution)testObject).isInEncodableRange(shifter));

        String actual = testObject.encrypt("ANDREWTHEBABE");

        assertEquals("ZMWIVDGSVYZYV", actual);
    }

    @Test
    @DisplayName("Substitution Decrypt - 'A' - 'Z' Reverse alphabet shifter")
    public void testSubDecrypt() {
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('Z'));

        String shifter = "";
        for(int i = Cipher.MAX_CHAR; i>= Cipher.MIN_CHAR; i--){
            shifter += (char)i;
        }
        Cipher testObject = new Substitution(shifter);

        assertFalse(((Substitution)testObject).containsDuplicateChar(shifter));
        assertTrue(((Substitution)testObject).isInEncodableRange(shifter));

        String actual = testObject.decrypt("ZMWIVDGSVYZYV");

        assertEquals("ANDREWTHEBABE", actual);
    }

    @Test
    @DisplayName("CaesarKey Encrypt - 'A' - 'Z' Key -> \"KEY\"")
    public void testCkEncrypt() {
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('Z'));

        Cipher testObject = new CaesarKey("KEY");

        assertEquals("KEYABCDFGHIJLMNOPQRSTUVWXZ", ((CaesarKey)testObject).getShifter("KEY"));

        String actual = testObject.encrypt("ANDREWTHEBABE");

        assertEquals("KMAQBVSFBEKEB", actual);
    }

    @Test
    @DisplayName("CaesarKey Decrypt - 'A' - 'Z' Key -> \"KEY\"")
    public void testCkDecrypt() {
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('Z'));

        Cipher testObject = new CaesarKey("KEY");

        assertFalse(((CaesarKey)testObject).
                containsDuplicateChar(((CaesarKey)testObject).getShifter("KEY")));

        String actual = testObject.decrypt("KMAQBVSFBEKEB");

        assertEquals("ANDREWTHEBABE", actual);
    }

    @Test
    @DisplayName("CaesarShift Encrypt - 'A' - 'Z' Shift -> 5")
    public void testCsEncrypt() {
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('Z'));

        Cipher testObject = new CaesarShift(5);
        String shifter = "FGHIJKLMNOPQRSTUVWXYZABCDE";

        assertTrue(shifter.equals(((CaesarShift)testObject).getShifter(5)));

        String actual = testObject.encrypt("ANDREWTHEBABE");

        assertEquals("FSIWJBYMJGFGJ", actual);
    }

    @Test
    @DisplayName("CaesarShift Decrypt - 'A' - 'Z' Shift -> 5")
    public void testCsDecrypt() {
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('Z'));

        Cipher testObject = new CaesarShift(5);

        assertFalse(((CaesarShift)testObject).
                containsDuplicateChar(((CaesarShift)testObject).getShifter(5)));

        String actual = testObject.decrypt("FSIWJBYMJGFGJ");

        assertEquals("ANDREWTHEBABE", actual);
    }

    @Test
    @DisplayName("MultiCipher Encrypt - 'A' - 'Z' List of Ciphers")
    public void testMcEncrypt() {
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('Z'));

        List<Cipher> testCiphers = new ArrayList<>(List.of(
            new Substitution("ZYXWVUTSRQPONMLKJIHGFEDCBA"),
            new CaesarKey("KEY"),
            new CaesarShift(5)));
            String CsShifter = "FGHIJKLMNOPQRSTUVWXYZABCDE";    

            assertFalse(((Substitution)testCiphers.get(0)).
                    containsDuplicateChar("ZYXWVUTSRQPONMLKJIHGFEDCBA"));

            assertEquals("KEYABCDFGHIJLMNOPQRSTUVWXZ", 
                    ((CaesarKey)testCiphers.get(1)).getShifter("KEY"));

            assertTrue(CsShifter.equals(((CaesarShift)testCiphers.get(2)).getShifter(5)));

            Cipher testObject = new MultiCipher(testCiphers);

            String actual = testObject.encrypt("ANDREWTHEBABE");

            assertEquals("EQALZFIWZCECZ", actual);
    }

    @Test
    @DisplayName("MultiCipher Decrypt - 'A' - 'Z' List of Ciphers")
    public void testMcDecrypt() {
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('Z'));
        
        List<Cipher> testCiphers = new ArrayList<>(List.of(
            new Substitution("ZYXWVUTSRQPONMLKJIHGFEDCBA"),
            new CaesarKey("KEY"),
            new CaesarShift(5)));
            String CsShifter = "FGHIJKLMNOPQRSTUVWXYZABCDE";

            assertFalse(((Substitution)testCiphers.get(0)).
                    containsDuplicateChar("ZYXWVUTSRQPONMLKJIHGFEDCBA"));

            assertEquals("KEYABCDFGHIJLMNOPQRSTUVWXZ", 
                    ((CaesarKey)testCiphers.get(1)).getShifter("KEY"));

            assertTrue(CsShifter.equals(((CaesarShift)testCiphers.get(2)).getShifter(5)));

            Cipher testObject = new MultiCipher(testCiphers);

            String actual = testObject.decrypt("EQALZFIWZCECZ");

            assertEquals("ANDREWTHEBABE", actual);
    }
}
