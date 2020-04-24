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

/***
 * 재시도 및 회복 기능 샘플
 * XXException 발생 시, n번의 재 시도, n번 이후는 회복 처리 또는 최종에러처리
 * 
 * 1. 공통 템플릿 RetryTemplate방식
 * 익명 클래스 기반으로 한큐에 처리하는 방식이 보통이라 읽기에 번잡함
 * 프로그래밍적 처리가 쉬울 것 같음
 * 
 * 2. Annotaion 기반의 방식
 * 기능마다 값을 따로따로 적용해줄때 적합해보임
 *
 */
@Service
public class AsyncService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RetryTemplate retryTemplate;

    // 1. 공통 템플릿 RetryTemplate방식
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

    // 2. Annotaion 기반의 방식
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

    // 2. Annotaion 기반의 방식의 Recover 
    @Recover
    public void test2Recover(TestException e, String a, String b) {
        logger.info("[Recovery] Ok.");
    }
}
