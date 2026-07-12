package com.agif.credit.service;

import com.agif.credit.enums.VehicleCondition;
import com.agif.credit.enums.VehicleType;
import com.agif.credit.model.Loan;
import com.agif.credit.model.Vehicle;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {

    private static final String API_URL = "https://run.mocky.io/v3/9108b1da-beec-409e-ae14-e212003666c";
    private static final int TIMEOUT_SECONDS = 10;

    private final HttpClient httpClient;
    private final Gson gson;

    public ApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public Loan loadFromApi() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .timeout(java.time.Duration.ofSeconds(TIMEOUT_SECONDS))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("API returned status code: " + response.statusCode());
        }

        JsonObject json = gson.fromJson(response.body(), JsonObject.class);

        String vehicleTypeStr = json.get("vehicleType").getAsString();
        String vehicleConditionStr = json.get("vehicleCondition").getAsString();
        int vehicleYear = json.get("vehicleYear").getAsInt();
        double totalLoanAmount = json.get("totalLoanAmount").getAsDouble();
        int loanTenure = json.get("loanTenure").getAsInt();
        double downPayment = json.get("downPayment").getAsDouble();

        VehicleType vehicleType = VehicleType.fromString(vehicleTypeStr);
        VehicleCondition vehicleCondition = VehicleCondition.fromString(vehicleConditionStr);

        Vehicle vehicle = new Vehicle(vehicleType, vehicleCondition, vehicleYear);

        return new Loan(vehicle, totalLoanAmount, downPayment, loanTenure);
    }
}
