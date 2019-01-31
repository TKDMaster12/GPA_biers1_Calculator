package com.example.gpa_biers1_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewGroup parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize
        Button addCourseBtn = (Button) findViewById(R.id.addCourseBtn);
        Button calculateGPABtn = (Button) findViewById(R.id.CalculateGPABtn);
        parentLayout = (ViewGroup) findViewById(R.id.linearLayout);

        //when click on button add textView
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditTextView();
            }
        });

        //compute GPA on click
        calculateGPABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<EditText> myEditTextList = new ArrayList<>();

                for( int i = 0; i < parentLayout.getChildCount(); i++ ) {
                    if (parentLayout.getChildAt(i) instanceof EditText) {
                        myEditTextList.add((EditText) parentLayout.getChildAt(i));
                    }
                }

                int listSize = myEditTextList.size();

                for (int i = 0; i<listSize; i++){
                    Log.i(MainActivity.class.getSimpleName(), myEditTextList.get(i).toString());
                }
            }
        });

        //add five textViews and editTexts
        for(int i = 0; i < 5; i++){
            addEditTextView();
        }
    }

    protected void addEditTextView() {
        LayoutInflater.from(this).inflate(R.layout.course_item, parentLayout, true);
    }
}
