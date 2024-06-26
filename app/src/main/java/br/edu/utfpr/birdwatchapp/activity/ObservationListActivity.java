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
import br.edu.utfpr.birdwatchapp.adapter.ObservationListAdapter;
import br.edu.utfpr.birdwatchapp.component.BirdComponent;
import br.edu.utfpr.birdwatchapp.component.ObservationComponent;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategyRegistry;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.FinishExecutorStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.ObservationCreateExecutorStrategy;
import br.edu.utfpr.birdwatchapp.response.ObservationResponse;
import br.edu.utfpr.birdwatchapp.ui.config.ActionBarConfig;
import br.edu.utfpr.birdwatchapp.ui.dialog.AlertDeleteDialog;
import br.edu.utfpr.birdwatchapp.ui.listener.DataPickerListener;
import br.edu.utfpr.birdwatchapp.ui.listener.TimePickerListener;
import br.edu.utfpr.birdwatchapp.ui.modal.ObservationModal;
import java.util.List;

public class ObservationListActivity extends AppCompatActivity implements ActionBarConfig,
    AlertDeleteDialog, DataPickerListener, TimePickerListener {

  private List<ObservationResponse> observations;
  private ListView listViewObservations;
  private ObservationListAdapter observationListAdapter;
  private ObservationComponent observationComponent;
  private ActivityResultLauncher<Intent> activityResultLauncher;
  private BirdComponent birdComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_observation_list);
    setTitle(R.string.label_observations);

    initializeComponents();
    registerActivityResultLauncher();
    registerStrategies();
  }

  private void initializeComponents() {
    listViewObservations = findViewById(R.id.activity_observation_list_view);
    observationComponent = new ObservationComponent(this);
    birdComponent = new BirdComponent(this);

    observations = observationComponent.findAllObservations();
    observationListAdapter = new ObservationListAdapter(this, observations);
    enableHomeAsUp();
    setupListView();
  }

  private void registerStrategies() {
    ExecutorStrategyRegistry.register(R.id.menu_add,
        new ObservationCreateExecutorStrategy(this, activityResultLauncher));
    ExecutorStrategyRegistry.register(android.R.id.home, new FinishExecutorStrategy(this));
  }

  private void registerActivityResultLauncher() {
    activityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), result -> {
          if (result.getResultCode() == Activity.RESULT_OK) {
            updateObservations();
          }
        });
  }

  private void setupListView() {
    listViewObservations.setAdapter(observationListAdapter);
    listViewObservations.setOnItemClickListener(
        (parent, view, position, id) -> handleItemClick(view));
  }

  private void handleItemClick(View view) {
    int position = listViewObservations.getPositionForView(view);
    Long id = observations.get(position).getId();
    ObservationEntity observationEntity = observationComponent.findObservationById(id);

    ObservationModal observationModal = new ObservationModal(this, birdComponent, observationEntity,
        updatedObservation -> {
          observationComponent.updateObservation(updatedObservation);
          updateObservations();
        });

    observationModal.show();
  }

  private void updateObservations() {
    observations = observationComponent.findAllObservations();
    observationListAdapter.updateObservations(observations);
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

  public void onDeleteClick(View view) {
    DialogInterface.OnClickListener listener = (dialog, which) -> {
      if (which == DialogInterface.BUTTON_POSITIVE) {
        handleDeleteClick(view);
      }
    };
    confirm(this, listener);
  }

  private void handleDeleteClick(View view) {
    int position = listViewObservations.getPositionForView(view);
    Long id = observations.get(position).getId();
    ObservationEntity observationEntity = observationComponent.findObservationById(id);
    observationComponent.deleteObservation(observationEntity);
    updateObservations();
  }
}