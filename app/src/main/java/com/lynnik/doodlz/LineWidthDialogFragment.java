package com.lynnik.doodlz;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

public class LineWidthDialogFragment extends DialogFragment {

  private ImageView widthImageView;

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    View lineWidthDialogView = getActivity().getLayoutInflater()
        .inflate(R.layout.fragment_line_width, null);
    builder.setView(lineWidthDialogView);

    builder.setTitle(R.string.title_line_width_dialog);

    widthImageView = lineWidthDialogView.findViewById(R.id.widthImageView);

    final DoodleView doodleView = getDoodleFragment().getDoodleView();
    final SeekBar widthSeekBar = lineWidthDialogView
        .findViewById(R.id.widthSeekBar);
    widthSeekBar.setOnSeekBarChangeListener(lineWidthChanged);
    widthSeekBar.setProgress(doodleView.getLineWidth());

    builder.setPositiveButton(
        R.string.button_set_line_width, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            doodleView.setLineWidth(widthSeekBar.getProgress());
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

  private final SeekBar.OnSeekBarChangeListener lineWidthChanged =
      new SeekBar.OnSeekBarChangeListener() {

        final Bitmap bitmap = Bitmap.createBitmap(
            400, 100, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
          Paint p = new Paint();
          p.setColor(getDoodleFragment().getDoodleView().getDrawingColor());
          p.setStrokeCap(Paint.Cap.ROUND);
          p.setStrokeWidth(i);

          bitmap.eraseColor(getResources().getColor(
              android.R.color.transparent, getContext().getTheme()));
          canvas.drawLine(30, 50, 370, 50, p);
          widthImageView.setImageBitmap(bitmap);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
      };
}
