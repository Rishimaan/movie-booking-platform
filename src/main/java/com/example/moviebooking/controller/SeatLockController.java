package com.example.moviebooking.controller;

import com.example.moviebooking.dto.SeatLockRequest;
import com.example.moviebooking.dto.SeatLockResponse;
import com.example.moviebooking.service.SeatLockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seats")
public class SeatLockController {

    private final SeatLockService seatLockService;

    public SeatLockController(SeatLockService seatLockService) {
        this.seatLockService = seatLockService;
    }

    @PostMapping("/lock")
    public ResponseEntity<SeatLockResponse> lockSeats(
            @RequestBody SeatLockRequest request) {

        SeatLockResponse response =
                seatLockService.lockSeats(
                        request.getShowId(),
                        request.getSeatIds()
                );

        return ResponseEntity.ok(response);
    }
}
