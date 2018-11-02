package edu.sjsu.cmpe275.aop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.sjsu.cmpe275.aop.aspect.StatsAspect;

public class TweetStatsImpl implements TweetStats {
    /***
     * Following is a dummy implementation.
     * You are expected to provide an actual implementation based on the requirements.
     */

	StatsAspect stats = new StatsAspect();
	
	int lengthoflongesttweet;
	
	public HashMap<String, ArrayList<String>> followstats =new HashMap<String, ArrayList<String>>();

	public HashMap<String, ArrayList<String>> tweetstats = new HashMap<String, ArrayList<String>>();
	
	public HashMap<String, ArrayList<String>> blockstats = new HashMap<String, ArrayList<String>>();
	
	@Override
	public void resetStatsAndSystem() {
		
		lengthoflongesttweet = 0;
		followstats = stats.follow;
		blockstats = stats.block;
		blockstats = new HashMap<String, ArrayList<String>>();
		followstats = new HashMap<String, ArrayList<String>>();
		tweetstats = new HashMap<String, ArrayList<String>>();
	}
    
	
	@Override
	public int getLengthOfLongestTweet() {
		
		tweetstats = stats.tweet;
		String prvmsg = "";
		
		for(Map.Entry<String, ArrayList<String>> entry: tweetstats.entrySet()) {
			for(String message : entry.getValue()) {
				if(message.length() == 0) return 0;
				else if(message.length() > prvmsg.length()) {
					prvmsg = message;
				} 
			}
		}
		return prvmsg.length();
	}

	
	@Override
	public String getMostFollowedUser() {
		
		followstats = stats.follow;
		blockstats = stats.block;
		String mostfolloweduser = "";
		ArrayList<String> list  = new ArrayList<String>();
		int listsize = list.size();
		
		for(Map.Entry<String, ArrayList<String>> entry: followstats.entrySet()) {
			if(followstats.isEmpty()) return null;
			if(entry.getValue().size() > listsize) {
				mostfolloweduser = entry.getKey();
				listsize = entry.getValue().size();
			} else if(entry.getValue().size() == listsize) {
				String key1 = mostfolloweduser;
				String key2 = entry.getKey();
				System.out.println(key1 + key2);
				int compare = key1.compareTo(key2);
				if(compare < 0) {
					mostfolloweduser = key1;
				} else {
					mostfolloweduser = key2;
				}
			}
			// System.out.println(entry.getValue());
			// System.out.println(entry.getValue().size() + entry.getKey());
		}
		
		return mostfolloweduser;
	}

	
	@Override
	public String getMostPopularMessage() {
		
		if(stats.mostpopularmessage.length() == 0) return null;
		
		return stats.mostpopularmessage;
	}
	
	
	@Override
	public String getMostProductiveUser() {
		
		String mostproductiveuser = "";
		tweetstats = stats.tweet;
		String prv = "";
		if(tweetstats.isEmpty()) return null;
		
		for(Map.Entry<String, ArrayList<String>> entry: tweetstats.entrySet()) {
			
			StringBuilder sb = new StringBuilder();
			for (String s : entry.getValue())
			{
			    sb.append(s);
			    if(s.length() == 0) return null;
			    else if(s.length() > prv.length()) {
			    	mostproductiveuser = entry.getKey();
			    	prv = s;
			    } else if(s.length() == prv.length()) {
			    	String key1 = mostproductiveuser;
					String key2 = entry.getKey();
					System.out.println(key1 + key2);
					int compare = key1.compareTo(key2);
					if(compare < 0) {
						mostproductiveuser = key1;
					} else {
						mostproductiveuser = key2;
					}
			    }   
			}
			// System.out.println(entry.getKey());
			// System.out.println(sb.toString());
			// System.out.println(sb.toString().length());
		}
		return mostproductiveuser;
	}

	
	@Override
	public String getMostBlockedFollower() {
		
		blockstats = stats.blockkey;
		String mostblockeduser = "";
		ArrayList<String> list  = new ArrayList<String>();
		int listsize = list.size();
		if(blockstats.isEmpty()) return null;
		
		for(Map.Entry<String, ArrayList<String>> entry: blockstats.entrySet()) {
			if(entry.getValue().size() > listsize) {
				mostblockeduser = entry.getKey();
				listsize = entry.getValue().size();
			} else if(entry.getValue().size() == listsize) {
				String key1 = mostblockeduser;
				String key2 = entry.getKey();
				System.out.println(key1 + key2);
				int compare = key1.compareTo(key2);
				if(compare < 0) {
					mostblockeduser = key1;
				} else {
					mostblockeduser = key2;
				}
			} 
			// System.out.println(entry.getValue());
			// System.out.println(entry.getValue().size() + entry.getKey());
		}
		return mostblockeduser;
	}
}



