package leeda.ga;

import java.io.Serializable;

public class BookingInfo implements Serializable {

    private static final long serialVersionUID = -5906294628124794643L;

    private String userId;
        private int seatNo;

        public BookingInfo(final String userId, final int seatNo) {
            this.userId = userId;
            this.seatNo = seatNo;
        }

        @Override
        public String toString() {
            return "BookingInfo{" +
                    "userId='" + userId + '\'' +
                    ", seatNo=" + seatNo +
                    '}';
        }
    }