package com.example.mustofa.shipinspector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class Hull_Prop_Form extends AppCompatActivity {

    EditText editText;
    Button submit,main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hull__prop__form);
        editText = (EditText) findViewById(R.id.catatan);
        submit = (Button) findViewById(R.id.submit);
        main = (Button) findViewById(R.id.main);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),HullProperty.class));
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),MainActivity.class));
            }
        });
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_good:
                if (checked)
                    editText.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
            case R.id.radio_repair:
                if (checked)
                    editText.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
        }
    }
    public void openHelp(View v)
    {
        startActivity(new Intent(this,Help.class));
    }
}
