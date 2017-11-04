package test.infoapp.util.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionUtil extends ConnectionUtilAbs {
    private Context context;

    public ConnectionUtil(Context context) {
        this.context = context;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }
}
