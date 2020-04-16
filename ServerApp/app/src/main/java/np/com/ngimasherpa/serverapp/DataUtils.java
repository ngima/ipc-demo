package np.com.ngimasherpa.serverapp;

import android.content.Context;

public class DataUtils {

    private static final String KEY_SHARED_PREF = "KEY_SHARED_PREF";
    private static final String KEY_SAVED_DATA_NAME = "KEY_SAVED_DATA_NAME";
    private static final String KEY_SAVED_DATA_CAST = "KEY_SAVED_DATA_CAST";

    public static void saveName(Context context, String name) {
        context.getSharedPreferences(KEY_SHARED_PREF, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_SAVED_DATA_NAME, name)
                .apply();
    }

    public static String getName(Context context) {
        return context.getSharedPreferences(KEY_SHARED_PREF, Context.MODE_PRIVATE)
                .getString(KEY_SAVED_DATA_NAME, "");
    }

    public static void saveCast(Context context, String cast) {
        context.getSharedPreferences(KEY_SHARED_PREF, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_SAVED_DATA_CAST, cast)
                .apply();
    }

    public static String getCast(Context context) {
        return context.getSharedPreferences(KEY_SHARED_PREF, Context.MODE_PRIVATE)
                .getString(KEY_SAVED_DATA_CAST, "");
    }
}
