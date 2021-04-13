import 'dart:convert';

import 'package:flutter_demo/http/request/base_request.dart';

///网络请求抽象类
abstract class FNetAdapter {
  Future<FNetResponse<T>> send<T>(BaseRequest request);
}

/// 统一网络层返回格式
class FNetResponse<T> {
  //构造方法设置为可选参数
  FNetResponse(
      {this.data,
      this.request,
      this.statusCode,
      this.statusMessage,
      this.extra});

  T data;
  BaseRequest request;
  int statusCode;
  String statusMessage;
  dynamic extra; //额外的数据 使用动态类型

  @override
  String toString() {
    if (data is Map) {
      //如果data是map的话 转换为json字符串
      return json.encode(data);
    }
    return data.toString();
  }
}
