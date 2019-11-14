package com.example.wilson.exampleapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wilson.exampleapi.viewmodel.MainViewModel;


public class MainActivity extends AppCompatActivity {

    private final MainViewModel viewModel;
    private Button requestButton;

    public MainActivity () {
        viewModel = new MainViewModel(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        TextView tv = findViewById(R.id.message);

        viewModel.setMessage(tv);
        viewModel.setRecyclerView(rv);

        requestButton = (Button)findViewById(R.id.getButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onUIUpdate();
            }
        });
    }

}
