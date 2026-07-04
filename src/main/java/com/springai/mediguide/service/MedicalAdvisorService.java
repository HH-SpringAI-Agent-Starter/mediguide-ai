package com.springai.mediguide.service;

import com.springai.mediguide.agent.ChronicCareAgent;
import com.springai.mediguide.agent.HealthSummaryAgent;
import com.springai.mediguide.agent.MedicationGuideAgent;
import com.springai.mediguide.agent.SymptomTriageAgent;
import com.springai.mediguide.model.MedicalQuery;
import com.springai.mediguide.model.MedicalResponse;
import com.springai.mediguide.rag.MedicalRagService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Multi-Agent Orchestration Service.
 * Routes requests to the appropriate medical agent based on query category.
 */
@Service
public class MedicalAdvisorService {

    private final SymptomTriageAgent symptomTriageAgent;
    private final MedicationGuideAgent medicationGuideAgent;
    private final HealthSummaryAgent healthSummaryAgent;
    private final ChronicCareAgent chronicCareAgent;
    private final MedicalRagService medicalRagService;

    public MedicalAdvisorService(SymptomTriageAgent symptomTriageAgent,
                                  MedicationGuideAgent medicationGuideAgent,
                                  HealthSummaryAgent healthSummaryAgent,
                                  ChronicCareAgent chronicCareAgent,
                                  MedicalRagService medicalRagService) {
        this.symptomTriageAgent = symptomTriageAgent;
        this.medicationGuideAgent = medicationGuideAgent;
        this.healthSummaryAgent = healthSummaryAgent;
        this.chronicCareAgent = chronicCareAgent;
        this.medicalRagService = medicalRagService;
    }

    public MedicalResponse process(MedicalQuery query) {
        // Enrich with RAG knowledge
        String ragContext = medicalRagService.search(query.getQuery());
        if (query.getQuery() != null && !ragContext.isBlank()) {
            query.setQuery(query.getQuery() + "\n\n【参考知识】\n" + ragContext);
        }

        return switch (query.getCategory() != null ? query.getCategory() : "SYMPTOM") {
            case "MEDICATION" -> medicationGuideAgent.inquire(query);
            case "HEALTH_SUMMARY" -> healthSummaryAgent.summarize(query, List.of());
            case "CHRONIC_CARE" -> chronicCareAgent.manage(query);
            default -> symptomTriageAgent.assess(query);
        };
    }
}
