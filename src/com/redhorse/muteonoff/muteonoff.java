package com.redhorse.muteonoff;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

public class muteonoff extends Activity {

	private SharedPreferences share;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		share = this.getPreferences(MODE_PRIVATE);

		AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		
		if (am.getRingerMode()!=AudioManager.RINGER_MODE_SILENT) {
			Editor editor = share.edit();
			editor.putInt("RINGER_MODE", am.getRingerMode());
			editor.putInt("VIBRATE_TYPE_RINGER", am.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER));
			editor.putInt("VIBRATE_TYPE_NOTIFICATION", am.getVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION));
			try {
				editor.putInt("SYSTEM_MODE_RINGER", Settings.System.getInt(getContentResolver(),Settings.System.MODE_RINGER));
			} catch (SettingNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			editor.commit();// 提交刷新数据		
	        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	        am.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
	        am.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_OFF);
//	        Settings.System.putInt(getContentResolver(),Settings.System.MODE_RINGER,0);
		} else {
	        am.setRingerMode(share.getInt("RINGER_MODE", AudioManager.MODE_NORMAL));
	        am.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, share.getInt("VIBRATE_TYPE_RINGER", AudioManager.VIBRATE_SETTING_ON));
	        am.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, share.getInt("VIBRATE_TYPE_NOTIFICATION", AudioManager.VIBRATE_SETTING_ON));
//	        Settings.System.putInt(getContentResolver(),Settings.System.MODE_RINGER,share.getInt("SYSTEM_MODE_RINGER", 0));			
		}
		finish();
    }
}