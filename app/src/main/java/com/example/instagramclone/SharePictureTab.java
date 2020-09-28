package com.example.instagramclone;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SharePictureTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SharePictureTab extends Fragment implements View.OnClickListener {
    ImageView image;
    EditText edtDetail;
    Button btnShare;
    Bitmap bitmap;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SharePictureTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SharePictureTab.
     */
    // TODO: Rename and change types and number of parameters
    public static SharePictureTab newInstance(String param1, String param2) {
        SharePictureTab fragment = new SharePictureTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_share_picture_tab, container, false);
        edtDetail=view.findViewById(R.id.edtDetail);
        btnShare=view.findViewById(R.id.btnShare);
        image=view.findViewById(R.id.image);
        image.setOnClickListener(SharePictureTab.this);
        btnShare.setOnClickListener(SharePictureTab.this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.image:
                if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                }
                else {
                    getImage();
                }
                break;
            case R.id.btnShare:
                if (bitmap!=null)
                {
                    if (edtDetail.getText().toString().equals(""))
                    {
                        Toast.makeText(getContext(),"Detail is required",Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                            ByteArrayOutputStream byteArrayInputStream=new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayInputStream);
                            byte[] bytes=byteArrayInputStream.toByteArray();
                            ParseFile parseFile=new ParseFile("image.png",bytes);
                            ParseObject parseObject=new ParseObject("Image");
                            parseObject.put("picture",parseFile);
                            parseObject.put("Detail",edtDetail.getText().toString());
                            parseObject.put("Username", ParseUser.getCurrentUser().getUsername());
                            final ProgressDialog progressDialog=new ProgressDialog(getContext());
                            progressDialog.setMessage("Updating...");
                            progressDialog.show();
                            parseObject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e==null)
                                    {
                                        Toast.makeText(getContext(),"Saved",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    }
                else
                {
                    Toast.makeText(getContext(),"Select a image",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getImage() {
       //Toast.makeText(getContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,200);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100)
        {
            if (grantResults.length==1&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                getImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==200)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    try
                    {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        image.setImageBitmap(bitmap);
                    } catch (IOException e)
                        {
                        e.printStackTrace();
                         }
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}