package com.kevinlawler.twitterbot;

import java.util.Map;
import java.io.FileReader;
import java.io.IOException;
import org.yaml.snakeyaml.Yaml;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class TweetConfig {

  private static final Log log = LogFactory.getLog(TweetConfig.class);

  private static Map<String, Object> map = null;

  public static String CronString             = null;
  public static String TweetProviderAddress   = null;
  public static String TweetProviderKey       = null;
  public static String OAuthConsumerKey       = null;
  public static String OAuthConsumerSecret    = null;
  public static String OAuthAccessToken       = null;
  public static String OAuthAccessTokenSecret = null;

  public static void loadConfigFromFile() {
      try {
          FileReader fr = new FileReader("config.yml");
          Yaml yaml = new Yaml();
          Map<String, String> map = (Map<String, String>) yaml.load(fr);

          log.info(yaml.dump(map));//Print sensitive data we read from the config file

          //It would be simpler but less Java-like to return map.get values in other parts of the app 
          CronString             = map.get("CronString");
          TweetProviderAddress   = map.get("TweetProviderAddress");
          TweetProviderKey       = map.get("TweetProviderKey");
          OAuthConsumerKey       = map.get("OAuthConsumerKey");
          OAuthConsumerSecret    = map.get("OAuthConsumerSecret");
          OAuthAccessToken       = map.get("OAuthAccessToken");
          OAuthAccessTokenSecret = map.get("OAuthAccessTokenSecret");

      }
      catch(IOException e)
      {
          log.error("Couldn't read config file.");
          System.exit(1);
      }
  }

}
