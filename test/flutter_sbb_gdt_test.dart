import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_sbb_gdt/flutter_sbb_gdt.dart';

void main() {
  const MethodChannel channel = MethodChannel('flutter_sbb_gdt');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await FlutterSbbGdt.platformVersion, '42');
  });
}
