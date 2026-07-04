package com.springai.mediguide.agent;

import com.springai.mediguide.model.MedicalQuery;
import com.springai.mediguide.model.MedicalResponse;
import com.springai.mediguide.tool.DrugInteractionTool;
import com.springai.mediguide.tool.EmergencyTriageTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Symptom Triage Agent - First-line symptom assessment and urgency classification.
 */
@Component
public class SymptomTriageAgent {

    private final ChatClient chatClient;

    public SymptomTriageAgent(ChatClient.Builder chatBuilder) {
        this.chatClient = chatBuilder
                .defaultSystem("""
                    [ROLE] 你是 MediGuide 症状分诊助手，专业的医学分诊AI。
                    [RULES]
                    1. 你的职责是症状初步评估和建议，不是诊断。
                    2. 识别"红色警报"症状（胸痛、呼吸困难、严重出血、意识障碍等）→ 立即标记为紧急。
                    3. 对非紧急情况，提供居家护理建议和观察要点。
                    4. 始终强调：此信息仅供参考，不替代医生诊断。
                    5. 告知何时应该就医（症状加重、持续不缓解等）。
                    6. 回答简洁，分点列出。
                    [OUTPUT FORMAT]
                    - 分诊等级：[URGENT/CONSULT/SELF_CARE]
                    - 症状分析
                    - 建议
                    - 需警惕的危险信号
                    """)
                .build();
    }

    public MedicalResponse assess(MedicalQuery query) {
        MedicalResponse response = new MedicalResponse();
        response.setSessionId(UUID.randomUUID().toString());
        response.setAgentSource("SymptomTriage");

        // Run triage tool first
        String triageLevel = EmergencyTriageTool.triage(query);
        response.setTriageLevel(triageLevel);

        String triageAdvice = EmergencyTriageTool.getTriageAdvice(triageLevel);

        String userPrompt = buildPrompt(query);
        ChatResponse chatResponse = chatClient.prompt()
                .messages(
                        new SystemMessage("""
                            You are a medical triage assistant. Assess symptoms and provide triage recommendations.
                            Always include: triage level, symptom analysis, actionable recommendations, and warning signs.
                            Respond primarily in Chinese.""")
                )
                .messages(new UserMessage(userPrompt))
                .call()
                .chatResponse();

        response.setAnswer(triageAdvice + "\n\n---\n\n" + chatResponse.getResult().getOutput().getText());
        response.setRecommendedActions(List.of(
                switch (triageLevel) {
                    case "URGENT" -> "🚨 立即拨打120或前往急诊科";
                    case "CONSULT" -> "🏥 预约就诊，建议24-48小时内就医";
                    default -> "✅ 居家观察，注意休息";
                },
                "📋 记录症状变化",
                "⚠️ 如症状加重请及时就医"
        ));

        return response;
    }

    private String buildPrompt(MedicalQuery q) {
        StringBuilder sb = new StringBuilder();
        sb.append("### 患者主诉\n");
        if (q.getSymptom() != null) sb.append("症状：").append(q.getSymptom()).append("\n");
        if (q.getDuration() != null) sb.append("持续时间：").append(q.getDuration()).append("\n");
        if (q.getAge() != null) sb.append("年龄：").append(q.getAge()).append("\n");
        if (q.getGender() != null) sb.append("性别：").append(q.getGender()).append("\n");
        if (q.getPreExistingConditions() != null && !q.getPreExistingConditions().isEmpty())
            sb.append("基础疾病：").append(String.join("、", q.getPreExistingConditions())).append("\n");
        if (q.getCurrentMedications() != null && !q.getCurrentMedications().isEmpty())
            sb.append("当前用药：").append(String.join("、", q.getCurrentMedications())).append("\n");
        if (q.getQuery() != null) sb.append("\n补充信息：").append(q.getQuery()).append("\n");
        sb.append("\n请进行症状分诊评估，给出分诊等级和建议。");
        return sb.toString();
    }
}
