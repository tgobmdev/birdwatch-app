package br.edu.utfpr.birdwatchapp.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.ui.config.ActionBarConfig;
import br.edu.utfpr.birdwatchapp.ui.config.KeyboardConfig;

public class BirdCreateActivity extends AppCompatActivity implements ActionBarConfig,
    KeyboardConfig {

  private EditText editTextViewSpecie, editTextViewColor, editTextViewCommonName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bird_form);
    setTitle(R.string.label_birds);

    initializeComponents();
  }

  private void initializeComponents() {
    ConstraintLayout layoutBird = findViewById(R.id.layoutBird);
    editTextViewSpecie = findViewById(R.id.textViewSpecie);
    editTextViewColor = findViewById(R.id.texViewColor);
    editTextViewCommonName = findViewById(R.id.textViewCommonName);
    enableHomeAsUp();
    hideKeyboard(layoutBird);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_save_clear, menu);
    return true;
  }

  private void saveBird() {
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