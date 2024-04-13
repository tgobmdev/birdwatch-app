package br.edu.utfpr.birdwatchapp.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.adapter.BirdListAdapter;
import br.edu.utfpr.birdwatchapp.component.BirdComponent;
import br.edu.utfpr.birdwatchapp.entity.BirdEntity;
import br.edu.utfpr.birdwatchapp.parse.BirdParse;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategyRegistry;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.BirdCreateExecutorStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.FinishExecutorStrategy;
import br.edu.utfpr.birdwatchapp.response.BirdResponse;
import br.edu.utfpr.birdwatchapp.ui.config.ActionBarConfig;
import br.edu.utfpr.birdwatchapp.ui.dialog.AlertDeleteDialog;
import br.edu.utfpr.birdwatchapp.ui.modal.BirdModal;
import java.util.List;

public class BirdListActivity extends AppCompatActivity implements ActionBarConfig,
    AlertDeleteDialog {

  private List<BirdResponse> birds;
  private ListView listViewBirds;
  private BirdListAdapter birdListAdapter;
  private BirdParse birdParse;
  private BirdComponent birdComponent;
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
    birdComponent = new BirdComponent(this);
    birdParse = new BirdParse();
    birds = birdParse.toResponseList(birdComponent.findAllBirds());
    birdListAdapter = new BirdListAdapter(this, birds);
    enableHomeAsUp();
    setupListView();
  }

  private void setupListView() {
    listViewBirds.setAdapter(birdListAdapter);
    listViewBirds.setOnItemClickListener((parent, view, position, id) -> handleItemClick(view));
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
    birds = birdParse.toResponseList(birdComponent.findAllBirds());
    birdListAdapter.updateBirds(birds);
  }

  private void handleItemClick(View view) {
    int position = listViewBirds.getPositionForView(view);
    Long id = birds.get(position).getId();
    BirdEntity birdEntity = birdComponent.findBirdById(id);

    BirdModal birdModal = new BirdModal(this, birdEntity, updatedBird -> {
      birdComponent.updateBird(updatedBird);
      updateBirds();
    });

    birdModal.show();
  }

  public void onDeleteClick(View view) {
    DialogInterface.OnClickListener listener = (dialog, which) -> {
      if (which == DialogInterface.BUTTON_POSITIVE) {
        handleDeleteClick(view);
      }
    };
    confirm(this, listener);
  }

  private void handleDeleteClick(View view) {
    int position = listViewBirds.getPositionForView(view);
    Long id = birds.get(position).getId();
    BirdEntity birdEntity = birdComponent.findBirdById(id);
    birdComponent.deleteBird(birdEntity);
    updateBirds();
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