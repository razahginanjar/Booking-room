package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.constant.APIUrl;
import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.dto.request.MailSenderRequest;
import com.enigma.challengebookingroom.service.EmployeeService;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MailSenderService {
    String API_PUBLIC_KEY;

    String API_SECRET_KEY;

    String EMAIL_SENDER;

    String URL_SERVER;

    String EMAIL_ADMIN;

    EmployeeService employeeService;

    public MailSenderService(@Value("${challengebookingroom.API_KEY_PUBLIC}")String API_PUBLIC_KEY,
                             @Value("${challengebookingroom.API_KEY_SECRET}")String API_SECRET_KEY,
                             @Value("${challengebookingroom.API_MAIL_SENDER}") String EMAIL_SENDER,
                             @Value("${challengebookingroom.API_MAIL_ADMIN}") String EMAIL_ADMIN,
                             @Value("${challengebookingroom.API_URL_SERVER}")String URL_SERER,
                             EmployeeService employeeService) {
        this.API_PUBLIC_KEY = API_PUBLIC_KEY;
        this.API_SECRET_KEY = API_SECRET_KEY;
        this.EMAIL_SENDER = EMAIL_SENDER;
        this.EMAIL_ADMIN = EMAIL_ADMIN;
        this.URL_SERVER =URL_SERER;
        this.employeeService = employeeService;
    }

    public String create(MailSenderRequest mailSenderRequest) throws MailjetException {
        MailjetRequest request;
        MailjetResponse response;

        ClientOptions options = ClientOptions.builder()
                .apiKey(API_PUBLIC_KEY)
                .apiSecretKey(API_SECRET_KEY)
                .build();

        MailjetClient client = new MailjetClient(options);
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", EMAIL_SENDER)
                                        .put("Name", "bot: ROOM_BOOKING"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", EMAIL_ADMIN)
                                                .put("Name", "GA")))
                                .put(Emailv31.Message.TEMPLATEID, 6271822)
                                .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                                .put(Emailv31.Message.VARIABLES, Map.of("employee_name", mailSenderRequest.getEmployeeName(),
                                        "room_type", mailSenderRequest.getRoomType(),
                                        "start_date", mailSenderRequest.getStartDate(),
                                        "end_date", mailSenderRequest.getEndDate(),
                                        "url_accept", URL_SERVER+ APIUrl.RESERVATION+APIUrl.PATH_STATUS +"/"+mailSenderRequest.getIdReservation()+"?action="+ ConstantReservationStatus.APPROVED,
                                "url_decline", URL_SERVER+ APIUrl.RESERVATION+APIUrl.PATH_STATUS +"/"+mailSenderRequest.getIdReservation()+"?action="+ ConstantReservationStatus.DECLINED))
                                .put(Emailv31.Message.SUBJECT, "Booking Request!")

                        ));

        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
        return null;
    }
}
