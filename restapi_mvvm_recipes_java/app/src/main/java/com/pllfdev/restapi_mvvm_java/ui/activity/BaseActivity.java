package com.pllfdev.restapi_mvvm_java.ui.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.pllfdev.restapi_mvvm_java.databinding.ActivityBaseBinding;

import org.xmlpull.v1.XmlPullParser;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityBaseBinding baseBinding;
    public ProgressBar mProgressBar;

    @Override
    public void setContentView(View layoutID) {
        baseBinding = ActivityBaseBinding.inflate(getLayoutInflater());
        baseBinding.activityContent.addView(layoutID);
        //getLayoutInflater().inflate((XmlPullParser) layoutID,baseBinding.activityContent,true);
        mProgressBar = baseBinding.progressBar;
        super.setContentView(baseBinding.getRoot());

/*        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base,null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar);
        getLayoutInflater().inflate(layoutResID,frameLayout,true);
        super.setContentView(constraintLayout);*/
    }

    public void showProgressBar(boolean visibility){
        mProgressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

}
