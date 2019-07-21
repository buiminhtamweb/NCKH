package com.example.nckh.data;

import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.nckh.R;
import com.example.nckh.util.Constant;
import com.example.nckh.util.SharedPreferencesHandler;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import static com.example.nckh.util.Constant.TK_ID;


public class ConnectSocketIO {

    private static final String STATIC_CHANGE = "online-table-";
    private static final String DISCONNECT = "disconnect";
    private static final String CONNECTED = "connected";
    private static final String SLEEP = "sleep";
    private static final String TAG = "ConnectSocketIO";
    private static String mTK_ID;
    private static NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder mBuilder;

    private static ConnectSocketIO mConnectSocketIO;
    private static Socket mSocket;

    private ConnectSocketIO(final Context context) {

        try {
            mTK_ID = SharedPreferencesHandler.getString(context, TK_ID);
            String serverSocketUrl = SharedPreferencesHandler.getString(context, Constant.SERVER_SOCKET_URL);
            mSocket = IO.socket(serverSocketUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        mSocket.connect();
//
        mSocket.on("Server-send-location-" + mTK_ID, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
//                Toast.makeText(context, "Yêu cầu bị hủy. Vì chưa có món ăn hoàn thành và vận chuyển", Toast.LENGTH_SHORT).show();

                JSONObject obj = (JSONObject) args[0];
                try {
                    String msg = obj.getString("msg");
                    Log.e(TAG, "SOCKET_NOTIFICATION: " + msg);
                    Toast.makeText(context, "Dữ liệu nhận được: " + msg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//
        if(mSocket.connected()){
            Toast.makeText(context, "Đã kết nối Socket.IO thành công", Toast.LENGTH_SHORT).show();
        }
//        mSocket.emit(STATIC_CHANGE, data);
//

    }

    public static ConnectSocketIO getInstance(Context context) {

        if (mConnectSocketIO == null) {
            Log.e(TAG, "getInstance: new ConnectServer ");
            mConnectSocketIO = new ConnectSocketIO(context);
        }
        return mConnectSocketIO;
    }

//    public static void destroy() {
//        JSONObject data = new JSONObject();
//        try {
//            data.put(STT_BAN_AN, mSTTBA);
//            data.put(STATUS, DISCONNECT);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if (mSocket.connected()) {
//            mSocket.emit(STATIC_CHANGE, data);
//        }
//
//        mSocket.disconnect();
//        mSocket.close();
//        mSocket = null;
//        mConnectSocketIO = null;
//        notificationManager.cancel(NOTIFI_ID);
//        Log.e(TAG, "Destroy mConnectSocketIO ");
//    }

    public boolean sendData(String event, String key, String value) {
        Log.e(TAG, "IO->sendData: " + event);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(TK_ID, mTK_ID);
            jsonObject.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mSocket.connected()) {

            mSocket.emit(event, jsonObject);

            return true;
        } else return false;

    }

    public boolean sendData(String event, String key, boolean value) {
        Log.e(TAG, "IO->sendData: " + event);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(TK_ID, mTK_ID);
            jsonObject.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mSocket.connected()) {

            mSocket.emit(event, jsonObject);

            return true;
        } else return false;
    }

    public boolean sendData(String event, String key, int value) {
        Log.e(TAG, "IO->sendData: " + event);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(TK_ID, mTK_ID);
            jsonObject.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mSocket.connected()) {

            mSocket.emit(event, jsonObject);

            return true;
        } else return false;
    }

//    public void setmThanhToanInterface(ThanhToanInterface mThanhToanInterface) {
//        this.mThanhToanInterface = mThanhToanInterface;
//    }
}
