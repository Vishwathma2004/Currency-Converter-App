package com.example.currency_converter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Spinner fromCurrencySpinner, toCurrencySpinner;
    private EditText amountInput;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        fromCurrencySpinner = findViewById(R.id.fromCurrencySpinner);
        toCurrencySpinner = findViewById(R.id.toCurrencySpinner);
        amountInput = findViewById(R.id.amountType);
        convertButton = findViewById(R.id.btn);

        // Define currency options
        String[] currencies = {
                "AED - UAE Dirham", "AFN - Afghan Afghani", "ALL - Albanian Lek", "AMD - Armenian Dram",
                "ANG - Netherlands Antillean Guilder", "AOA - Angolan Kwanza", "ARS - Argentine Peso",
                "AUD - Australian Dollar", "AWG - Aruban Florin", "AZN - Azerbaijani Manat",
                "BAM - Bosnia and Herzegovina Mark", "BBD - Barbados Dollar", "BDT - Bangladeshi Taka",
                "BGN - Bulgarian Lev", "BHD - Bahraini Dinar", "BIF - Burundian Franc", "BMD - Bermudian Dollar",
                "BND - Brunei Dollar", "BOB - Bolivian Boliviano", "BRL - Brazilian Real", "BSD - Bahamian Dollar",
                "BTN - Bhutanese Ngultrum", "BWP - Botswana Pula", "BYN - Belarusian Ruble", "BZD - Belize Dollar",
                "CAD - Canadian Dollar", "CDF - Congolese Franc", "CHF - Swiss Franc", "CLP - Chilean Peso",
                "CNY - Chinese Renminbi", "COP - Colombian Peso", "CRC - Costa Rican Colon", "CUP - Cuban Peso",
                "CVE - Cape Verdean Escudo", "CZK - Czech Koruna", "DJF - Djiboutian Franc", "DKK - Danish Krone",
                "DOP - Dominican Peso", "DZD - Algerian Dinar", "EGP - Egyptian Pound", "ERN - Eritrean Nakfa",
                "ETB - Ethiopian Birr", "EUR - Euro", "FJD - Fiji Dollar", "FKP - Falkland Islands Pound",
                "FOK - Faroese Króna", "GBP - Pound Sterling", "GEL - Georgian Lari", "GGP - Guernsey Pound",
                "GHS - Ghanaian Cedi", "GIP - Gibraltar Pound", "GMD - Gambian Dalasi", "GNF - Guinean Franc",
                "GTQ - Guatemalan Quetzal", "GYD - Guyanese Dollar", "HKD - Hong Kong Dollar", "HNL - Honduran Lempira",
                "HRK - Croatian Kuna", "HTG - Haitian Gourde", "HUF - Hungarian Forint", "IDR - Indonesian Rupiah",
                "ILS - Israeli New Shekel", "IMP - Manx Pound", "INR - Indian Rupee", "IQD - Iraqi Dinar",
                "IRR - Iranian Rial", "ISK - Icelandic Króna", "JEP - Jersey Pound", "JMD - Jamaican Dollar",
                "JOD - Jordanian Dinar", "JPY - Japanese Yen", "KES - Kenyan Shilling", "KGS - Kyrgyzstani Som",
                "KHR - Cambodian Riel", "KID - Kiribati Dollar", "KMF - Comorian Franc", "KRW - South Korean Won",
                "KWD - Kuwaiti Dinar", "KYD - Cayman Islands Dollar", "KZT - Kazakhstani Tenge", "LAK - Lao Kip",
                "LBP - Lebanese Pound", "LKR - Sri Lanka Rupee", "LRD - Liberian Dollar", "LSL - Lesotho Loti",
                "LYD - Libyan Dinar", "MAD - Moroccan Dirham", "MDL - Moldovan Leu", "MGA - Malagasy Ariary",
                "MKD - Macedonian Denar", "MMK - Burmese Kyat", "MNT - Mongolian Tögrög", "MOP - Macanese Pataca",
                "MRU - Mauritanian Ouguiya", "MUR - Mauritian Rupee", "MVR - Maldivian Rufiyaa", "MWK - Malawian Kwacha",
                "MXN - Mexican Peso", "MYR - Malaysian Ringgit", "MZN - Mozambican Metical", "NAD - Namibian Dollar",
                "NGN - Nigerian Naira", "NIO - Nicaraguan Córdoba", "NOK - Norwegian Krone", "NPR - Nepalese Rupee",
                "NZD - New Zealand Dollar", "OMR - Omani Rial", "PAB - Panamanian Balboa", "PEN - Peruvian Sol",
                "PGK - Papua New Guinean Kina", "PHP - Philippine Peso", "PKR - Pakistani Rupee", "PLN - Polish Złoty",
                "PYG - Paraguayan Guaraní", "QAR - Qatari Riyal", "RON - Romanian Leu", "RSD - Serbian Dinar",
                "RUB - Russian Ruble", "RWF - Rwandan Franc", "SAR - Saudi Riyal", "SBD - Solomon Islands Dollar",
                "SCR - Seychellois Rupee", "SDG - Sudanese Pound", "SEK - Swedish Krona", "SGD - Singapore Dollar",
                "SHP - Saint Helena Pound", "SLE - Sierra Leonean Leone", "SOS - Somali Shilling", "SRD - Surinamese Dollar",
                "SSP - South Sudanese Pound", "STN - São Tomé and Príncipe Dobra", "SYP - Syrian Pound", "SZL - Eswatini Lilangeni",
                "THB - Thai Baht", "TJS - Tajikistani Somoni", "TMT - Turkmenistan Manat", "TND - Tunisian Dinar",
                "TOP - Tongan Paʻanga", "TRY - Turkish Lira", "TTD - Trinidad and Tobago Dollar", "TVD - Tuvaluan Dollar",
                "TWD - New Taiwan Dollar", "TZS - Tanzanian Shilling", "UAH - Ukrainian Hryvnia", "UGX - Ugandan Shilling",
                "USD - United States Dollar", "UYU - Uruguayan Peso", "UZS - Uzbekistani So'm", "VES - Venezuelan Bolívar Soberano",
                "VND - Vietnamese Đồng", "VUV - Vanuatu Vatu", "WST - Samoan Tālā", "XAF - Central African CFA Franc",
                "XCD - East Caribbean Dollar", "XDR - Special Drawing Rights", "XOF - West African CFA franc", "XPF - CFP Franc",
                "YER - Yemeni Rial", "ZAR - South African Rand", "ZMW - Zambian Kwacha", "ZWL - Zimbabwean Dollar"
        };

        // Set up ArrayAdapter for spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromCurrencySpinner.setAdapter(adapter);
        toCurrencySpinner.setAdapter(adapter);

        // Handle "Convert" button click
        convertButton.setOnClickListener(v -> {
            String amountText = amountInput.getText().toString().trim();
            if (amountText.isEmpty()) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }
            double amount = Double.parseDouble(amountText);
            String fromCurrency = fromCurrencySpinner.getSelectedItem().toString().split(" - ")[0]; // Extract currency code
            String toCurrency = toCurrencySpinner.getSelectedItem().toString().split(" - ")[0]; // Extract currency code

            // Fetch exchange rates and perform conversion
            fetchExchangeRatesAndConvert(amount, fromCurrency, toCurrency);
        });
    }

    private void fetchExchangeRatesAndConvert(double amount, String fromCurrency, String toCurrency) {
        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://v6.exchangerate-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CurrencyService service = retrofit.create(CurrencyService.class);

        // Use the API key from BuildConfig
        String apiKey = BuildConfig.EXCHANGE_RATE_API_KEY;
        if (apiKey == null || apiKey.isEmpty()) {
            Toast.makeText(this, "API key is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        // Make API call
        service.getExchangeRates(apiKey, fromCurrency)
                .enqueue(new Callback<ExchangeRateResponse>() {
                    @Override
                    public void onResponse(Call<ExchangeRateResponse> call, Response<ExchangeRateResponse> response) {
                        if (response.isSuccessful()) {
                            Map<String, Double> rates = response.body().getConversionRates();
                            Double toRate = rates.get(toCurrency);
                            if (toRate != null) {
                                double convertedAmount = amount * toRate;
                                Toast.makeText(MainActivity.this,
                                        "Converted Amount: " + convertedAmount + " " + toCurrency,
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Invalid currency selection", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to fetch exchange rates", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ExchangeRateResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}