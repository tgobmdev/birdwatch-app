package br.edu.utfpr.birdwatchapp.activity;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategyRegistry;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.FinishExecutorStrategy;
import br.edu.utfpr.birdwatchapp.ui.config.ActionBarConfig;

public class AboutActivity extends AppCompatActivity implements ActionBarConfig {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);
    setTitle(R.string.label_about);

    enableHomeAsUp();
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    ExecutorStrategyRegistry.register(android.R.id.home, new FinishExecutorStrategy(this));
    ExecutorStrategy executorStrategy = ExecutorStrategyRegistry.getExecutor(item.getItemId());
    return executorStrategy != null ? executorStrategy.execute()
        : super.onOptionsItemSelected(item);
  }
}