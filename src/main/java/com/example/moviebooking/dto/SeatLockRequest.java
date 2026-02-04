package com.example.moviebooking.dto;

import lombok.Data;

import java.util.List;

@Data
public class SeatLockRequest {

    private String showId;
    private List<String> seatIds;
}
