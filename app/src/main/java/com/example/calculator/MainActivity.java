package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

public class MainActivity extends ComponentActivity {
    private TextView display;
    private String currentInput = "";
    private String fullExpression = ""; // Для зберігання всього виразу
    private String operator = "";
    private double firstValue = 0;
    private boolean isNewOperation = true;
    private boolean resultShown = false; // Чи був результат вже показаний

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Знаходимо TextView для дисплея
        display = findViewById(R.id.display);

        // Додаємо обробники для кнопок
        setNumberButtonListeners();
        setOperatorButtonListeners();
        setDecimalButtonListener(); // Додаємо обробник для десяткової крапки
    }

    // Метод для обробки натискань числових кнопок
    private void setNumberButtonListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                // Якщо нова операція або результат вже показаний - почати новий ввід
                if (isNewOperation || resultShown) {
                    currentInput = "";
                    if (resultShown) {
                        fullExpression = ""; // Очищуємо весь вираз після результату
                    }
                    isNewOperation = false;
                    resultShown = false;
                }
                currentInput += button.getText().toString();
                fullExpression += button.getText().toString();
                display.setText(fullExpression); // Відображаємо увесь вираз
            }
        };

        findViewById(R.id.btn_zero).setOnClickListener(listener);
        findViewById(R.id.btn_one).setOnClickListener(listener);
        findViewById(R.id.btn_two).setOnClickListener(listener);
        findViewById(R.id.btn_three).setOnClickListener(listener);
        findViewById(R.id.btn_four).setOnClickListener(listener);
        findViewById(R.id.btn_five).setOnClickListener(listener);
        findViewById(R.id.btn_six).setOnClickListener(listener);
        findViewById(R.id.btn_seven).setOnClickListener(listener);
        findViewById(R.id.btn_eight).setOnClickListener(listener);
        findViewById(R.id.btn_nine).setOnClickListener(listener);
    }

    // Метод для обробки операторів (+, -, *, /)
    private void setOperatorButtonListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                operator = button.getText().toString();

                // Якщо новий ввід почався після результату - обнулити вираз
                if (resultShown) {
                    fullExpression = currentInput; // Почати з поточного числа
                    resultShown = false;
                }

                if (!currentInput.isEmpty()) {
                    firstValue = Double.parseDouble(currentInput);
                    currentInput = "";
                }

                fullExpression += " " + operator + " ";
                display.setText(fullExpression);
                isNewOperation = false; // Для продовження виразу
            }
        };

        findViewById(R.id.btn_add).setOnClickListener(listener);
        findViewById(R.id.btn_subtract).setOnClickListener(listener);
        findViewById(R.id.btn_multiply).setOnClickListener(listener);
        findViewById(R.id.btn_divide).setOnClickListener(listener);

        // Обробка кнопки "="
        findViewById(R.id.btn_equals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    double secondValue = Double.parseDouble(currentInput);
                    double result = 0;

                    switch (operator) {
                        case "+":
                            result = firstValue + secondValue;
                            break;
                        case "-":
                            result = firstValue - secondValue;
                            break;
                        case "*":
                            result = firstValue * secondValue;
                            break;
                        case "/":
                            if (secondValue != 0) {
                                result = firstValue / secondValue;
                            } else {
                                display.setText("Error");
                                return;
                            }
                            break;
                    }
                    fullExpression += " = " + result;
                    display.setText(fullExpression); // Відображаємо весь вираз разом з результатом
                    currentInput = String.valueOf(result);
                    firstValue = result; // Щоб можна було продовжити обчислення
                    isNewOperation = true;
                    resultShown = true; // Вказуємо, що результат вже показано
                }
            }
        });

        // Очищення дисплея
        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "";
                fullExpression = "";
                firstValue = 0;
                operator = "";
                display.setText("0");
                isNewOperation = true;
                resultShown = false; // Скидаємо стан результату
            }
        });
    }

    // Метод для обробки десяткової крапки
    private void setDecimalButtonListener() {
        findViewById(R.id.btn_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.contains(".")) {
                    currentInput += ".";
                    fullExpression += ".";
                    display.setText(fullExpression);
                }
            }
        });
    }
}
