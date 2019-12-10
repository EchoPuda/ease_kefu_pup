import 'package:ease_kefu/model/Message.dart';

/// 
/// @author puppet
class ListMessage {
  final List<Message> list;

  ListMessage.fromList(List list)
      : list = _getListMessage(list);
}

List<Message> _getListMessage(List list) {
  List<Message> listMsg = new List();
  list.forEach((value) {
    listMsg.add(Message.fromMap(value));
  });
  return listMsg;
}

