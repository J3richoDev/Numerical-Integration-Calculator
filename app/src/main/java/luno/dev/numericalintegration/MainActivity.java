package luno.dev.numericalintegration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView cardViewMethod1 = findViewById(R.id.cardview1);
        CardView cardViewMethod2 = findViewById(R.id.cardview2);

        cardViewMethod1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Method1.class);
                startActivity(intent);
            }
        });

        cardViewMethod2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Method2.class);
                startActivity(intent);
            }
        });

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
    }

    private void handleNavigationItemSelected(MenuItem item) {
        Intent intent = null;
        int id = item.getItemId();

        if (id == R.id.home) {
            Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
            intent = new Intent(MainActivity.this, MainActivity.class);
        } else if (id == R.id.method1) {
            Toast.makeText(MainActivity.this, "Method 1", Toast.LENGTH_SHORT).show();
            intent = new Intent(MainActivity.this, Method1.class);
        } else if (id == R.id.method2) {
            Toast.makeText(MainActivity.this, "Method 2", Toast.LENGTH_SHORT).show();
            intent = new Intent(MainActivity.this, Method2.class);
        } else if (id == R.id.about) {
            Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
            intent = new Intent(MainActivity.this, Method2.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
