package com.transistorsoft.cordova.bggeo;

import com.transistorsoft.locationmanager.adapter.BackgroundGeolocation;
import com.transistorsoft.locationmanager.adapter.TSCallback;
import com.transistorsoft.locationmanager.logger.TSLog;
import com.transistorsoft.locationmanager.settings.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This BroadcastReceiver receives broadcasted events from the BackgroundGeolocation plugin.
 * It's designed for you to customize in your own application, to handle events in the native
 * environment.  You can use this in cases where the user has terminated your foreground Activity
 * while the BackgroundGeolocation background Service continues to operate.
 *
 * You have full access to the BackgroundGeolocation API adapter.  You may execute any documented
 * API method, such as #start, #stop, #changePace, #getCurrentPosition, etc.
 *
 * @author chris scott, Transistor Software www.transistorsoft.com
 *
 * This BroadcastReceiver receives the following events:
 *
 * @event heartbeat         BackgroundGeolocation.EVENT_HEARTBEAT
 * @event motionchange      BackgroundGeolocation.EVENT_MOTIONCHANGE
 * @event location          BackgroundGeolocation.EVENT_LOCATION
 * @event geofence          BackgroundGeolocation.EVENT_GEOFENCE
 * @event http              BackgroundGeolocation.EVENT_HTTP
 * @event schedule          BackgroundGeolocation.EVENT_SCHEDULE
 * @event activitychange    BackgroundGeolocation.EVENT_ACTIVITYCHANGE
 * @event providerchange    BackgroundGeolocation.EVENT_PROVIDERCHANGE
 * @event geofenceschange   BackgroundGeolocation.EVENT_GEOFENCESCHANGE
 * @event heartbeat         BackgroundGeolocation.EVENT_BOOT
 *
 */
