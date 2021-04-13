import 'package:flutter/material.dart';
import 'package:flutter_demo/db/f_cache.dart';
import 'package:flutter_demo/http/core/f_error.dart';
import 'package:flutter_demo/http/dao/login_dao.dart';
import 'package:flutter_demo/utils/string_util.dart';
import 'package:flutter_demo/utils/toast.dart';
import 'package:flutter_demo/widget/appbar.dart';
import 'package:flutter_demo/widget/login_button.dart';
import 'package:flutter_demo/widget/login_effect.dart';
import 'package:flutter_demo/widget/login_input.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  bool protect = false;

  //注册按钮是否可点击
  bool loginEnable = false;
  String username;
  String password;

  var myController = TextEditingController();

  @override
  void initState() {
    super.initState();
    // username = FCache.getInstance().get("username");
    username = "aaa";
    myController.text = username;
  }

  @override
  void dispose() {
    myController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: appBar("密码登录", "注册", () {}),
      body: Container(
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
              controller: myController,
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
                setState(() {
                  protect = focus;
                });
              },
            ),
            Padding(
              padding: EdgeInsets.only(top: 20, left: 20, right: 20),
              child: LoginButton(
                title: "登录",
                enable: loginEnable,
                onPressed: () {
                  _send();
                },
              ),
            ),
          ],
        ),
      ),
    );
  }

  //检查输入
  void checkInput() {
    bool enable;
    if (isNotEmpty(username) && isNotEmpty(password)) {
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
      var result = await LoginDao.login(username, password);
      if (result['code'] == 0) {
        showToast("登录成功");
        //缓存用户名
        FCache.getInstance().setString("username", username);
      } else {
        showWarnToast("登录失败");
      }
    } on NeedAuth catch (e) {
      showWarnToast(e.message);
    } on FNetError catch (e) {
      showWarnToast(e.message);
    } catch (e) {
      print(e);
    }
  }
}
