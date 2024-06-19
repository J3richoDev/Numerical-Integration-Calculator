package luno.dev.numericalintegration;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class Solution extends AppCompatActivity {

    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_solution);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tableLayout = findViewById(R.id.tableLayout);
        TextView x1 = findViewById(R.id.given_x1);
        TextView x2 = findViewById(R.id.given_x2);
        TextView n_given = findViewById(R.id.given_n);
        TextView hSolution = findViewById(R.id.hSolution);
        TextView hResult = findViewById(R.id.hResult);
        TextView rule1Solution = findViewById(R.id.rule1Solution);
        TextView rule1Result = findViewById(R.id.rule1Result);
        TextView rule2Solution = findViewById(R.id.rule2Solution);
        TextView rule2Result = findViewById(R.id.rule2Result);
        TextView rule3Solution = findViewById(R.id.rule3Solution);
        TextView rule3Result = findViewById(R.id.rule3Result);
        TextView rule4Solution = findViewById(R.id.rule4Solution);
        TextView rule4Result = findViewById(R.id.rule4Result);

        Intent intent = getIntent();


        if (intent != null) {
            int n = intent.getIntExtra("n", 0);
            double[] xValues = intent.getDoubleArrayExtra("xValues");
            double[] fxValues = intent.getDoubleArrayExtra("fxValues");

            if (xValues != null && fxValues != null && xValues.length == fxValues.length) {
                for (int i = 0; i < xValues.length; i++) {
                    TableRow row = new TableRow(this);

                    row.setGravity(Gravity.CENTER);

                    TextView nTextView = new TextView(this);
                    nTextView.setText(String.valueOf(i));
                    nTextView.setWidth(50);
                    nTextView.setGravity(Gravity.CENTER);
                    nTextView.setPadding(5, 5, 5, 5);
                    row.addView(nTextView);

                    TextView xValueTextView = new TextView(this);
                    xValueTextView.setText(String.format(Locale.getDefault(), "%.2f", xValues[i]));
                    xValueTextView.setWidth(100);
                    xValueTextView.setGravity(Gravity.CENTER);
                    xValueTextView.setPadding(5, 5, 5, 5);
                    row.addView(xValueTextView);

                    TextView fxValueTextView = new TextView(this);
                    fxValueTextView.setText(String.format(Locale.getDefault(), "%.8f", fxValues[i]));
                    fxValueTextView.setWidth(100);
                    fxValueTextView.setGravity(Gravity.CENTER);
                    fxValueTextView.setPadding(5, 5, 5, 5);
                    row.addView(fxValueTextView);

                    tableLayout.addView(row);
                }
            }
        }


        String x1_text = intent.getStringExtra("x1");
        String x2_text = intent.getStringExtra("x2");
        String n_text = intent.getStringExtra("n");


        x1.setText("= "+x1_text);
        x2.setText("= "+x2_text);
        n_given.setText("= "+n_text);
        hSolution.setText("h = ("+x2_text+" + " + x1_text + ") / " + n_text);
        hResult.setText(String.format("h = %.2f", intent.getDoubleExtra("h", 0)));

        rule1Solution.setText(intent.getStringExtra("RULE1_SOLUTION"));
        rule1Result.setText(String.format("Result: %.8f", intent.getDoubleExtra("RULE1_RESULT", 0)));
        rule2Solution.setText(intent.getStringExtra("RULE2_SOLUTION"));
        rule2Result.setText(String.format("Result: %.8f", intent.getDoubleExtra("RULE2_RESULT", 0)));
        rule3Solution.setText(intent.getStringExtra("RULE3_SOLUTION"));
        rule3Result.setText(String.format("Result: %.8f", intent.getDoubleExtra("RULE3_RESULT", 0)));
        rule4Solution.setText(intent.getStringExtra("RULE4_SOLUTION"));
        rule4Result.setText(String.format("Result: %.8f", intent.getDoubleExtra("RULE4_RESULT", 0)));



    }
}