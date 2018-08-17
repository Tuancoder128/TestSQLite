package com.example.vst.testsqlite;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vst.testsqlite.Adapter.CustomAdapter;
import com.example.vst.testsqlite.data.DBManager;
import com.example.vst.testsqlite.model.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtId;
    private EditText edtNumber;
    private EditText edtEmail;
    private EditText edtAddress;
    private Button btnSave;
    private Button btnUpdate;
    private ListView lvStudents;
    private CustomAdapter customAdapter;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DBManager dbManager = new DBManager(this);
        innitView();
        studentList = dbManager.getAllStudents();
        setAdapter();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Student student = createStudient();

                if (student != null) {
                    dbManager.addStudent(student);

                }
                studentList.clear();
                studentList.addAll(dbManager.getAllStudents());
            }
        });

        lvStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Student student = studentList.get(i);
                edtId.setText(String.valueOf(student.getmID()));
                edtAddress.setText(student.getmAddress());
                edtEmail.setText(student.getmEmail());
                edtNumber.setText(student.getmPhoneNumber());
                edtName.setText(student.getmName());
                btnSave.setEnabled(false);
                btnUpdate.setEnabled(true);

            }
        });

        lvStudents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Student student = studentList.get(i);
                int result = dbManager.deleteStudent(student.getmID());
                if(result>0){
                    Toast.makeText(MainActivity.this, "Delete successfuly", Toast.LENGTH_SHORT).show();
                    studentList.clear();
                    studentList.addAll(dbManager.getAllStudents());
                    customAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(MainActivity.this, "Delete Don't successfuly", Toast.LENGTH_SHORT).show();

                }

                return false;


            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = new Student();
                student.setmID(Integer.parseInt(String.valueOf(edtId.getText().toString())));
                student.setmName(edtName.getText().toString());
                student.setmPhoneNumber(edtNumber.getText().toString());
                student.setmEmail(edtEmail.getText().toString());
                student.setmAddress(edtAddress.getText().toString());

                int result = dbManager.updateStudent(student);
                if (result > 0) {
                    btnSave.setEnabled(true);
                    studentList.clear();
                    studentList.addAll(dbManager.getAllStudents());
                    customAdapter.notifyDataSetChanged();

                } else {
                    btnSave.setEnabled(true);
                    btnUpdate.setEnabled(false);
                }


            }
        });

    }

    private Student createStudient() {
        String name = edtName.getText().toString();
        String address = edtAddress.getText().toString();
        String number = edtNumber.getText().toString();
        String email = edtEmail.getText().toString();
        Student student = new Student(name, address, number, email);
        return student;

    }

    private void innitView() {
        edtId = (EditText) findViewById(R.id.id);
        edtName = (EditText) findViewById(R.id.name);
        edtNumber = (EditText) findViewById(R.id.number);
        edtEmail = (EditText) findViewById(R.id.email);
        edtAddress = (EditText) findViewById(R.id.address);
        btnSave = (Button) findViewById(R.id.save);
        btnUpdate = (Button) findViewById(R.id.update);
        lvStudents = (ListView) findViewById(R.id.listview_danhsach);

    }

    public void setAdapter() {
        if (customAdapter == null) {
            customAdapter = new CustomAdapter(this, R.layout.item_list_student, studentList);
            lvStudents.setAdapter(customAdapter);
        } else {
            customAdapter.notifyDataSetChanged();
            lvStudents.setSelection(customAdapter.getCount() - 1);

        }


    }

}
