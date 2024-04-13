package br.edu.utfpr.birdwatchapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategyRegistry;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.BirdCreateExecutorStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.FinishExecutorStrategy;
import br.edu.utfpr.birdwatchapp.ui.config.ActionBarConfig;
import br.edu.utfpr.birdwatchapp.ui.dialog.AlertDeleteDialog;

public class BirdListActivity extends AppCompatActivity implements ActionBarConfig,
    AlertDeleteDialog {

  private ListView listViewBirds;
  private ActivityResultLauncher<Intent> activityResultLauncher;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bird_list);
    setTitle(R.string.label_birds);

    initializeComponents();
    registerActivityResultLauncher();
    registerStrategies();
  }

  private void initializeComponents() {
    listViewBirds = findViewById(R.id.listViewBirds);

    enableHomeAsUp();
  }

  private void registerStrategies() {
    ExecutorStrategyRegistry.register(R.id.menu_add,
        new BirdCreateExecutorStrategy(this, activityResultLauncher));
    ExecutorStrategyRegistry.register(android.R.id.home, new FinishExecutorStrategy(this));
  }

  private void registerActivityResultLauncher() {
    activityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), result -> {
          if (result.getResultCode() == Activity.RESULT_OK) {
            updateBirds();
          }
        });
  }

  private void updateBirds() {
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_list, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    ExecutorStrategy executorStrategy = ExecutorStrategyRegistry.getExecutor(item.getItemId());
    return executorStrategy != null ? executorStrategy.execute()
        : super.onOptionsItemSelected(item);
  }
}