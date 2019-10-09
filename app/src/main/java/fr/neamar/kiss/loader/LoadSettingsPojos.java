package fr.neamar.kiss.loader;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import fr.neamar.kiss.R;
import fr.neamar.kiss.pojo.SettingsPojo;
import fr.neamar.kiss.utils.CompletedFuture;

public class LoadSettingsPojos extends LoadPojos<SettingsPojo> {

    public LoadSettingsPojos(Context context) {
        super(context, "setting://");
    }

    @Override
    protected ArrayList<SettingsPojo> doInBackground(Void... params) {
        ArrayList<SettingsPojo> settings = new ArrayList<>();

        final Context context = this.context.get();
        if(context == null) {
            return settings;
        }

        PackageManager pm = context.getPackageManager();
        settings.add(createPojo(context, R.string.settings_airplane,
                Settings.ACTION_AIRPLANE_MODE_SETTINGS, R.drawable.setting_airplane));
        settings.add(createPojo(context, R.string.settings_device_info,
                Settings.ACTION_DEVICE_INFO_SETTINGS, R.drawable.setting_info));
        settings.add(createPojo(context, R.string.settings_applications,
                Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS, R.drawable.setting_apps));
        settings.add(createPojo(context, R.string.settings_connectivity,
                Settings.ACTION_WIRELESS_SETTINGS, R.drawable.setting_wifi));
        settings.add(createPojo(context, R.string.settings_storage,
                Settings.ACTION_INTERNAL_STORAGE_SETTINGS, R.drawable.setting_storage));
        settings.add(createPojo(context, R.string.settings_accessibility,
                Settings.ACTION_ACCESSIBILITY_SETTINGS, R.drawable.setting_accessibility));
        settings.add(createPojo(context, R.string.settings_battery,
                Intent.ACTION_POWER_USAGE_SUMMARY, R.drawable.setting_battery));
        settings.add(createPojo(context, R.string.settings_tethering, "com.android.settings",
                "com.android.settings.TetherSettings", R.drawable.setting_tethering));
        settings.add(createPojo(context, R.string.settings_sound,
                Settings.ACTION_SOUND_SETTINGS, R.drawable.setting_dev));
        settings.add(createPojo(context, R.string.settings_display,
                Settings.ACTION_DISPLAY_SETTINGS, R.drawable.setting_dev));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
                settings.add(createPojo(context, R.string.settings_nfc,
                        Settings.ACTION_NFC_SETTINGS, R.drawable.setting_nfc));
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            settings.add(createPojo(context, R.string.settings_dev,
                    Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS, R.drawable.setting_dev));
        }
        return settings;
    }

    private SettingsPojo createPojo(Context context, @StringRes int nameId, String packageName, String settingName,
                                    @DrawableRes int resId) {
        String name = context.getString(nameId);
        Drawable resource = context.getResources().getDrawable(resId);

        SettingsPojo pojo = new SettingsPojo(getId(settingName), settingName, packageName,
                new CompletedFuture<>(resource));
        assingName(pojo, name);
        return pojo;
    }

    private SettingsPojo createPojo(Context context, @StringRes int nameId, String settingName,
                                    @DrawableRes  int resId) {
        String name = context.getString(nameId);
        Drawable resource = context.getResources().getDrawable(resId);

        SettingsPojo pojo = new SettingsPojo(getId(settingName), settingName, new CompletedFuture<>(resource));
        assingName(pojo, name);
        return pojo;
    }

    private String getId(String settingName) {
        return pojoScheme + settingName.toLowerCase(Locale.ENGLISH);
    }

    private void assingName(SettingsPojo pojo, String name) {
        pojo.setName(name, true);
    }

}
