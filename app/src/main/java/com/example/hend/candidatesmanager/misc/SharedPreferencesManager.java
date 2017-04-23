package com.example.hend.candidatesmanager.misc;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hend on 4/21/2017.
 */

public class SharedPreferencesManager {

	private static final String SHARED_PREFERENCE_NAME = "Pref";
	private static SharedPreferencesManager mStorageManger;
	private SharedPreferences mSharedPreferences;

	private SharedPreferencesManager(Context context) {
		mSharedPreferences = context.getSharedPreferences(
				SHARED_PREFERENCE_NAME, 0);
	}

	public static SharedPreferencesManager getInstance(Context context) {
		if (mStorageManger == null)
			mStorageManger = new SharedPreferencesManager(context);
		return mStorageManger;
	}

	/**
	 * Get the String value from {@link SharedPreferences}
	 * 
	 * @param key
	 * @return empty if we don't find the value
	 */
	public String getStringValue(String key, String defaultValue) {

		String value = defaultValue;

		if (key != null) {
			value = mSharedPreferences.getString(key, defaultValue);
		}

		return value;
	}

	/**
	 * Get the Long value from {@link SharedPreferences}
	 * 
	 * @param key
	 * @return empty if we don't find the value
	 */
	public long getLongValue(String key, Long defaultValue) {

		long value = defaultValue;

		if (key != null) {
			value = mSharedPreferences.getLong(key, defaultValue);
		}

		return value;
	}

	/**
	 * Get the Int value from {@link SharedPreferences}
	 * 
	 * @param key
	 * @return empty if we don't find the value
	 */
	public int getIntValue(String key, int defaultValue) {

		int value = defaultValue;

		if (key != null) {
			value = mSharedPreferences.getInt(key, defaultValue);
		}

		return value;
	}

	/**
	 * Get the Boolean value from {@link SharedPreferences}
	 * 
	 * @param key
	 * @return empty if we don't find the value
	 */
	public boolean getBooleanValue(String key, boolean defaultValue) {

		boolean value = defaultValue;

		if (key != null) {
			value = mSharedPreferences.getBoolean(key, defaultValue);
		}

		return value;
	}

	/**
	 * Add String value in {@link SharedPreferences}
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addString(String key, String value) {

		boolean added = false;

		if (key != null && value != null) {
			SharedPreferences.Editor editor = mSharedPreferences.edit()
					.putString(key, value);
			added = editor.commit();
		}

		return added;
	}

	/**
	 * Add Int value in {@link SharedPreferences}
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addInt(String key, int value) {

		boolean added = false;

		if (key != null) {
			SharedPreferences.Editor editor = mSharedPreferences.edit().putInt(
					key, value);
			added = editor.commit();
		}

		return added;
	}

	/**
	 * Add Long value in {@link SharedPreferences}
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addLong(String key, long value) {

		boolean added = false;

		if (key != null) {
			SharedPreferences.Editor editor = mSharedPreferences.edit()
					.putLong(key, value);
			added = editor.commit();
		}

		return added;
	}

	/**
	 * Add Boolean value in {@link SharedPreferences}
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addBoolean(String key, boolean value) {

		boolean added = false;

		if (key != null) {
			SharedPreferences.Editor editor = mSharedPreferences.edit()
					.putBoolean(key, value);
			added = editor.commit();
		}

		return added;
	}

	/**
	 * Clear the content of storage
	 */
	public boolean clear() {

		SharedPreferences.Editor editor = mSharedPreferences.edit().clear();
		return editor.commit();
	}

	/**
	 * Remove specific value from the content of storage
	 */
	public boolean removeValue(String key) {

		SharedPreferences.Editor editor = mSharedPreferences.edit().remove(key);
		return editor.commit();
	}

}
