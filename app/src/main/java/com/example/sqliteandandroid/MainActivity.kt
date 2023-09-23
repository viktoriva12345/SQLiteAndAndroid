package com.example.sqliteandandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Switch
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnAll = findViewById<Button>(R.id.btn_viewAll)
        var btnAdd = findViewById<Button>(R.id.btn_add)

        var et_name = findViewById<EditText>(R.id.et_name)
        var et_age = findViewById<EditText>(R.id.et_age)

        var switch = findViewById<Switch>(R.id.sw_active)
        var listView = findViewById<ListView>(R.id.lv_customerList)

        fun updateListView() {
            val databaseHelper = DataBaseHelper(this)
            val list = databaseHelper.getEveryone() as ArrayList<CustomModel>
            val customerArrayAdapter = ArrayAdapter<CustomModel>(this, android.R.layout.simple_list_item_1, list)
            listView.adapter = customerArrayAdapter
        }

        btnAdd.setOnClickListener() {
            var c1 = CustomModel();
            try {
                c1 = CustomModel(-1,et_name.text.toString(), et_age.text.toString().toInt(), switch.isChecked());
                //Toast.makeText(this, c1.toString() , Toast.LENGTH_SHORT).show()
            }catch ( ex: Exception ){
                Toast.makeText(this, "Error making customer model!" , Toast.LENGTH_SHORT).show()
                c1 = CustomModel(-1,"error", 0 , false);



            }


            val databaseHelper = DataBaseHelper(this);
            var addOne : Boolean = databaseHelper.addOne(c1)

            Toast.makeText(this,"Successfully inserted",Toast.LENGTH_SHORT).show();

            // Call the function to update the ListView
            updateListView()

        }
        btnAll.setOnClickListener() {

            val databaseHelper = DataBaseHelper(this);
            var list = ArrayList<CustomModel>()
            list = databaseHelper.getEveryone() as ArrayList<CustomModel>;

            var customerArrayAdapter = ArrayAdapter<CustomModel>(this, android.R.layout.simple_list_item_1, list);
            listView.adapter = customerArrayAdapter



        ///Toast.makeText(this, list.toString() , Toast.LENGTH_LONG).show()
        // Call the function to update the ListView
            updateListView()
        }

        /////// DELETE ROW ON ROW CLICK
        listView.setOnItemClickListener { parent, view, position, id ->
            var clickedCustomer = parent.getItemAtPosition(position) as CustomModel
            val databaseHelper = DataBaseHelper(this);
            databaseHelper.deleteOne(clickedCustomer)
            updateListView()
            Toast.makeText(this, "Delete " + clickedCustomer.toString(), Toast.LENGTH_SHORT).show()

        }
    }
}