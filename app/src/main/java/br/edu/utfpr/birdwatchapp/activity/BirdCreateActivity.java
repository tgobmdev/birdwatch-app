package br.edu.utfpr.birdwatchapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.component.BirdComponent;
import br.edu.utfpr.birdwatchapp.entity.BirdEntity;
import br.edu.utfpr.birdwatchapp.parse.BirdParse;
import br.edu.utfpr.birdwatchapp.pattern.builder.request.BirdRequestBuilder;
import br.edu.utfpr.birdwatchapp.request.BirdRequest;
import br.edu.utfpr.birdwatchapp.ui.config.ActionBarConfig;
import br.edu.utfpr.birdwatchapp.ui.config.KeyboardConfig;
import br.edu.utfpr.birdwatchapp.util.ConstantsUtil;
import br.edu.utfpr.birdwatchapp.validator.BirdValidator;

public class BirdCreateActivity extends AppCompatActivity implements ActionBarConfig,
    KeyboardConfig {

  private EditText formSpecie, formColor, formCommonName;
  private BirdComponent birdComponent;
  private BirdParse birdParse;
  private SharedPreferences sharedPreferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bird_form);
    setTitle(R.string.label_birds);

    initializeComponents();
  }

  private void initializeComponents() {
    setupLayout();
    initializeFields();
    initializePreferences();
    initializeBirdComponent();
    configureActionBar();
  }

  private void setupLayout() {
    ConstraintLayout layoutBird = findViewById(R.id.layout_bird);
    hideKeyboard(layoutBird);
  }

  private void initializeFields() {
    formSpecie = findViewById(R.id.activity_bird_form_field_specie);
    formColor = findViewById(R.id.activity_bird_form_field_color);
    formCommonName = findViewById(R.id.activity_bird_form_field_common_name);
  }

  private void initializePreferences() {
    sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
    boolean saveColorPreference = sharedPreferences.getBoolean(ConstantsUtil.SAVE_COLOR_PREFERENCE,
        false);

    if (saveColorPreference) {
      String formColorPreference = sharedPreferences.getString("bird_color_preference", "");
      formColor.setText(formColorPreference);
    }
  }

  private void initializeBirdComponent() {
    birdComponent = new BirdComponent(this);
    birdParse = new BirdParse();
  }

  private void configureActionBar() {
    enableHomeAsUp();
  }

  private boolean validateFields() {
    BirdValidator birdValidator = new BirdValidator(this);
    return birdValidator.validateAllFields(formSpecie, formColor, formCommonName);
  }

  private void saveBird() {
    if (validateFields()) {
      savePreferences();
      saveBirdToDatabase();
      setResultAndFinish();
    }
  }

  private void saveBirdToDatabase() {
    String specie = formSpecie.getText().toString();
    String color = formColor.getText().toString();
    String commonName = formCommonName.getText().toString();

    BirdRequest birdRequest = new BirdRequestBuilder().setSpecie(specie).setColor(color)
        .setCommonName(commonName).build();

    BirdEntity birdEntity = birdParse.toBirdEntity(birdRequest);
    birdComponent.saveBird(birdEntity);
  }

  private void savePreferences() {
    boolean saveColorPreference = sharedPreferences.getBoolean(ConstantsUtil.SAVE_COLOR_PREFERENCE,
        false);

    if (saveColorPreference) {
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putString("bird_color_preference", formColor.getText().toString());
      editor.apply();
    }
  }

  private void setResultAndFinish() {
    setResult(Activity.RESULT_OK, new Intent());
    finish();
  }

  private void clearForm() {
    formSpecie.setText("");
    formColor.setText("");
    formCommonName.setText("");
  }

  private void toggleSaveColorPreference(MenuItem item) {
    boolean isChecked = !item.isChecked();
    item.setChecked(isChecked);

    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(ConstantsUtil.SAVE_COLOR_PREFERENCE, isChecked);
    editor.apply();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_save_clear, menu);
    boolean saveCor = sharedPreferences.getBoolean(ConstantsUtil.SAVE_COLOR_PREFERENCE, false);
    MenuItem saveColorItem = menu.findItem(R.id.menu_save_bird_color);
    saveColorItem.setChecked(saveCor);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int itemId = item.getItemId();
    if (itemId == R.id.menu_save_bird_color) {
      toggleSaveColorPreference(item);
      return true;
    } else if (itemId == R.id.menu_save) {
      saveBird();
      return true;
    } else if (itemId == R.id.menu_clear) {
      clearForm();
      return true;
    } else if (itemId == android.R.id.home) {
      finish();
      return true;
    } else {
      return super.onOptionsItemSelected(item);
    }
  }
}