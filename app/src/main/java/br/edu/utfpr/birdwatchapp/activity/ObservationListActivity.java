package br.edu.utfpr.birdwatchapp.activity;

import android.app.Activity;
import android.content.Context;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.adapter.ObservationListAdapter;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.persistence.ObservationDatabase;
import br.edu.utfpr.birdwatchapp.ui.configurable.ActionBarConfigurable;
import br.edu.utfpr.birdwatchapp.util.MessageUtil;
import java.util.List;

public class ObservationListActivity extends AppCompatActivity implements ActionBarConfigurable {

  private List<ObservationEntity> observations;
  private ListView listViewObservations;
  private ObservationListAdapter observationListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_observation_list);
    setTitle(R.string.label_observations);

    listViewObservations = findViewById(R.id.listViewObservations);
    observations = ObservationDatabase.getObservationDatabase(this).observationDao().findAll();
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
      ObservationEntity observation = observations.get(position);
      Toast.makeText(ObservationListActivity.this, observation.toString(), Toast.LENGTH_SHORT)
          .show();
    });
  }

  public void alertTeste(Context context, String msg) {
    AlertDialog.Builder builder = new Builder(this);
    builder.setTitle("Aviso");
    builder.setIcon(android.R.drawable.ic_dialog_alert);
    builder.setMessage("Teste");

    builder.setNeutralButton("OK", null);
    AlertDialog alertDialog = builder.create();
    alertDialog.show();
  }

  public void confirmarAlertTest(Context context, String msg,
      DialogInterface.OnClickListener listener) {
    AlertDialog alertDialog = new AlertDialog.Builder(context) //
        .setTitle("Confirmacao") //
        .setIcon(android.R.drawable.ic_dialog_alert) //
        .setMessage("Teste") //
        .setPositiveButton("Sim", listener) //
        .setNegativeButton("Nao", listener) //
        .create();
    alertDialog.show();
  }

  public void onDeleteClick(View view) {
    DialogInterface.OnClickListener listener = (dialog, which) -> {
      if (which == DialogInterface.BUTTON_POSITIVE) {
        int position = listViewObservations.getPositionForView(view);
        ObservationEntity observation = observations.get(position);
        ObservationDatabase.getObservationDatabase(getApplicationContext()).observationDao()
            .delete(observation);

        observations = ObservationDatabase.getObservationDatabase(getApplicationContext())
            .observationDao().findAll();
        observationListAdapter.updateObservations(observations);
      }
    };
    confirmarAlertTest(this, "", listener);
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
      observationListAdapter.updateObservations(observations);
    }
  }
}