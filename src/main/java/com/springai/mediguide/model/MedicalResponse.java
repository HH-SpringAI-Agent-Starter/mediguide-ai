package com.springai.mediguide.model;

import java.time.LocalDateTime;
import java.util.List;

public class MedicalResponse {
    private String sessionId;
    private String answer;
    private List<String> references;
    private String triageLevel; // URGENT, CONSULT, SELF_CARE
    private String disclaimer;
    private LocalDateTime timestamp;
    private List<String> recommendedActions;
    private String agentSource; // SymptomTriage, MedicationGuide, HealthSummary, ChronicCare

    public MedicalResponse() {
        this.timestamp = LocalDateTime.now();
        this.disclaimer = "⚠️ 此信息仅供参考，不构成医疗建议。如有紧急情况请立即就医。";
    }

    // Getters and Setters
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public List<String> getReferences() { return references; }
    public void setReferences(List<String> references) { this.references = references; }
    public String getTriageLevel() { return triageLevel; }
    public void setTriageLevel(String triageLevel) { this.triageLevel = triageLevel; }
    public String getDisclaimer() { return disclaimer; }
    public void setDisclaimer(String disclaimer) { this.disclaimer = disclaimer; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public List<String> getRecommendedActions() { return recommendedActions; }
    public void setRecommendedActions(List<String> recommendedActions) { this.recommendedActions = recommendedActions; }
    public String getAgentSource() { return agentSource; }
    public void setAgentSource(String agentSource) { this.agentSource = agentSource; }
}
