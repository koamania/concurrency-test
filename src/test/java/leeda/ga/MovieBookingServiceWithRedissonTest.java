package leeda.ga;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieBookingServiceWithRedissonTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void bookMovieTest() throws InterruptedException {
        MovieBookingServiceWithRedisson service = new MovieBookingServiceWithRedisson(redissonClient);
        int max = 60;
        ExecutorService executorService = Executors.newFixedThreadPool(max);
        final Random random = new Random();
        AtomicInteger userNo = new AtomicInteger(0);
        for (int i = 0; i < max ; i++) {
            executorService.execute(() -> {

                int seatNo = random.nextInt(max) + 1;
                String userId = "user" + userNo.getAndIncrement();
                boolean success = false;
                System.out.println("start : " + userId);
                try {
                    success = service.bookMovie(userId, seatNo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("user : " + userId + ", seatNo : " + seatNo + ", success? : " + success);
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        service.getBoookingInfoMap().entrySet()
                .forEach(System.out::println);
    }
}