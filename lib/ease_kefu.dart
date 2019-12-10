import 'dart:async';

import 'package:ease_kefu/model/ListMessage.dart';
import 'package:flutter/services.dart';

import 'model/LoginState.dart';

enum TYPE {
  TXT, IMAGE, VIDEO, LOCATION, VOICE, FILE, CMD,
}

class EaseKefu {
  static MethodChannel _channel =
      const MethodChannel('ease_kefu')..setMethodCallHandler(_handler);

  static Future<String> get platformVersion async {
//    final String version = await _channel.invokeMethod('getPlatformVersion');
    return "0";
  }

  /// -----------------------------方法调用-------------------------------------

  /// 初始化
  static Future<bool> initEaseKefu(String kAppKey, String kTenantId, {
    bool debug : false,
  }) async {
    bool result = await _channel.invokeMethod("initEaseKefu",{
      "kAppKey" : kAppKey,
      "kTenantId" : kTenantId,
      "debug" : debug,
    });
    return result;
  }

  /// 注册（仅用来测试）
  static Future register(String username, String password) async {
    _channel.invokeMethod("register",{
      "username" : username,
      "password" : password,
    });
  }

  /// 登录（二选一）
  static Future login(String username, String password) async {
    _channel.invokeMethod("login",{
      "username" : username,
      "password" : password,
    });
  }

  /// 用token登录
  static Future loginWithToken(String username, String token) async {
    _channel.invokeMethod("loginWithToken",{
      "username" : username,
      "token" : token,
    });
  }

  /// 登出
  static Future loginOut() async {
    _channel.invokeMethod("loginOut",{});
  }

  /// 获取登录状态
  /// 建议进入会话前判断一次
  static Future<bool> get isLoggedInBefore async {
    bool result = await _channel.invokeMethod("isLoggedInBefore",{});
    return result;
  }

  /// 添加网络监听
  static Future addConnectionListener() async {
    _channel.invokeMethod("addConnectionListener",{});
  }

  /// 添加消息监听
  static Future addMessageListener() async {
    _channel.invokeMethod("addMessageListener",{});
  }

  /// 移除消息监听
  static Future removeMessageListener() async {
    _channel.invokeMethod("removeMessageListener",{});
  }

  /// 发送文本消息
  static Future<String> createTxtSendMessage(String content, String toChatUsername) async {
    String result = await _channel.invokeMethod("createTxtSendMessage",{
      "content" : content,
      "toChatUsername" : toChatUsername,
    });
    return result;
  }

  /// 发送扩展消息
  static Future<String> createExtraTxtSendMessage(String content, String toChatUsername,String extraString) async {
    String result = await _channel.invokeMethod("createExtraTxtSendMessage",{
      "content" : content,
      "toChatUsername" : toChatUsername,
      "extraString" : extraString,
    });
    return result;
  }

  /// 发送语音消息
  static Future<String> createVoiceSendMessage(String filePath, int length,String toChatUsername) async {
    String result = await  _channel.invokeMethod("createVoiceSendMessage",{
      "filePath" : filePath,
      "length" : length,
      "toChatUsername" : toChatUsername,
    });
    return result;
  }

  /// 发送图片消息
  static Future<String> createImageSendMessage(String filePath, bool originallImage,String toChatUsername) async {
    String result = await _channel.invokeMethod("createImageSendMessage",{
      "filePath" : filePath,
      "originallImage" : originallImage,
      "toChatUsername" : toChatUsername,
    });
    return result;
  }

  /// 发送地理消息
  static Future<String> createLocationSendMessage(double latitude, double longitude,String locAddress,String toChatUsername) async {
    String result = await _channel.invokeMethod("createLocationSendMessage",{
      "latitude" : latitude,
      "longitude" : longitude,
      "locAddress" : locAddress,
      "toChatUsername" : toChatUsername,
    });
    return result;
  }

  /// 发送文件消息
  static Future<String> createFileSendMessage(String filePath,String toChatUsername) async {
    String result = await _channel.invokeMethod("createFileSendMessage",{
      "filePath" : filePath,
      "toChatUsername" : toChatUsername,
    });
    return result;
  }

  /// 发送转接客服消息
  static Future createTranferToKefuMessage(String toUserName) async {
    String result = await _channel.invokeMethod("createTranferToKefuMessage",{
      "toUserName" : toUserName,
    });
    return result;
  }

  /// 发送透传消息
  static Future createCmdSendMessage(String action,String toChatUsername) async {
    String result = await _channel.invokeMethod("createCmdSendMessage",{
      "action" : action,
      "toChatUsername" : toChatUsername,
    });
    return result;
  }

  /// 获取聊天记录
  static Future<ListMessage> getAllMessages(String toChatUsername) async {
    var result = await _channel.invokeMethod("getAllMessages",{
      "toChatUsername" : toChatUsername,
    });
    return ListMessage.fromList(result);
  }

  /// 获取更多聊天记录
  static Future<ListMessage> getAllMessagesMore(String startMsgId,String toChatUsername) async {
    var result = await _channel.invokeMethod("getAllMessagesMore",{
      "startMsgId" : startMsgId,
      "toChatUsername" : toChatUsername,
    });
    return ListMessage.fromList(result);
  }

  /// 获取某个会话未读消息数量
  static Future<int> getUnreadMessagesCount(String toChatUsername) async {
    var result = await _channel.invokeMethod("getUnreadMessagesCount",{
      "toChatUsername" : toChatUsername,
    });
    return result;
  }

  /// 获取未读消息数量
  static Future<int> getAllUnreadMessagesCount() async {
    var result = await _channel.invokeMethod("getAllUnreadMessagesCount",{
    });
    return result;
  }

  /// 某个会话未读消息数清零
  static Future markConversationsAsRead(String toChatUsername) async {
    await _channel.invokeMethod("markConversationsAsRead",{
      "toChatUsername" : toChatUsername,
    });
  }

  /// 所有未读消息数清零
  static Future markAllConversationsAsRead() async {
    await _channel.invokeMethod("markAllConversationsAsRead",{
    });
  }

  ///------------------------------------监听-----------------------------------
  ///

  /// 登录监听
  static StreamController<LoginState> _onLoginProcess = new StreamController.broadcast();

  static Stream<LoginState> get responseFromLoginProcess => _onLoginProcess.stream;

  /// 退出监听
  static StreamController<LoginState> _onLoginOutProcess = new StreamController.broadcast();

  static Stream<LoginState> get responseFromLoginOutProcess => _onLoginOutProcess.stream;

  /// 消息监听
  static StreamController<ListMessage> _onKfMsgListener = new StreamController.broadcast();

  static Stream<ListMessage> get responseFromKfMsgListener => _onKfMsgListener.stream;

  /// CMD消息监听
  static StreamController<ListMessage> _onKfCmdMsgListener = new StreamController.broadcast();

  static Stream<ListMessage> get responseFromKfCmdMsgListener => _onKfCmdMsgListener.stream;

  static Future<dynamic> _handler(MethodCall methodCall) {
    if ("onLoginProcess" == methodCall.method) {
      _onLoginProcess.add(LoginState.fromMap(methodCall.arguments));
    } else if ("onLoginOutProcess" == methodCall.method) {
      _onLoginOutProcess.add(LoginState.fromMap(methodCall.arguments));
    } else if ("kefuMsgForListener" == methodCall.method) {
      _onKfMsgListener.add(ListMessage.fromList(methodCall.arguments));
     }else if ("kefuCmdMsgForListener" == methodCall.method) {
      _onKfCmdMsgListener.add(ListMessage.fromList(methodCall.arguments));
    }
    return Future.value(true);
  }
}
