package com.puppet.ease_kefu;

import com.puppet.ease_kefu.handler.EaseKefuHandler;
import com.puppet.ease_kefu.handler.EaseKefuRequestHandler;
import com.puppet.ease_kefu.handler.EaseKefuResponseHandler;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * EaseKefuPlugin
 * @author Puppet
 */
public class EaseKefuPlugin implements MethodCallHandler {

  Registrar registrar;
  MethodChannel channel;

  private EaseKefuPlugin(Registrar registrar, MethodChannel channel) {
    this.registrar = registrar;
    this.channel = channel;
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "ease_kefu");
    EaseKefuHandler.setRegistrar(registrar);
    EaseKefuRequestHandler.setRegistrar(registrar);
    EaseKefuResponseHandler.setMethodChannel(channel);
    channel.setMethodCallHandler(new EaseKefuPlugin(registrar, channel));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("initEaseKefu")) {
      EaseKefuHandler.initEaseKefu(call,result);
    } else if (call.method.equals("register")) {
      EaseKefuHandler.register(call,result);
    } else if (call.method.equals("login")) {
      EaseKefuHandler.login(call,result);
    } else if (call.method.equals("loginWithToken")) {
      EaseKefuHandler.loginWithToken(call,result);
    } else if (call.method.equals("loginOut")) {
      EaseKefuHandler.loginOut(call,result);
    } else if (call.method.equals("isLoggedInBefore")) {
      EaseKefuHandler.isLoggedInBefore(call,result);
    } else if (call.method.equals("setServiceImNumber")) {
      EaseKefuHandler.setServiceImNumber(call,result);
    } else if (call.method.equals("addConnectionListener")) {
      EaseKefuHandler.addConnectionListener(call,result);
    } else if (call.method.equals("addMessageListener")) {
      EaseKefuHandler.addMessageListener(call,result);
    } else if (call.method.equals("removeMessageListener")) {
      EaseKefuHandler.removeMessageListener(call,result);
    } else if (call.method.equals("getEnterpriseWelcome")) {
      EaseKefuHandler.getEnterpriseWelcome(call,result);
    } else if (call.method.equals("getCurrentSessionId")) {
      EaseKefuHandler.getCurrentSessionId(call,result);
    } else if (call.method.equals("createTxtSendMessage")) {
      EaseKefuHandler.createTxtSendMessage(call,result);
    } else if (call.method.equals("createExtraTxtSendMessage")) {
      EaseKefuHandler.createExtraTxtSendMessage(call,result);
    } else if (call.method.equals("createVoiceSendMessage")) {
      EaseKefuHandler.createVoiceSendMessage(call,result);
    } else if (call.method.equals("createImageSendMessage")) {
      EaseKefuHandler.createImageSendMessage(call,result);
    } else if (call.method.equals("createLocationSendMessage")) {
      EaseKefuHandler.createLocationSendMessage(call,result);
    } else if (call.method.equals("createFileSendMessage")) {
      EaseKefuHandler.createFileSendMessage(call,result);
    } else if (call.method.equals("createTranferToKefuMessage")) {
      EaseKefuHandler.createTranferToKefuMessage(call,result);
    } else if (call.method.equals("createCmdSendMessage")) {
      EaseKefuHandler.createCmdSendMessage(call,result);
    } else if (call.method.equals("getAllMessages")) {
      EaseKefuHandler.getAllMessages(call,result);
    } else if (call.method.equals("getAllMessagesMore")) {
      EaseKefuHandler.getAllMessagesMore(call,result);
    } else if (call.method.equals("getUnreadMessagesCount")) {
      EaseKefuHandler.getUnreadMessagesCount(call,result);
    } else if (call.method.equals("getAllUnreadMessagesCount")) {
      EaseKefuHandler.getAllUnreadMessagesCount(call,result);
    } else if (call.method.equals("markConversationsAsRead")) {
      EaseKefuHandler.markConversationsAsRead(call,result);
    } else if (call.method.equals("markAllConversationsAsRead")) {
      EaseKefuHandler.markAllConversationsAsRead(call,result);
    } else {
      result.notImplemented();
    }
  }
}
