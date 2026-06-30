package com.agentstack.mediguide.controller;

import com.agentstack.mediguide.agent.AgentService;
import com.agentstack.mediguide.dto.AgentRequest;
import com.agentstack.mediguide.dto.AgentResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent")
public class AgentController {
    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/ask")
    public AgentResponse ask(@RequestBody AgentRequest request) {
        return agentService.ask(request);
    }
}
