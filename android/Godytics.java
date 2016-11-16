package org.godotengine.godot;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.HitBuilders;
import android.app.Activity;
import android.util.Log;

public class Godytics extends Godot.SingletonBase {

    private Activity activity = null;
    private GoogleAnalytics analytics;
    private Tracker tracker;

    private String trackerId = null;
    private boolean initialized = false;

    public Godytics(Activity activity) {
        this.activity = activity;
        registerClass("Godytics", new String[] {
            "init", "screen", "event"
        });
    }

    static public Godot.SingletonBase initialize(Activity activity) {
      return new Godytics(activity);
    }

    public void init(final String tId) {
      initialized = false;
      this.trackerId = tId;
      activity.runOnUiThread(new Runnable(){
        @Override
        public void run() {
          initTracker();
        }
      });
    }

    private void initTracker() {
      analytics = GoogleAnalytics.getInstance(activity);
      analytics.setLocalDispatchPeriod(30);
      analytics.setDryRun(false);
      tracker = analytics.newTracker(trackerId);
      initialized = true;
      Log.i("godot", "GoogleAnalytics: initialized for " + trackerId);
    }

    private Tracker getTracker() {
      if(trackerId == null || trackerId.equals("")) {
        Log.e("godot", "GoogleAnalytics: trackerId is null or empty");
        return null;
      }
      if(!initialized) {
        synchronized (trackerId) {
          if(!initialized) {
            initTracker();
          }
        }
      }
      return tracker;
    }



    public void screen(final String name) {
          activity.runOnUiThread(new Runnable(){
            @Override
            public void run() {
              Tracker tracker = getTracker();
              if(tracker != null) {
                tracker.setScreenName(name);
                tracker.send(new HitBuilders.ScreenViewBuilder().build());
                Log.i("godot", "GoogleAnalytics: sending screen name: " + name);
              }
              else {
                Log.e("godot", "GoogleAnalytics: tracker is not initialized.");
              }
            }
          });
     }

     public void event(final String category, final String action, final String label) {
         activity.runOnUiThread(new Runnable(){
           @Override
           public void run() {
             Tracker tracker = getTracker();
             if(tracker != null) {
               tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build());
                Log.i("godot", "GoogleAnalytics: sending event: c:" + category + ", a:" +action + ", l:" + label);
              }
              else {
                Log.e("godot", "GoogleAnalytics: tracker is not initialized.");
              }
            }

         });
     }
}
