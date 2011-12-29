package com.kevinlawler.twitterbot;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.CounterMetric;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public final class TweetReader {

  private static final Log log = LogFactory.getLog(TweetReader.class);

  private static final CounterMetric pageRequestErrors = Metrics.newCounter(TweetIssuer.class, "page_request_errors");

  public static String readTweet() {
      log.info("Getting new tweet");
      //Retrieve tweet text from webpage
      HttpClient httpclient = new DefaultHttpClient();
      HttpPost httppost = new HttpPost(TweetConfig.TweetProviderAddress);
      HttpResponse response;
      String tweet;

      try {
          List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
          nameValuePairs.add(new BasicNameValuePair("key", TweetConfig.TweetProviderKey));
          httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
          response = httpclient.execute(httppost);
          BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
          tweet = rd.readLine();
          log.info("Got Tweet (Response): " + tweet);
          if("error"==tweet) throw new RuntimeException("Error tweet returned");
      } catch (ClientProtocolException e) {
          pageRequestErrors.inc();
          log.error("Failed to retrieve tweet info page: " + e.toString());
          throw new RuntimeException("Error getting tweet", e);
      } catch (IOException e) {
          pageRequestErrors.inc();
          log.error("Failed to retrieve tweet info page: " + e.toString());
          throw new RuntimeException("Error getting tweet", e);
      }
      return tweet;
  }
}
