package com.enigma.challengebookingroom;

import com.enigma.challengebookingroom.controller.EmployeeController;
import com.enigma.challengebookingroom.dto.request.EmployeeRequest;
import com.enigma.challengebookingroom.dto.response.CommonResponse;
import com.enigma.challengebookingroom.dto.response.EmployeeResponse;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.service.EmployeeService;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext
public class LoggingIntegrationTest {

    private static final String LOG_FILE_PATH = "/var/logs/myapp/app.log";

    @Autowired
    private EmployeeController employeeController;

    @Setter
    @Getter
    @Autowired
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoggingToFile() throws IOException {
        // Trigger logging by calling a method
        EmployeeRequest request = new EmployeeRequest("1", "John Doe", "Engineering", "1234567890","john.doe@example.com");
        request.setEmployeeName("John Doe");
        request.setDepartment("Engineering");
        request.setPhoneNumber("1234567890");
        request.setCorporateEmail("john.doe@example.com");

        ResponseEntity<CommonResponse<EmployeeResponse>> response = employeeController.createEmployee(request);

        // Verify that the response is successful
        assertTrue(response.getStatusCode().is2xxSuccessful());

        // Check that the log file contains expected log entries
        Path logFilePath = Paths.get(LOG_FILE_PATH);
        List<String> logLines = Files.readAllLines(logFilePath);

        // Check for specific log entry (example)
        boolean logContainsEntry = logLines.stream()
                .anyMatch(line -> line.contains("Entering method: createEmployee"));

        assertTrue(logContainsEntry, "Log file should contain entry for createEmployee");
    }

    @AfterEach
    void tearDown() {
        // Clean up if necessary
    }

}
