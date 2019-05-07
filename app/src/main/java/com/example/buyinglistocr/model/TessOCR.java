package com.example.buyinglistocr.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.example.buyinglistocr.R;
import com.googlecode.tesseract.android.TessBaseAPI;

public class TessOCR {
    private final TessBaseAPI mTess;

    public TessOCR(Context context, String language) {

        mTess = new TessBaseAPI();
        String path = "android.resource://" + context.getPackageName() + "/";
        String datapath = context.getPackageResourcePath();
        System.out.println(path);
        mTess.init(        path, "fra");
    }

    public String getOCRResult(Bitmap bitmap) {
        mTess.setImage(bitmap);
        return mTess.getUTF8Text();
    }

    public void onDestroy() {
        if (mTess != null) mTess.end();
    }
}