import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:ease_kefu/ease_kefu.dart';
import 'package:oktoast/oktoast.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  void initState() {
    super.initState();
  }


  @override
  Widget build(BuildContext context) {
    return OKToast(
      position: ToastPosition.center,
      backgroundColor: Color(0x80000000),
      movingOnWindowChange: false,
      child: MaterialApp(
        home: TestHome(),
      ),
    );
  }
}

class TestHome extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new TestHomeState();

}

class TestHomeState extends State<TestHome> {

  String account = "";
  String password = "123456";

  bool isInit = false;

  @override
  void initState() {
    super.initState();

    EaseKefu.responseFromLoginProcess.listen((data) {
      log("login:${data.process}");
      log("login:${data.status}");
      if (data.process == -1) {
        showToast("登录失败");
      } else if (data.process == 1) {
        showToast("登录成功");
      }
    });

    EaseKefu.responseFromLoginOutProcess.listen((data) {
      log("login:${data.process}");
      log("login:${data.status}");
      if (data.process == -1) {
        showToast("退出失败");
      } else if (data.process == 1) {
        showToast("退出成功");
      }
    });
  }

  Future _initEaseKefu() async {
    bool result = await EaseKefu.initEaseKefu("1400171218061390#kefuchannelapp387", "387", debug: true);
    isInit = result;
    log("initEaseKefu=$result");
  }

  Future _register() async {
    if (!isInit) {
      showToast("请先初始化");
      return;
    }
    EaseKefu.register(account, password);
  }

  Future _login() async {
    if (!isInit) {
      showToast("请先初始化");
      return;
    }
    EaseKefu.login(account, password);
  }

  Future _loginWithToken() async {
    if (!isInit) {
      showToast("请先初始化");
      return;
    }
    EaseKefu.loginWithToken(account, password);
  }

  Future _loginOut() async {
    if (!isInit) {
      showToast("请先初始化");
      return;
    }
    EaseKefu.loginOut();
  }

  Future _isLoggedInBefore() async {
    if (!isInit) {
      showToast("请先初始化");
      return;
    }
    bool isLogin = await EaseKefu.isLoggedInBefore;
    showToast("_isLoggedInBefore:$isLogin");
    log("_isLoggedInBefore:$isLogin");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("环信客服云Demo"),
      ),
      body: DefaultTextStyle(
        style: TextStyle(
          fontSize: 14,
          color: Color(0xFF333333),
        ),
        child: Container(
          width: double.maxFinite,
          height: double.maxFinite,
          padding: EdgeInsets.all(20),
          child: SingleChildScrollView(
            child: Column(
              children: <Widget>[
                GestureDetector(
                  onTap: () {
                    _initEaseKefu();
                  },
                  child: Container(
                    width: double.maxFinite,
                    height: 30,
                    margin: EdgeInsets.symmetric(vertical: 10),
                    alignment: Alignment.center,
                    color: Colors.lightGreen,
                    child: Text(
                      "初始化",
                      style: TextStyle(
                          color: Colors.white
                      ),
                    ),
                  ),
                ),

                Container(
                  width: double.maxFinite,
                  height: 40,
                  child: TextField(
                    onChanged: (value) {
                      setState(() {
                        account = value;
                      });
                    },
                    decoration: InputDecoration(
                        hintText: "账号"
                    ),
                  ),
                ),

                Container(
                  width: double.maxFinite,
                  height: 40,
                  child: TextField(
                    onChanged: (value) {
                      setState(() {
                        password = value;
                      });
                    },
                    decoration: InputDecoration(
                        hintText: "密码或token"
                    ),
                  ),
                ),

                GestureDetector(
                  onTap: () {
                    _register();
                  },
                  child: Container(
                    width: double.maxFinite,
                    height: 30,
                    margin: EdgeInsets.symmetric(vertical: 10),
                    alignment: Alignment.center,
                    color: Colors.lightGreen,
                    child: Text(
                      "注册",
                      style: TextStyle(
                          color: Colors.white
                      ),
                    ),
                  ),
                ),

                GestureDetector(
                  onTap: () {
                    _login();
                  },
                  child: Container(
                    width: double.maxFinite,
                    height: 30,
                    margin: EdgeInsets.symmetric(vertical: 10),
                    alignment: Alignment.center,
                    color: Colors.lightGreen,
                    child: Text(
                      "登录",
                      style: TextStyle(
                          color: Colors.white
                      ),
                    ),
                  ),
                ),

                GestureDetector(
                  onTap: () {
                    _loginWithToken();
                  },
                  child: Container(
                    width: double.maxFinite,
                    height: 30,
                    margin: EdgeInsets.symmetric(vertical: 10),
                    alignment: Alignment.center,
                    color: Colors.lightGreen,
                    child: Text(
                      "用Token登录",
                      style: TextStyle(
                          color: Colors.white
                      ),
                    ),
                  ),
                ),

                GestureDetector(
                  onTap: () {
                    _loginOut();
                  },
                  child: Container(
                    width: double.maxFinite,
                    height: 30,
                    margin: EdgeInsets.symmetric(vertical: 10),
                    alignment: Alignment.center,
                    color: Colors.lightGreen,
                    child: Text(
                      "登出",
                      style: TextStyle(
                          color: Colors.white
                      ),
                    ),
                  ),
                ),

                GestureDetector(
                  onTap: () {
                    _isLoggedInBefore();
                  },
                  child: Container(
                    width: double.maxFinite,
                    height: 30,
                    margin: EdgeInsets.symmetric(vertical: 10),
                    alignment: Alignment.center,
                    color: Colors.lightGreen,
                    child: Text(
                      "获取登录状态",
                      style: TextStyle(
                          color: Colors.white
                      ),
                    ),
                  ),
                ),

                GestureDetector(
                  onTap: () {

                  },
                  child: Container(
                    width: double.maxFinite,
                    height: 30,
                    margin: EdgeInsets.symmetric(vertical: 10),
                    alignment: Alignment.center,
                    color: Colors.lightGreen,
                    child: Text(
                      "聊天窗口",
                      style: TextStyle(
                          color: Colors.white
                      ),
                    ),
                  ),
                ),
              ],
            ),
          )
        ),
      )
    );
  }

  void log(String msg) {
    print("easeKefu" + "-------" + msg);
  }

}
