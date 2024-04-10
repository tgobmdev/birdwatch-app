package br.edu.utfpr.birdwatchapp.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.adapter.ObservationAdapter;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.parse.ObservationParse;
import java.util.List;

public class ObservationListActivity extends AppCompatActivity {

  private List<ObservationEntity> observations;
  private ListView listViewObservations;
  private ObservationAdapter observationAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_observation_list);
    ObservationParse observationParse = new ObservationParse(getResources());

    listViewObservations = findViewById(R.id.listViewObservations);
    observations = observationParse.toObservationsList();
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
}