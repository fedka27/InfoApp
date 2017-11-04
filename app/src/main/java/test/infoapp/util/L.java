package test.infoapp.util;


import test.infoapp.BuildConfig;

@SuppressWarnings("unused")
public final class L {

    public static final String TAG = "L_";

    private L() {
    }

    public static int v(String msg) {
        return v(TAG, msg);
    }

    public static int v(String tag, String msg) {
        if (!BuildConfig.DEBUG) {
            return 0;
        }
        return android.util.Log.v(TAG + tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (!BuildConfig.DEBUG) {
            return 0;
        }
        return android.util.Log.v(TAG + tag, msg, tr);
    }

    public static int d(String msg) {
        return d(TAG, msg);
    }

    public static int d(String tag, String msg) {
        if (!BuildConfig.DEBUG) {
            return 0;
        }
        return android.util.Log.d(TAG + tag, msg);
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (!BuildConfig.DEBUG) {
            return 0;
        }
        return android.util.Log.d(TAG + tag, msg, tr);
    }

    public static int i(String msg) {
        return i(TAG, msg);
    }

    public static int i(String tag, String msg) {
        if (!BuildConfig.DEBUG) {
            return 0;
        }
        return android.util.Log.i(TAG + tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (!BuildConfig.DEBUG) {
            return 0;
        }
        return android.util.Log.i(TAG + tag, msg, tr);
    }

    public static int w(String msg) {
        return w(TAG, msg);
    }

    public static int w(String tag, String msg) {
        if (!BuildConfig.DEBUG) {
            return 0;
        }
        return android.util.Log.w(TAG + tag, msg);
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (!BuildConfig.DEBUG) {
            return 0;
        }
        return android.util.Log.w(TAG + tag, msg, tr);
    }

    public static int w(String tag, Throwable tr) {
        if (!BuildConfig.DEBUG) {
            return 0;
        }
        return android.util.Log.w(TAG + tag, tr);
    }

    public static int e(String msg) {
        return e(TAG, msg);
    }

    public static int e(String tag, String msg) {
        if (!BuildConfig.DEBUG) {
            return 0;
        }
        return android.util.Log.e(TAG + tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (!BuildConfig.DEBUG) {
            return 0;
        }
        return android.util.Log.e(TAG + tag, msg, tr);
    }

    public static void printStackTrace() {
        printStackTrace(TAG);
    }

    public static void printStackTrace(String tag) {
        if (BuildConfig.DEBUG) {
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                android.util.Log.i(TAG + tag, ste.toString());
            }
        }
    }

    public static void printStackTrace(Throwable t) {
        if (BuildConfig.DEBUG) {
            t.printStackTrace();
        }
    }
}
