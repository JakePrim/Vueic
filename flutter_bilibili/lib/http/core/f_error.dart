///需要登录的异常
class NeedLogin extends FNetError {
  NeedLogin({int code: 401, String message: "需要登录"}) : super(code, message);
}

///需要授权的异常
class NeedAuth extends FNetError {
  ///code 和 data是可选项，code的默认值为403
  NeedAuth(String message, {int code: 403, dynamic data})
      : super(code, message, data: data);
}

/// 网络异常统一格式类
class FNetError implements Exception {
  final int code;
  final String message;
  final dynamic data;

  /// code 和 Message是必填的 data是可选的
  FNetError(this.code, this.message, {this.data});
}
