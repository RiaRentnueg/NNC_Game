package de.mi.ur.CustomFont;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by maxiwindl on 01.09.16.
 */
public class FontSaver {

    public static HashMap<String, Typeface> fontSaver = new HashMap<>();

    public static Typeface getTypeface(String font, Context context) {
        Typeface typeface = fontSaver.get(font);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), font);
            } catch (Exception e) {
                e.printStackTrace();
            }
            fontSaver.put(font, typeface);
        }
        return typeface;
    }
}
