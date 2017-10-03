package com.Yegorisa.clusterws;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Egor on 01.10.2017.
 */
class Message {
    static String messageEncode(String event, Object data, String type) {
        JSONObject json = new JSONObject();
        switch (type) {
            case "publish":
                json.put("#", new JSONArray().put("p").put(event).put(data));
                return json.toString();
            case "emit":
                json.put("#", new JSONArray().put("e").put(event).put(data));
                return json.toString();
            case "system":
                switch (event) {
                    case "subscribe":
                        json.put("#", new JSONArray().put("s").put("s").put(data));
                        return json.toString();
                    case "unsubscribe":
                        json.put("#", new JSONArray().put("s").put("u").put(data));
                        return json.toString();
                }
            case "ping":
                return event;
            default:
                return event;
        }
    }

    static void messageDecode(ClusterWS webSocket, String message){
        JSONObject jsonObject = new JSONObject(message);
        ArrayList<Channel> channels = webSocket.getChannels();
        switch (jsonObject.getJSONArray("#").getString(0)) {
            case "p":
                String channelName = jsonObject.getJSONArray("#").getString(1);
                for (Channel channel :
                        channels) {
                    if (channel.getChannelName().equals(channelName)) {
                        channel.onMessage(jsonObject.getJSONArray("#").getString(2));
                        break;
                    }
                }
                break;
            case "e":
                webSocket.getEmitter().emit(jsonObject.getJSONArray("#").getString(1), jsonObject.getJSONArray("#").get(2));
                break;
            case "s":
                switch (jsonObject.getJSONArray("#").getString(1)) {
                    case "c":
                        break;
                }
        }
    }
}
