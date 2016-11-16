def can_build(plat):
	return plat == "iphone" or plat == "android"

def configure(env):
	if env["platform"] == "iphone":
		env.Append(LIBPATH=['../../modules/godytics/ios/lib','/usr/lib/'])
		env.Append(LINKFLAGS=[
  			'-ObjC',
  			'-framework','Foundation',
			'-framework','AdSupport',
  			'-framework','AVFoundation',
  			'-framework','EventKit',
  			'-framework','EventKitUI',
  			'-framework','StoreKit',
  			'-framework','SystemConfiguration',
  			'-framework','AssetsLibrary',
  			'-framework','CoreData',
  			'-framework','CoreLocation',
  			'-framework','CoreText',
			'-l','GoogleAnalyticsServices',
			'-l','sqlite3',
			'-l','z'
    	])
	if env["platform"] == "android":
		env.android_add_dependency("compile 'com.android.support:appcompat-v7:23.0.0'")
        env.android_add_dependency("compile 'com.google.android.gms:play-services-analytics:9.2.0'")
        env.android_add_java_dir("android")
        env.android_add_to_manifest("android/AndroidManifestChunk.xml")
        env.disable_module()
