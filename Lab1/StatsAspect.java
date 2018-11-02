package edu.sjsu.cmpe275.aop.aspect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.TweetStatsImpl;

@Aspect
@Order(0)
public class StatsAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */
	
	public static HashMap<String, ArrayList<String>> tweet = new HashMap<String, ArrayList<String>>(); 
	
	public static HashMap<String, ArrayList<String>> follow =new HashMap<String, ArrayList<String>>();
	
	public static HashMap<String, ArrayList<String>> block = new HashMap<String, ArrayList<String>>();
	
	public static HashMap<String, ArrayList<String>> blockkey = new HashMap<String, ArrayList<String>>();
	
	public static int length = 0;
	
	public static String mostpopularmessage = "";
	
	
	@Autowired TweetStatsImpl stats;
	
	@After("execution(public void edu.sjsu.cmpe275.aop.TweetService.tweet(..))")
	public void AfterTweet(JoinPoint joinPoint) {
		
		String user = joinPoint.getArgs()[0].toString();
		String message = joinPoint.getArgs()[1].toString();
		int followlength = 0;
		int blocklength = 0;
		
		   if(message.length() > 140) {
			   try {
				   throw new IllegalArgumentException();
			   } catch (IllegalArgumentException e) {
				   System.out.println("String is more than 140 characters long");
			   }
			} else {
			if(follow.containsKey(user)) {
				followlength = follow.get(user).size();
			}
			if(block.containsKey(user)) {
				ArrayList<String> blockstr = new ArrayList<String>();
				ArrayList<String> followstr = new ArrayList<String>();
				blockstr = block.get(user);
				followstr = follow.get(user);
				blocklength = block.get(user).size();
				for(String str: blockstr) {
					if(followstr.contains(str)) {
						blocklength++;
					}
				}
			}
			if((followlength - blocklength) > length) {
				length = followlength - blocklength;
				mostpopularmessage = message;
			} else if((followlength - blocklength) == length) {
				String key1 = mostpopularmessage;
				String key2 = message;
				int compare = key1.compareTo(key2);
				if(compare < 0) {
					mostpopularmessage = key1;
				} else {
					mostpopularmessage = key2;
				}
			}
		}
		
		// System.out.println("most popular inside : "+mostpopularmessage);
		// System.out.println(joinPoint.getArgs()[1].toString().length());
		// System.out.printf("After the execution of the method %s\n", joinPoint.getSignature().getName());
		//stats.resetStats();
	}
	
	@Before("execution(public void edu.sjsu.cmpe275.aop.TweetService.tweet(..))")
	public void BeforeTweet(JoinPoint joinPoint) {
		
		System.out.printf("Before the execution of the method %s\n", joinPoint.getSignature().getName());
		
		ArrayList<String>  user1 = new ArrayList<String>();
		
		String user = joinPoint.getArgs()[0].toString();
		String message = joinPoint.getArgs()[1].toString();
		
		if(user == null || message==null || message.length() == 0 || message=="") {
			try {
				throw new IllegalArgumentException(); 
			} catch (IllegalArgumentException e) {
				System.out.println(" plase check the parameters");
			}	
		}
		
		user1.add(message);
		if(tweet.containsKey(user)) {
			tweet.get(user).add(message);	
		} 
		else {
			tweet.put(user.toString(), user1);
		}
		// System.out.println(user);
		// System.out.println(tweet.values());
	}
	
	@Before("execution(public void edu.sjsu.cmpe275.aop.TweetService.follow(..))")
	public void BeforeFollow(JoinPoint joinPoint) {
		
		System.out.printf("Before the execution of the method %s\n", joinPoint.getSignature().getName());
		
		ArrayList<String> user1 = new ArrayList<String>();
		
		String follower = joinPoint.getArgs()[0].toString();
		String followee = joinPoint.getArgs()[1].toString();
		
		if(follower == null || followee == null || follower == followee || follower == "" || followee == "" ) 
		{
			try {
				throw new IllegalArgumentException(); 
			} catch (IllegalArgumentException e) {
				System.out.println(" plase check the parameters");
			}	
		}
		
		user1.add(follower);
		if(follow.containsKey(followee)) {
			user1=follow.get(followee);
			follow.get(followee).add(follower);	
			
		} else {
			follow.put(followee, user1);
		}
		//System.out.println(follow);
	}
	
	@Before("execution(public void edu.sjsu.cmpe275.aop.TweetService.block(..))")
	public void BeforeBlock(JoinPoint joinPoint) {
		
		System.out.printf("Before the execution of the method %s\n", joinPoint.getSignature().getName());
		
		ArrayList<String>  user1 = new ArrayList<String>();
		ArrayList<String>  user2 = new ArrayList<String>();
		
		String user = joinPoint.getArgs()[0].toString();
		String follower = joinPoint.getArgs()[1].toString();
		
		if(user == null || follower == null || user == follower || user == "" || follower == "") {
			try {
				throw new IllegalArgumentException(); 
			} catch (IllegalArgumentException e) {
				System.out.println(" plase check the parameters");
			}	
		}
		
		user1.add(follower);
		if(block.containsKey(user)) {
			user1 = block.get(user);
			if (!user1.contains(follower)){
				block.get(user).add(follower);	
			}
		} else {
			block.put(user, user1);
		}
		
		//This is for easy retrieval for most blocked user
		user2.add(user);
		if(blockkey.containsKey(follower)) {
			user2 = blockkey.get(follower);
			if (!user2.contains(user)){
				blockkey.get(follower).add(user);	
			}
		} else {
			blockkey.put(follower, user2);
		}
		
		
		// System.out.println(block);
		// System.out.println(blockkey);
		// System.out.println(follow);
	}
}
