package leeda.ga;

import leeda.ga.MovieBookingService;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MovieBookingServiceTest {
    @Test
    public void bookMovieTest() throws InterruptedException {
        MovieBookingService service = new MovieBookingService();
        int max = 60;
        ExecutorService executorService = Executors.newFixedThreadPool(max);
        final Random random = new Random();
        AtomicInteger userNo = new AtomicInteger(0);
        for (int i = 0; i < max ; i++) {
            executorService.execute(() -> {

                int seatNo = random.nextInt(max) + 1;
                String userId = "user" + userNo.getAndIncrement();
                boolean success = service.bookMovie(userId, seatNo);
                System.out.println("user : " + userId + ", seatNo : " + seatNo + ", success? : " + success);
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(service.getMap());
    }
}