package com.xuemooc.edxapp.utils.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by hackeris on 15/7/29.
 */
public class NetworkUtil {

    //private static final Logger logger = new Logger(NetworkUtil.class.getName());
    //private static final String TAG = NetworkUtil.class.getSimpleName();

    /**
     * Returns true if device is connected to wifi or mobile network, false
     * otherwise.
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo infoWifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (infoWifi != null) {
            NetworkInfo.State wifi = infoWifi.getState();
            if (wifi == NetworkInfo.State.CONNECTED) {
                //logger.debug("Wifi is connected");
                return true;
            }
        }

        NetworkInfo infoMobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (infoMobile != null) {
            NetworkInfo.State mobile = infoMobile.getState();
            if (mobile == NetworkInfo.State.CONNECTED) {
                //logger.debug("Mobile data is connected");
                return true;
            }
        }

        //logger.debug("Network not available");
        return false;
    }

    /**
     * Check if there is any connectivity to a Wifi network
     * @param context
     * @return
     */
    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     * @param context
     * @return
     */
    public static boolean isConnectedMobile(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Get the network info
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static String getIpAddress(Context context){
        String ipAddress = null;
        try {
            //Using WIFI Manger
            WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();

            String ipString = String.format(
                    "%d.%d.%d.%d",
                    (ip & 0xff),
                    (ip >> 8 & 0xff),
                    (ip >> 16 & 0xff),
                    (ip >> 24 & 0xff));
            ipAddress = ipString;


            //Using InetAddress
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ipAddress = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            //logger.error(ex);
        }
        return ipAddress;
    }
}

