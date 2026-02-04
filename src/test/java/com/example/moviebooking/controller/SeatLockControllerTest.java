package com.example.moviebooking.controller;

import com.example.moviebooking.dto.SeatLockResponse;
import com.example.moviebooking.service.SeatLockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SeatLockController.class)
class SeatLockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeatLockService seatLockService;

    @Test
    void lockSeatsShouldReturn200() throws Exception {

        when(seatLockService.lockSeats(any(), any()))
                .thenReturn(new SeatLockResponse("lock-123", 300));

        mockMvc.perform(
                post("/api/seats/lock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "showId": "SHOW1",
                              "seatIds": ["A1"]
                            }
                        """)
        ).andExpect(status().isOk());
    }
}
