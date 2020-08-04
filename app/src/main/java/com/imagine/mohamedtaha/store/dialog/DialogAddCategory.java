package com.imagine.mohamedtaha.store.dialog;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.imagine.mohamedtaha.store.R;

public class DialogAddCategory extends AppCompatActivity {
    private EditText nameCategory, naturalCategory,notes;
    private Button BTAddOrUpdate, BTDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add_category);
        nameCategory = (EditText)findViewById(R.id.ETNameCategory);
        naturalCategory = (EditText)findViewById(R.id.ETNaturalGategory);
        notes = (EditText)findViewById(R.id.EtNotes);

        BTAddOrUpdate = (Button) findViewById(R.id.BTAdd);
        BTDelete = (Button)findViewById(R.id.BTDelete);
    }
}
