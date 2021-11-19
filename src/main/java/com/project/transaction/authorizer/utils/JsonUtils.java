package com.project.transaction.authorizer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.transaction.authorizer.enums.EOptions;
import org.springframework.stereotype.Service;

@Service
public class JsonUtils {

    public <T> T getSpecificObject(String inputString, EOptions option, Class<T> objectClass) throws JsonProcessingException {
        JsonNode jsonNode = this.getJsonNode(inputString);
        JsonNode accountJsonNode = jsonNode.get(option.getTransactionsOptions());
        return this.getObject(accountJsonNode, objectClass);
    }

    public JsonNode getJsonNode(String inputString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(inputString);
    }

    public <T> T getObject(JsonNode input, Class<T> objectClass) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(input, objectClass);
    }
}

// {"account": {"id": 1, "active-card": true, "available-limit": 100}}
// {"transaction": {"id": 1, "merchant": "Burger King", "amount": 20,"time":"2019-02-13T10:00:00.000Z"}}
// {"transaction": {"id": 1, "merchant": "Habbib's", "amount": 90, "time":"2019-02-13T11:00:00.000Z"}}