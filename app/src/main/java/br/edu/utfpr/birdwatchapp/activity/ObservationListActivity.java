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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.adapter.ObservationListAdapter;
import br.edu.utfpr.birdwatchapp.component.ObservationComponent;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.parse.ObservationParse;
import br.edu.utfpr.birdwatchapp.response.ObservationResponse;
import br.edu.utfpr.birdwatchapp.ui.config.ActionBarConfig;
import br.edu.utfpr.birdwatchapp.ui.dialog.AlertDeleteDialog;
import br.edu.utfpr.birdwatchapp.util.MessageUtil;
import java.util.List;

public class ObservationListActivity extends AppCompatActivity implements ActionBarConfig,
    AlertDeleteDialog {

  private List<ObservationResponse> observations;
  private ListView listViewObservations;
  private ObservationListAdapter observationListAdapter;
  private ObservationParse observationParse;
  private ObservationComponent observationComponent;

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
    int idItem = item.getItemId();

    if (idItem == R.id.menu_observation_add) {
      Intent intent = new Intent(this, ObservationCreateActivity.class);
      startActivityForResult(intent, MessageUtil.REQUEST_CODE_CREATE_OBSERVATION);
      return true;
    } else if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    } else {
      return super.onOptionsItemSelected(item);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == MessageUtil.REQUEST_CODE_CREATE_OBSERVATION
        && resultCode == Activity.RESULT_OK) {
      observations = observationParse.toResponseList(observationComponent.findAllObservations());
      observationListAdapter.updateObservations(observations);
    }
  }
}