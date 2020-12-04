package com.example.fitnessdemo.MR;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class FileUtils {
    public static Intent getWordFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }
}
