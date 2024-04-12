package br.edu.utfpr.birdwatchapp.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.adapter.ObservationListAdapter;
import br.edu.utfpr.birdwatchapp.component.ObservationComponent;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.parse.ObservationParse;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategyRegistry;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.FinishExecutorStrategy;
import br.edu.utfpr.birdwatchapp.pattern.strategy.executor.ObservationCreateExecutorStrategy;
import br.edu.utfpr.birdwatchapp.response.ObservationResponse;
import br.edu.utfpr.birdwatchapp.ui.config.ActionBarConfig;
import br.edu.utfpr.birdwatchapp.ui.dialog.AlertDeleteDialog;
import br.edu.utfpr.birdwatchapp.ui.listener.DataPickerListener;
import br.edu.utfpr.birdwatchapp.ui.listener.TimePickerListener;
import br.edu.utfpr.birdwatchapp.util.ConstantsUtil;
import br.edu.utfpr.birdwatchapp.util.DateUtil;
import java.util.List;

public class ObservationListActivity extends AppCompatActivity implements ActionBarConfig,
    AlertDeleteDialog, DataPickerListener, TimePickerListener {

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

    initializeComponents();
    registerActivityResultLauncher();
    registerStrategies();
  }

  private void initializeComponents() {
    listViewObservations = findViewById(R.id.listViewObservations);
    observationComponent = new ObservationComponent(this);
    observationParse = new ObservationParse();
    observations = observationParse.toResponseList(observationComponent.findAllObservations());
    observationListAdapter = new ObservationListAdapter(this, observations);
    enableHomeAsUp();
    setupListView();
  }

  private void registerStrategies() {
    ExecutorStrategyRegistry.register(R.id.menu_observation_add,
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

    // Crie o diálogo
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Editar Observação");

    // Infle o layout do formulário
    View formView = getLayoutInflater().inflate(R.layout.activity_observation_form, null);

    DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    float density = displayMetrics.density;

    // Convert dp to px
    int dp = 28; // Replace with your dp value
    int px = Math.round(dp * density);
    formView.setPadding(0, 0, 0, px);
    builder.setView(formView);

    // Obtenha referências para os campos do formulário
    EditText editTextDate = formView.findViewById(R.id.editTextDate);
    EditText editTextTime = formView.findViewById(R.id.editTextTime);
    EditText editTextLocation = formView.findViewById(R.id.editTextLocation);
    Spinner spinnerSpecie = formView.findViewById(R.id.spinnerSpecie);

    // Preencha os campos com os dados da observação selecionada
    String date = DateUtil.formatDate(observationEntity.getDateTime(),
        ConstantsUtil.DATE_FORMAT_YYYY_MM_DD);
    String time = DateUtil.formatDate(observationEntity.getDateTime(),
        ConstantsUtil.TIME_FORMAT_HH_MM);
    editTextDate.setText(date);
    editTextTime.setText(time);
    editTextLocation.setText(observationEntity.getLocation());

    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.observation_species, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerSpecie.setAdapter(adapter);

    // listener para os campos data e time
    setDataPickerListener(this, editTextDate);
    setTimePickerListener(this, editTextTime);

    // Configure os botões do diálogo
    builder.setPositiveButton("Salvar", (dialog, which) -> {
      // Atualize a observação com os novos valores
      String dateTime = editTextDate.getText().toString() + "T" + editTextTime.getText().toString();
      observationEntity.setDateTime(DateUtil.parseDateDefault(dateTime));
      observationEntity.setLocation(editTextLocation.getText().toString());
      observationEntity.setSpecie(spinnerSpecie.getSelectedItem().toString());

      observationComponent.updateObservation(observationEntity);

      // Atualize a lista de observações na UI
      updateObservations();
    });

    builder.setNegativeButton("Cancelar", null);

    // Exiba o diálogo
    builder.create().show();
  }

  private void updateObservations() {
    observations = observationParse.toResponseList(observationComponent.findAllObservations());
    observationListAdapter.updateObservations(observations);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_observation_list, menu);
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