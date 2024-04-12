package br.edu.utfpr.birdwatchapp.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.adapter.ObservationListAdapter;
import br.edu.utfpr.birdwatchapp.component.ObservationComponent;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.parse.ObservationParse;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategyRegistry;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.FinishActivityExecutorStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.ObservationCreateExecutorStrategy;
import br.edu.utfpr.birdwatchapp.response.ObservationResponse;
import br.edu.utfpr.birdwatchapp.ui.config.ActionBarConfig;
import br.edu.utfpr.birdwatchapp.ui.dialog.AlertDeleteDialog;
import java.util.List;

public class ObservationListActivity extends AppCompatActivity implements ActionBarConfig,
    AlertDeleteDialog {

  private List<ObservationResponse> observations;
  private ListView listViewObservations;
  private ObservationListAdapter observationListAdapter;
  private ObservationParse observationParse;
  private ObservationComponent observationComponent;
  private ActivityResultLauncher<Intent> activityResultLauncher;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_observation_list);
    setTitle(R.string.label_observations);

    listViewObservations = findViewById(R.id.listViewObservations);
    observationComponent = new ObservationComponent(this);
    observationParse = new ObservationParse();
    observations = observationParse.toResponseList(observationComponent.findAllObservations());
    observationListAdapter = new ObservationListAdapter(this, observations);
    enableHomeAsUp();
    setup();

    activityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), result -> {
          if (result.getResultCode() == Activity.RESULT_OK) {
            observations = observationParse.toResponseList(
                observationComponent.findAllObservations());
            observationListAdapter.updateObservations(observations);
          }
        });
  }

  private void setup() {
    setupObservationArrayAdapter();
    setupItemClickListener();
  }

  private void setupObservationArrayAdapter() {
    listViewObservations.setAdapter(observationListAdapter);
  }

  private void setupItemClickListener() {
    listViewObservations.setOnItemClickListener((parent, view, position, id) -> {
      ObservationEntity observationEntity = observationComponent.findObservationById(
          (long) position);
      Toast.makeText(ObservationListActivity.this, observationEntity.toString(), Toast.LENGTH_SHORT)
          .show();
    });
  }

  public void onDeleteClick(View view) {
    DialogInterface.OnClickListener listener = (dialog, which) -> {
      if (which == DialogInterface.BUTTON_POSITIVE) {
        int position = listViewObservations.getPositionForView(view);
        Long id = observations.get(position).getId();
        ObservationEntity observationEntity = observationComponent.findObservationById(id);
        observationComponent.deleteObservation(observationEntity);
        observations = observationParse.toResponseList(observationComponent.findAllObservations());
        observationListAdapter.updateObservations(observations);
      }
    };
    confirm(this, listener);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_observation_list, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    ExecutorStrategyRegistry.register(R.id.menu_observation_add,
        new ObservationCreateExecutorStrategy(this, activityResultLauncher));
    ExecutorStrategyRegistry.register(android.R.id.home, new FinishActivityExecutorStrategy(this));

    ExecutorStrategy executorStrategy = ExecutorStrategyRegistry.getExecutor(item.getItemId());

    return executorStrategy != null ? executorStrategy.execute()
        : super.onOptionsItemSelected(item);
  }
}