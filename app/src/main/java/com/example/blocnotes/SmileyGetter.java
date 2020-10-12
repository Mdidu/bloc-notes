package com.example.blocnotes;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;

public class SmileyGetter implements Html.ImageGetter {
    protected Context context = null;
    public SmileyGetter(Context c) {
        context = c;
    }

    @Override
    public Drawable getDrawable(String source) {
        Drawable getter = null;

        Resources resources = context.getResources();

        if(source.compareTo("smile") == 0) {
            getter = resources.getDrawable(R.drawable.smile);
        } else if(source.compareTo("happy") == 0) {
            getter = resources.getDrawable(R.drawable.happy);
        } else if(source.compareTo("clin") == 0){
            getter = resources.getDrawable(R.drawable.clin);
        }
        //On délimite l'image
        getter.setBounds(0, 0, getter.getIntrinsicHeight(), getter.getIntrinsicWidth());
        return getter;
    }
}
