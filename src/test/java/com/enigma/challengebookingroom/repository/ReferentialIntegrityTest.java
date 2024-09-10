package com.enigma.challengebookingroom.repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enigma.challengebookingroom.constant.ConstantReservationStatus;
import com.enigma.challengebookingroom.entity.Employee;
import com.enigma.challengebookingroom.entity.Equipment;
import com.enigma.challengebookingroom.entity.Reservation;
import com.enigma.challengebookingroom.entity.Room;
import com.enigma.challengebookingroom.service.impl.ReservationServiceImpl;

class ReferentialIntegrityTest {

    private static final Logger logger = LoggerFactory.getLogger(ReferentialIntegrityTest.class);

    @Mock
    private ReservationRepository reservationRepository;

//     @Mock
//     private ReservationService reservationService;  // Mocking the interface instead of injecting

        @InjectMocks
        private ReservationServiceImpl reservationService; // Inject real implementation


    private Reservation reservation;
    private Employee employee;
    private Room room;
    private Equipment equipment;

    

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock objects for referential integrity test
        employee = Employee.builder()
                .employeeId("emp1")
                .employeeName("John Doe")
                .department("IT")
                .corporateEmail("john.doe@company.com")
                .build();

        room = Room.builder()
                .roomId("room1")
                .roomType("Meeting Room")
                .roomCapacity(10)
                .build();

        equipment = Equipment.builder()
                .equipmentId("equip1")
                .equipmentName("Projector")
                .build();

        reservation = Reservation.builder()
                .reservationId("res1")
                .employee(employee)
                .room(room)
                .reserveDate(LocalDate.now())
                .startTime(LocalDate.now().plusDays(1))
                .endTime(LocalDate.now().plusDays(1))
                .reservationStatus(ConstantReservationStatus.PENDING)
                .reservationDescription("Team meeting")
                .equipments(Collections.singletonList(equipment))
                .build();
    }

//     @Test
// void testReferentialIntegrity() {
//     when(reservationRepository.findById("res1")).thenReturn(Optional.of(reservation));

//     Optional<Reservation> foundReservationOpt = reservationRepository.findById("res1");
//     Reservation foundReservation = foundReservationOpt.orElse(null);

//     assert foundReservation != null;
//     assert foundReservation.getEmployee().equals(employee);
//     assert foundReservation.getRoom().equals(room);

//     assert foundReservation.getEquipments().contains(equipment);

//     logger.info("Referential integrity maintained: Reservation contains employee {}, room {}, and equipment {}.",
//             foundReservation.getEmployee().getEmployeeName(),
//             foundReservation.getRoom().getRoomType(),
//             foundReservation.getEquipments().get(0).getEquipmentName());

//     verify(reservationRepository, times(1)).findById("res1");
// }

@Test
void testReferentialIntegrity() {
    // Mock repository behavior
    when(reservationRepository.findById("res1")).thenReturn(Optional.of(reservation));

    // Call the service, which interacts with the repository
    Reservation foundReservation = reservationService.getReservationById("res1");

    // Verify that reservation contains the correct employee and room
    assert foundReservation != null;
    assert foundReservation.getEmployee().equals(employee);
    assert foundReservation.getRoom().equals(room);

    // Verify that equipment is correctly associated with the reservation
    assert foundReservation.getEquipments().contains(equipment);

    // Log referential integrity check
    logger.info("Referential integrity maintained: Reservation contains employee {}, room {}, and equipment {}.",
            foundReservation.getEmployee().getEmployeeName(),
            foundReservation.getRoom().getRoomType(),
            foundReservation.getEquipments().get(0).getEquipmentName());

    // Verify interactions with the repository
    verify(reservationRepository, times(1)).findById("res1");
}

}