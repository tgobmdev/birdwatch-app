package br.edu.utfpr.birdwatchapp;

import android.os.Bundle;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import br.edu.utfpr.birdwatchapp.pattern.strategy.NavigationActionStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.action.AboutNavigationActionStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.action.BirdNavigationActionStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.action.ObservationNavigationActionStrategy;
import com.google.android.material.navigation.NavigationView;
import java.util.HashMap;
import java.util.Map;

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
      Map<Integer, NavigationActionStrategy> actions = createNavigationActions();
      NavigationActionStrategy action = actions.get(item.getItemId());
      if (action != null) {
        action.execute();
      }
      closeDrawer();
      return true;
    });
  }

  private Map<Integer, NavigationActionStrategy> createNavigationActions() {
    Map<Integer, NavigationActionStrategy> actions = new HashMap<>();
    actions.put(R.id.menu_nav_observation, new ObservationNavigationActionStrategy(this));
    actions.put(R.id.menu_nav_bird, new BirdNavigationActionStrategy(this));
    actions.put(R.id.menu_nav_about, new AboutNavigationActionStrategy(this));
    return actions;
  }

  private void closeDrawer() {
    drawerLayout.closeDrawer(navigationView);
  }
}