package com.example.bajaj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private static final String NAME = "Harshitha CS";
    private static final String REGNO = "U25UV22T029022";  
    private static final String EMAIL = "csharshitha2004@gmail.com";

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // Step 1: Generate webhook
        String webhookApi = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
        Map<String, String> requestBody = Map.of(
                "name", NAME,
                "regNo", REGNO,
                "email", EMAIL
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(webhookApi, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String webhookUrl = response.getBody().get("webhook").toString();
            String accessToken = response.getBody().get("accessToken").toString();

            System.out.println("Webhook URL: " + webhookUrl);
            System.out.println("Access Token: " + accessToken);

            // Step 2: Solve SQL problem
            String finalQuery = solveSQL(REGNO);

            // Step 3: Submit solution
            HttpHeaders submitHeaders = new HttpHeaders();
            submitHeaders.setContentType(MediaType.APPLICATION_JSON);
            submitHeaders.setBearerAuth(accessToken);

            Map<String, String> submitBody = Map.of("finalQuery", finalQuery);
            HttpEntity<Map<String, String>> submitRequest = new HttpEntity<>(submitBody, submitHeaders);

            ResponseEntity<String> submitResponse = restTemplate.postForEntity(webhookUrl, submitRequest, String.class);
            System.out.println("Submission Response: " + submitResponse.getBody());
        } else {
            System.out.println("Failed to generate webhook: " + response.getStatusCode());
        }
    }

    // Decide question based on last digit of regNo
    private String solveSQL(String regNo) {
        char lastDigit = regNo.charAt(regNo.length() - 1);
        if (Character.getNumericValue(lastDigit) % 2 == 0) {
            // Even → Question 2
            return "SELECT * FROM table2 WHERE ..."; // Replace with actual SQL
        } else {
            // Odd → Question 1
            return "SELECT * FROM table1 WHERE ..."; // Replace with actual SQL
        }
    }
}
