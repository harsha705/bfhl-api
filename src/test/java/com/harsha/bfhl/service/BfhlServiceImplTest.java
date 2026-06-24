package com.harsha.bfhl.service;

import com.harsha.bfhl.dto.BfhlRequest;
import com.harsha.bfhl.dto.BfhlResponse;
import com.harsha.bfhl.service.impl.BfhlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BfhlServiceImplTest {

    private BfhlServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new BfhlServiceImpl("harsha", "24012005", "vermaharsha314@gmail.com", "2311981227");
    }

    @Test
    @DisplayName("Example A: mixed input with numbers, alphabets, special character")
    void testExampleA() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "1", "334", "4", "R", "$"));
        BfhlResponse response = service.processData(request);

        assertTrue(response.isSuccess());
        assertEquals("harsha_24012005", response.getUserId());
        assertEquals("vermaharsha314@gmail.com", response.getEmail());
        assertEquals("2311981227", response.getRollNumber());

        assertEquals(List.of("1"), response.getOddNumbers());
        assertEquals(List.of("334", "4"), response.getEvenNumbers());
        assertEquals(List.of("A", "R"), response.getAlphabets());
        assertEquals(List.of("$"), response.getSpecialCharacters());
        assertEquals("339", response.getSum());
        assertEquals("Ra", response.getConcatString());
    }

    @Test
    @DisplayName("Example B: broader mixed input")
    void testExampleB() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        BfhlResponse response = service.processData(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of("5"), response.getOddNumbers());
        assertEquals(List.of("2", "4", "92"), response.getEvenNumbers());
        assertEquals(List.of("A", "Y", "B"), response.getAlphabets());
        assertEquals(List.of("&", "-", "*"), response.getSpecialCharacters());
        assertEquals("103", response.getSum());
        assertEquals("ByA", response.getConcatString());
    }

    @Test
    @DisplayName("Example C: only alphabets, no numbers, no special chars")
    void testExampleC() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "ABCD", "DOE"));
        BfhlResponse response = service.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertEquals(List.of("A", "ABCD", "DOE"), response.getAlphabets());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("EoDdCbAa", response.getConcatString());
    }

    @Test
    @DisplayName("Empty data array returns all empty lists and sum 0")
    void testEmptyData() {
        BfhlRequest request = new BfhlRequest(Collections.emptyList());
        BfhlResponse response = service.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Null data field treated as empty list")
    void testNullData() {
        BfhlRequest request = new BfhlRequest(null);
        BfhlResponse response = service.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Only numbers: classify odd and even correctly, sum as string")
    void testOnlyNumbers() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("1", "2", "3", "100"));
        BfhlResponse response = service.processData(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of("1", "3"), response.getOddNumbers());
        assertEquals(List.of("2", "100"), response.getEvenNumbers());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("106", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Only alphabets: all go to alphabets list as uppercase")
    void testOnlyAlphabets() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("abc", "XYZ", "m"));
        BfhlResponse response = service.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertEquals(List.of("ABC", "XYZ", "M"), response.getAlphabets());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());
        // combined = "abcXYZm", reversed = "mZYXcba", alternating caps = "mZyXcBa"
        assertEquals("mZyXcBa", response.getConcatString());
    }

    @Test
    @DisplayName("Only special characters: all go to special_characters")
    void testOnlySpecialCharacters() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("$", "@", "#", "!"));
        BfhlResponse response = service.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertEquals(List.of("$", "@", "#", "!"), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Mixed alphanumeric strings go to special_characters")
    void testMixedAlphanumericToSpecial() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a1", "1a", "ab#", "@x", "12abc"));
        BfhlResponse response = service.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertEquals(List.of("a1", "1a", "ab#", "@x", "12abc"), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
    }

    @Test
    @DisplayName("Null items inside the array are skipped gracefully")
    void testNullItemsInsideArray() {
        List<String> data = new java.util.ArrayList<>();
        data.add("1");
        data.add(null);
        data.add("a");
        BfhlRequest request = new BfhlRequest(data);
        BfhlResponse response = service.processData(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of("1"), response.getOddNumbers());
        assertEquals(List.of("A"), response.getAlphabets());
        assertEquals("1", response.getSum());
    }

    @Test
    @DisplayName("user_id format is fullname_ddmmyyyy in lowercase")
    void testUserIdFormat() {
        BfhlResponse response = service.processData(new BfhlRequest(Collections.emptyList()));
        assertEquals("harsha_24012005", response.getUserId());
    }

    @Test
    @DisplayName("Multi-digit numbers are classified and summed correctly")
    void testMultiDigitNumbers() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("100", "201", "500"));
        BfhlResponse response = service.processData(request);

        assertEquals(List.of("201"), response.getOddNumbers());
        assertEquals(List.of("100", "500"), response.getEvenNumbers());
        assertEquals("801", response.getSum());
    }

    @Test
    @DisplayName("Zero is treated as even number")
    void testZeroIsEven() {
        BfhlRequest request = new BfhlRequest(List.of("0"));
        BfhlResponse response = service.processData(request);

        assertEquals(List.of("0"), response.getEvenNumbers());
        assertTrue(response.getOddNumbers().isEmpty());
        assertEquals("0", response.getSum());
    }

    @Test
    @DisplayName("concat_string: single alphabet string reversed alternating caps")
    void testConcatSingleAlpha() {
        // Input: ["aR"], combined="aR", reversed="Ra", alternating="Ra"
        BfhlRequest request = new BfhlRequest(List.of("aR"));
        BfhlResponse response = service.processData(request);
        assertEquals("Ra", response.getConcatString());
    }

    @Test
    @DisplayName("Negative numbers should be classified as special_characters")
    void testNegativeNumbersToSpecial() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("-5", "-10", "abc"));
        BfhlResponse response = service.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertEquals(List.of("ABC"), response.getAlphabets());
        assertEquals(List.of("-5", "-10"), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
    }
}
