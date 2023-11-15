package com.mourid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mourid.ui.AddEmployee;
import com.mourid.ui.EmployeeList;

public class MainActivity extends AppCompatActivity {
    private Button add, list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.add);
        list = findViewById(R.id.list);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEmployee.class);
                startActivity(intent);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EmployeeList.class);
                startActivity(intent);
            }
        });
    }
}