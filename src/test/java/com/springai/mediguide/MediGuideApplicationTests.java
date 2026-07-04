package com.springai.mediguide;

import com.springai.mediguide.model.MedicalQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MediGuideApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertThat(restTemplate).isNotNull();
    }

    @Test
    void healthCheck() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/mediguide/health", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void triageLevels() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/mediguide/triage/levels", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).contains("URGENT", "CONSULT", "SELF_CARE");
    }

    @Test
    void symptomChat() {
        MedicalQuery query = new MedicalQuery();
        query.setSymptom("头痛发热三天，体温38度");
        query.setAge(30);
        query.setCategory("SYMPTOM");

        ResponseEntity<String> response = restTemplate.postForEntity("/api/mediguide/chat", query, String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
