import 'package:flutter_demo/db/f_cache.dart';
import 'package:flutter_demo/http/core/f_net.dart';
import 'package:flutter_demo/http/request/base_request.dart';
import 'package:flutter_demo/http/request/login_request.dart';
import 'package:flutter_demo/http/request/register_request.dart';

///实现数据交互
class LoginDao {
  static const BOARDING_PASS = "boarding-pass";

  //登录
  static login(String username, String password) {
    return _send(username, password);
  }

  //注册
  static register(
      String username, String password, String imoocId, String orderId) {
    return _send(username, password, imoocId: imoocId, orderId: orderId);
  }

  /// 登录与注册的数据交互
  static _send(String username, String password,
      {String imoocId, String orderId}) async {
    BaseRequest request;
    if (imoocId != null && orderId != null) {
      request = RegisterRequest();
    } else {
      request = LoginRequest();
    }
    request
        .add("userName", username)
        .add("password", password)
        .add("imoocId", imoocId)
        .add("orderId", orderId);

    var result = await FNet.getInstance().fire(request);
    print(result);
    //判断登录
    if (result['code'] == 0 && result['data'] != null) {
      //保存登录令牌
      FCache.getInstance().setString(BOARDING_PASS, result['data']);
    }
    return result;
  }

  ///获取登录令牌
  static getBoardingPass() {
    return FCache.getInstance().get(BOARDING_PASS);
  }
}
