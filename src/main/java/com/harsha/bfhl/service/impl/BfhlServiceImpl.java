package com.harsha.bfhl.service.impl;

import com.harsha.bfhl.dto.BfhlRequest;
import com.harsha.bfhl.dto.BfhlResponse;
import com.harsha.bfhl.service.BfhlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    private final String fullName;
    private final String dob;
    private final String email;
    private final String rollNumber;

    public BfhlServiceImpl(
            @Value("${app.full-name}") String fullName,
            @Value("${app.dob}") String dob,
            @Value("${app.email}") String email,
            @Value("${app.roll-number}") String rollNumber) {
        this.fullName = fullName;
        this.dob = dob;
        this.email = email;
        this.rollNumber = rollNumber;
    }

    @Override
    public BfhlResponse processData(BfhlRequest request) {
        List<String> data = request.getData();
        if (data == null) {
            data = Collections.emptyList();
        }

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        long totalSum = 0;
        StringBuilder alphaBuilder = new StringBuilder();

        for (String item : data) {
            if (item == null) {
                continue;
            }

            if (isPureNumber(item)) {
                long num = Long.parseLong(item);
                totalSum += num;
                if (num % 2 == 0) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }
            } else if (isPureAlphabet(item)) {
                alphabets.add(item.toUpperCase());
                alphaBuilder.append(item);
            } else {
                specialCharacters.add(item);
            }
        }

        String concatString = buildConcatString(alphaBuilder.toString());
        String userId = buildUserId();

        return new BfhlResponse.Builder()
                .isSuccess(true)
                .userId(userId)
                .email(email)
                .rollNumber(rollNumber)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialCharacters)
                .sum(String.valueOf(totalSum))
                .concatString(concatString)
                .build();
    }

    // Pure digits only: 0-9, one or more
    private boolean isPureNumber(String s) {
        return s.matches("^\\d+$");
    }

    // Pure alphabets only: a-z or A-Z, one or more
    private boolean isPureAlphabet(String s) {
        return s.matches("^[a-zA-Z]+$");
    }

    // Build user_id: fullname_ddmmyyyy (lowercase, spaces replaced with underscores)
    private String buildUserId() {
        String normalized = fullName.trim().toLowerCase().replaceAll("\\s+", "_");
        return normalized + "_" + dob;
    }

    /**
     * Steps:
     * 1. Take all characters from the combined alphabet string
     * 2. Reverse the string
     * 3. Apply alternating caps starting with uppercase at index 0
     */
    private String buildConcatString(String combined) {
        if (combined.isEmpty()) {
            return "";
        }
        String reversed = new StringBuilder(combined).reverse().toString();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        return result.toString();
    }
}
