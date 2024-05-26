package com.taleweaver.services;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service

public class CallPythonService {
    private String flaskApiUrl = "http://localhost:5000/generate-story";

    public String generateStory(String input_prompt) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) { // Use try-with-resources for automatic closing

            // Create HTTP POST request
            HttpPost request = new HttpPost(flaskApiUrl);

            // Set request headers
            request.setHeader("Content-Type", "application/json");

            // Set request body
            String requestBody = "{\"input_prompt\": \"" + input_prompt + "\"}";

            request.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

            // Execute the request
            HttpResponse response = httpClient.execute(request);

            // Extract response content
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);

            // Parse JSON response
            JSONObject responseJson = new JSONObject(responseString);

            // Extract and return generated story
            String generatedStory = responseJson.getString("generated_story");
            System.out.println("Story Returned successfully");
            return generatedStory;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while generating story";
        }
    }
}
