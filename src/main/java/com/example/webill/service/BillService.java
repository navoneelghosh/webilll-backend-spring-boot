package com.example.webill.service;

import com.example.webill.models.Constants;
import com.example.webill.models.OCRBill;
import com.example.webill.models.VeryfiOCRResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.transaction.Transactional;
import java.util.Arrays;
import org.json.simple.JSONObject;

@Service
@Transactional
public class BillService {

    @Autowired
    private Constants constants;

    @Value("${veryfi.client_id}")
    private String veryfiCliendId;

    @Value("${veryfi.api_key}")
    private String veryfiApikey;

    public VeryfiOCRResponse processBill(OCRBill ocrBill) throws JsonProcessingException {
        String clientId = veryfiCliendId;
        String apiKey = veryfiApikey;
        System.out.println(clientId);
        System.out.println(apiKey);
        String URL = constants.getVERYFIENVURL() + "api/v7/partner/documents/";
        String fileData = ocrBill.getBase64encodedString();
        String fileName = ocrBill.getBillName();
        RestTemplate restTemplate = new RestTemplate();
        JSONObject requestBody = new JSONObject();
        requestBody.put("file_name", fileName);
        requestBody.put("file_data", fileData);
        requestBody.put("categories", Arrays.asList("Meals & Entertainment", "Grocery", "Utilities"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("CLIENT-ID", clientId);
        headers.add("AUTHORIZATION", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        String ocrResponse = restTemplate.postForObject(URL,entity,String.class);
        String str = ocrResponse.replace("\\","");

        VeryfiOCRResponse veryfiOCRResponse = new ObjectMapper().readValue(str,VeryfiOCRResponse.class);
        return veryfiOCRResponse;
    }
}