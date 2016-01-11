package me.littlekey.opencvdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

/**
 * Created by littlekey on 16/1/8.
 */
public class DemoFragment extends Fragment {

  private BaseLoaderCallback mOpenCVCallback;

  public static DemoFragment newInstance() {
    return new DemoFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_demo, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Button btn = (Button) view.findViewById(R.id.bMean);
    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        getActivity().startActivityForResult(photoPickerIntent, Const.SELECT_PHOTO);
      }
    });
    mOpenCVCallback = new BaseLoaderCallback(getActivity()) {
      @Override
      public void onManagerConnected(int status) {
        switch (status) {
          case LoaderCallbackInterface.SUCCESS:
            Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
            break;

          default:
            super.onManagerConnected(status);
            break;
        }
      }
    };
  }

  @Override
  public void onResume() {
    super.onResume();
    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, getActivity(), mOpenCVCallback);
  }
}
