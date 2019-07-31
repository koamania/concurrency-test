package leeda.ga;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MovieBookingServiceWithRedisson {

    private static final String LOCK_PREFIX = "bookMovie:lock:";

    private final RedissonClient redissonClient;

    public MovieBookingServiceWithRedisson(final RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public boolean bookMovie(String userId, int seatNo) {
        Map<Integer, BookingInfo> bookingInfoMap = redissonClient.getMap("booking");
        if (bookingInfoMap.get(seatNo) != null) {
            return false;
        }
        RLock lock = redissonClient.getLock(LOCK_PREFIX + seatNo);

        if (lock.tryLock()) {
            try {
                if (bookingInfoMap.get(seatNo) != null) {
                    return false;
                }
                bookingInfoMap.put(seatNo, new BookingInfo(userId, seatNo));
                return true;

            } finally {
                if (lock.isLocked()) {
                    lock.unlock();
                }
            }
        }

        return false;
    }

    public Map<Integer, BookingInfo> getBoookingInfoMap() {
        return redissonClient.getMap("booking");
    }
}
