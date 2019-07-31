package leeda.ga.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class EmbeddedRedisConfiguration {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() {
        redisServer = new RedisServer(redisPort);
        redisServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(this::stopRedis));
    }

    @PreDestroy
    private void stopRedis() {
        if (this.redisServer != null && this.redisServer.isActive()) {
            redisServer.stop();
        }
    }


}
