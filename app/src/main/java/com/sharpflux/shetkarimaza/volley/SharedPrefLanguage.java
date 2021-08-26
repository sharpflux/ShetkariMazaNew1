package com.sharpflux.shetkarimaza.volley;
import android.content.Context;
import android.content.SharedPreferences;
import com.sharpflux.shetkarimaza.model.LanguageModal;


public class SharedPrefLanguage {

    private static final String SHARED_PREF_NAME = "KisanLanguae";
    private static final String KEY_LANGUAGE = "Language";
    private static SharedPrefLanguage mInstance;
    private static Context mCtx;

    private SharedPrefLanguage(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefLanguage getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefLanguage(context);
        }
        return mInstance;
    }

    public void LanguageSetup(LanguageModal lang) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LANGUAGE, lang.getLanguage());
        editor.apply();
    }
    public LanguageModal getLanguage() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new LanguageModal(
                sharedPreferences.getString(KEY_LANGUAGE, null)
        );
    }
}


