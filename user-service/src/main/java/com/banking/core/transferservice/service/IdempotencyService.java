package com.banking.core.transferservice.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdempotencyService {
    final private RedisTemplate<String, String> redisTemplate;

    // TTL สำหรับ Idempotency Key (นาที)
    private static final long IDEMPOTENCY_KEY_EXPIRATION = 5;

    /**
     * ตรวจสอบว่ามี Idempotency Key อยู่ใน Redis หรือไม่
     * หากไม่มี จะบันทึกลงใน Redis
     *
     * @param idempotencyKey Key ที่ใช้ตรวจสอบ
     * @return true หาก Key มีอยู่ใน Redis (หมายถึง Request ซ้ำ)
     */
    public boolean isDuplicateRequest(String idempotencyKey) {
        // ตรวจสอบว่ามี Key ใน Redis แล้วหรือไม่
        Boolean exists = redisTemplate.hasKey(idempotencyKey);

        if (exists != null && exists) {
            // Key มีอยู่ใน Redis หมายถึงเป็น Request ซ้ำ
            return true;
        }

        // Key ยังไม่มีใน Redis => บันทึก Key พร้อม TTL
        redisTemplate.opsForValue().set(idempotencyKey, "PROCESSED", IDEMPOTENCY_KEY_EXPIRATION, TimeUnit.MINUTES);

        return false;
    }

    /**
     * บันทึก Key ลง Redis โดยกำหนด TTL
     *
     * @param idempotencyKey Key ที่จะบันทึก
     */
    public void saveIdempotencyKey(String idempotencyKey) {
        redisTemplate.opsForValue().set(idempotencyKey, "PROCESSED", IDEMPOTENCY_KEY_EXPIRATION, TimeUnit.MINUTES);
    }

    /**
     * ตรวจสอบ Key ใน Redis
     *
     * @param idempotencyKey Key ที่ต้องการตรวจสอบ
     * @return true หาก Key มีอยู่ใน Redis
     */
    public boolean checkKeyExists(String idempotencyKey) {
        return redisTemplate.hasKey(idempotencyKey) == Boolean.TRUE;
    }
}
