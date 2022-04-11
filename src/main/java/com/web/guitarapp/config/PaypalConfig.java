package com.web.guitarapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.api.payments.PaymentHistory;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaypalConfig {

    @Value("${paypal.client.id}")
    private String clientsId;
    @Value("${paypal.client.secret}")
    private String clientsSecret;
    @Value("${paypal.mode}")
    private String modeOfPayment;


    public Map<String, String> paypalSdkConfiguration() {
        Map<String, String> configureMap = new HashMap<>();
        configureMap.put("modeOfPayment", modeOfPayment);
        return configureMap;
    }

    public OAuthTokenCredential oAuthTokenCredential() {
        return new OAuthTokenCredential(clientsId, clientsSecret, paypalSdkConfiguration());
    }

    public APIContext apiContext() throws PayPalRESTException {
        APIContext contexts = new APIContext(oAuthTokenCredential().getAccessToken());
        contexts.setConfigurationMap(paypalSdkConfiguration());
        return contexts;
    }
}
