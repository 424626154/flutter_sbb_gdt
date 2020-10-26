#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint flutter_sbb_gdt.podspec' to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'flutter_sbb_gdt'
  s.version          = '0.0.1'
  s.summary          = 'flutter gdt'
  s.description      = <<-DESC
flutter gdt
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  s.platform = :ios, '9.0'
  #优量汇
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'GDTMobSDK', '~> 4.11.11'
  #依赖frameworks
  s.frameworks = 'CoreTelephony'
  s.static_framework = true

  # Flutter.framework does not contain a i386 slice.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386' }
end