public class EventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String eventName = getEventName(intent.getAction());

        String message = TSLog.header("BackgroundGeolocation EventReceiver: " + eventName);
        TSLog.logger.info(message);

        // Decode event name
        if (BackgroundGeolocation.EVENT_HEARTBEAT.equalsIgnoreCase(eventName)) {
            onHeartbeat(context, intent);
        } else if (BackgroundGeolocation.EVENT_MOTIONCHANGE.equalsIgnoreCase(eventName)) {
            onMotionChange(context, intent);
        } else if (BackgroundGeolocation.EVENT_LOCATION.equalsIgnoreCase(eventName)) {
            onLocation(context, intent);
        } else if (BackgroundGeolocation.EVENT_GEOFENCE.equalsIgnoreCase(eventName)) {
            onGeofence(context, intent);
        } else if (BackgroundGeolocation.EVENT_HTTP.equalsIgnoreCase(eventName)) {
            onHttp(context, intent);
        } else if (BackgroundGeolocation.EVENT_SCHEDULE.equalsIgnoreCase(eventName)) {
            onSchedule(context, intent);
        } else if (BackgroundGeolocation.EVENT_ACTIVITYCHANGE.equalsIgnoreCase(eventName)) {
            onActivityChange(context, intent);
        } else if (BackgroundGeolocation.EVENT_PROVIDERCHANGE.equalsIgnoreCase(eventName)) {
            onProviderChange(context, intent);
        } else if (BackgroundGeolocation.EVENT_GEOFENCESCHANGE.equalsIgnoreCase(eventName)) {
            onGeofencesChange(context, intent);
        } else if (BackgroundGeolocation.EVENT_BOOT.equalsIgnoreCase(eventName)) {
            onBoot(context, intent);
        } else if (BackgroundGeolocation.EVENT_TERMINATE.equalsIgnoreCase(eventName)) {
            onTerminate(context, intent);
        }
    }

    /**
    * @event heartbeat
    * @param {Boolean} isMoving
    * @param {JSONObject} location
    */
    private void onHeartbeat(Context context, Intent intent) {
        try {
            JSONObject location = new JSONObject(intent.getStringExtra("location"));
            TSLog.logger.debug(location.toString());
        } catch (JSONException e) {
            TSLog.logger.error(TSLog.error(e.getMessage()));
        }
    }

    /**
    * @event motionchange
    * @param {Boolean} isMoving
    * @param {JSONObject} location
    */
    private void onMotionChange(Context context, Intent intent) {
        boolean isMoving = intent.getBooleanExtra("isMoving", false);
        try {
            JSONObject location = new JSONObject(intent.getStringExtra("location"));
            TSLog.logger.debug(location.toString());
        } catch (JSONException e) {
            TSLog.logger.error(TSLog.error(e.getMessage()));
        }
    }

    /**
    * @event location
    * @param {JSONObject} location
    */
    private void onLocation(Context context, Intent intent) {
        try {
            JSONObject location = new JSONObject(intent.getStringExtra("location"));
            TSLog.logger.debug(location.toString());
        } catch (JSONException e) {
            TSLog.logger.error(e.getMessage());
        }
    }

     /**
    * @event geofence
    * @param {JSONObject} geofence
    */
    private void onGeofence(Context context, Intent intent) {
        try {
            JSONObject geofenceEvent = new JSONObject(intent.getStringExtra("geofence"));
            String action = geofenceEvent.getString("action");
            String identifier = geofenceEvent.getString("identifier");
            JSONObject location = geofenceEvent.getJSONObject("location");
            if (geofenceEvent.has("extras")) {
                JSONObject extras = geofenceEvent.getJSONObject("extras");
            }
            TSLog.logger.debug(geofenceEvent.toString());
        } catch (JSONException e) {
            TSLog.logger.error(TSLog.error(e.getMessage()));
        }
    }

    /**
    * @event http
    * @param {Integer} status
    * @param {String} responseText
    */
    private void onHttp(Context context, Intent intent) {
        String responseText = intent.getStringExtra("responseText");
        int status = intent.getIntExtra("status", -1);
        TSLog.logger.debug("status: " + status + ", " + responseText);
    }

    /**
    * @event schedule
    * @param {JSONObject} state
    */
    private void onSchedule(Context context, Intent intent) {
        try {
            JSONObject state = new JSONObject(intent.getStringExtra("state"));
            TSLog.logger.debug(state.toString());
        } catch (JSONException e) {
            TSLog.logger.error(TSLog.error(e.getMessage()));
        }
    }

    /**
    * @event activitychange
    * @param {String} activity
    */
    private void onActivityChange(Context context, Intent intent) {
        String activityName = intent.getStringExtra("activity");
        TSLog.logger.debug(activityName);
    }

    /**
    * @event providerchange
    * @param {String} activityName
    */
    private void onProviderChange(Context context, Intent intent) {
        try {
            JSONObject provider = new JSONObject(intent.getStringExtra("provider"));
            TSLog.logger.debug(provider.toString());
        } catch (JSONException e) {
            TSLog.logger.error(TSLog.error(e.getMessage()));
        }
    }

    /**
    * @event geofenceschange
    * @param {JSONArray} on
    * @param {JSONArray} off
    */
    private void onGeofencesChange(Context context, Intent intent) {
        try {
            JSONArray on = new JSONArray(intent.getStringExtra("on"));
            JSONArray off = new JSONArray(intent.getStringExtra("off"));
            TSLog.logger.debug("on: " + on.toString() + "\noff:" + off.toString());
        } catch (JSONException e) {
            TSLog.logger.error(TSLog.error(e.getMessage()));
        }
    }

    /**
    * @event boot
    * @param {JSONObject} state
    */
    private void onBoot(Context context, Intent intent) {
        BackgroundGeolocation adapter = BackgroundGeolocation.getInstance(context, intent);
        JSONObject state = adapter.getState();
    }

    /**
    * @event terminate
    * @param {JSONObject} state
    */
    private void onTerminate(Context context, Intent intent) {
        BackgroundGeolocation adapter = BackgroundGeolocation.getInstance(context, intent);
        JSONObject state = adapter.getState();
    }

    /**
    * Fetch the last portion of the Intent action foo.bar.EVENT_NAME -> event_name
    * @param {String} action
    * @return {string} eventName
    */
    private String getEventName(String action) {
        String[] path = action.split("\\.");
        return path[path.length-1].toLowerCase();
    }
}

