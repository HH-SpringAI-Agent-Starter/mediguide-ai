package com.agentstack.mediguide.agent;

import com.agentstack.mediguide.dto.AgentRequest;
import com.agentstack.mediguide.dto.AgentResponse;
import com.agentstack.mediguide.tools.DomainTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgentServiceTest {

    @Mock
    private ChatClient chatClient;

    @Mock
    private ChatClient.ChatClientRequestSpec requestSpec;

    @Mock
    private ChatClient.CallResponseSpec responseSpec;

    @Mock
    private DomainTools domainTools;

    private AgentService agentService;

    @BeforeEach
    void setUp() {
        when(chatClient.prompt()).thenReturn(requestSpec);
        when(requestSpec.user(any(String.class))).thenReturn(requestSpec);
        when(requestSpec.tools(any())).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.content()).thenReturn("根据您的症状描述，建议尽快就医。");

        agentService = new AgentService(chatClient, domainTools);
    }

    @Test
    void ask_returnsStructuredResponse() {
        AgentRequest request = new AgentRequest(
                "头痛发热三天", "user-1", "session-1", "demo", Map.of()
        );
        AgentResponse response = agentService.ask(request);
        assertNotNull(response);
        assertNotNull(response.answer());
        assertFalse(response.answer().isEmpty());
        assertNotNull(response.traceId());
    }

    @Test
    void ask_invokesChatClient() {
        AgentRequest request = new AgentRequest(
                "头痛", "user-2", "session-2", "demo", Map.of()
        );
        agentService.ask(request);
        verify(chatClient, times(1)).prompt();
        verify(requestSpec, times(1)).call();
    }
}
