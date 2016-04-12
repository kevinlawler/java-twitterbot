(Warning: Twitter has since abandoned the authentication method this app uses. At a minimum, that portion would need to be replaced.)

Java Twitterbot
====================

A cron-like Twitterbot with a web interface for monitoring. Built using java-webapp-archetype (http://github.com/smanek/java-webapp-archetype).

This bot runs as a webservice. At the times specified in the cron string it queries a URL for text and then tweets that text. You could easily change the TweetReader to read from some other source besides a URL, for instance a plain text file or a database.

To use
-------

1. Put your data in the config file

         config.yml

2. Start the server

         $ mvn tomcat:run
  
3. Check that it works

         $  curl "http://localhost:8080/twitterbot"
