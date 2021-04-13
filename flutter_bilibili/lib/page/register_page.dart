import 'package:flutter/material.dart';
import 'package:flutter_demo/http/core/f_error.dart';
import 'package:flutter_demo/http/dao/login_dao.dart';
import 'package:flutter_demo/utils/string_util.dart';
import 'package:flutter_demo/utils/toast.dart';
import 'package:flutter_demo/widget/appbar.dart';
import 'package:flutter_demo/widget/login_button.dart';
import 'package:flutter_demo/widget/login_effect.dart';
import 'package:flutter_demo/widget/login_input.dart';

class RegisterPage extends StatefulWidget {
  //路由的跳转逻辑回调
  final VoidCallback onJumpToLogin;

  const RegisterPage({Key key, this.onJumpToLogin}) : super(key: key);

  @override
  _RegisterPageState createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  //标记 密码输入保护
  bool protect = false;

  //注册按钮是否可点击
  bool loginEnable = false;
  String username;
  String password;
  String rePassword;
  String imoocId;
  String orderId;

  @override
  Widget build(BuildContext context) {
    //Scaffold 是一个路由页的骨架
    return Scaffold(
      appBar: appBar("注册", "登录", widget.onJumpToLogin),
      body: Container(
        //使用listview 表示输入框 小屏的时候 输入框被遮挡的问题
        //自适应键盘 防止遮挡
        child: ListView(
          children: [
            LoginEffect(protect: protect),
            LoginInput(
              "用户名",
              "请输入用户名",
              onChange: (text) {
                username = text;
                checkInput();
              },
            ),
            LoginInput(
              "密码",
              "请输入密码",
              obscureText: true,
              onChange: (text) {
                password = text;
                checkInput();
              },
              focusChanged: (focus) {
                //密码输入框获取焦点 改变状态
                this.setState(() {
                  protect = focus;
                });
              },
            ),
            LoginInput(
              "密码",
              "请再次输入密码",
              obscureText: true,
              onChange: (text) {
                rePassword = text;
                checkInput();
              },
              focusChanged: (focus) {
                //密码输入框获取焦点 改变状态
                this.setState(() {
                  protect = focus;
                });
              },
            ),
            LoginInput("慕课网ID", "请输入慕课网ID", onChange: (text) {
              imoocId = text;
              checkInput();
            }),
            LoginInput("课程订单号", "请输入课程订单号后四位", onChange: (text) {
              orderId = text;
              checkInput();
            }),
            Padding(
              padding: EdgeInsets.only(top: 20, left: 20, right: 20),
              child: LoginButton(
                title: "注册",
                enable: loginEnable,
                onPressed: () {
                  if (loginEnable) {
                    _checkParams();
                  } else {
                    print("输入不完整");
                  }
                },
              ),
            )
          ],
        ),
      ),
    );
  }

  //检查输入
  void checkInput() {
    bool enable;
    if (isNotEmpty(username) &&
        isNotEmpty(password) &&
        isNotEmpty(rePassword) &&
        isNotEmpty(imoocId) &&
        isNotEmpty(orderId)) {
      enable = true;
    } else {
      enable = false;
    }
    setState(() {
      loginEnable = enable;
    });
  }

  void _send() async {
    try {
      var result =
          await LoginDao.register(username, password, imoocId, orderId);
      if (result['code'] == 0) {
        showToast("注册成功");
        if (widget.onJumpToLogin != null) {
          widget.onJumpToLogin(); //跳转到登录页
        } else {
          print(result['message']);
        }
      }
    } on NeedAuth catch (e) {
      showWarnToast(e.message);
    } on FNetError catch (e) {
      showWarnToast(e.message);
    }
  }

  ///检查输入的数据
  void _checkParams() {
    String tips;
    if (password != rePassword) {
      tips = "两次密码输入不一致";
    } else if (orderId.length != 4) {
      tips = "请输入等单号的后四位";
    }
    if (tips != null) {
      showWarnToast(tips);
      return;
    }
    //数据校验正确发送注册请求
    _send();
  }
}
