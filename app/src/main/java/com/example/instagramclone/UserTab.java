package com.example.instagramclone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserTab extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    ListView listView;
    ArrayList arrayList;
    ArrayAdapter arrayAdapter;
    TextView textViewData;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserTab.
     */
    // TODO: Rename and change types and number of parameters
    public static UserTab newInstance(String param1, String param2) {
        UserTab fragment = new UserTab();
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
        View view= inflater.inflate(R.layout.fragment_user_tab, container, false);
        listView=view.findViewById(R.id.listview);
        textViewData=view.findViewById(R.id.textView2);
        String msg="";
        arrayList=new ArrayList();
        arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setOnItemClickListener(UserTab.this);
        listView.setOnItemLongClickListener(UserTab.this);
        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
           @Override
           public void done(List<ParseUser> objects, ParseException e) {
               if (e==null)
               {
                   for (ParseUser user:objects)
                   {
                    arrayList.add(user.getUsername());
                   }
                   listView.setAdapter(arrayAdapter);
                   textViewData.animate().alpha(0).setDuration(2000);
                   listView.setVisibility(View.VISIBLE);
               }
           }
       });
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getContext(),UserPost.class);
        intent.putExtra("Username",arrayList.get(position)+"");
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, int position, long id) {
        final ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
        parseQuery.whereEqualTo("username",arrayList.get(position));
       parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
           @Override
           public void done(ParseUser object, ParseException e) {
               if (object!=null&& e==null)
               {
                    //Toast.makeText(getContext(),object.get("Profession")+"",Toast.LENGTH_SHORT).show();
                   final AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext())
                           .setTitle(object.getUsername()+"'s Detail")
                           .setMessage("Name - "+object.get("Name")+"\n"
                           +"Username - "+object.get("username")+"\n"
                           +"Profession"+object.get("Profession")+"\n"
                           +"Bio - "+object.get("Bio")+"\n"
                           +"Hobby - "+object.get("Hobby"));
                   alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                       }
                   });
                   alertDialog.setCancelable(false);
                   alertDialog.setIcon(R.drawable.people);
                   alertDialog.show();

               }
               else {
                   Toast.makeText(getContext(),"Nothing to show",Toast.LENGTH_SHORT).show();
               }
           }
       });
        return true;
    }
}