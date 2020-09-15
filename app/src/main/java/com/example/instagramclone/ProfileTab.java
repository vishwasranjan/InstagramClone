package com.example.instagramclone;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName,edtProfileAge,edtProfileProfession,edtProfileHobby,edtProfileBio;
    private Button btnProfileSubmit;
    final ParseUser parseUser=ParseUser.getCurrentUser();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileTab.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileTab newInstance(String param1, String param2) {
        ProfileTab fragment = new ProfileTab();
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
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        btnProfileSubmit=view.findViewById(R.id.btnProfileSubmit);
        edtProfileName=view.findViewById(R.id.edtProfileName);
        edtProfileAge=view.findViewById(R.id.edtProfileAge);
        edtProfileBio=view.findViewById(R.id.edtProfileBio);
        edtProfileHobby=view.findViewById(R.id.edtProfileHobby);
        edtProfileProfession=view.findViewById(R.id.edtProfileProfession);
        if (parseUser.get("Name")==null){
                edtProfileName.setText("");
            }
        else
            {
                edtProfileName.setText(parseUser.get("Name") + "");
            }
        if (parseUser.get("Age")==null){
                edtProfileAge.setText("");
            }
        else {
                edtProfileAge.setText(parseUser.get("Age") + "");
            }
        if (parseUser.get("Bio")==null){
                edtProfileBio.setText("");
            }
        else {
                edtProfileBio.setText(parseUser.get("Bio") + "");
            }
        if (parseUser.get("Hobby")==null){
                edtProfileHobby.setText("");
            }
        else {
                edtProfileHobby.setText(parseUser.get("Hobby") + "");
            }
        if (parseUser.get("Profession")==null){
                edtProfileProfession.setText("");
            }
        else {
                edtProfileProfession.setText(parseUser.get("Profession") + "");
            }

        btnProfileSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog=new ProgressDialog(getContext());
                progressDialog.setMessage("Saving");
                progressDialog.show();
                parseUser.put("Name",edtProfileName.getText().toString());
                parseUser.put("Age",edtProfileAge.getText().toString());
                parseUser.put("Bio",edtProfileBio.getText().toString());
                parseUser.put("Profession",edtProfileProfession.getText().toString());
                parseUser.put("Hobby",edtProfileHobby.getText().toString());
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null)
                        {
                            Toast.makeText(getContext(),edtProfileName.getText().toString()+" Saved sucessfully",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.cancel();
                    }
                });
            }
        });
        return view;
    }
}
