//定义枚举类型
import 'package:flutter_demo/http/dao/login_dao.dart';

enum HttpMethod { GET, POST, DELETE }

///基础请求
abstract class BaseRequest {
  var pathParams; //请求参数
  var useHttps = true; //是否使用HTTPs请求
  String authority() {
    return "api.devio.org";
  }

  HttpMethod httpMethod(); //请求方法
  String path(); //请求路径
  //请求地址
  String url() {
    Uri uri;
    var pathStr = path();
    //拼接参数
    if (pathParams != null) {
      if (path().endsWith("/")) {
        pathStr = "${path()}$pathParams";
      } else {
        pathStr = "${path()}/$pathParams";
      }
    }
    //http和https切换
    if (useHttps) {
      uri = Uri.https(authority(), pathStr, params);
    } else {
      uri = Uri.http(authority(), pathStr, params);
    }
    if (needLogin()) {
      //给需要登录的接口携带登录令牌
      addHeader(LoginDao.BOARDING_PASS, LoginDao.getBoardingPass());
    }
    print('url:${uri.toString()}');
    return uri.toString();
  }

  ///是否需要登录 当请求某个接口的时候
  bool needLogin();

  Map<String, String> params = Map();

  ///添加参数
  BaseRequest add(String k, Object v) {
    params[k] = v.toString();
    return this;
  }

  ///全局的header
  Map<String, dynamic> header = {
    'course-flag': 'fa',
    'auth-token': 'ZmEtMjAyMS0wNC0xMiAyMToyMjoyMC1mYQ==fa'
  };

  ///添加header
  BaseRequest addHeader(String k, Object v) {
    header[k] = v.toString();
    return this;
  }
}
