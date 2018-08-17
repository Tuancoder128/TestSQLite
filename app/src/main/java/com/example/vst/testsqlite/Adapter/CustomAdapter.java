package com.example.vst.testsqlite.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vst.testsqlite.R;
import com.example.vst.testsqlite.model.Student;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Student> {

    private Context context;
    private int resource;
    private List<Student> listStudent;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listStudent = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_student, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvID = (TextView) convertView.findViewById(R.id.item_id);
            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.item_address);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.tvEmail = (TextView) convertView.findViewById(R.id.item_email);
            viewHolder.tvNumber = (TextView) convertView.findViewById(R.id.item_phone);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        Student student = listStudent.get(position);
        viewHolder.tvID.setText(String.valueOf(student.getmID()));
        viewHolder.tvAddress.setText(student.getmAddress());
        viewHolder.tvName.setText(student.getmName());
        viewHolder.tvEmail.setText(student.getmEmail());
        viewHolder.tvNumber.setText(student.getmPhoneNumber());

        return convertView;
    }

    public class ViewHolder {

        private TextView tvID;
        private TextView tvAddress;
        private TextView tvName;
        private TextView tvEmail;
        private TextView tvNumber;

    }

}
