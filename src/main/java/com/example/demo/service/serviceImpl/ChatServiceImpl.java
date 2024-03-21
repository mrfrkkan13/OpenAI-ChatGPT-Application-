package com.example.demo.service.serviceImpl;



import com.example.demo.dto.ChatRequest;
import com.example.demo.dto.ChatResponse;
import com.example.demo.service.ChatService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatServiceImpl implements ChatService {

    @Qualifier("openAiRestTemplate")
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String url;

    public ChatServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String sendMessage(String message) {
        ChatRequest chatRequest = new ChatRequest(model,message);
        ChatResponse chatResponse = restTemplate.postForObject(url,chatRequest, ChatResponse.class);

        if (chatResponse != null) {
            return chatResponse.getChoices().get(0).getMessage().getContent();
        }else {
            throw new NullPointerException("There is no message present...");
        }
    }
}
