package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;
import java.util.Random;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.TweetStatsImpl;

import org.aspectj.lang.annotation.Around;

@Aspect
@Order(1)
public class RetryAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     * @throws Throwable 
     */
	
	static int temp =0;
	
	@Around("execution(public void edu.sjsu.cmpe275.aop.TweetService.*(..))")
	public void tweetAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
	System.out.printf("Prior to the execution of the method %s\n", joinPoint.getSignature().getName());
		Object result = null;
		try {
			result = joinPoint.proceed();
			System.out.printf("Finished the execution of the method %s with result %s\n", joinPoint.getSignature().getName(), result);
		} catch (Throwable e) {
			temp++;
			if(temp<=3) {
				System.out.println("Failed due to netwrok failure");
				tweetAdvice(joinPoint);
			} else {
				throw new IOException("Failed more than 3 times");
			}
			e.printStackTrace();
			System.out.printf("After Failing more than 3 times %s\n", joinPoint.getSignature().getName());
		}	
	}
}
