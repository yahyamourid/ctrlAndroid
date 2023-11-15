package com.mourid.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mourid.R;
import com.mourid.entities.Service;
import com.android.volley.Response;
import com.android.volley.Request;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddEmployee extends AppCompatActivity {
    private EditText nom, prenom, date;
    private Spinner spinner;
    private Button submit;
    private Service selectedService;
    private List<Service> services = new ArrayList<>();
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        date = findViewById(R.id.date);
        spinner = findViewById(R.id.spinner);
        submit = findViewById(R.id.submit);
        getServices();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitEmployee();
            }
        });
    }
    private void getServices() {
        String getFUrl = "http://192.168.43.20:8080/api/services/all";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                getFUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                Log.d("services",response.toString());
                TypeToken<List<Service>> token = new TypeToken<List<Service>>() {};
                services = gson.fromJson(response.toString(), token.getType());

                final HashMap<String, Service> serviceMap = new HashMap<>();
                for (Service service : services) {
                    serviceMap.put(service.getNom(), service);
                }


                List<String> nomFilieres = new ArrayList<>(serviceMap.keySet());


                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddEmployee.this, android.R.layout.simple_spinner_item, nomFilieres);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        String selectedServiceNom = (String) parentView.getItemAtPosition(position);
                        selectedService = serviceMap.get(selectedServiceNom);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {

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
    public void submitEmployee() {
        String insertUrl = "http://192.168.43.20:8080/api/employees";
        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("id", "0");
            jsonBody.put("nom", nom.getText().toString());
            jsonBody.put("prenom", prenom.getText().toString());
            jsonBody.put("dateNaissance", date.getText().toString());
            JSONObject serviceObject = new JSONObject();
            serviceObject.put("id", selectedService.getId());
            jsonBody.put("service", serviceObject);
            Log.d("Employee", jsonBody.toString());
            Toast.makeText(AddEmployee.this, "Student Added", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(AddStudent.this, StudentList.class);
//            startActivity(intent);
//            AddStudent.this.finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                insertUrl, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("resultat", response+"");
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