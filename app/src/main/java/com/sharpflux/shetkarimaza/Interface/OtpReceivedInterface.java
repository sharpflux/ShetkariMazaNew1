package com.sharpflux.shetkarimaza.Interface;


public interface OtpReceivedInterface {
  void onOtpReceived(String otp);
  void onOtpTimeout();
}
