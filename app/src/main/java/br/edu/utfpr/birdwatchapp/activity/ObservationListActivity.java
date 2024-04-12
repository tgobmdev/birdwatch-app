package br.edu.utfpr.birdwatchapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.adapter.ObservationListViewAdapter;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.persistence.ObservationDatabase;
import br.edu.utfpr.birdwatchapp.ui.configurable.ActionBarConfigurable;
import br.edu.utfpr.birdwatchapp.util.MessageUtil;
import java.util.List;

public class ObservationListActivity extends AppCompatActivity implements ActionBarConfigurable {

  private List<ObservationEntity> observations;
  private ListView listViewObservations;
  private ObservationListViewAdapter observationListViewAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_observation_list);
    setTitle(R.string.label_observations);

    listViewObservations = findViewById(R.id.listViewObservations);
    observations = ObservationDatabase.getObservationDatabase(this).observationDao().findAll();
    observationListViewAdapter = new ObservationListViewAdapter(this, observations);
    enableHomeAsUp();
    setup();
  }

  private void setup() {
    setupObservationArrayAdapter();
    setupItemClickListener();
  }

  private void setupObservationArrayAdapter() {
    listViewObservations.setAdapter(observationListViewAdapter);
  }

  private void setupItemClickListener() {
    listViewObservations.setOnItemClickListener((parent, view, position, id) -> {
      ObservationEntity observation = observations.get(position);
      Toast.makeText(ObservationListActivity.this, observation.toString(), Toast.LENGTH_SHORT)
          .show();
    });
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
      observations = ObservationDatabase.getObservationDatabase(this).observationDao().findAll();
      observationListViewAdapter.updateObservations(observations);
    }
  }
}