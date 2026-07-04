package com.springai.mediguide.agent;

import com.springai.mediguide.model.MedicalQuery;
import com.springai.mediguide.model.MedicalResponse;
import com.springai.mediguide.tool.DrugInteractionTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Medication Guide Agent - Drug inquiry, interaction checking, and usage guidance.
 */
@Component
public class MedicationGuideAgent {

    private final ChatClient chatClient;

    public MedicationGuideAgent(ChatClient.Builder chatBuilder) {
        this.chatClient = chatBuilder
                .defaultSystem("""
                    [ROLE] 你是 MediGuide 用药指导助手，专业的药学AI顾问。
                    [RULES]
                    1. 提供药品基本信息（用途、剂量、副作用、注意事项）。
                    2. 回答应基于药品说明书和临床指南。
                    3. 始终强调：用药调整必须在医生指导下进行。
                    4. 对于潜在的药物相互作用，提醒用户咨询药师。
                    5. 不推荐处方药，不更改现有用药方案。
                    """)
                .build();
    }

    public MedicalResponse inquire(MedicalQuery query) {
        MedicalResponse response = new MedicalResponse();
        response.setSessionId(UUID.randomUUID().toString());
        response.setAgentSource("MedicationGuide");

        StringBuilder fullAnswer = new StringBuilder();

        // Check drug interactions if two drugs mentioned
        if (query.getCurrentMedications() != null && query.getCurrentMedications().size() >= 2) {
            var drugs = query.getCurrentMedications();
            fullAnswer.append("### 🔍 药物相互作用检查\n\n");
            for (int i = 0; i < drugs.size() - 1; i++) {
                for (int j = i + 1; j < drugs.size(); j++) {
                    String result = DrugInteractionTool.checkInteraction(drugs.get(i), drugs.get(j));
                    fullAnswer.append("**").append(drugs.get(i)).append(" + ").append(drugs.get(j)).append("**：\n")
                              .append(result).append("\n\n");
                }
            }
        }

        // Query drug info if drug name provided
        if (query.getQuery() != null && !query.getQuery().isBlank()) {
            String info = DrugInteractionTool.getDrugInfo(query.getQuery().trim());
            fullAnswer.append("### 💊 药品信息\n\n").append(info).append("\n\n");
        }

        // Get AI response for broader questions
        if (query.getQuery() != null || (query.getCurrentMedications() != null && !query.getCurrentMedications().isEmpty())) {
            String aiResponse = chatClient.prompt()
                    .messages(new SystemMessage("Provide medication guidance in Chinese. Be concise and actionable."))
                    .messages(new UserMessage(buildPrompt(query)))
                    .call()
                    .chatResponse().getResult().getOutput().getText();
            fullAnswer.append("---\n\n### 🤖 AI建议\n\n").append(aiResponse);
        }

        response.setAnswer(fullAnswer.toString());
        response.setTriageLevel("CONSULT");

        return response;
    }

    private String buildPrompt(MedicalQuery q) {
        StringBuilder sb = new StringBuilder("### 用药咨询\n");
        if (q.getCurrentMedications() != null)
            sb.append("当前用药：").append(String.join("、", q.getCurrentMedications())).append("\n");
        if (q.getPreExistingConditions() != null)
            sb.append("基础疾病：").append(String.join("、", q.getPreExistingConditions())).append("\n");
        if (q.getQuery() != null) sb.append("问题：").append(q.getQuery());
        return sb.toString();
    }
}
