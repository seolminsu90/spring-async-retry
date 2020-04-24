package com.example.demo.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer{
    @Override
    public Executor getAsyncExecutor() {
         ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
         executor.setCorePoolSize(10);                  // init Pool size
         executor.setMaxPoolSize(50);                   // max Pool size
         executor.setQueueCapacity(100);                // wait Queue size
         executor.setThreadNamePrefix("LogAsync-");
         executor.initialize();
         return executor;
    }
}
