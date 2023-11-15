package com.mourid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mourid.R;
import com.mourid.entities.Employee;

import java.text.SimpleDateFormat;
import java.util.List;

public class EmployeeAdapter extends BaseAdapter {
    private List<Employee> employees;
    private LayoutInflater inflater;

    public EmployeeAdapter(List<Employee> students, Activity activity) {
        this.employees = students;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void updateStudentsList(List<Employee> newEmployees) {
        employees.clear();
        employees.addAll(newEmployees);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int position) {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return employees.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = inflater.inflate(R.layout.employee_tem, null);
        TextView firstName = convertView.findViewById(R.id.nom);
        TextView lastName = convertView.findViewById(R.id.prenom);
        TextView date = convertView.findViewById(R.id.date);
        TextView  service= convertView.findViewById(R.id.service);
        ImageView image = convertView.findViewById(R.id.img);
        image.setImageResource(R.mipmap.profil);
        firstName.setText(employees.get(position).getNom());
        lastName.setText(employees.get(position).getPrenom());
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        String targetDateString = targetFormat.format(employees.get(position).getDateNaissance());
        date.setText(targetDateString);
        service.setText(employees.get(position).getService().getNom()+"");
        return convertView;
    }
}

