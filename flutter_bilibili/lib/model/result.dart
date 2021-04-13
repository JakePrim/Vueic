import 'package:json_annotation/json_annotation.dart';

part 'result.g.dart'; //将在运行生成命令后自动生成

@JsonSerializable()
class Result {
  int code;
  String method;
  String requestPrams;

  Result(this.code, this.method, this.requestPrams);

  //固定格式 不同的类使用不同的mixin
  factory Result.fromJson(Map<String, dynamic> json) => _$ResultFromJson(json);

  Map<String, dynamic> toJson() => _$ResultToJson(this);
}
