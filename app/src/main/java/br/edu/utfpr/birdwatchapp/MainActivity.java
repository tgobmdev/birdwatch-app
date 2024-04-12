package br.edu.utfpr.birdwatchapp;

import android.os.Bundle;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategyRegistry;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.AboutExecutorStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.BirdExecutorStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.ObservationExecutorStrategy;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

  private DrawerLayout drawerLayout;
  private NavigationView navigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initializeUIComponents();
    configureActionBarDrawerToggle();
    configureNavigationView();
  }

  private void initializeUIComponents() {
    drawerLayout = findViewById(R.id.activity_main_drawer_layout);
    navigationView = findViewById(R.id.activity_main_navigation_view);
    Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
    setSupportActionBar(toolbar);
  }

  private void configureActionBarDrawerToggle() {
    Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
        R.string.open, R.string.close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();
    toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
  }

  private void configureNavigationView() {
    navigationView.setNavigationItemSelectedListener(item -> {
      ExecutorStrategyRegistry.register(R.id.menu_nav_observation,
          new ObservationExecutorStrategy(this)); //
      ExecutorStrategyRegistry.register(R.id.menu_nav_bird, new BirdExecutorStrategy(this));
      ExecutorStrategyRegistry.register(R.id.menu_nav_about, new AboutExecutorStrategy(this));

      ExecutorStrategyRegistry.getExecutor(item.getItemId()).execute();
      closeDrawer();
      return true;
    });
  }

  private void closeDrawer() {
    drawerLayout.closeDrawer(navigationView);
  }
}