package com.wolaidai.year500.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created by danielzhang on 15/11/2.
 */
public class SharedPreferencesHelper {

    public static boolean getBoolean(Context context, String name, String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static int getInt(Context context, String name, String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static String getString(Context context, String name, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static float getFloat(Context context, String name, String key, float defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    public static long getLong(Context context, String name, String key, long defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    public static Set<String> getStringSet(Context context, String name, String key, Set<String> defaultValues) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getStringSet(key, defaultValues);
    }

    public static Map<String, ?> getAll(Context context, String name) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    public static void putBoolean(Context context, String name, String key, boolean value) {
        SharedPreferences.Editor et = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        et.putBoolean(key, value);
        et.commit();
    }

    public static void putInt(Context context, String name, String key, int value) {
        SharedPreferences.Editor et = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        et.putInt(key, value);
        et.commit();
    }

    public static void putString(Context context, String name, String key, String value) {
        SharedPreferences.Editor et = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        et.putString(key, value);
        et.commit();
    }

    public static void putFloat(Context context, String name, String key, float value) {
        SharedPreferences.Editor et = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        et.putFloat(key, value);
        et.commit();
    }

    public static void putLong(Context context, String name, String key, long value) {
        SharedPreferences.Editor et = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        et.putLong(key, value);
        et.commit();
    }

    public static void putStringSet(Context context, String name, String key, Set<String> value) {
        SharedPreferences.Editor et = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        et.putStringSet(key, value);
        et.commit();
    }

}
