package com.example.moviebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatLockResponse {

    private String lockId;
    private int expiresInSeconds;
}
