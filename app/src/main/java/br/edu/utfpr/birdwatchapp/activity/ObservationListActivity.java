package br.edu.utfpr.birdwatchapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.adapter.ObservationAdapter;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.observable.ObservationObservable;
import br.edu.utfpr.birdwatchapp.persistence.ObservationDatabase;
import br.edu.utfpr.birdwatchapp.util.MessageUtil;
import java.util.List;

public class ObservationListActivity extends AppCompatActivity {

  private List<ObservationEntity> observations;
  private ListView listViewObservations;
  private ObservationAdapter observationAdapter;
  private ActivityResultLauncher<Intent> addObservationLauncher;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_observation_list);

    listViewObservations = findViewById(R.id.listViewObservations);
    observations = ObservationDatabase.getObservationDatabase(this).observationDao().findAll();
    observationAdapter = new ObservationAdapter(this, observations);

    setup();
  }

  private void setup() {
    setupObservationArrayAdapter();
    setupItemClickListener();
  }

  private void setupObservationArrayAdapter() {
    listViewObservations.setAdapter(observationAdapter);
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
    getMenuInflater().inflate(R.menu.menu_list, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int idItem = item.getItemId();

    if (idItem == R.id.menu_add) {
      Intent intent = new Intent(this, ObservationCreateActivity.class);
      startActivityForResult(intent, MessageUtil.REQUEST_CODE_CREATE_OBSERVATION);
      return true;
    } else {
      if (idItem == R.id.menu_about) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
        return true;
      } else {
        return super.onOptionsItemSelected(item);
      }
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == MessageUtil.REQUEST_CODE_CREATE_OBSERVATION
        && resultCode == Activity.RESULT_OK) {
      if (data != null && data.hasExtra(MessageUtil.EXTRA_OBSERVATION)) {
        ObservationEntity observation = (ObservationEntity) data.getSerializableExtra(
            MessageUtil.EXTRA_OBSERVATION);
        ObservationObservable.getInstance().addObservation(observation);
        observationAdapter.notifyDataSetChanged();
      }
    }
  }
}