package com.agentstack.mediguide.tools;

import com.agentstack.mediguide.rag.KnowledgeBaseService;
import com.agentstack.mediguide.tenant.TenantContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class DomainTools {
    private final KnowledgeBaseService knowledgeBaseService;

    public DomainTools(KnowledgeBaseService knowledgeBaseService) {
        this.knowledgeBaseService = knowledgeBaseService;
    }

    @Tool(description = "Search tenant scoped private knowledge base")
    public String knowledge_search(@ToolParam(description = "search query") String query) {
        return String.join("\n", knowledgeBaseService.search(query));
    }

    @Tool(description = "drug label search for 医疗健康助手")
    public String drug_label_search(@ToolParam(description = "business query") String query) {
        return "[drug_label_search] tenant=" + TenantContext.getTenantId() + "; result for: " + query + "; demo stub, connect real system in production.";
    }

    @Tool(description = "guideline rag search for 医疗健康助手")
    public String guideline_rag_search(@ToolParam(description = "business query") String query) {
        return "[guideline_rag_search] tenant=" + TenantContext.getTenantId() + "; result for: " + query + "; demo stub, connect real system in production.";
    }

    @Tool(description = "contraindication flag for 医疗健康助手")
    public String contraindication_flag(@ToolParam(description = "business query") String query) {
        return "[contraindication_flag] tenant=" + TenantContext.getTenantId() + "; result for: " + query + "; demo stub, connect real system in production.";
    }

    @Tool(description = "patient education draft for 医疗健康助手")
    public String patient_education_draft(@ToolParam(description = "business query") String query) {
        return "[patient_education_draft] tenant=" + TenantContext.getTenantId() + "; result for: " + query + "; demo stub, connect real system in production.";
    }

    @Tool(description = "red flag triage for 医疗健康助手")
    public String red_flag_triage(@ToolParam(description = "business query") String query) {
        return "[red_flag_triage] tenant=" + TenantContext.getTenantId() + "; result for: " + query + "; demo stub, connect real system in production.";
    }

    @Tool(description = "medical disclaimer for 医疗健康助手")
    public String medical_disclaimer(@ToolParam(description = "business query") String query) {
        return "[medical_disclaimer] tenant=" + TenantContext.getTenantId() + "; result for: " + query + "; demo stub, connect real system in production.";
    }

}
