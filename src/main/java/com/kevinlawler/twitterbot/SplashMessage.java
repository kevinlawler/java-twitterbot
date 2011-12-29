package com.kevinlawler.twitterbot;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SplashMessage {

  public String param;

  public SplashMessage() {
  }

  public SplashMessage(String param) {
    this.param = param;
  }
}
