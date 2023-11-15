package com.mourid.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mourid.R;
import com.mourid.adapter.EmployeeAdapter;
import com.mourid.entities.Employee;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class EmployeeList extends AppCompatActivity {

    private List<Employee> employees = new ArrayList<>();
    private ListView employeesList;
    RequestQueue requestQueue;
    EmployeeAdapter employeeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        employeeAdapter = new EmployeeAdapter(employees, this);
        getEmployees();



    }

    public void getEmployees(){
        String getSUrl = "http://192.168.43.20:8080/api/employees/all";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                getSUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
//                Log.d("students",response.toString());
                TypeToken<List<Employee>> token = new TypeToken<List<Employee>>() {};
                employees = gson.fromJson(response.toString(), token.getType());
                employeesList = findViewById(R.id.list);

                employeeAdapter.updateStudentsList(employees);
                employeesList.setAdapter(employeeAdapter);
                employeesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Erreur", error.toString());
            }
        });
        requestQueue.add(request);
    }

}