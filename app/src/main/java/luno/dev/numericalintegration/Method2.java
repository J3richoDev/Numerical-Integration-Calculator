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

import org.mariuszgromada.math.mxparser.Expression;

public class Method2 extends AppCompatActivity {

    private EditText firstXEditText;
    private EditText lastXEditText;
    private EditText nEditText;
    private EditText customEquationEditText;
    private TextView resultTextView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_method2);

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

        firstXEditText = findViewById(R.id.firstX);
        lastXEditText = findViewById(R.id.lastX);
        nEditText = findViewById(R.id.n);
        customEquationEditText = findViewById(R.id.customEquation); // Add this line in your XML layout
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
            Toast.makeText(Method2.this, "Home", Toast.LENGTH_SHORT).show();
            intent = new Intent(Method2.this, MainActivity.class);
        } else if (id == R.id.method1) {
            Toast.makeText(Method2.this, "Method 1", Toast.LENGTH_SHORT).show();
            intent = new Intent(Method2.this, Method1.class);
        } else if (id == R.id.method2) {
            Toast.makeText(Method2.this, "Method 2", Toast.LENGTH_SHORT).show();
            intent = new Intent(Method2.this, Method2.class);
        } else if (id == R.id.about) {
            Toast.makeText(Method2.this, "About", Toast.LENGTH_SHORT).show();
            intent = new Intent(Method2.this, Method2.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    private void computeRules() {
        double firstX = Double.parseDouble(firstXEditText.getText().toString());
        double lastX = Double.parseDouble(lastXEditText.getText().toString());
        int n = Integer.parseInt(nEditText.getText().toString());
        String x1 = String.valueOf(firstX);
        String x2 = String.valueOf(lastX);
        String nValue = String.valueOf(n);

        double h = (lastX - firstX) / n;

        double[] xValues = new double[n + 1];
        double[] fxValues = new double[n + 1];

        String customEquation = customEquationEditText.getText().toString();

        for (int i = 0; i <= n; i++) {
            xValues[i] = firstX + i * h;
            fxValues[i] = f(xValues[i], customEquation);
        }

        double trapezoidalRuleResult = computeTrapezoidalRule(fxValues, h);
        String trapezoidalRuleSolution = generateTrapezoidalSolution(fxValues, h);

        double simpsonOneThirdRuleResult = computeSimpsonOneThirdRule(fxValues, h);
        String simpsonOneThirdRuleSolution = generateSimpsonOneThirdSolution(fxValues, h);

        double simpsonThreeEighthRuleResult = computeSimpsonThreeEighthRule(fxValues, h);
        String simpsonThreeEighthRuleSolution = generateSimpsonThreeEighthSolution(fxValues, h);

        double booleRuleResult = computeBooleRule(fxValues, h);
        String booleRuleSolution = generateBooleSolution(fxValues, h);

        Intent intent = new Intent(Method2.this, Solution.class);
        intent.putExtra("x1", x1);
        intent.putExtra("x2", x2);
        intent.putExtra("n", nValue);
        intent.putExtra("h", h);
        intent.putExtra("xValues", xValues);
        intent.putExtra("fxValues", fxValues);
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

    private double f(double x, String customEquation) {
        Expression expression = new Expression(customEquation);
        expression.addArguments(new org.mariuszgromada.math.mxparser.Argument("x = " + x));
        return expression.calculate();
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
        solution.append(String.format("Solution: 3 * %.8f / 8 * [%.8f + %.8f + 2 * (", h, fxValues[0], fxValues[fxValues.length - 1]));
        for (int i = 1; i < fxValues.length - 1; i++) {
            if (i % 3 == 0) {
                solution.append(String.format("%.8f", fxValues[i]));
                if (i < fxValues.length - 4) {
                    solution.append(" + ");
                }
            }
        }
        solution.append(") + 3 * (");
        for (int i = 1; i < fxValues.length - 1; i++) {
            if (i % 3 != 0) {
                solution.append(String.format("%.8f", fxValues[i]));
                if (i < fxValues.length - 2) {
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
