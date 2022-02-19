package com.example.simplenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.Date;

public class EditTambahNote extends AppCompatActivity {
    int STATUS;
    int ID_NOTE;
    Note dbhelper;
    EditText id_note,title_note,content_note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tambah_note);
        getSupportActionBar().hide();
        dbhelper = new Note(this);

        Button tambah_note = (Button) findViewById(R.id.tambah_note);
        Button simpan_note = (Button)findViewById(R.id.simpan_note);
        Button lihat_note = (Button) findViewById(R.id.lihat_note);

        id_note = (EditText) findViewById(R.id.id_note);
        title_note = (EditText) findViewById(R.id.title);
        content_note = (EditText) findViewById(R.id.content);

        Bundle bundel  = getIntent().getExtras();
        STATUS = bundel.getInt(Setting.STATUS);
        if(STATUS==Setting.EDIT_DATA_LAMA){ //cek flag nya dulu
            ID_NOTE = (int) bundel.getInt(Note.clm_id);
            Cursor cursor = dbhelper.oneData((long)ID_NOTE);
            cursor.moveToFirst();//untuk memastikan pertama!
            System.out.println("ID NOTE: "+ID_NOTE);
            String string_id = cursor.getString(cursor.getColumnIndexOrThrow(Note.clm_id));
            String string_tanggal = cursor.getString(cursor.getColumnIndexOrThrow(Note.clm_tanggal));
            String string_title = cursor.getString(cursor.getColumnIndexOrThrow(Note.clm_title));
            String string_content = cursor.getString(cursor.getColumnIndexOrThrow(Note.clm_content));

            id_note.setText(String.valueOf(ID_NOTE));
            title_note.setText(string_title);
            content_note.setText(string_content);
            cursor.close();
        }else{
            // tak lakukan apapun
        }

        simpan_note.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.simpan_note:
                        if(STATUS==Setting.TAMBAH_DATA_BARU){
                            if(title_note.getText().toString().equals("")){
                                Toast.makeText(EditTambahNote.this, "Tak Jadi Simpan", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Date currentTime = Calendar.getInstance().getTime();
                            ContentValues values  = new ContentValues();
                            values.put(dbhelper.clm_title,title_note.getText().toString().trim());
                            values.put(dbhelper.clm_content,content_note.getText().toString().trim());
                            values.put(dbhelper.clm_tanggal,currentTime.toString());
                            dbhelper.insertData(values);
                            Toast.makeText(EditTambahNote.this, "1 Note telah tersimpan", Toast.LENGTH_SHORT).show();
                            Intent home = new Intent(EditTambahNote.this,TampilData.class);
                            startActivity(home);
                        }
                        if(STATUS==Setting.EDIT_DATA_LAMA){
                            Date currentTime = Calendar.getInstance().getTime();
                            ContentValues values  = new ContentValues();
                            values.put(dbhelper.clm_id,String.valueOf(ID_NOTE));
                            values.put(dbhelper.clm_title,title_note.getText().toString().trim());
                            values.put(dbhelper.clm_content,content_note.getText().toString().trim());
                            values.put(dbhelper.clm_tanggal,currentTime.toString());
                            dbhelper.updateData(values,ID_NOTE);
                            Toast.makeText(EditTambahNote.this, "Simpan data lama!!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });

        lihat_note.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.lihat_note:
                        Intent home = new Intent(EditTambahNote.this,TampilData.class);
                        startActivity(home);
                }
            }
        });


        tambah_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.tambah_note:
                        //hapus yang ada
                        id_note.setText("");
                        title_note.setText("");
                        content_note.setText("");
                        STATUS = Setting.TAMBAH_DATA_BARU;
                        break;
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Membaca file menu dan menambahkan isinya ke action bar jika ada.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                //action
                return true;
            case R.id.miProfile:
                //action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}