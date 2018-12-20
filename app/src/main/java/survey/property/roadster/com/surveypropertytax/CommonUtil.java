package survey.property.roadster.com.surveypropertytax;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class CommonUtil {

    public static void showSoftKeyboard(Context context, View v) {
        v.requestFocus();
        InputMethodManager imm = (InputMethodManager)context.getSystemService("input_method");
        imm.toggleSoftInput(2, 1);
    }

    public static void hideKeyBoard(Activity mActivity, View mGetView) {
        try {
            ((InputMethodManager)mActivity.getSystemService("input_method")).hideSoftInputFromWindow(mGetView.getRootView().getWindowToken(), 0);
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    public static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        Log.e("lat1" + lat1 + "  long1" + lng1, "lat 2" + lat2 + "  long2" + lng2);
        float[] result = new float[3];
        Location.distanceBetween(lat1, lng1, lat2, lng2, result);
        result[0] /= 1000.0F;
        return result[0];
    }

    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager)c.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
