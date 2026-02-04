package com.example.moviebooking.service;

import com.example.moviebooking.dto.SeatLockResponse;
import com.example.moviebooking.exception.SeatAlreadyLockedException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SeatLockService {

    private static final int LOCK_TTL_SECONDS = 300;

    private final RedisTemplate<String, String> redisTemplate;

    public SeatLockService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public SeatLockResponse lockSeats(String showId, List<String> seatIds) {

        String lockId = UUID.randomUUID().toString();

        for (String seatId : seatIds) {
            String redisKey = buildRedisKey(showId, seatId);

            Boolean locked = redisTemplate.opsForValue()
                    .setIfAbsent(redisKey, lockId, LOCK_TTL_SECONDS, TimeUnit.SECONDS);

            if (Boolean.FALSE.equals(locked)) {
                throw new SeatAlreadyLockedException(seatId);
            }
        }

        return new SeatLockResponse(lockId, LOCK_TTL_SECONDS);
    }

    private String buildRedisKey(String showId, String seatId) {
        return "seat_lock:" + showId + ":" + seatId;
    }
}
