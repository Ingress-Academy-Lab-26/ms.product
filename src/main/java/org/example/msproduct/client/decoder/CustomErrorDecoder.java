package org.example.msproduct.client.decoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.example.msproduct.exception.CustomFeignException;

import static org.example.msproduct.client.decoder.JsonNodeFieldName.CODE;
import static org.example.msproduct.client.decoder.JsonNodeFieldName.MESSAGE;
import static org.example.msproduct.constant.ErrorConstants.CLIENT_EXCEPTION;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        String message = CLIENT_EXCEPTION.getMessage();
        String code = CLIENT_EXCEPTION.getCode();
        int status = response.status();

        JsonNode node;
        try (var body = response.body().asInputStream()) {
            node = new ObjectMapper().readValue(body, JsonNode.class);

        } catch (Exception e) {
            throw new CustomFeignException(code, message, status);
        }

        if (node.has(MESSAGE.getValue())) {
            message = node.get(MESSAGE.getValue()).asText();
        }
        if (node.has(CODE.getValue())) {
            code = node.get(CODE.getValue()).asText();
        }

        return new CustomFeignException(code, message, status);
    }
}
