package com.example.simplenote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TampilData extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Note dbhelper;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data);
        getSupportActionBar().hide();
        dbhelper = new Note(this);
        listView = (ListView)findViewById(R.id.list_data);
        listView.setOnItemClickListener(this); //biar pas di sentuh ada action listener
        loadagain();
        FloatingActionButton tambah_note_baru = findViewById(R.id.fabtambah);
        //Button tambah_note_baru = (Button) findViewById(R.id.back_tambah);
        tambah_note_baru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                switch (v.getId()){
//                    case R.id.back_tambah:
//
//                        break;
//                }
                Intent intent = new Intent(TampilData.this,EditTambahNote.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Setting.STATUS,Setting.TAMBAH_DATA_BARU); //kasih flag
                intent.putExtras(bundle);                       //bahwa Inten yang dibuat untuk
                startActivity(intent);                          //TAMBAH DATA BARU
                finish();
            }
        });



    }

    private void loadagain(){
        Cursor cursor = dbhelper.allData();
        NoteCursorAdapter customCursorAdapter = new NoteCursorAdapter(this, cursor);
        listView.setAdapter(customCursorAdapter);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listid);
        final long id = Long.parseLong(getId.getText().toString());

        final AlertDialog.Builder builder = new AlertDialog.Builder(TampilData.this);
        builder.setTitle("Menu : ");

        String [] opsi = new String[]{"Edit","Hapus","Batal"};
        builder.setItems(opsi,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        Intent intent = new Intent(TampilData.this,EditTambahNote.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt(Setting.STATUS,Setting.EDIT_DATA_LAMA); //kasih flag
                        bundle.putInt(Note.clm_id,Integer.valueOf(getId.getText().toString())); //ID record
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 1:
                        dbhelper.deleteData(id);
                        loadagain();
                        break;
                    case 2:
                        //do nothing
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}