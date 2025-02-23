package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

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

        // Manually construct the JSON response string without using ObjectMapper.
        String jsonResponse = "{role: \"bob\", text: \"Message from bob\"}";

        return request.createResponseBuilder(HttpStatus.OK)
                      .header("Content-Type", "application/json")
                      .body(jsonResponse)
                      .build();
    }
}