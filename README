Background Geolocation Broadcast Receiver
========================================================================================

This plugin is designed for use with the Android platform of the [Cordova Background Geolocation](https://github.com/transistorsoft/cordova-background-geolocation-lt) plugin.  The plugin will add an Android **`BroadcastReceiver`** to your application, capable of receiving events from the `BackgroundGeolocation` plugin in the native Android environment.

You can use this plugin in situations where the user has terminated the app with `BackgroundGeolocation` configured for `{stopOnTerminate: false}`.  In this situation, only the `BackgroundGeolocation` plugin's background service is running &mdash; your Cordova app (ie: your Javascript app) has terminated.  You can use this plugin to implement your own custom functionality in the Android environment.

An example usage might be to request a new API token for your server endpoint, for tokens that have an expiration time.

Within the Broadcast receiver environment, you have full access to the `BackgroundGeolocation` API and you can execute any documented method, using the Java API.

```java
public class EventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      BackgroundGeolocation adapter = BackgroundGeolocation.getInstance(context, intent);

      // Get the current state.
      JSONObject state = adapter.getState();

      // Create a TSCallback instance
      TSCallback callback = new TSCallback() {
        public void success(Object result) {
          // Change pace success callback.
        }
        public void error(Object result) {
          // Change pace failure callback.
        }
      };

      adapter.changePace(true, callback);
    }
}
```


## Using the plugin ##

The plugin has no Javascript API &mdash; Only a Java `BroadcastReciever`.  The idea of the plugin is that you will fork this repo and implement your own custom functionality, most likely not shared with the public.  You should treat your fork as a private extension of your application code.

## Installing the plugin ##

#### From master (latest, greatest.)

```
   cordova plugin add https://github.com/transistorsoft/cordova-background-geolocation-broadcast-receiver.git
```

## Implementing your own custom `BroadcastReceiver`

### Selecting Events

You can choose the `BackgroundGeolocation` events you wish to subscribe to in this plugin's [`plugin.xml`]().  By default, **all** events are subscribed:

```xml
<receiver android:name="com.transistorsoft.cordova.bggeo.EventReceiver">
  <intent-filter>
    <action android:name="com.transistorsoft.locationmanager.event.BOOT" />
    <action android:name="com.transistorsoft.locationmanager.event.TERMINATE" />
    <action android:name="com.transistorsoft.locationmanager.event.HEARTBEAT" />
    <action android:name="com.transistorsoft.locationmanager.event.MOTIONCHANGE" />
    <action android:name="com.transistorsoft.locationmanager.event.LOCATION" />
    <action android:name="com.transistorsoft.locationmanager.event.GEOFENCE" />
    <action android:name="com.transistorsoft.locationmanager.event.HTTP" />
    <action android:name="com.transistorsoft.locationmanager.event.SCHEDULE" />
    <action android:name="com.transistorsoft.locationmanager.event.ACTIVITYCHANGE" />
    <action android:name="com.transistorsoft.locationmanager.event.PROVIDERCHANGE" />
    <action android:name="com.transistorsoft.locationmanager.event.GEOFENCESCHANGE" />
  </intent-filter>
</receiver>
```

If you're not interested in some particular event, simply delete that element.

### Implementing Event Handlers

The file [`EventReceiver.java`]() contains example implementations of all the event-handlers.  These methods are all prefixed with **`on{EventName}`**.  These methods are free for you to implement you own custom funcionality.  Eg:

```java
class EventReciever extends BroadcastReceiver {

  /**
  * @event heartbeat
  * @param {Boolean} isMoving
  * @param {JSONObject} location
  */
  private void onHeartbeat(Context context, Intent intent) {
    try {
        JSONObject location = new JSONObject(intent.getStringExtra("location"));
        // Implement your own custom functionality here.
    } catch (JSONException e) {
        TSLog.logger.error(TSLog.error(e.getMessage()));
    }
  }
}
```

:warning: You should **NOT** modify any method not beginning with **`on{EventName}` (ie: `onReceive`, `getEventName`).

