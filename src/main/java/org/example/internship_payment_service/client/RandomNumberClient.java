package org.example.internship_payment_service.client;

import org.example.internship_payment_service.exception.RandomNumberGenerationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class RandomNumberClient {

    private final RestClient restClient;

    public RandomNumberClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://www.random.org")
                .build();
    }

    public Integer getRandomNumber() {
        String response;
        try {
            response = restClient.get()
                    .uri("/integers/?num=1&min=1&max=1000&col=1&base=10&format=plain&rnd=new")
                    .retrieve()
                    .body(String.class);
        } catch (RestClientException e) {
            throw new RandomNumberGenerationException("Failed to call random number API");
        }

        if (response == null || response.isBlank()) {
            throw new RandomNumberGenerationException("Random number API returned an empty response");
        }

        try {
            return Integer.parseInt(response.trim());
        } catch (NumberFormatException e) {
            throw new RandomNumberGenerationException("Random number API returned a non-numeric response: " + response);
        }
    }
}
