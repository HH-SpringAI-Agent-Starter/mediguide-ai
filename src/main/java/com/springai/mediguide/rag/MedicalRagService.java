package com.springai.mediguide.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.stereotype.Service;

/**
 * RAG service for medical knowledge retrieval.
 * Uses PGVector to search semantically similar medical documents.
 */
@Service
public class MedicalRagService {

    private static final Logger log = LoggerFactory.getLogger(MedicalRagService.class);

    private final VectorStore vectorStore;

    public MedicalRagService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public String search(String query) {
        if (query == null || query.isBlank()) return "";

        try {
            var results = vectorStore.similaritySearch(
                    SearchRequest.builder()
                            .query(query)
                            .topK(3)
                            .similarityThreshold(0.7)
                            .build()
            );

            if (results.isEmpty()) return "";

            StringBuilder context = new StringBuilder();
            for (var doc : results) {
                context.append("---\n").append(doc.getContent()).append("\n");
            }
            return context.toString();

        } catch (Exception e) {
            log.warn("RAG search failed: {}", e.getMessage());
            return "";
        }
    }
}
