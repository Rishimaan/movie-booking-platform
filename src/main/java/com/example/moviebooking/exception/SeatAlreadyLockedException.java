package com.example.moviebooking.exception;

public class SeatAlreadyLockedException extends RuntimeException {

    public SeatAlreadyLockedException(String seatId) {
        super("Seat already locked or unavailable: " + seatId);
    }
}
