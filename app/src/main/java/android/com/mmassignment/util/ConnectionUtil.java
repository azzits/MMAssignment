package android.com.mmassignment.util;

import android.com.mmassignment.app.AssignmentApplication;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ajit on 7/2/2016.
 */

public class ConnectionUtil implements ConnectionStatus {
    @Override
    public boolean isNetworkAvailable() {
        return checkNetworkAvailability(AssignmentApplication.getApplication());
    }


    private boolean checkNetworkAvailability(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
