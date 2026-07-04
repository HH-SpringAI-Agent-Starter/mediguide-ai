package com.springai.mediguide.controller;

import com.springai.mediguide.model.MedicalQuery;
import com.springai.mediguide.model.MedicalResponse;
import com.springai.mediguide.service.MedicalAdvisorService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mediguide")
public class MedicalChatController {

    private final MedicalAdvisorService advisorService;

    public MedicalChatController(MedicalAdvisorService advisorService) {
        this.advisorService = advisorService;
    }

    @PostMapping("/chat")
    public MedicalResponse chat(@RequestBody MedicalQuery query) {
        return advisorService.process(query);
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "MediGuide AI", "version", "0.0.1");
    }

    @GetMapping("/triage/levels")
    public Map<String, String> triageLevels() {
        return Map.of(
                "URGENT", "🚨 紧急 — 立即拨打120或前往急诊",
                "CONSULT", "🏥 建议就医 — 24-48小时内就诊",
                "SELF_CARE", "✅ 自我护理 — 居家观察"
        );
    }
}
