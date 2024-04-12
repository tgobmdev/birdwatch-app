package br.edu.utfpr.birdwatchapp.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import br.edu.utfpr.birdwatchapp.R;

public interface AlertDeleteDialog {

  default void confirm(Context context, DialogInterface.OnClickListener listener) {
    AlertDialog alertDeleteDialog = new AlertDialog.Builder(context) //
        .setTitle(R.string.confirmation) //
        .setIcon(android.R.drawable.ic_dialog_alert) //
        .setMessage(R.string.label_delete_record) //
        .setPositiveButton(R.string.yes, listener) //
        .setNegativeButton(R.string.no, listener) //
        .create();
    alertDeleteDialog.show();
  }
}