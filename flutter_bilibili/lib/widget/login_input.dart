import 'package:flutter/material.dart';
import 'package:flutter_demo/utils/color.dart';

/// 登录输入框 自定义widget
class LoginInput extends StatefulWidget {
  final String title;
  final String hint;
  final ValueChanged<String> onChange; //输入框文字发生变化 则进行通知
  final ValueChanged<bool> focusChanged; //ValueChanged 监听变化
  final bool lineStretch; //底部横线是否撑满一行
  final bool obscureText; //控制密码的输入模式
  final TextInputType keyboardType; //键盘弹出的类型
  final TextEditingController controller; //展示文本框值

  LoginInput(this.title, this.hint,
      {this.onChange,
      this.controller,
      this.focusChanged,
      this.lineStretch = false,
      this.obscureText = false,
      this.keyboardType}); //输入框输入的类型

  @override
  _LoginInputState createState() => _LoginInputState();
}

class _LoginInputState extends State<LoginInput> {
  final _focusNode = FocusNode(); // 判断输入框是否获取光标

  @override
  void initState() {
    super.initState();
    // 是否获取光标的监听
    _focusNode.addListener(() {
      print("has focus:${_focusNode.hasFocus}");
      if (widget.focusChanged != null) {
        //传递给调用者
        widget.focusChanged(_focusNode.hasFocus);
      }
    });
  }

  @override
  void dispose() {
    _focusNode.dispose(); //释放监听
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    //Column 垂直布局
    return Column(
      children: [
        // row 水平布局
        Row(
          children: [
            //container 容器
            Container(
              //左侧padding 15dp
              padding: EdgeInsets.only(left: 15),
              width: 100,
              child: Text(
                widget.title,
                style: TextStyle(fontSize: 16),
              ),
            ),
            _input()
          ],
        ),
        // 底部画一条线
        Padding(
          padding: EdgeInsets.only(left: !widget.lineStretch ? 15 : 0),
          child: Divider(
            height: 1,
            thickness: 0.5,
          ),
        )
      ],
    );
  }

  _input() {
    // Expanded 可以按比例 扩展或者伸缩比距，Row Column Flex 子组件所占用的空间
    return Expanded(
        child: TextField(
      //是否获取光标
      focusNode: _focusNode,
      //输入框输入变化监听
      onChanged: widget.onChange,
      //是否隐藏正在编辑的文本，用于输入密码的场景等 文本内容用"."替换
      obscureText: widget.obscureText,
      // 键盘的类型：字符串 密码 数字等
      keyboardType: widget.keyboardType,
      // 是否自动获取焦点， 只要不是密码输入框 都自动获取焦点
      autofocus: !widget.obscureText,
      //光标的颜色
      cursorColor: primary,
      controller: widget.controller,
      //输入文本的样式
      style: TextStyle(
          fontSize: 16, color: Colors.black, fontWeight: FontWeight.w300),
      //输入框的样式
      decoration: InputDecoration(
          contentPadding: EdgeInsets.only(left: 20, right: 20),
          border: InputBorder.none,
          hintText: widget.hint ?? '',
          hintStyle: TextStyle(fontSize: 15, color: Colors.grey)),
    ));
  }
}
