
import '../ease_kefu.dart';

/// 消息实体
/// @author puppet
class Message {
  final TYPE type;
  final String body;
  /// 类型为图片时原图地址
  final String image;
  /// 类型为语音时的语音时长（秒）
  final int soundLength;
  final String msgId;
  final String fromUser;
  final String toUser;
  final int time;
  final String error;
  final String extra;

  Message.fromMap(Map map)
      : type = getType(map["type"]),
        body = map["body"],
        image = map["image"],
        soundLength = map["soundLength"],
        msgId = map["msgId"],
        fromUser = map["fromUser"],
        time = map["time"],
        error = map["error"],
        extra = map["extra"],
        toUser = map["toUser"];

}

/// 消息类型：文本，图片，视频，位置，语音，文件,透传消息
TYPE getType(String type) {
  switch(type) {
    case "TXT":
      return TYPE.TXT;
    case "IMAGE":
      return TYPE.IMAGE;
    case "VIDEO":
      return TYPE.VIDEO;
    case "LOCATION":
      return TYPE.LOCATION;
    case "VOICE":
      return TYPE.VOICE;
    case "FILE":
      return TYPE.FILE;
    case "CMD":
      return TYPE.CMD;
    default:
      return TYPE.TXT;
  }
}