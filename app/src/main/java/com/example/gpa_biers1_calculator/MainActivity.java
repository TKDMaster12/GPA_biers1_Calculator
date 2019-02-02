package com.example.gpa_biers1_calculator;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ViewGroup parentLayout;
    ScrollView mainLayout;
    int counter = 5000;
    Button calculateGPABtn;
    Button addCourseBtn;
    TextView GPAResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize
        addCourseBtn = findViewById(R.id.addCourseBtn);
        calculateGPABtn = findViewById(R.id.CalculateGPABtn);
        parentLayout = findViewById(R.id.linearLayout);
        mainLayout = findViewById(R.id.MainLayout);
        GPAResults = findViewById(R.id.GPAResults);

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
                if (calculateGPABtn.getText().toString().equals(getResources().getString(R.string.clearForm))) {
                    //clear text boxes
                    clearForm();
                    calculateGPABtn.setText(getResources().getString(R.string.calculateGPA));
                } else {
                    // validate forms then calculate
                    validate();
                }
            }
        });

        //add five textViews and editTexts
        for (int i = 0; i < 5; i++)
            addEditTextView();
    }

    protected void addEditTextView() {

        View view = LayoutInflater.from(this).inflate(R.layout.course_item, null);

        //get TextInputLayout
        TextInputLayout textInputLayout = view.findViewById(R.id.gpa_text_input_layout);
        //get editText
        TextInputEditText textInputEditText = view.findViewById(R.id.editFinalGrade);
        //get spinner
        Spinner spinner = view.findViewById(R.id.spinnerCredits);
        //get TextView
        TextView textView = view.findViewById(R.id.course);

        //set id
        textInputLayout.setId(R.id.gpa_text_input_layout + counter);
        textInputEditText.setId(R.id.editFinalGrade + counter);
        textView.setId(R.id.course + counter);
        spinner.setId(R.id.spinnerCredits + counter);
        spinner.setSelection(2);

        //add it into my view
        parentLayout.addView(view);
        counter++;
    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public void validate() {
        int error = 0;

        for (int x = 5000; x < counter; x++) {
            try {
                TextInputEditText textInputEditText = findViewById(R.id.editFinalGrade + x);
                TextInputLayout textInputLayout = findViewById(R.id.gpa_text_input_layout + x);

                String gradeValidator = textInputEditText.getText().toString();

                if (TextUtils.isEmpty(gradeValidator)) {
                    textInputLayout.setError("Please enter a grade");
                    error++;
                } else if (!isNumeric(gradeValidator)) {
                    textInputLayout.setError("Please enter a number");
                    error++;
                } else {
                    int grade = Integer.parseInt(gradeValidator);
                    if (grade < 0 || grade > 100) {
                        textInputLayout.setError("A Number 0 to 100");
                        error++;
                    } else {
                        textInputLayout.setError(null);
                    }
                }
            } catch (Exception e) {
                Log.e(getPackageName(), "Exception: " + Log.getStackTraceString(e));
            }
        }

        if (error == 0) {
            calculateGPA();
            calculateGPABtn.setText(getResources().getString(R.string.clearForm));
            addCourseBtn.setVisibility(View.INVISIBLE);
        }
    }

    public void deleteView(View view) {
        parentLayout.removeView((View) view.getParent());
    }

    public void calculateGPA() {

        double total = 0;
        int howManyCredits = 0;

        for (int x = 5000; x < counter; x++) {
            try {
                TextInputEditText textInputEditText = findViewById(R.id.editFinalGrade + x);
                Spinner spinner = findViewById(R.id.spinnerCredits + x);

                int grade = Integer.parseInt(textInputEditText.getText().toString());
                char c = spinner.getSelectedItem().toString().charAt(0);
                int credits = (int) c;

                total += grade * credits;
                howManyCredits += credits;

            } catch (Exception e) {
                Log.e(getPackageName(), "Exception: " + Log.getStackTraceString(e));
            }
        }

        double gpa = total / howManyCredits;
        double finalGPA = convertGradetoGPA(gpa);
        String answer = "GPA: " + String.format(Locale.getDefault(), "%.2f", finalGPA);

        GPAResults.setText(answer);

        if (gpa <= 60)
            mainLayout.setBackgroundColor(ContextCompat.getColor(this.getApplicationContext(), R.color.redBackground));
        else if (gpa < 80)
            mainLayout.setBackgroundColor(ContextCompat.getColor(this.getApplicationContext(), R.color.yellowBackground));
        else
            mainLayout.setBackgroundColor(ContextCompat.getColor(this.getApplicationContext(), R.color.greenBackground));
    }

    public void clearForm() {
        GPAResults.setText(null);
        for (int x = 5000; x < counter; x++) {
            try {
                TextInputEditText textInputEditText = findViewById(R.id.editFinalGrade + x);
                Spinner spinner = findViewById(R.id.spinnerCredits + x);

                textInputEditText.getText().clear();
                spinner.setSelection(2);
            } catch (Exception e) {
                Log.e(getPackageName(), "Exception: " + Log.getStackTraceString(e));
            }
        }
        mainLayout.setBackgroundColor(ContextCompat.getColor(this.getApplicationContext(), android.R.color.transparent));
        addCourseBtn.setVisibility(View.VISIBLE);
    }

    public double convertGradetoGPA(double grade) {
        //A
        if (grade > 94)
            return 4.0;
            //A-
        else if (grade > 89)
            return 3.7;
            //B+
        else if (grade > 86)
            return 3.33;
            //B
        else if (grade > 83)
            return 3.0;
            //B-
        else if (grade > 79)
            return 2.7;
            //C+
        else if (grade > 76)
            return 2.3;
            //C
        else if (grade > 73)
            return 2.0;
            //C-
        else if (grade > 69)
            return 1.7;
            //D+
        else if (grade > 66)
            return 1.3;
            //D
        else if (grade > 63)
            return 1.0;
            //D-
        else if (grade > 59)
            return 0.7;
        else
            return 0.0;
    }
}