package com.deficientleaf.smstest;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;




/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment1 extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener{

    static final int MAX = 20 ;
    static final String TB_NAME = "hotlist" ;
    static final String[] FROM = new String[]{"name","phone","email"};
    SimpleCursorAdapter adapter;
    EditText etName,etPhone,etEmail;
    Button btInsert,btUpdate,btDelete;
    ListView lv;
    Cursor cur;
    private MyDBHelper db;


    public BlankFragment1() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_blank_fragment1, container, false);
        etName = (EditText)rootview.findViewById(R.id.etName);
        etPhone = (EditText)rootview.findViewById(R.id.etPhone);
        etEmail = (EditText)rootview.findViewById(R.id.etEmail);
        btInsert = (Button)rootview.findViewById(R.id.btInsert);
        btUpdate = (Button)rootview.findViewById(R.id.btUpdate);
        btDelete = (Button)rootview.findViewById(R.id.btDelete);
        //建立資料庫

        db =MyDBHelper.getInstance(getActivity());
        cur = db.getReadableDatabase().query(TB_NAME,null,null,null,null,null,null);

        //建立資料表
        adapter = new SimpleCursorAdapter(getActivity(),R.layout.item,cur,FROM,
                new  int[]{R.id.name,R.id.phone, R.id.email}, 0 );

        lv = (ListView)rootview.findViewById(R.id.lv);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(this);
        if(cur.getCount()==0) {
            addData("王大明", "05-22113355", "akfgajw65@gmail.com");
            addData("李小強", "08-31864512", "lkhvui1748@gmail.com");

        }
        requery();
        //聯絡人5個按鈕行為
        buttonAction(rootview);


        return rootview;
    }

    private void buttonAction(View rootview) {
        rootview.findViewById(R.id.btInsert).setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v) {
                  String nameStr = etName.getText().toString().trim();
                  String phoneStr = etPhone.getText().toString().trim();
                  String emailStr = etEmail.getText().toString().trim();
                  if(nameStr.length()==0 || phoneStr.length()==0 || emailStr.length()==0)  return;
                  addData(nameStr,phoneStr,emailStr);
                  requery();
              }
                 });
        rootview.findViewById(R.id.btUpdate).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String nameStr = etName.getText().toString().trim();
                String phoneStr = etPhone.getText().toString().trim();
                String emailStr = etEmail.getText().toString().trim();
                if(nameStr.length()==0 || phoneStr.length()==0 || emailStr.length()==0)  return;
                update(nameStr,phoneStr,emailStr,cur.getInt(0));
                requery();
            }
        });

        rootview.findViewById(R.id.btDelete).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                db.getWritableDatabase().delete(TB_NAME,"_id="+cur.getInt(0),null);
                requery();
            }
        });
        rootview.findViewById(R.id.btCall).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String uri = "tel:"+ cur.getString(cur.getColumnIndex(FROM[1]));
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(it);
            }
        });
        rootview.findViewById(R.id.btSend).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               String uri = "mailto:"+ cur.getString(cur.getColumnIndex(FROM[2]));
                Intent it = new Intent(Intent.ACTION_SENDTO,Uri.parse(uri));
                startActivity(it);

            }
        });
    }

    private void addData(String name,String phone,String email){
        ContentValues cv = new ContentValues(3);
        cv.put(FROM[0],name);
        cv.put(FROM[1],phone);
        cv.put(FROM[2],email);
        db.getWritableDatabase().insert(TB_NAME,null,cv);
    }
    private void update(String name, String phone ,String email, int id) {
        ContentValues cv = new ContentValues(3);
        cv.put(FROM[0], name);
        cv.put(FROM[1], phone);
        cv.put(FROM[2],email);
        //更新id所指的紀錄
        db.getWritableDatabase().update(TB_NAME,cv,"_id="+id,null);

    }

    private void requery(){
        cur = db.getReadableDatabase().query(TB_NAME,null,null,null,null,null,null);
        adapter.changeCursor(cur);   //更改adapter的Cursor
        if (cur.getCount()==MAX){
            btInsert.setEnabled(false);
        }else{
            btInsert.setEnabled(true);
        }

        btUpdate.setEnabled(false);
        btDelete.setEnabled(false);

    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cur.moveToPosition(position);   //移動cursor至使用者選取的項目
        //讀取姓名，電話資料並顯示
        etName.setText(cur.getString(cur.getColumnIndex(FROM[0])));
        etPhone.setText(cur.getString(cur.getColumnIndex(FROM[1])));
        etEmail.setText(cur.getString(cur.getColumnIndex(FROM[2])));
        btUpdate.setEnabled(true);
        btDelete.setEnabled(true);

    }

    @Override
    public void onClick(View v) {

    }



}