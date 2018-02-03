package whatsappclone.ruans.com.whatsappclone.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import whatsappclone.ruans.com.whatsappclone.R;
import whatsappclone.ruans.com.whatsappclone.adapter.TabAdapter;
import whatsappclone.ruans.com.whatsappclone.config.ConfiguracaoFirebase;
import whatsappclone.ruans.com.whatsappclone.helper.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Whatsapp");
        setSupportActionBar(toolbar);

        slidingTabLayout = findViewById(R.id.sld_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccent));
        viewPager = findViewById(R.id.vp_pagina);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.item_sair:
                deslogarUsuario();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void deslogarUsuario(){
        firebaseAuth.signOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
