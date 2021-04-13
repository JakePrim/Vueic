import 'package:flutter_demo/http/core/dio_adapter.dart';
import 'package:flutter_demo/http/core/f_error.dart';
import 'package:flutter_demo/http/core/f_net_adapter.dart';
import 'package:flutter_demo/http/request/base_request.dart';

class FNet {
  FNet._();

  static FNet _instance;

  static FNet getInstance() {
    if (_instance == null) {
      _instance = FNet._();
    }
    return _instance;
  }

  ///调用请求的入口方法
  Future fire(BaseRequest request) async {
    //统一的响应结果类
    FNetResponse response;
    //统一的响应错误信息类
    var error;
    try {
      response = await send(request);
    } on FNetError catch (e) {
      //自定义的异常捕捉
      error = e;
      response = e.data;
      printLog(e.message);
    } catch (e) {
      //其他异常
      error = e;
      printLog(e);
    }
    if (response == null) {
      printLog(error);
    }
    //获取结果
    var result = response.data;
    printLog(result);

    //1. 解析状态码 状态码是否可以抽象出来 有调用层自定义处理
    var statusCode = response.statusCode;
    switch (statusCode) {
      case 200: //请求正常的状态码
        return result;
      case 401: //需要登录
        throw NeedLogin();
        return;
      case 403: //需要鉴权
        throw NeedAuth(result.toString(), data: result);
        return;
      default:
        throw FNetError(statusCode, result.toString(), data: result);
    }
  }

  ///发送网络请求
  Future<dynamic> send<T>(BaseRequest request) async {
    FNetAdapter adapter = DioAdapter();

    ///切换底层的网络库
    return adapter.send(request);
  }

  void printLog(log) {
    print('net:${log.toString()}');
  }
}
