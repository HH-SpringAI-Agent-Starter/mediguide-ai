package com.springai.mediguide.agent;

import com.springai.mediguide.model.MedicalQuery;
import com.springai.mediguide.model.MedicalResponse;
import com.springai.mediguide.tool.HealthDataTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Health Summary Agent - Generates structured health summaries from patient data.
 */
@Component
public class HealthSummaryAgent {

    private final ChatClient chatClient;

    public HealthSummaryAgent(ChatClient.Builder chatBuilder) {
        this.chatClient = chatBuilder
                .defaultSystem("""
                    [ROLE] 你是 MediGuide 健康摘要助手，专业的医学报告AI助理。
                    [RULES]
                    1. 将医疗数据、检查结果组织为结构化健康摘要。
                    2. 突出异常指标和建议复查项目。
                    3. 保持客观、准确，不夸大也不淡化问题。
                    4. 始终建议专业医生解读完整报告。
                    """)
                .build();
    }

    public MedicalResponse summarize(MedicalQuery query, List<HealthDataTool.VitalsSnapshot> vitals) {
        MedicalResponse response = new MedicalResponse();
        response.setSessionId(UUID.randomUUID().toString());
        response.setAgentSource("HealthSummary");

        StringBuilder report = new StringBuilder();

        // Vitals trend analysis
        if (vitals != null && !vitals.isEmpty()) {
            report.append("## 📊 生命体征分析\n\n");
            if (vitals.size() >= 2) {
                report.append(HealthDataTool.analyzeVitalsTrend(vitals));
            } else {
                report.append(HealthDataTool.generateDailySummary(vitals));
            }
        }

        // AI-generated summary
        if (query.getQuery() != null) {
            String aiSummary = chatClient.prompt()
                    .messages(new UserMessage("基于以下信息生成一份健康摘要：\n" + query.getQuery()))
                    .call()
                    .chatResponse().getResult().getOutput().getText();
            report.append("\n## 📝 综合健康摘要\n\n").append(aiSummary);
        }

        response.setAnswer(report.toString());
        response.setTriageLevel("SELF_CARE");
        response.setRecommendedActions(List.of(
                "📋 打印报告复诊时携带",
                "🏥 定期体检（每年至少一次）",
                "💡 如有异常指标请咨询专科医生"
        ));

        return response;
    }
}
