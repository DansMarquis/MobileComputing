package com.cm.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class Edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            String num = extras.getString("number");
            TextInputEditText nameEdit = (TextInputEditText)findViewById(R.id.nameEdit);
            TextInputEditText numberEdit = (TextInputEditText)findViewById(R.id.numberEdit);
            nameEdit.setText(name);
            numberEdit.setText(num);
            //The key argument here must match that used in the other activity
        }

    }
    public void buttonClickEvent(View v) {
        String phoneNumber = editNumber.getText().toString();
    }
}
