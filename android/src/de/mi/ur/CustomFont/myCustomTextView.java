package de.mi.ur.CustomFont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import de.mi.ur.Constants;

/**
 * Created by maxiwindl on 01.09.16.
 */
public class myCustomTextView extends TextView {

    public myCustomTextView(Context context) {
        super(context);
        utilizeMyFont(context);
    }

    public myCustomTextView(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        utilizeMyFont(context);
    }

    public myCustomTextView(Context context, AttributeSet attrSet, int style) {
        super(context, attrSet, style);
        utilizeMyFont(context);

    }

    private void utilizeMyFont(Context context) {
        Typeface myFont = FontSaver.getTypeface(Constants.FONT, context);
        setTypeface(myFont);

    }
}
