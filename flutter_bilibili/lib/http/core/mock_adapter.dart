import 'package:flutter_demo/http/core/f_net_adapter.dart';
import 'package:flutter_demo/http/request/base_request.dart';

///测试的adapter

class MockAdapter extends FNetAdapter {
  @override
  Future<FNetResponse<T>> send<T>(BaseRequest request) {
    return Future<FNetResponse>.delayed(Duration(milliseconds: 1000), () {
      return FNetResponse(
          data: {"code": 0, "message": "success"}, statusCode: 401);
    });
  }
}
