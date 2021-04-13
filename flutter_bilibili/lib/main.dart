import 'package:flutter/material.dart';
import 'package:flutter_demo/http/core/f_error.dart';
import 'package:flutter_demo/http/core/f_net.dart';
import 'package:flutter_demo/http/dao/login_dao.dart';
import 'package:flutter_demo/http/request/notice_request.dart';
import 'package:flutter_demo/http/request/test_request.dart';
import 'package:flutter_demo/page/login_page.dart';
import 'package:flutter_demo/utils/color.dart';

import 'db/f_cache.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    FCache.preInit();
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: white,
      ),
      home: LoginPage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  @override
  void setState(fn) {
    super.setState(fn);
    FCache.preInit();
  }

  void _incrementCounter() async {
    TestRequest request = new TestRequest();
    request.add("aa", "ddd").add("bb", "2333").add("requestPrams", "kkk");
    try {
      var result = await FNet.getInstance().fire(request);
    } on NeedAuth catch (e) {
      print(e);
    } on NeedLogin catch (e) {
      print(e);
    } on FNetError catch (e) {
      print(e);
    }
    // test();
    testLogin();
    testNotice();
    setState(() {
      _counter++;
    });
  }

  void test() {
    FCache.getInstance().setString("a", "123");
    String a = FCache.getInstance().get("a");
    print("FCache:${a}");
  }

  void testLogin() async {
    try {
      // var result = await LoginDao.register("a", "abc", "337039", "8166");
      var result = await LoginDao.login("a", "abc");
      print(result);
    } catch (e) {
      print(e);
    }
  }

  void testNotice() async {
    try {
      var notice = await FNet.getInstance().fire(NoticeRequest());
      print(notice);
    } catch (e) {
      print(e);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

//导航系统
