package com.harsha.bfhl.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BfhlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /health returns UP status")
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("BFHL API"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("POST /bfhl - Example A returns 200 with correct fields")
    void testExampleA() throws Exception {
        String body = """
                {
                  "data": ["a", "1", "334", "4", "R", "$"]
                }
                """;

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.user_id").value("harsha_24012005"))
                .andExpect(jsonPath("$.email").value("vermaharsha314@gmail.com"))
                .andExpect(jsonPath("$.roll_number").value("2311981227"))
                .andExpect(jsonPath("$.odd_numbers[0]").value("1"))
                .andExpect(jsonPath("$.even_numbers[0]").value("334"))
                .andExpect(jsonPath("$.even_numbers[1]").value("4"))
                .andExpect(jsonPath("$.alphabets[0]").value("A"))
                .andExpect(jsonPath("$.alphabets[1]").value("R"))
                .andExpect(jsonPath("$.special_characters[0]").value("$"))
                .andExpect(jsonPath("$.sum").value("339"))
                .andExpect(jsonPath("$.concat_string").value("Ra"));
    }

    @Test
    @DisplayName("POST /bfhl - Example B returns correct response")
    void testExampleB() throws Exception {
        String body = """
                {
                  "data": ["2", "a", "y", "4", "&", "-", "*", "5", "92", "b"]
                }
                """;

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.odd_numbers[0]").value("5"))
                .andExpect(jsonPath("$.even_numbers[0]").value("2"))
                .andExpect(jsonPath("$.even_numbers[1]").value("4"))
                .andExpect(jsonPath("$.even_numbers[2]").value("92"))
                .andExpect(jsonPath("$.alphabets[0]").value("A"))
                .andExpect(jsonPath("$.alphabets[1]").value("Y"))
                .andExpect(jsonPath("$.alphabets[2]").value("B"))
                .andExpect(jsonPath("$.special_characters[0]").value("&"))
                .andExpect(jsonPath("$.special_characters[1]").value("-"))
                .andExpect(jsonPath("$.special_characters[2]").value("*"))
                .andExpect(jsonPath("$.sum").value("103"))
                .andExpect(jsonPath("$.concat_string").value("ByA"));
    }

    @Test
    @DisplayName("POST /bfhl - Example C returns correct concat_string EoDdCbAa")
    void testExampleC() throws Exception {
        String body = """
                {
                  "data": ["A", "ABCD", "DOE"]
                }
                """;

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.odd_numbers").isEmpty())
                .andExpect(jsonPath("$.even_numbers").isEmpty())
                .andExpect(jsonPath("$.alphabets[0]").value("A"))
                .andExpect(jsonPath("$.alphabets[1]").value("ABCD"))
                .andExpect(jsonPath("$.alphabets[2]").value("DOE"))
                .andExpect(jsonPath("$.special_characters").isEmpty())
                .andExpect(jsonPath("$.sum").value("0"))
                .andExpect(jsonPath("$.concat_string").value("EoDdCbAa"));
    }

    @Test
    @DisplayName("POST /bfhl - empty data array returns empty lists and sum 0")
    void testEmptyData() throws Exception {
        String body = """
                {
                  "data": []
                }
                """;

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.odd_numbers").isEmpty())
                .andExpect(jsonPath("$.even_numbers").isEmpty())
                .andExpect(jsonPath("$.alphabets").isEmpty())
                .andExpect(jsonPath("$.special_characters").isEmpty())
                .andExpect(jsonPath("$.sum").value("0"))
                .andExpect(jsonPath("$.concat_string").value(""));
    }

    @Test
    @DisplayName("POST /bfhl - null request body returns 400")
    void testNullRequestBody() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }

    @Test
    @DisplayName("POST /bfhl - malformed JSON returns 400")
    void testMalformedJson() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{bad json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }

    @Test
    @DisplayName("POST /bfhl - only special characters")
    void testOnlySpecialCharacters() throws Exception {
        String body = """
                {
                  "data": ["$", "@", "#"]
                }
                """;

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.special_characters[0]").value("$"))
                .andExpect(jsonPath("$.special_characters[1]").value("@"))
                .andExpect(jsonPath("$.special_characters[2]").value("#"))
                .andExpect(jsonPath("$.sum").value("0"));
    }

    @Test
    @DisplayName("POST /bfhl - mixed alphanumeric strings classified as special_characters")
    void testMixedAlphanumericToSpecial() throws Exception {
        String body = """
                {
                  "data": ["a1", "1a", "ab#"]
                }
                """;

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.special_characters[0]").value("a1"))
                .andExpect(jsonPath("$.special_characters[1]").value("1a"))
                .andExpect(jsonPath("$.special_characters[2]").value("ab#"))
                .andExpect(jsonPath("$.alphabets").isEmpty())
                .andExpect(jsonPath("$.odd_numbers").isEmpty())
                .andExpect(jsonPath("$.even_numbers").isEmpty());
    }

    @Test
    @DisplayName("POST /bfhl - null data field treated as empty")
    void testNullDataField() throws Exception {
        String body = """
                {
                  "data": null
                }
                """;

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.sum").value("0"));
    }

    @Test
    @DisplayName("POST /bfhl - negative numbers classified as special_characters")
    void testNegativeNumbersToSpecial() throws Exception {
        String body = """
                {
                  "data": ["-5", "-10", "abc"]
                }
                """;

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.special_characters[0]").value("-5"))
                .andExpect(jsonPath("$.special_characters[1]").value("-10"))
                .andExpect(jsonPath("$.alphabets[0]").value("ABC"))
                .andExpect(jsonPath("$.odd_numbers").isEmpty())
                .andExpect(jsonPath("$.even_numbers").isEmpty())
                .andExpect(jsonPath("$.sum").value("0"));
    }
}
