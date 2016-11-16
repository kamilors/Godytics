def can_build(plat):
	return plat == "iphone"

def configure(env):
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
