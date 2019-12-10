import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:ease_kefu/ease_kefu.dart';

void main() {
  const MethodChannel channel = MethodChannel('ease_kefu');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await EaseKefu.platformVersion, '42');
  });
}
