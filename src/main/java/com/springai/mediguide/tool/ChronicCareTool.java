package com.springai.mediguide.tool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Chronic disease management tool - provides condition-specific guidance.
 */
public class ChronicCareTool {

    private static final Map<String, List<String>> CONDITION_GUIDELINES = Map.of(
            "diabetes", List.of(
                    "✅ 空腹血糖目标：4.4-7.0 mmol/L",
                    "✅ 餐后2小时血糖：<10.0 mmol/L",
                    "✅ 糖化血红蛋白(HbA1c)目标：<7.0%",
                    "✅ 每3-6个月检查一次糖化血红蛋白",
                    "✅ 每年检查眼底、肾功能、足部",
                    "💊 遵医嘱用药，不漏服",
                    "🏃 每周至少150分钟中等强度运动",
                    "🥗 控制碳水化合物摄入，增加膳食纤维"
            ),
            "hypertension", List.of(
                    "✅ 血压控制目标：<130/80 mmHg",
                    "✅ 每日晨起后、睡前各测一次血压",
                    "✅ 低盐饮食：每日钠摄入<5g（约一啤酒瓶盖）",
                    "✅ 限制饮酒：男性<25g/日，女性<15g/日",
                    "✅ 保持BMI在18.5-24.0之间",
                    "🏃 规律有氧运动：每周5天，每次30分钟",
                    "💊 降压药不可擅自停用",
                    "📊 记录血压日志，复诊时携带"
            ),
            "copd", List.of(
                    "✅ 戒烟是第一要务",
                    "✅ 避免接触粉尘、烟雾、化学刺激物",
                    "💊 按时使用吸入药物，掌握正确吸入方法",
                    "🏥 每年接种流感疫苗和肺炎疫苗",
                    "🏃 肺康复训练：缩唇呼吸、腹式呼吸",
                    "⚠️ 症状加重（痰量增加、颜色变黄）→ 及时就医"
            ),
            "asthma", List.of(
                    "✅ 控制药物每日规律使用，不因无症状而停用",
                    "✅ 记录PEF（呼气峰流速），每日晨起测量",
                    "⚠️ 识别诱发因素：过敏原、冷空气、运动",
                    "💊 急救药物（如沙丁胺醇）随身携带",
                    "🏥 每3-6个月复诊评估控制水平",
                    "📱 哮喘行动计划 + ACT评分每月评估"
            )
    );

    public static String getGuidelines(String condition) {
        var key = condition.toLowerCase().trim();
        if (CONDITION_GUIDELINES.containsKey(key)) {
            var items = CONDITION_GUIDELINES.get(key);
            StringBuilder sb = new StringBuilder();
            sb.append("📋 **").append(getChineseName(key)).append("管理指南**\n\n");
            sb.append("（更新日期：" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "）\n\n");
            for (String item : items) {
                sb.append(item).append("\n");
            }
            return sb.toString();
        }
        return "暂未收录该慢病的管理指南。建议咨询专科医生制定个性化管理方案。";
    }

    public static String generateWeeklyChecklist(String condition) {
        var key = condition.toLowerCase().trim();
        return """
            📋 **每周自我管理清单**
            
            **每日必做：**
            - ☐ 按时服药/用药
            - ☐ 测量并记录关键指标（血压/血糖/PEF）
            - ☐ 充足饮水（1.5-2L）
            
            **每周至少3次：**
            - ☐ 30分钟中等强度运动
            - ☐ 健康饮食（多蔬果、少盐少油）
            
            **本周关注：**
            - ☐ 症状是否稳定？有无新发症状？
            - ☐ 用药有无不良反应？
            - ☐ 睡眠质量如何？
            
            ⚠️ 异常信号：症状持续加重、指标失控 → 及时就医
            """;
    }

    private static String getChineseName(String condition) {
        return switch (condition) {
            case "diabetes" -> "糖尿病";
            case "hypertension" -> "高血压";
            case "copd" -> "慢性阻塞性肺疾病(COPD)";
            case "asthma" -> "支气管哮喘";
            default -> condition;
        };
    }
}
