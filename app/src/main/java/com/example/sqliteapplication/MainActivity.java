package com.example.sqliteapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
        DbHelper mydb;
        EditText editname,editsurname,editmarks, editId;
        Button submit,view,update,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DbHelper(this);
        editname = findViewById(R.id.editTextName);
        editsurname = findViewById(R.id.editSurname);
        editmarks = findViewById(R.id.editMarks);
        editId = findViewById(R.id.editTextIDnumber);
        submit = findViewById(R.id.buttonSubmit);
        view = findViewById(R.id.buttonView);
        update = findViewById(R.id.buttonUpdate);
        delete = findViewById(R.id.buttonDelete);
        AddData();
        GetData();
        UpdateData();
        DeleteData();
    }

    public void AddData(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean isInserted =  mydb.insertData(editname.getText().toString(),
                        editsurname.getText().toString(),
                        editsurname.getText().toString());
               if(isInserted == true)
                   Toast.makeText(MainActivity.this,"Data Entered",Toast.LENGTH_LONG).show();
               else
                   Toast.makeText(MainActivity.this,"Data not Entered",Toast.LENGTH_LONG).show();

            }
        });
    }


//VIEW DATA
    public void GetData()
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        Cursor res = mydb.getAllData();
        if(res.getCount() == 0){
            //show message
            showMessage("ERROR","No data Found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Id :"+ res.getString(0)+"\n");
            buffer.append("name :"+ res.getString(1)+"\n");
            buffer.append("surname :"+ res.getString(2)+"\n");
            buffer.append("marks :"+ res.getString(3)+"\n\n");
        }
        //show alll data

                showMessage("Data", buffer.toString());

            }
        });
    }
//UPDATE DATA
    public void UpdateData(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = mydb.updateData(editId.getText().toString(),
                        editname.getText().toString(),
                        editsurname.getText().toString(),
                        editmarks.getText().toString());
                if (isUpdate == true)
                    Toast.makeText(MainActivity.this,"Data is Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
            }
        });
    }

    //DELETE DATA
    public  void DeleteData(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = mydb.deleteData(editId.getText().toString());
                if(deletedRows > 0)
                    Toast.makeText(MainActivity.this,"Data is Deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
            }
        });
    }
    //show Messaage created
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
