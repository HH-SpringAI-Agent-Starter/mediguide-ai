package com.springai.mediguide.agent;

import com.springai.mediguide.model.MedicalQuery;
import com.springai.mediguide.model.MedicalResponse;
import com.springai.mediguide.tool.ChronicCareTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Chronic Care Agent - Long-term disease management and lifestyle guidance.
 */
@Component
public class ChronicCareAgent {

    private final ChatClient chatClient;

    public ChronicCareAgent(ChatClient.Builder chatBuilder) {
        this.chatClient = chatBuilder
                .defaultSystem("""
                    [ROLE] 你是 MediGuide 慢病管理助手，专注于慢性病的长期管理指导。
                    [RULES]
                    1. 提供疾病管理指南、生活方式建议、监测计划。
                    2. 强调规律用药、定期复查的重要性。
                    3. 鼓励患者自我管理，提高依从性。
                    4. 不替代专科医生的诊疗意见。
                    5. 回答基于临床指南（中国+国际），标注参考来源。
                    """)
                .build();
    }

    public MedicalResponse manage(MedicalQuery query) {
        MedicalResponse response = new MedicalResponse();
        response.setSessionId(UUID.randomUUID().toString());
        response.setAgentSource("ChronicCare");

        StringBuilder answer = new StringBuilder();

        // Get condition-specific guidelines
        if (query.getPreExistingConditions() != null && !query.getPreExistingConditions().isEmpty()) {
            for (String condition : query.getPreExistingConditions()) {
                String guidelines = ChronicCareTool.getGuidelines(condition);
                answer.append(guidelines).append("\n\n");

                answer.append(ChronicCareTool.generateWeeklyChecklist(condition)).append("\n\n");
            }
        }

        // AI-enhanced response
        String aiAdvice = chatClient.prompt()
                .messages(new UserMessage(buildPrompt(query)))
                .call()
                .chatResponse().getResult().getOutput().getText();
        answer.append("---\n\n### 🤖 AI定制建议\n\n").append(aiAdvice);

        response.setAnswer(answer.toString());
        response.setTriageLevel("SELF_CARE");
        response.setRecommendedActions(List.of(
                "💊 按时服药，不擅自停药",
                "📊 每日监测关键指标",
                "🏥 按时复诊（一般每3-6个月）",
                "⚠️ 出现异常及时就医"
        ));

        return response;
    }

    private String buildPrompt(MedicalQuery q) {
        StringBuilder sb = new StringBuilder("### 慢病管理咨询\n");
        if (q.getPreExistingConditions() != null)
            sb.append("慢病类型：").append(String.join("、", q.getPreExistingConditions())).append("\n");
        if (q.getAge() != null) sb.append("年龄：").append(q.getAge()).append("\n");
        if (q.getCurrentMedications() != null)
            sb.append("当前用药：").append(String.join("、", q.getCurrentMedications())).append("\n");
        if (q.getQuery() != null)
            sb.append("咨询内容：").append(q.getQuery());
        sb.append("\n\n请提供个性化慢病管理建议。");
        return sb.toString();
    }
}
