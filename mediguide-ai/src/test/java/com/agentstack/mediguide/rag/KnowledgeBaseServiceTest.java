package com.agentstack.mediguide.rag;

import com.agentstack.mediguide.tenant.TenantContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KnowledgeBaseServiceTest {

    private KnowledgeBaseService knowledgeBaseService;

    @BeforeEach
    void setUp() {
        knowledgeBaseService = new KnowledgeBaseService();
        TenantContext.setTenantId("test-tenant");
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void search_returnsResults() {
        List<String> results = knowledgeBaseService.search("高血压用药指南");
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.get(0).contains("test-tenant"));
    }

    @Test
    void search_demoFallback() {
        TenantContext.clear();
        List<String> results = knowledgeBaseService.search("体检");
        assertNotNull(results);
        assertTrue(results.stream().anyMatch(r -> r.contains("tenant=demo")));
    }
}
