package com.example.lambdareport;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.Month;
import java.util.Iterator;
import java.util.Map;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Log4j2
public class WeeklyCSVReportLambda implements RequestHandler<Object, String> {

    private DynamoDB dynamoDb;

    private static final String DYNAMO_DB_TABLE_NAME = "TrainerMonthlySummary";
    private static final String S3_BUCKET_NAME = "gym-crm-zakhar-zakharchuk";
    private YearMonthSummariesConverter converter = new YearMonthSummariesConverter();

    public String handleRequest(Object input, Context context) {
        AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDB dynamoDBClient = new DynamoDB(dynamoDB);

        AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();

        // Query DynamoDB table
        Table table = dynamoDBClient.getTable(DYNAMO_DB_TABLE_NAME);
        Iterator<Item> items = table.scan().iterator();

        try {
            StringWriter csvReport = new StringWriter();
            csvReport.append("Trainer First Name,Trainer Last Name,Training Duration (minutes)\n");

            while (items.hasNext()) {
                Item item = items.next();
                String firstName = item.getString("firstname");
                String lastName = item.getString("lastname");
                String summaries = item.getString("yearMonthSummaries");
                Map<Integer, Map<Month, Integer>> yearMonthSummaries = new ObjectMapper().readValue(
                      summaries,
                      new TypeReference<>() {
                      });
                int trainingDuration = yearMonthSummaries.get(LocalDate.now().getYear())
                      .get(LocalDate.now().getMonth());

                if (trainingDuration > 0) {
                    csvReport.append(firstName).append(",").append(lastName).append(",")
                          .append(String.valueOf(trainingDuration)).append("\n");
                }
            }

            // Upload the report to S3
            String reportContent = csvReport.toString();
            String reportName = "Trainers_Trainings_summary_" + getYearAndMonth() + ".csv";
            InputStream reportStream = new ByteArrayInputStream(reportContent.getBytes("UTF-8"));
            s3.putObject(new PutObjectRequest(S3_BUCKET_NAME, reportName, reportStream, null));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error generating or uploading the report.");
        }
        log.info("CSV report has been generated and uploaded to S3.");
        return "CSV report has been generated and uploaded to S3.";
    }

    private String getYearAndMonth() {
        return DateTime.now().toString("YYYY_MM");
    }
}
