package com.example.demo.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.exception.TestException;

@Service
public class AsyncService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RetryTemplate retryTemplate;

    // 템플릿 방식.. 좀 번잡
    @Async
    public void test(String a, String b) {
        Random r = new Random();
        retryTemplate.execute(retryContext -> {
            logger.info("[execute] {}", retryContext);
            if (r.nextBoolean()) {
                logger.info("[Error] Exception !");
                throw new TestException("Hi Exception");
            } else {
                logger.info("[Info] Pass");
            }
            return null;
        }, recoveryContext -> {
            logger.info("[Recovery] Ok.");
            return null;
        });
    }

    // annotation 방식
    @Async
    @Retryable(value = { TestException.class }, maxAttempts = 2, backoff = @Backoff(delay = 2000))
    public void test2(String a, String b) {
        Random r = new Random();
        if (r.nextBoolean()) {
            logger.info("[Error] Exception !");
            throw new TestException("Hi Exception");
        } else {
            logger.info("[Info] Pass");
        }
    }

    // annotation 방식의 Recover
    @Recover
    public void test2Recover(TestException e, String a, String b) {
        logger.info("[Recovery] Ok.");
    }
}
