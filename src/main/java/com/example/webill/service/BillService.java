package com.example.webill.service;

import com.example.webill.models.*;
import com.example.webill.repository.BillLocRepository;
import com.example.webill.repository.BillRepository;
import com.example.webill.repository.SplitBillRepository;
import com.example.webill.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class BillService {

    @Autowired
    private Constants constants;

    @Autowired
    private BillRepository billRepository;

//    @Autowired
//    private BillLocRepository billLocRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SplitBillRepository splitBillRepository;

    @Value("${veryfi.client_id}")
    private String veryfiCliendId;

    @Value("${veryfi.api_key}")
    private String veryfiApikey;

    public int processPayment(ProcessPaymentModel processPaymentModel){
        int response = 0;
        try{
            response = billRepository.processPayment(processPaymentModel.getUsernameFrom(),processPaymentModel.getUsernameTo());
            response = HttpStatus.OK.value();
        }catch (Exception e){
            response = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        return response;
    }

    public void processBillImage(MultipartFile file) throws IOException {
        String clientId = "CLIENT_ID";
        String apiKey = "apikey USERNAME:API_KEY";
        String URL = constants.getVERYFIENVURL() + "api/v7/partner/documents/";
        String filename = "example.png";

        HttpHeaders headers = new HttpHeaders();
        headers.add("CLIENT-ID", clientId);
        headers.add("ACCEPT", "application/json");
        headers.add("AUTHORIZATION", apiKey);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        File imgFile = new File(file.getOriginalFilename());
        OutputStream os = new FileOutputStream(imgFile);
        os.write(file.getBytes());
        os.close();
        //body.add("file", new FileSystemResource(filename));
        //body.add("file",new FileSystemResource());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(URL, requestEntity, String.class);
            System.out.println(response.getBody());
        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
    }

    public VeryfiOCRResponse processBill(OCRBill ocrBill) throws JsonProcessingException {
        String clientId = veryfiCliendId;
        String apiKey = veryfiApikey;
        System.out.println(clientId);
        System.out.println(apiKey);
        String URL = constants.getVERYFIENVURL() + "api/v7/partner/documents/";
        String fileData = "image"+"/"+"png;base64,"+ocrBill.getBase64encodedString();
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

    public List<BillModel> getBillsForUser(String username, String year) {
        String yearParam = "%"+year;
        List<BillModel> bills = new ArrayList<>();
        List<Integer> billids = userRepository.getBillIdsForUser(username);
        if (billids.size() == 0) {
            return bills;
        } else {
            for (Integer billId : billids) {
                BillModel billModel = billRepository.getBillById(billId,yearParam);
                if (billModel == null) continue;
                bills.add(billModel);
            }

            return bills;

        }
    }

    public List<BillModel> getBillsForUserByLoc(String username, String latitude, String longitude, String dateString) {
        String yearParam = "%"+dateString;
        ArrayList<BillModel> bills = new ArrayList<>();
        List<Integer> billids = userRepository.getBillIdsForUser(username);
        if (billids.size() == 0) {
            return bills;
        } else {
            for (Integer billId : billids) {
                BillModel billModel = billRepository.getBillsForUserByLoc(billId,latitude,longitude,yearParam);
                if (billModel == null) continue;
                bills.add(billModel);
            }

            return bills;

        }

    }

    public void splitBill(int billId, SplitBillRequest splitBillRequest){
        String usernameTo = splitBillRequest.getPaid_by();
        Map<String,Double> splitMap = splitBillRequest.getSplitMap();
        String usernameFrom;
        double amount;
        for(Map.Entry<String,Double> splitEntry : splitMap.entrySet()) {
            usernameFrom = splitEntry.getKey();
            amount = splitEntry.getValue();
            splitBillRepository.addBillSplit(billId,usernameFrom,usernameTo,amount);
        }
    }

    public int putSplitBill(SplitBillRequest splitBillRequest){
        String username = splitBillRequest.getUsername();
        String billname = splitBillRequest.getBillname();
        double totalamount = splitBillRequest.getTotalamount();
        String date = splitBillRequest.getDate();
        String paid_by = splitBillRequest.getPaid_by();
        String latitude = splitBillRequest.getLatitude();
        String longitude = splitBillRequest.getLongitude();
//        Map<String,Double> splitMap = splitBillRequest.getSplitMap();
        List<Integer> billId;

        try {
            billRepository.addBill(billname,totalamount,date,paid_by,latitude,longitude);
        } catch (Exception e){
            return constants.getBADREQUEST();
        }

        try {
            billId = billRepository.getLastAddedBillId(billname,totalamount,date,paid_by,latitude,longitude);
        } catch (Exception e){
            return constants.getUSERNOTFOUND();
        }

        try {
            System.out.println("=======================BILL ID===================="+billId.get(0));
            splitBill(billId.get(0), splitBillRequest);
        } catch (Exception e){
            return constants.getBADREQUEST();
        }
        return constants.getSUCCESS();
    }
}
