package com.example.lambdareport;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Month;
import java.util.Map;

public class YearMonthSummariesConverter implements
      DynamoDBTypeConverter<String, Map<Integer, Map<Month, Integer>>> {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(
          new JavaTimeModule());

    @Override
    public String convert(Map<Integer, Map<Month, Integer>> yearMonthSummaries) {
        try {
            return objectMapper.writeValueAsString(yearMonthSummaries);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert yearMonthSummaries to JSON", e);
        }
    }

    @Override
    public Map<Integer, Map<Month, Integer>> unconvert(String yearMonthSummariesString) {
        try {
            return objectMapper.readValue(yearMonthSummariesString,
                  new TypeReference<>() {
                  });
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to yearMonthSummaries", e);
        }
    }
}
