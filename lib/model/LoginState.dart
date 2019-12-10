
/// 登录和退出状态
/// @author puppet
class LoginState {
  int process;
  String status;

  LoginState.fromMap(Map map) :
        process = map["process"],
        status = map["status"];
}