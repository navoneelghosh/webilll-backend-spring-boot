package com.example.webill.service;

import com.example.webill.models.*;
import com.example.webill.repository.BillLocRepository;
import com.example.webill.repository.BillRepository;
import com.example.webill.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;

@Service
@Transactional
public class BillService {

    @Autowired
    private Constants constants;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillLocRepository billLocRepository;

    @Autowired
    private UserRepository userRepository;

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

        String ocrResponse = restTemplate.postForObject(URL, entity, String.class);
        String str = ocrResponse.replace("\\", "");

        VeryfiOCRResponse veryfiOCRResponse = new ObjectMapper().readValue(str, VeryfiOCRResponse.class);
        return veryfiOCRResponse;
    }

    public List<BillModel> getBillsForUser(String username) {
        List<BillModel> bills = new ArrayList<>();
        List<Integer> billids = userRepository.getBillIdsForUser(username);
        if (billids.size() == 0) {
            return bills;
        } else {
            for (Integer billId : billids) {
                BillModel billModel = billRepository.getBillById(billId);
                if (billModel == null) continue;
                bills.add(billModel);
            }

            return bills;

        }
    }

    public List<Bills_Prod> getBillsForUserByLoc(String username, String latitude, String longitude, String dateString) {

        ArrayList<Bills_Prod> bills = new ArrayList<>();
        List<Integer> billids = userRepository.getBillIdsForUser(username);
        if (billids.size() == 0) {
            return bills;
        } else {
            for (Integer billId : billids) {
                Bills_Prod billModel = billLocRepository.getBillsForUserByLoc(billId,latitude,longitude);
                if (billModel == null) continue;
                bills.add(billModel);
            }

            return bills;

        }

    }
}
