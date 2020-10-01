package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.parse.ParseUser;

public class SocialMediaActiviry extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media_activiry);
        setTitle("Social Media App");
        toolbar=findViewById(R.id.mytoolbar);
        //toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        viewPager=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tablayout);
        tabAdapter=new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu_logout){
           ParseUser.getCurrentUser().logOut();
           finish();
           Intent intent=new Intent(SocialMediaActiviry.this,SignUp.class);
           startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}