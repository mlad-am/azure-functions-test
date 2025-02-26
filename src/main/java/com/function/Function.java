package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class Function {

    /**
     * This function listens at endpoint "/api/GiftMessageFunction" and accepts POST requests.
     * It ignores the request body and always returns a JSON response with a message "ОК".
     */
    @FunctionName("MessageFunctionTest")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        
        context.getLogger().info("Processing GiftMessageFunction request.");

        // Retrieve the request body (ignored for this implementation)
        String requestBody = request.getBody().orElse("");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        String text;
        try {
            rootNode = objectMapper.readTree(requestBody);

            // Извличане на текста от първия елемент в "messages"
            text = rootNode.get("messages").get(0).get("text").asText();
        } catch (JsonProcessingException e) {
            context.getLogger().severe("Failed to parse JSON: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                          .body("Invalid JSON format")
                          .build();
        }

        // Manually construct the JSON response string without using ObjectMapper.
        String jsonResponse = "{role: \"ai\", text: \" Test 5 " + text +"\"}";

        return request.createResponseBuilder(HttpStatus.OK)
                      .header("Content-Type", "application/json")
                      .body(jsonResponse)
                      .build();
    }
}