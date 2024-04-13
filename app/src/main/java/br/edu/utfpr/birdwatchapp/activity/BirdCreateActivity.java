package br.edu.utfpr.birdwatchapp.activity;

import android.app.Activity;
import android.content.Intent;
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
import br.edu.utfpr.birdwatchapp.pattern.builder.BirdRequestBuilder;
import br.edu.utfpr.birdwatchapp.request.BirdRequest;
import br.edu.utfpr.birdwatchapp.ui.config.ActionBarConfig;
import br.edu.utfpr.birdwatchapp.ui.config.KeyboardConfig;

public class BirdCreateActivity extends AppCompatActivity implements ActionBarConfig,
    KeyboardConfig {

  private EditText editTextViewSpecie, editTextViewColor, editTextViewCommonName;
  private BirdComponent birdComponent;
  private BirdParse birdParse;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bird_form);
    setTitle(R.string.label_birds);

    initializeComponents();
  }

  private void initializeComponents() {
    ConstraintLayout layoutBird = findViewById(R.id.layoutBird);
    editTextViewSpecie = findViewById(R.id.textEditViewSpecie);
    editTextViewColor = findViewById(R.id.editTextViewColor);
    editTextViewCommonName = findViewById(R.id.textEditViewCommonName);

    birdComponent = new BirdComponent(this);
    birdParse = new BirdParse();
    enableHomeAsUp();
    hideKeyboard(layoutBird);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_save_clear, menu);
    return true;
  }

  private void saveBird() {
    String specie = editTextViewSpecie.getText().toString();
    String color = editTextViewColor.getText().toString();
    String commonName = editTextViewCommonName.getText().toString();

    BirdRequest birdRequest = new BirdRequestBuilder().setSpecie(specie) //
        .setColor(color) //
        .setCommonName(commonName) //
        .build();

    BirdEntity birdEntity = birdParse.toBirdEntity(birdRequest);
    birdComponent.saveBird(birdEntity);

    Intent intent = new Intent();
    setResult(Activity.RESULT_OK, intent);
    finish();
  }

  private void clearForm() {
    editTextViewSpecie.setText("");
    editTextViewColor.setText("");
    editTextViewCommonName.setText("");
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int itemId = item.getItemId();

    if (itemId == R.id.menu_save) {
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