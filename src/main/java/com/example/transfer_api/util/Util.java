package com.example.transfer_api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Component;

@Component
public class Util {

    private final ObjectMapper objectMapper;
    private final ObjectWriter objectWriter;

    public Util(
            final ObjectWriter objectWriter,
            final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectWriter = objectWriter;
    }

    public <T> T parseToType(
            final Object object,
            final Class<T> clazz) {

        T objectType = null;

        try {
            if (object != null) {
                objectType = objectMapper.readValue(objectWriter.writeValueAsString(object), clazz);
            }
        } catch (Exception e) {
            String a = e.toString();
            //log.error("Ocorreu um erro ao realizar parse de um objeto para outro.", e);
        }

        return objectType;
    }
}
