package com.example.microservice.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import java.time.Month;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@DynamoDBTable(tableName = "TrainerMonthlySummary")
@NoArgsConstructor
@AllArgsConstructor
public class TrainerMonthlySummary {
    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "email")
    private String email;
    @DynamoDBAttribute(attributeName = "firstname")
    private String firstname;
    @DynamoDBAttribute(attributeName = "lastname")
    private String lastname;
    @DynamoDBAttribute(attributeName = "yearMonthSummaries")
    @DynamoDBTypeConverted(converter = YearMonthSummariesConverter.class)
    private Map<Integer, Map<Month, Integer>> yearMonthSummaries;
}

