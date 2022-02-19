package com.example.simplenote;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class NoteCursorAdapter extends CursorAdapter {

    public NoteCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, Setting.VERSI_DB);

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View inflater = LayoutInflater.from(context).inflate(R.layout.record_note,parent, false);
        //index = 1;
        return inflater;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //https://stackoverflow.com/questions/13179876/how-to-number-items-in-a-list-view

        TextView id = (TextView) view.findViewById(R.id.listid);
        TextView no = (TextView) view.findViewById(R.id.listno);

        TextView tanggal = (TextView) view.findViewById(R.id.listtanggal);
        TextView title = (TextView) view.findViewById(R.id.listtitle);
        TextView content = (TextView) view.findViewById(R.id.listcontent);
        //get cursor
        String string_no = String.valueOf(cursor.getPosition()+1);
        String string_id = cursor.getString(cursor.getColumnIndexOrThrow(Note.clm_id));
        String string_tanggal = cursor.getString(cursor.getColumnIndexOrThrow(Note.clm_tanggal));
        String string_title = cursor.getString(cursor.getColumnIndexOrThrow(Note.clm_title));
        String string_content = cursor.getString(cursor.getColumnIndexOrThrow(Note.clm_content));

        no.setText(string_no);
        id.setText(string_id);
        tanggal.setText(string_tanggal);
        title.setText(string_title.toUpperCase());
        content.setText(string_content);


    }
}
