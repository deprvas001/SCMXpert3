package com.example.scmxpert.base;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.scmxpert.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    public void showProgressDialog(String message) {
        if(progressDialog == null)
            progressDialog = new ProgressDialog(this);

        if (!progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (progressDialog !=null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showAlertDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                   dialog.dismiss();
                });
        builder.show();
    }

    public String getDatetime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss SSSZ");
        String timezone = sdf.format(c.getTime());
        return timezone;
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}
