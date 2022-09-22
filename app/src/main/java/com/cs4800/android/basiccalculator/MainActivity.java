package com.cs4800.android.basiccalculator;

import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting a reference to the EditText view in our activity_main.xml
        display = findViewById(R.id.input);
        //Stops the default keyboard from popping up when we click on the EditText view
        display.setShowSoftInputOnFocus(false);

        //Clears default string in the EditText view when you click on the text in the view.
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getString(R.string.display).equals(display.getText().toString())) {
                    display.setText("");
                }
            }
        });
    }

    //Update EditText view to change with button presses
    //This function adds a string wherever the cursor position is
    private void updateText(String strToAdd) {
        String oldStr = display.getText().toString();
        int cursorPosition = display.getSelectionStart();
        String leftStr = oldStr.substring(0,cursorPosition);
        String rightStr = oldStr.substring(cursorPosition);

        //Makes it so we don't have to click every time to clear the default string the EditText view
        if (getString(R.string.display).equals(display.getText().toString())) {
            display.setText(strToAdd);
            display.setSelection(cursorPosition + 1); //fix cursor position
        }
        else {
            display.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));
            display.setSelection(cursorPosition + 1); //fix cursor position
        }
    }

    //Number buttons
    public void zeroButton(View view) {
        updateText("0");
    }

    public void oneButton(View view) {
        updateText("1");
    }

    public void twoButton(View view) {
        updateText("2");
    }

    public void threeButton(View view) {
        updateText("3");
    }

    public void fourButton(View view) {
        updateText("4");
    }

    public void fiveButton(View view) {
        updateText("5");
    }

    public void sixButton(View view) {
        updateText("6");
    }

    public void sevenButton(View view) {
        updateText("7");
    }

    public void eightButton(View view) {
        updateText("8");
    }

    public void nineButton(View view) {
        updateText("9");
    }

    //Math operation Buttons
    public void addButton(View view) {
        updateText("+");
    }

    public void subtractButton(View view) {
        updateText("-");
    }

    public void multiplyButton(View view) {
        //alt code: 0215
        updateText("×");
    }

    public void divideButton(View view) {
        //alt code: 0247
        updateText("÷");
    }

    //Other operation buttons
    public void parenthesesButton(View view) {
        int cursorPosition = display.getSelectionStart();
        int openParentheses = 0;
        int closedParentheses = 0;
        int textLength = display.getText().length();

        //Iterating through the text and seeing if any of the characters are an open or closing
        //parenthesis.
        for (int i=0; i < cursorPosition; i++) {
            if (display.getText().toString().substring(i, i+1).equals("(")) {
                openParentheses += 1;
            }
            if (display.getText().toString().substring(i, i+1).equals(")")) {
                closedParentheses += 1;
            }
        }

        //Adds an open parentheses or closing parenthesis depending on certain criteria in if statements
        //Checking if the open parentheses are equal to the closing parenthesis.
        if (openParentheses == closedParentheses || display.getText().toString().substring(textLength - 1, textLength).equals("(")) {
            updateText("(");
        }
        else if (closedParentheses < openParentheses && !display.getText().toString().substring(textLength - 1, textLength).equals("(")) {
            updateText(")");
        }
        display.setSelection(cursorPosition + 1);

    }

    public void equalsButton(View view) {
        //Get the user expression in the view
        String userExpression = display.getText().toString();

        //Replace alt code symbols with symbols the math parser library can understand
        userExpression = userExpression.replaceAll("÷","/");
        userExpression = userExpression.replaceAll("×","*");

        //Create Expression object that inherits methods from the math parser library
        Expression exp = new Expression(userExpression);

        //Use the .calculate() method from the math parser library to calculate result of expression
        String result = String.valueOf(exp.calculate());

        //Update the EditText view with the result and move cursor to correct position
        display.setText(result);
        display.setSelection(result.length());
    }

    public void backspaceButton(View view) {
        int cursorPosition = display.getSelectionStart();
        int textLength = display.getText().length();

        //makes sure that if user hits backspace when there is no text in the EditText view,
        //an out of bounds exception will not occur
        if (cursorPosition != 0 && textLength != 0) {
            SpannableStringBuilder selection = (SpannableStringBuilder) display.getText();
            selection.replace(cursorPosition - 1, cursorPosition, "");
            display.setText(selection);
            display.setSelection(cursorPosition - 1);
        }
    }

    public void exponentButton(View view) {
        updateText("^");
    }

    public void clearButton(View view) {
        display.setText("");
    }

    public void plusminusButton(View view) {
        updateText("-");
    }

    public void pointButton(View view) {
        updateText(".");
    }

}