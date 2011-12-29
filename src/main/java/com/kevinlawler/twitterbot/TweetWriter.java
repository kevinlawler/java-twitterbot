package com.kevinlawler.twitterbot;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.CounterMetric;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public final class TweetWriter {

  private static final Log log = LogFactory.getLog(TweetWriter.class);
  private static final CounterMetric tweetWriteErrors = Metrics.newCounter(TweetWriter.class, "tweet_write_errors");

  public static void writeTweet(String tweet) {

       ConfigurationBuilder cb = new ConfigurationBuilder();
       cb.setDebugEnabled(true)
         .setOAuthConsumerKey(TweetConfig.OAuthConsumerKey)
         .setOAuthConsumerSecret(TweetConfig.OAuthConsumerSecret)
         .setOAuthAccessToken(TweetConfig.OAuthAccessToken)
         .setOAuthAccessTokenSecret(TweetConfig.OAuthAccessTokenSecret);
       TwitterFactory tf = new TwitterFactory(cb.build());
       Twitter twitter = tf.getInstance();

       try {
            Status status;
            status = twitter.updateStatus(tweet);
            log.info("Successfully updated the status to [" + status.getText() + "].");
        } catch (IllegalStateException e) {
            if (!twitter.getAuthorization().isEnabled()) {
                e.printStackTrace();
                log.error("OAuth not set correctly: " + e.getMessage());
                tweetWriteErrors.inc();
            }
        }
        catch (TwitterException e) {
            e.printStackTrace();
            log.error("Failed to tweet: " + e.getMessage());
            tweetWriteErrors.inc();
        } 
  }
}
