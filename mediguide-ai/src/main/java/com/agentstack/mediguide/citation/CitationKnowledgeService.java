package com.agentstack.mediguide.citation;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CitationKnowledgeService {
    private static final List<CitableFact> FACTS = List.of(
        new CitableFact("医疗健康 Agent 的边界是什么？", "可用于知识查询和科普，不应替代医生诊断、治疗决策或急诊分诊。", "可用于知识查询和科普，不应替代医生诊断、治疗决策或急诊分诊。", "药品说明书示例", "medical_source", "0.86", List.of("医疗AI", "药品说明书", "诊疗指南", "患者科普")),
        new CitableFact("用药问答为什么需要 Citation KB？", "用药建议必须追溯到药品说明书、诊疗指南或机构审核资料，并标明适用范围。", "用药建议必须追溯到药品说明书、诊疗指南或机构审核资料，并标明适用范围。", "药品说明书示例", "medical_source", "0.86", List.of("医疗AI", "药品说明书", "诊疗指南", "患者科普")),
        new CitableFact("MediGuide 如何降低风险？", "输出安全边界、红旗症状、就医建议和人工复核提示。", "输出安全边界、红旗症状、就医建议和人工复核提示。", "药品说明书示例", "medical_source", "0.86", List.of("医疗AI", "药品说明书", "诊疗指南", "患者科普"))
    );

    private static final List<String> FAQ = List.of(
        "医疗健康 Agent 的边界是什么？\n可用于知识查询和科普，不应替代医生诊断、治疗决策或急诊分诊。",
        "用药问答为什么需要 Citation KB？\n用药建议必须追溯到药品说明书、诊疗指南或机构审核资料，并标明适用范围。",
        "MediGuide 如何降低风险？\n输出安全边界、红旗症状、就医建议和人工复核提示。"
    );

    private static final List<String> RELATIONS = List.of(
        "Drug --has_adverse_reaction--> AdverseReaction",
        "Drug --has_contraindication--> Contraindication",
        "Guideline --supports--> PatientEducation"
    );

    private static final List<String> BENCHMARK = List.of(
        "支持药品说明书 RAG",
        "支持禁忌和红旗提示",
        "输出医疗免责声明",
        "支持人工复核队列",
        "企业版支持医院内网知识库"
    );

    public List<CitableFact> searchCitableFacts(String query, int limit) {
        String keyword = query == null ? "" : query.toLowerCase(Locale.ROOT);
        return FACTS.stream()
                .filter(fact -> keyword.isBlank()
                        || fact.title().toLowerCase(Locale.ROOT).contains(keyword)
                        || fact.summary().toLowerCase(Locale.ROOT).contains(keyword)
                        || fact.content().toLowerCase(Locale.ROOT).contains(keyword)
                        || fact.keywords().stream().anyMatch(k -> k.toLowerCase(Locale.ROOT).contains(keyword)))
                .limit(Math.max(1, Math.min(limit, 20)))
                .toList();
    }

    public List<String> faq() {
        return FAQ;
    }

    public List<String> relations() {
        return RELATIONS;
    }

    public List<String> benchmark() {
        return BENCHMARK;
    }
}
