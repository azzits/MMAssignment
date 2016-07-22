package android.com.mmassignment.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Ajit on 6/14/2016.
 */

public class GeneralUtils {
    public static String getFormattedDate(String strDate) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        java.util.Date date = null;
        try {
            date = form.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM dd yyyy");
        return newFormat.format(date);
    }
}
