package com.springai.mediguide.tool;

import java.util.Map;

/**
 * Simulated drug interaction checker.
 * In production, replace with real drug database API (e.g. RxNorm, OpenFDA).
 */
public class DrugInteractionTool {

    private static final Map<String, String> KNOWN_INTERACTIONS = Map.of(
            "warfarin+aspirin", "⚠️ 出血风险显著增加，需监测INR值。",
            "metformin+contrast", "⚠️ 造影检查前需暂停二甲双胍48小时，预防乳酸酸中毒。",
            "lisinopril+potassium", "⚠️ 高钾血症风险增加，避免同时补钾。",
            "statins+macrolides", "⚠️ 横纹肌溶解风险增加，考虑暂停他汀类药物。",
            "ssri+nsaids", "⚠️ 上消化道出血风险增加，考虑加用PPI保护胃黏膜。"
    );

    public static String checkInteraction(String drugA, String drugB) {
        String key1 = drugA.toLowerCase() + "+" + drugB.toLowerCase();
        String key2 = drugB.toLowerCase() + "+" + drugA.toLowerCase();
        if (KNOWN_INTERACTIONS.containsKey(key1)) return KNOWN_INTERACTIONS.get(key1);
        if (KNOWN_INTERACTIONS.containsKey(key2)) return KNOWN_INTERACTIONS.get(key2);
        return "✅ 未找到已知的严重相互作用，但仍建议监测。";
    }

    public static String getDrugInfo(String drugName) {
        return switch (drugName.toLowerCase()) {
            case "metformin" -> """
                二甲双胍 (Metformin)：
                - 类别：双胍类口服降糖药
                - 用途：2型糖尿病一线用药
                - 常见副作用：胃肠道不适、腹泻
                - 禁忌：严重肾功能不全(eGFR<30)、肝功能衰竭
                - 注意：造影检查前需暂停48h
                """;
            case "lisinopril" -> """
                赖诺普利 (Lisinopril)：
                - 类别：ACEI类降压药
                - 用途：高血压、心力衰竭
                - 常见副作用：干咳、高血钾
                - 禁忌：妊娠期、血管神经性水肿史
                - 监测：定期检查肾功能和血钾
                """;
            case "warfarin" -> """
                华法林 (Warfarin)：
                - 类别：维生素K拮抗剂抗凝药
                - 用途：房颤卒中预防、静脉血栓
                - 监测：定期INR值（目标2.0-3.0）
                - 食物影响：维生素K含量高的食物需保持摄入稳定
                - 注意：出血风险，定期复查
                """;
            default -> "❌ 未找到该药品的信息。该药品可能不在知识库中，请咨询医师或药师。";
        };
    }
}
