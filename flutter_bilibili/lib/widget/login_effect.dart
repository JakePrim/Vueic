import 'package:flutter/material.dart';

///登录效果，自定义widget

class LoginEffect extends StatefulWidget {
  //控制显示的图片模式
  final bool protect;

  //@required 必须要传递的参数
  LoginEffect({Key key, @required this.protect}) : super(key: key);

  @override
  _LoginEffectState createState() => _LoginEffectState();
}

class _LoginEffectState extends State<LoginEffect> {
  @override
  Widget build(BuildContext context) {
    return Container(
      //内边距
      padding: EdgeInsets.only(top: 10),
      // 边框
      decoration: BoxDecoration(
          color: Colors.grey[100],
          border: Border(bottom: BorderSide(color: Colors.grey[300]))),
      child: Row(
        //平均分配空间
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          _image(true),
          Image(
            image: AssetImage('images/logo.png'),
            height: 90,
            width: 90,
          ),
          _image(false),
        ],
      ),
    );
  }

  _image(bool left) {
    var leftImage = widget.protect
        ? 'images/head_left_protect.png'
        : 'images/head_left.png';
    var rightImage = widget.protect
        ? 'images/head_right_protect.png'
        : 'images/head_right.png';
    return Image(
      image: AssetImage(left ? leftImage : rightImage),
      height: 90,
    );
  }
}
