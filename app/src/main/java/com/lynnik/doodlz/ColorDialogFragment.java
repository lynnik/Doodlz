package com.lynnik.doodlz;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.SeekBar;

public class ColorDialogFragment extends DialogFragment {

  private SeekBar alphaSeekBar;
  private SeekBar redSeekBar;
  private SeekBar greenSeekBar;
  private SeekBar blueSeekBar;
  private View colorView;

  private int color;

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    View colorDialogView = getActivity().getLayoutInflater()
        .inflate(R.layout.fragment_color, null);
    builder.setView(colorDialogView);

    builder.setTitle(R.string.title_color_dialog);

    alphaSeekBar = colorDialogView.findViewById(R.id.alphaSeekBar);
    redSeekBar = colorDialogView.findViewById(R.id.redSeekBar);
    greenSeekBar = colorDialogView.findViewById(R.id.greenSeekBar);
    blueSeekBar = colorDialogView.findViewById(R.id.blueSeekBar);
    colorView = colorDialogView.findViewById(R.id.colorView);

    alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
    redSeekBar.setOnSeekBarChangeListener(colorChangedListener);
    greenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
    blueSeekBar.setOnSeekBarChangeListener(colorChangedListener);

    final DoodleView doodleView = getDoodleFragment().getDoodleView();
    color = doodleView.getDrawingColor();
    alphaSeekBar.setProgress(Color.alpha(color));
    redSeekBar.setProgress(Color.red(color));
    greenSeekBar.setProgress(Color.green(color));
    blueSeekBar.setProgress(Color.blue(color));

    builder.setPositiveButton(
        R.string.button_set_color, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            doodleView.setDrawingColor(color);
          }
        });

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

  private final SeekBar.OnSeekBarChangeListener colorChangedListener =
      new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
          if (b) {
            color = Color.argb(
                alphaSeekBar.getProgress(),
                redSeekBar.getProgress(),
                greenSeekBar.getProgress(),
                blueSeekBar.getProgress());

            colorView.setBackgroundColor(color);
          }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
      };
}
