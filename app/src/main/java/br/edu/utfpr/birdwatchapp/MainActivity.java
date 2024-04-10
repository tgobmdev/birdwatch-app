package br.edu.utfpr.birdwatchapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.activity.AboutActivity;
import br.edu.utfpr.birdwatchapp.activity.ObservationCreateActivity;
import br.edu.utfpr.birdwatchapp.activity.ObservationListActivity;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.observable.ObservationObservable;
import br.edu.utfpr.birdwatchapp.util.MessageUtil;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void viewObservations(View view) {
    Intent intent = new Intent(MainActivity.this, ObservationListActivity.class);
    List<ObservationEntity> observations = ObservationObservable.getInstance().getObservations();
    intent.putExtra("observations", new ArrayList<>(observations));
    startActivity(intent);
  }

  public void createObservation(View view) {
    Intent intent = new Intent(MainActivity.this, ObservationCreateActivity.class);
    startActivityForResult(intent, MessageUtil.REQUEST_CODE_CREATE_OBSERVATION);
  }

  public void setupAbout(View view) {
    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
    startActivity(intent);
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
      }
    }
  }
}