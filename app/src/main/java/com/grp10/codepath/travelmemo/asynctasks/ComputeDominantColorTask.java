package com.grp10.codepath.travelmemo.asynctasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette;

import com.bumptech.glide.Glide;
import com.grp10.codepath.travelmemo.R;
import com.grp10.codepath.travelmemo.interfaces.DominantColor;

import java.util.concurrent.ExecutionException;

/**
 * Created by akshatjain on 8/16/16.
 */
public class ComputeDominantColorTask extends AsyncTask<Integer,Void,Integer> {
    private Context mContext;
    private DominantColor mDominantColor;
    public ComputeDominantColorTask(Context context, DominantColor dominantColorImpl) {
        mContext = context;
        mDominantColor = dominantColorImpl;
    }

    @Override
    protected Integer doInBackground(Integer... resourceIds) {

        int resourceId = resourceIds[0];
        Integer mainColor = mContext.getResources().getColor(R.color.md_blue_50);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext).load(resourceId).asBitmap().into(-1,-1).get();
            Palette p = new Palette.Builder(bitmap).generate();
            mainColor = p.getVibrantColor(mainColor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return mainColor;
    }

    @Override
    protected void onPostExecute(Integer color) {
        super.onPostExecute(color);
        if(mDominantColor != null && color != null)
            mDominantColor.onDominantColorComputed(color);
    }
}
