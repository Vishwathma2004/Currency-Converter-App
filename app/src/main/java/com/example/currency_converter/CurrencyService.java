package com.example.currency_converter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.Map;

public interface CurrencyService {
    @GET("v6/{api_key}/latest/{base_currency}")
    Call<ExchangeRateResponse> getExchangeRates(
            @Path("api_key") String apiKey,
            @Path("base_currency") String baseCurrency
    );
}

class ExchangeRateResponse {
    private String result;
    private Map<String, Double> conversion_rates;

    public Map<String, Double> getConversionRates() {
        return conversion_rates;
    }
}