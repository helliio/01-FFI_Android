package com.aprilchun.androidtest.util;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chun on 3/4/15.
 */
public class Sender {

    public static String sendSOAPRequest(SoapObject soapObject, String wsdl) {
        try {
            // Create a new SoapSerializationEnvelope instance
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.bodyOut = soapObject;

            List<HeaderProperty> headers = new ArrayList<>();
            HeaderProperty headerProperty;
            headerProperty = new HeaderProperty("Accept-Encoding", "gzip");
            headers.add(headerProperty);
            headerProperty = new HeaderProperty("Content-Encoding", "gzip");
            headers.add(headerProperty);

            HttpTransport ht = new HttpTransport(Constant.SERVICE_URL + "/SOAP/" + wsdl);
            ht.call(null, envelope, headers);

            return envelope.getResponse().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String sendRESTRequest(MultiValueMap requestData, String rest) {
        try {
            String url = Constant.SERVICE_URL + "/REST/" + rest;

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            // Add the gzip Accept-Encoding and Content-Encoding headers
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
            requestHeaders.setContentEncoding(ContentCodingType.GZIP);
            // Support GZIP
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            // Support POST Form
            restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
            // Make the HTTP POST request, marshaling the response to a String
            String result = restTemplate.postForObject(url, requestData, String.class);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

