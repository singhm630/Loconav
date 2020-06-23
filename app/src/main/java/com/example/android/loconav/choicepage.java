package com.example.android.loconav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class choicepage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choicepage);


        findViewById(R.id.buttonneedjob).setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.buttonneedjob:
                Intent intent=new Intent(this,nothingsogreat.class);
                startActivity(intent);
                break;

        }

    }
}