package com.lynnik.doodlz;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class EraseImageDialogFragment extends DialogFragment {

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    builder.setMessage(R.string.message_erase);

    builder.setPositiveButton(
        R.string.button_erase, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            getDoodleFragment().getDoodleView().clear();
          }
        }
    );

    builder.setNegativeButton(android.R.string.cancel, null);

    return builder.create();
  }

  private MainActivityFragment getDoodleFragment() {
    return (MainActivityFragment)
        getFragmentManager().findFragmentById(R.id.doodleFragment);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    MainActivityFragment fragment = getDoodleFragment();
    if (fragment != null)
      fragment.setDialogOnScreen(true);
  }

  @Override
  public void onDetach() {
    super.onDetach();

    MainActivityFragment fragment = getDoodleFragment();
    if (fragment != null)
      fragment.setDialogOnScreen(false);
  }
}
