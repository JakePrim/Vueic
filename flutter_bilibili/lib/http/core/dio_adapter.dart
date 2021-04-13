import 'package:dio/dio.dart';
import 'package:flutter_demo/http/core/f_error.dart';
import 'package:flutter_demo/http/core/f_net_adapter.dart';
import 'package:flutter_demo/http/request/base_request.dart';

///dio 网络请求适配器
///
class DioAdapter extends FNetAdapter {
  @override
  Future<FNetResponse<T>> send<T>(BaseRequest request) async {
    var response, option = Options(headers: request.header);
    var error;
    try {
      if (request.httpMethod() == HttpMethod.GET) {
        response = await Dio().get(request.url(), options: option);
      } else if (request.httpMethod() == HttpMethod.POST) {
        response = await Dio()
            .post(request.url(), data: request.params, options: option);
      } else if (request.httpMethod() == HttpMethod.DELETE) {
        response = await Dio()
            .delete(request.url(), data: request.params, options: option);
      }
    } on DioError catch (e) {
      error = e;
      response = e.response;
    }
    if (error != null) {
      ///抛出自定义异常类
      throw FNetError(response?.statusCode ?? -1, error.toString(),
          data: buildResponse(response, request));
    }
    return buildResponse(response, request);
  }

  /// 构建响应结果
  FNetResponse buildResponse(Response response, BaseRequest request) {
    return FNetResponse(
        data: response.data,
        request: request,
        statusCode: response.statusCode,
        statusMessage: response.statusMessage,
        extra: response);
  }
}
