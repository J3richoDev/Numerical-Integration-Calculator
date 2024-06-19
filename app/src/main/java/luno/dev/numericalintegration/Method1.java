package luno.dev.numericalintegration;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Method1 extends AppCompatActivity {

    private EditText xEditText;
    private EditText fxEditText;
    private TextView resultTextView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_method1);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                handleNavigationItemSelected(item);
                return true;
            }
        });

        xEditText = findViewById(R.id.x_EditText);
        fxEditText = findViewById(R.id.fx_EditText);
//        resultTextView = findViewById(R.id.resultTextView);
        Button computeButton = findViewById(R.id.computeButton);

        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeRules();
            }
        });
    }

    private void handleNavigationItemSelected(MenuItem item) {
        Intent intent = null;
        int id = item.getItemId();

        if (id == R.id.home) {
            Toast.makeText(Method1.this, "Home", Toast.LENGTH_SHORT).show();
            intent = new Intent(Method1.this, MainActivity.class);
        } else if (id == R.id.method1) {
            Toast.makeText(Method1.this, "Method 1", Toast.LENGTH_SHORT).show();
            intent = new Intent(Method1.this, Method1.class);
        } else if (id == R.id.method2) {
            Toast.makeText(Method1.this, "Method 2", Toast.LENGTH_SHORT).show();
            intent = new Intent(Method1.this, Method2.class);
        } else if (id == R.id.about) {
            Toast.makeText(Method1.this, "About", Toast.LENGTH_SHORT).show();
            intent = new Intent(Method1.this, Method2.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    private void computeRules() {
        String[] xValuesStr = xEditText.getText().toString().split(",");
        String[] fxValuesStr = fxEditText.getText().toString().split(",");

        int n = xValuesStr.length - 1;
        double[] xValues = new double[n + 1];
        double[] fxValues = new double[n + 1];

        for (int i = 0; i <= n; i++) {
            xValues[i] = Double.parseDouble(xValuesStr[i].trim());
            fxValues[i] = Double.parseDouble(fxValuesStr[i].trim());
        }

        double h = (xValues[n] - xValues[0]) / n;
        String xvalue1 = String.valueOf(xValues[0]);
        String xvalue2 = String.valueOf(xValues[n]);
        String nValue = String.valueOf(n);

        double trapezoidalRuleResult = computeTrapezoidalRule(fxValues, h);
        String trapezoidalRuleSolution = generateTrapezoidalSolution(fxValues, h);

        double simpsonOneThirdRuleResult = computeSimpsonOneThirdRule(fxValues, h);
        String simpsonOneThirdRuleSolution = generateSimpsonOneThirdSolution(fxValues, h);

        double simpsonThreeEighthRuleResult = computeSimpsonThreeEighthRule(fxValues, h);
        String simpsonThreeEighthRuleSolution = generateSimpsonThreeEighthSolution(fxValues, h);

        double booleRuleResult = computeBooleRule(fxValues, h);
        String booleRuleSolution = generateBooleSolution(fxValues, h);

        Intent intent = new Intent(Method1.this, Solution1.class);
        intent.putExtra("xValues", xValues);
        intent.putExtra("fxValues", fxValues);
        intent.putExtra("h", h);
        intent.putExtra("n", nValue);
        intent.putExtra("xvalue1", xvalue1);
        intent.putExtra("xvalue2", xvalue2);
        intent.putExtra("RULE1_SOLUTION", trapezoidalRuleSolution);
        intent.putExtra("RULE1_RESULT", trapezoidalRuleResult);
        intent.putExtra("RULE2_SOLUTION", simpsonOneThirdRuleSolution);
        intent.putExtra("RULE2_RESULT", simpsonOneThirdRuleResult);
        intent.putExtra("RULE3_SOLUTION", simpsonThreeEighthRuleSolution);
        intent.putExtra("RULE3_RESULT", simpsonThreeEighthRuleResult);
        intent.putExtra("RULE4_SOLUTION", booleRuleSolution);
        intent.putExtra("RULE4_RESULT", booleRuleResult);

        startActivity(intent);
    }

    private double computeTrapezoidalRule(double[] fxValues, double h) {
        double sum = 0.0;
        for (int i = 1; i < fxValues.length - 1; i++) {
            sum += 2 * fxValues[i];
        }
        sum += fxValues[0] + fxValues[fxValues.length - 1];
        return (h / 2) * sum;
    }

    private double computeSimpsonOneThirdRule(double[] fxValues, double h) {
        double sum = 0.0;
        for (int i = 1; i < fxValues.length - 1; i += 2) {
            sum += 4 * fxValues[i];
        }
        for (int i = 2; i < fxValues.length - 2; i += 2) {
            sum += 2 * fxValues[i];
        }
        sum += fxValues[0] + fxValues[fxValues.length - 1];
        return (h / 3) * sum;
    }

    private double computeSimpsonThreeEighthRule(double[] fxValues, double h) {
        double sum = 0.0;
        int n = fxValues.length - 1;

        for (int i = 1; i < n; i++) {
            if (i % 3 == 0) {
                sum += 2 * fxValues[i];
            } else {
                sum += 3 * fxValues[i];
            }
        }
        sum += fxValues[0] + fxValues[n];
        return (3 * h / 8) * sum;
    }

    private double computeBooleRule(double[] fxValues, double h) {
        double sum = 0.0;
        int n = fxValues.length - 1;
//        if (n % 4 != 0) {
//            return Double.NaN; // Boole's rule requires n to be a multiple of 4.
//        }
        for (int i = 1; i < n; i++) {
            if (i % 4 == 0) {
                sum += 14 * fxValues[i];
            } else if (i % 2 == 0) {
                sum += 12 * fxValues[i];
            } else {
                sum += 32 * fxValues[i];
            }
        }
        sum += 7 * (fxValues[0] + fxValues[n]);
        return (2 * h / 45) * sum;
    }

    private String generateTrapezoidalSolution(double[] fxValues, double h) {
        StringBuilder solution = new StringBuilder();
        solution.append(String.format("Solution: %.8f / 2 * [%.8f + %.8f + 2 * (", h, fxValues[0], fxValues[fxValues.length - 1]));
        for (int i = 1; i < fxValues.length - 1; i++) {
            solution.append(String.format("%.8f", fxValues[i]));
            if (i < fxValues.length - 2) {
                solution.append(" + ");
            }
        }
        solution.append(")]\n");
        return solution.toString();
    }

    private String generateSimpsonOneThirdSolution(double[] fxValues, double h) {
        StringBuilder solution = new StringBuilder();
        solution.append(String.format("Solution: %.8f / 3 * [%.8f + %.8f + 4 * (", h, fxValues[0], fxValues[fxValues.length - 1]));
        for (int i = 1; i < fxValues.length - 1; i += 2) {
            solution.append(String.format("%.8f", fxValues[i]));
            if (i < fxValues.length - 3) {
                solution.append(" + ");
            }
        }
        solution.append(") + 2 * (");
        for (int i = 2; i < fxValues.length - 2; i += 2) {
            solution.append(String.format("%.8f", fxValues[i]));
            if (i < fxValues.length - 3) {
                solution.append(" + ");
            }
        }
        solution.append(")]\n");
        return solution.toString();
    }

    private String generateSimpsonThreeEighthSolution(double[] fxValues, double h) {
        StringBuilder solution = new StringBuilder();
        solution.append(String.format("Solution: 3 * %.8f / 8 * [%.8f + %.8f + 3 * (", h, fxValues[0], fxValues[fxValues.length - 1]));

        for (int i = 1; i < fxValues.length - 1; i++) {
            if (i % 3 != 0) {
                solution.append(String.format("%.8f", fxValues[i]));
                if (i < fxValues.length - 2) {
                    solution.append(" + ");
                }
            }
        }

        solution.append(") + 2 * (");

        for (int i = 1; i < fxValues.length - 1; i++) {
            if (i % 3 == 0) {
                solution.append(String.format("%.8f", fxValues[i]));
                if (i < fxValues.length - 4) {
                    solution.append(" + ");
                }
            }
        }

        solution.append(")]\n");
        return solution.toString();
    }


    private String generateBooleSolution(double[] fxValues, double h) {
        StringBuilder solution = new StringBuilder();
        solution.append(String.format("Solution: 2 * %.8f / 45 * [7 * (%.8f + %.8f) + 32 * (", h, fxValues[0], fxValues[fxValues.length - 1]));
        for (int i = 1; i < fxValues.length - 1; i++) {
            if (i % 2 != 0 && i % 4 != 0) {
                solution.append(String.format("%.8f", fxValues[i]));
                if (i < fxValues.length - 3) {
                    solution.append(" + ");
                }
            }
        }
        solution.append(") + 12 * (");
        for (int i = 2; i < fxValues.length - 1; i += 2) {
            if (i % 4 != 0) {
                solution.append(String.format("%.8f", fxValues[i]));
                if (i < fxValues.length - 3) {
                    solution.append(" + ");
                }
            }
        }
        solution.append(") + 14 * (");
        for (int i = 4; i < fxValues.length - 1; i += 4) {
            solution.append(String.format("%.8f", fxValues[i]));
            if (i < fxValues.length - 5) {
                solution.append(" + ");
            }
        }
        solution.append(")]\n");
        return solution.toString();
    }
}
