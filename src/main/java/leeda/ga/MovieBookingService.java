package leeda.ga;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MovieBookingService {

    private final Map<Integer, BookingInfo> bookingInfoMap = new ConcurrentHashMap<>();

    public boolean bookMovie(String userId, int seatNo) {
        BookingInfo info = new BookingInfo(userId, seatNo);
        BookingInfo bookedInfo = bookingInfoMap.putIfAbsent(seatNo, info);
        return bookedInfo == null;
    }

    public Map<Integer, BookingInfo> getMap() {
        return this.bookingInfoMap;
    }


}
