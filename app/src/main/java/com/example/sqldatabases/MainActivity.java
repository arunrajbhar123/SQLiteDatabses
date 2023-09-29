package com.example.sqldatabases;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button viewAll, addCustomer;
    EditText et_name, et_age;
    SwitchMaterial isActive;
    ListView lv_CustomerList;

    ArrayAdapter<CustomerData> customerList;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewAll = findViewById(R.id.viewAll);
        addCustomer = findViewById(R.id.add);
        et_name = findViewById(R.id.et_Name);
        et_age = findViewById(R.id.et_Age);
        isActive = findViewById(R.id.switchMaterial);
        lv_CustomerList = findViewById(R.id.lv_CustomerList);
        databaseHelper = new DatabaseHelper(MainActivity.this);

        showCustomerOnListView();

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    showCustomerOnListView();

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error View Customer", Toast.LENGTH_SHORT).show();

                }
            }
        });

        lv_CustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CustomerData customerData = (CustomerData) adapterView.getItemAtPosition(i);
                databaseHelper.deleteOneCustomer(customerData.getId());
                showCustomerOnListView();
                Toast.makeText(MainActivity.this, "Deleted " + customerData.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CustomerData customerData = new CustomerData(-1, et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), isActive.isChecked());

                    boolean success = databaseHelper.addCustomer(customerData);

                    Toast.makeText(MainActivity.this, "Success " + success, Toast.LENGTH_SHORT).show();
                    showCustomerOnListView();


                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error Creating Customer", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private void showCustomerOnListView() {
        customerList = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getAllCustomer());
        lv_CustomerList.setAdapter(customerList);
    }
}