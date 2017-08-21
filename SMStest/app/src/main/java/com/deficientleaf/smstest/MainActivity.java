package com.deficientleaf.smstest;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;





public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    //fragment的宣告
     FragmentManager manager;
     FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();




        fragmentChange();


    }







    private void fragmentChange() {
        manager = getSupportFragmentManager();
        final BlankFragment1 fragment1 = new BlankFragment1();
        final BlankFragment2 fragment2 = new BlankFragment2();
        final BlankFragment3 fragment3 = new BlankFragment3();
        //transaction.addToBackStack(null);
        //預設畫面設為fragment3
        transaction = manager.beginTransaction();
        transaction.add(R.id.draw_content,fragment2).show(fragment3).commit();


        findViewById(R.id.b_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                transaction = manager.beginTransaction();
                if (!fragment1.isAdded()) {

                    transaction.hide(fragment2).hide(fragment3).add(R.id.draw_content,fragment1).show(fragment1).commit(); // 隱藏當前頁面 並新增明細頁面
                } else {
                    transaction.hide(fragment2).hide(fragment3).show(fragment1).commit(); //隱藏當前頁面 呼叫明細頁面
                }


            }
        });
        //側選單按鈕監聽位置
        findViewById(R.id.b_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                transaction = manager.beginTransaction();
                if (!fragment2.isAdded()) {	// 先判断是否被add過 如果沒有Add過 代表是第一次呼叫 則需要先add 其餘時候都直接使用show進行顯示
                    // hide裡面放的是自己當前所在的Fragment頁面，後面一定要.this才能夠指向現在要隱藏的頁面，否則會直接幫你生成一個新的頁面
                    transaction.hide(fragment1).hide(fragment3).add(R.id.draw_content,fragment2).show(fragment2).commit(); // 隱藏當前頁面 並新增明細頁面
                } else {
                    transaction.hide(fragment1).hide(fragment3).show(fragment2).commit(); //隱藏當前頁面 呼叫明細頁面
                }
            }
        });

        findViewById(R.id.b_work).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                transaction = manager.beginTransaction();
                if (!fragment3.isAdded()) {

                    transaction.hide(fragment2).hide(fragment1).add(R.id.draw_content,fragment3).show(fragment3).commit(); // 隱藏當前頁面 並新增明細頁面
                } else {
                    transaction.hide(fragment2).hide(fragment1).show(fragment3).commit(); //隱藏當前頁面 呼叫明細頁面
                }
            }
        });


        findViewById(R.id.b_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    public void goMap(View v){
        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        startActivity(intent);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


