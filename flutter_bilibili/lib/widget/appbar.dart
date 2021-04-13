import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

///自定义顶部AppBar
appBar(String title, String rightTitle, VoidCallback rightButtonClick) {
  return AppBar(
    // 让title居左
    centerTitle: false,
    titleSpacing: 0,
    //导航栏最左侧widget，常见为菜单按钮和返回按钮
    leading: BackButton(),
    //页面标题
    title: Text(
      title,
      style: TextStyle(fontSize: 18),
    ),
    //导航栏右侧菜单
    actions: [
      //点击效果
      InkWell(
        //点击事件
        onTap: rightButtonClick,
        child: Container(
          padding: EdgeInsets.only(left: 15, right: 15),
          alignment: Alignment.center,
          child: Text(
            rightTitle,
            style: TextStyle(
              fontSize: 18,
              color: Colors.grey[500],
            ),
            textAlign: TextAlign.center,
          ),
        ),
      )
    ],
  );
}
