package com.example.gpa_biers1_calculator;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ViewGroup parentLayout;
    int counter = 5000;
    Button calculateGPABtn;
    TextView GPAResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize
        Button addCourseBtn = findViewById(R.id.addCourseBtn);
        calculateGPABtn = findViewById(R.id.CalculateGPABtn);
        parentLayout = findViewById(R.id.linearLayout);
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
        EditText editText = view.findViewById(R.id.editFinalGrade);
        //get spinner
        Spinner spinner = view.findViewById(R.id.spinnerCredits);

        //set id
        textInputLayout.setId(R.id.gpa_text_input_layout + counter);
        editText.setId(R.id.editFinalGrade + counter);
        spinner.setId(R.id.spinnerCredits + counter);

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
                EditText editText = findViewById(R.id.editFinalGrade + x);
                TextInputLayout textInputLayout = findViewById(R.id.gpa_text_input_layout + x);

                String gradeValidator = editText.getText().toString();

                if (TextUtils.isEmpty(gradeValidator)) {
                    textInputLayout.setError("Please enter a grade");
                    error++;
                } else if (!isNumeric(gradeValidator)) {
                    textInputLayout.setError("Please enter a number");
                    error++;
                } else {
                    textInputLayout.setError(null);
                }
            } catch (Exception e) {

            }
        }

        if (error == 0) {
            calculateGPA();
            calculateGPABtn.setText(getResources().getString(R.string.clearForm));
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
                EditText editText = findViewById(R.id.editFinalGrade + x);
                Spinner spinner = findViewById(R.id.spinnerCredits + x);

                int grade = Integer.parseInt(editText.getText().toString());
                double finalgrade = convertGradetoGPA(grade);

                char c = spinner.getSelectedItem().toString().charAt(0);
                int credits = (int) c;

                total += finalgrade * credits;
                howManyCredits += credits;

            } catch (Exception e) {

            }
        }

        double gpa = total / howManyCredits;
        GPAResults.setText(String.format(Locale.getDefault(), "%.2f", gpa));
    }

    public void clearForm() {
        GPAResults.setText(null);
        for (int x = 5000; x < counter; x++) {
            try {
                EditText editText = findViewById(R.id.editFinalGrade + x);
                Spinner spinner = findViewById(R.id.spinnerCredits + x);

                editText.getText().clear();
                spinner.setSelection(0);
            } catch (Exception e) {

            }
        }
    }

    public double convertGradetoGPA(int grade) {
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