package com.springai.mediguide.tool;

import com.springai.mediguide.model.MedicalQuery;
import java.util.List;

/**
 * Emergency triage tool - determines urgency level based on symptoms.
 */
public class EmergencyTriageTool {

    private static final List<String> RED_FLAG_SYMPTOMS = List.of(
            "chest pain", "胸痛", "胸闷", "呼吸困难", "shortness of breath",
            "seizure", "抽搐", "昏迷", "unconscious", "unresponsive",
            "massive bleeding", "大出血", "anaphylaxis", "过敏性休克",
            "stroke symptoms", "中风", "一侧肢体无力", "sudden severe headache",
            "suicidal", "自杀", "药物过量", "overdose",
            "severe burn", "严重烧伤", "carbon monoxide", "一氧化碳"
    );

    private static final List<String> URGENT_SYMPTOMS = List.of(
            "high fever", "高烧>39", "persistent vomiting", "持续呕吐",
            "severe diarrhea", "严重腹泻", "dehydration", "脱水",
            "deep wound", "深伤口", "fracture", "骨折",
            "eye injury", "眼外伤", "chemical splash", "化学溅入"
    );

    public static String triage(MedicalQuery query) {
        String text = (query.getSymptom() != null ? query.getSymptom() : "") + " "
                + (query.getQuery() != null ? query.getQuery() : "");

        // Check red flags first
        for (String flag : RED_FLAG_SYMPTOMS) {
            if (text.toLowerCase().contains(flag.toLowerCase())) {
                return "URGENT";
            }
        }

        // Check urgent symptoms
        for (String urgent : URGENT_SYMPTOMS) {
            if (text.toLowerCase().contains(urgent.toLowerCase())) {
                return "CONSULT";
            }
        }

        // Age-based escalation
        if (query.getAge() != null && query.getAge() < 3 && !text.isEmpty()) {
            return "CONSULT";
        }
        if (query.getAge() != null && query.getAge() > 70) {
            return "CONSULT";
        }

        return "SELF_CARE";
    }

    public static String getTriageAdvice(String level) {
        return switch (level) {
            case "URGENT" -> """
                    🚨 **紧急情况 — 请立即就医！**
                    
                    您描述的症状提示可能的紧急医疗情况。
                    请立即拨打 120 或前往最近的急诊科。
                    """;
            case "CONSULT" -> """
                    🏥 **建议尽快就医**
                    
                    建议在24-48小时内前往医疗机构就诊。
                    如果症状加重，请立即挂急诊。
                    """;
            case "SELF_CARE" -> """
                    ✅ **自我护理建议**
                    
                    目前描述的症状不提示紧急情况。
                    建议休息、观察。如症状持续或加重，请就医。
                    """;
            default -> "";
        };
    }
}
