package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UserPost extends AppCompatActivity {
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);
        linearLayout=findViewById(R.id.linearlayout);
        Intent received=getIntent();
        final String receivedUsername=received.getStringExtra("Username");
        setTitle(receivedUsername+"'s post");
        final ProgressDialog progressDialog=new ProgressDialog(UserPost.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        final ParseQuery<ParseObject> parseQuery=new ParseQuery<ParseObject>("Image");
        parseQuery.whereEqualTo("Username",receivedUsername);
        parseQuery.orderByDescending("createdAt");

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size()>0&&e==null)
                {
                    for (ParseObject object : objects)
                    {
                        final TextView postdescription=new TextView(UserPost.this);
                        postdescription.setText(object.get("Detail")+"");
                        ParseFile postimage=(ParseFile)object.get("picture");
                        postimage.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (data.length>0&&e==null)
                                {
                                    Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postimage=new ImageView(UserPost.this);
                                    LinearLayout.LayoutParams imageview_param=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageview_param.setMargins(5,5,5,5);
                                    postimage.setLayoutParams(imageview_param);
                                    postimage.setScaleType(ImageView.ScaleType.FIT_XY);
                                    postimage.setImageBitmap(bitmap);
                                    LinearLayout.LayoutParams description_param=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    description_param.setMargins(10,10,10,10);
                                    postdescription.setLayoutParams(description_param);
                                    postdescription.setTextColor(Color.RED);
                                    postdescription.setGravity(Gravity.CENTER_HORIZONTAL);
                                    postdescription.setTextSize(25f);
                                    linearLayout.addView(postimage);
                                    linearLayout.addView(postdescription);
                                }
                            }
                        });
                    }
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(UserPost.this,receivedUsername+" doesn't have any post yet",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}