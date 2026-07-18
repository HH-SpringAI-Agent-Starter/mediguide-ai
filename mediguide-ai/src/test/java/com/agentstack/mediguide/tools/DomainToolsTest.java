package com.agentstack.mediguide.tools;

import com.agentstack.mediguide.rag.KnowledgeBaseService;
import com.agentstack.mediguide.tenant.TenantContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DomainToolsTest {

    @Mock
    private KnowledgeBaseService knowledgeBaseService;

    private DomainTools domainTools;

    @BeforeEach
    void setUp() {
        when(knowledgeBaseService.search(anyString()))
                .thenReturn(List.of("result-1", "result-2"));
        domainTools = new DomainTools(knowledgeBaseService);
        TenantContext.setTenantId("test-tenant");
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void knowledge_search_returnsTenantScopedResults() {
        String result = domainTools.knowledge_search("糖尿病饮食");
        assertTrue(result.contains("test-tenant"));
        assertTrue(result.contains("result-1"));
    }

    @Test
    void regulation_rag_search_returnsStub() {
        String result = domainTools.regulation_rag_search("药品说明");
        assertNotNull(result);
        assertTrue(result.contains("test-tenant"));
    }

    @Test
    void case_law_search_returnsStub() {
        String result = domainTools.case_law_search("医疗纠纷");
        assertNotNull(result);
    }

    @Test
    void contract_clause_extract_returnsStub() {
        String result = domainTools.contract_clause_extract("保密条款");
        assertNotNull(result);
    }

    @Test
    void compliance_disclaimer_returnsStub() {
        String result = domainTools.compliance_disclaimer("处方药");
        assertNotNull(result);
    }
}
