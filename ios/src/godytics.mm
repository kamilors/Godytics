#include "godytics.h"
#include "core/globals.h"
#include "core/variant.h"
#include "core/message_queue.h"

#import <UIKit/UIKit.h>


Godytics* GODYTICS_INSTANCE = NULL;

Godytics::Godytics() {
    ERR_FAIL_COND(GODYTICS_INSTANCE != NULL);
    GODYTICS_INSTANCE = this;
    initialized = false;
}

Godytics::~Godytics() {
    GODYTICS_INSTANCE = NULL;
}

void Godytics::init(const String &tId) {
  [[GAI sharedInstance] setDispatchInterval:30];
  [[GAI sharedInstance] setDryRun:NO];

  NSString * kGaPropertyId = [NSString stringWithCString:tId.utf8().get_data() encoding:NSUTF8StringEncoding];
  tracker = [[GAI sharedInstance] trackerWithTrackingId:kGaPropertyId];
  initialized = true;
}

void Godytics::screen(const String &name) {
  if(initialized) {
    NSString * screenName = [NSString stringWithCString:name.utf8().get_data() encoding:NSUTF8StringEncoding];
    [tracker set:kGAIScreenName value:screenName];
    [tracker send:[[GAIDictionaryBuilder createScreenView]  build]];
  }
}

void Godytics::event(const String &cat, const String &act, const String &lab) {
  if(initialized) {
    NSString * category = [NSString stringWithCString:cat.utf8().get_data() encoding:NSUTF8StringEncoding];
    NSString * action = [NSString stringWithCString:act.utf8().get_data() encoding:NSUTF8StringEncoding];
    NSString * label = [NSString stringWithCString:lab.utf8().get_data() encoding:NSUTF8StringEncoding];
    [tracker send:[[GAIDictionaryBuilder createEventWithCategory:category action:action label:label value:nil] build]];
  }
}

void Godytics::_bind_methods() {
    ObjectTypeDB::bind_method("init",&Godytics::init);
    ObjectTypeDB::bind_method("screen",&Godytics::screen);
    ObjectTypeDB::bind_method("event",&Godytics::event);
}
