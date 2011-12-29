package com.kevinlawler.twitterbot;

import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;


public class TweetIssuer {

  private static final TweetIssuer INSTANCE = new TweetIssuer();

  private static final Log log = LogFactory.getLog(TweetIssuer.class);

  public static TweetIssuer getInstance() {
    return INSTANCE;
  }

  private final AtomicBoolean started = new AtomicBoolean(false);
  private final ThreadPoolTaskScheduler taskScheduler;

  private TweetIssuer() {
    this.taskScheduler = new ThreadPoolTaskScheduler();
    this.taskScheduler.initialize();
  }

  public void start() {
    log.info("Starting the TweetIssuer");
    boolean alreadyStarted = started.getAndSet(true);
    if (alreadyStarted) {
      log.error("The TweetIssuer has already been started");
    } else {
      taskScheduler.schedule(getTweetJob(), new CronTrigger(TweetConfig.CronString));
    }

  }

  private Runnable getTweetJob() {
    return new Runnable() {
      @Override
      public void run() {

        //Read tweet from external webservice
        String tweet = TweetReader.readTweet();
        //Post tweet to Twitter
        TweetWriter.writeTweet(tweet);

      }
    };
  }

}
