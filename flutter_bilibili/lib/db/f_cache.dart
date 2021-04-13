import 'package:shared_preferences/shared_preferences.dart';

///本地缓存 SharedPreferences
class FCache {
  SharedPreferences prefs;

  FCache._() {
    init();
  }

  FCache._pre(SharedPreferences prefs) {
    this.prefs = prefs;
  }

  static FCache _instance;

  ///预初始化方法,防止在使用get时，prefs还未完成初始化，在项目启动时就进行初始化处理
  static Future<FCache> preInit() async {
    if (_instance == null) {
      var prefs = await SharedPreferences.getInstance();
      _instance = FCache._pre(prefs);
    }
  }

  static FCache getInstance() {
    if (_instance == null) {
      _instance = FCache._();
    }
    return _instance;
  }

  ///初始化
  void init() async {
    if (prefs == null) {
      prefs = await SharedPreferences.getInstance();
    }
  }

  void setString(String key, String value) {
    prefs.setString(key, value);
  }

  void setDouble(String key, double value) {
    prefs.setDouble(key, value);
  }

  void setInt(String key, int value) {
    prefs.setInt(key, value);
  }

  void setBool(String key, bool value) {
    prefs.setBool(key, value);
  }

  void setStringList(String key, List<String> value) {
    prefs.setStringList(key, value);
  }

  T get<T>(String key) {
    return prefs.get(key);
  }
}
