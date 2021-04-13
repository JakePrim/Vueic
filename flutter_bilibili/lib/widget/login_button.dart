import 'package:flutter/material.dart';
import 'package:flutter_demo/utils/color.dart';

class LoginButton extends StatelessWidget {
  final String title;
  final bool enable;
  final VoidCallback onPressed;

  const LoginButton(
      {Key key, @required this.title, this.enable = true, this.onPressed})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return FractionallySizedBox(
      widthFactor: 1, //填满一行的宽度
      child: MaterialButton(
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(5)),
        height: 45,
        onPressed: enable ? onPressed : null,
        disabledColor: primary[50],
        color: primary,
        child: Text(
          title,
          style: TextStyle(color: Colors.white, fontSize: 16),
        ),
      ),
    );
  }
}
