package com.example.electricitybillestimator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText inputUnits;
    private EditText inputRebate;
    private TextView tvBillResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the input fields and buttons
        inputUnits = findViewById(R.id.inputUnits);
        inputRebate = findViewById(R.id.inputRebate);
        tvBillResult = findViewById(R.id.tvBillResult);
        Button btnCalculate = findViewById(R.id.btnCalculate);
        Button btnClear = findViewById(R.id.btnClearCalculation); // Find the Clear button
        Button btnAboutMe = findViewById(R.id.btnAboutMe); // Ensure ID matches the one in XML

        // Set onClickListener for Calculate Button using Lambda
        btnCalculate.setOnClickListener(v -> calculateBill());

        // Set onClickListener for Clear Button using Lambda
        btnClear.setOnClickListener(v -> {
            // Clear the input fields and reset the result text
            inputUnits.setText("");
            inputRebate.setText("");
            tvBillResult.setText(getString(R.string.bill_result, "0.00")); // Reset to default value
        });

        // Set onClickListener for AboutMe Button using Lambda
        btnAboutMe.setOnClickListener(v -> {
            // Navigate to AboutMeActivity
            Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
            startActivity(intent);
        });
    }

    private void calculateBill() {
        // Get the values from the input fields
        String unitsStr = inputUnits.getText().toString();
        String rebateStr = inputRebate.getText().toString();

        if (!unitsStr.isEmpty() && !rebateStr.isEmpty()) {
            float units = Float.parseFloat(unitsStr);
            float rebatePercentage = Float.parseFloat(rebateStr);

            // Check if rebatePercentage exceeds 5
            if (rebatePercentage > 5) {
                tvBillResult.setText(getString(R.string.error_invalid_rebate)); // Show error message
                return;
            }

            // Calculate the bill
            float billAmount = calculateElectricityBill(units);

            // Apply rebate
            float finalBill = billAmount * (1 - (rebatePercentage / 100));

            // Display the result
            String formattedBill = String.format(Locale.US, "%.2f", finalBill);
            tvBillResult.setText(getString(R.string.bill_result, formattedBill));

        } else {
            tvBillResult.setText(getString(R.string.error_invalid_values));
        }
    }

    // Method to calculate the bill based on units consumed using block rates
    private float calculateElectricityBill(float units) {
        float bill;

        if (units <= 200) {
            bill = units * 0.218f; // RM 0.218 per unit for first 200 units
        } else if (units <= 300) {
            bill = (200 * 0.218f) + ((units - 200) * 0.334f); // RM 0.334 per unit for next 100 units
        } else if (units <= 600) {
            bill = (200 * 0.218f) + (100 * 0.334f) + ((units - 300) * 0.516f); // RM 0.516 per unit for next 300 units
        } else {
            bill = (200 * 0.218f) + (100 * 0.334f) + (300 * 0.516f) + ((units - 600) * 0.546f); // RM 0.546 per unit for units above 600
        }

        return bill;
    }
}
