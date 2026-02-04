package com.example.moviebooking.service;

import com.example.moviebooking.dto.SeatLockResponse;
import com.example.moviebooking.exception.SeatAlreadyLockedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class SeatLockServiceTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private SeatLockService seatLockService;

    @BeforeEach
    void setup() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void shouldLockSeatsSuccessfully() {
        when(valueOperations.setIfAbsent(any(), any(), anyLong(), any()))
                .thenReturn(true);

        SeatLockResponse response =
                seatLockService.lockSeats("SHOW1", List.of("A1", "A2"));

        assertNotNull(response.getLockId());
        assertEquals(300, response.getExpiresInSeconds());
    }

    @Test
    void shouldFailWhenSeatAlreadyLocked() {
        when(valueOperations.setIfAbsent(any(), any(), anyLong(), any()))
                .thenReturn(false);

        assertThrows(SeatAlreadyLockedException.class, () ->
                seatLockService.lockSeats("SHOW1", List.of("A1"))
        );
    }
}
