package me.littlekey.opencvdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by littlekey on 16/1/11.
 */
public class MainFragment extends Fragment {

  public int ACTION_MODE = 0;
  public final static String IMAGE_URI = "me.littlekey.opencvdemo.mainfragment.image_uri";

  private Mat mSrcMat;
  private Uri mImageUri;
  private Bitmap mSelectBitmap;
  private Bitmap mProcessedBitmap;

  public static MainFragment newInstance(Uri uri) {
    Bundle args = new Bundle();
    args.putParcelable(IMAGE_URI, uri);
    MainFragment fragment = new MainFragment();
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mImageUri = getArguments().getParcelable(IMAGE_URI);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_main, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (mImageUri != null) {
      try {
        final InputStream imageStream = getActivity().getContentResolver().openInputStream(mImageUri);
        mSelectBitmap = BitmapFactory.decodeStream(imageStream);
        mSrcMat = new Mat(mSelectBitmap.getHeight(), mSelectBitmap.getWidth(), CvType.CV_8UC4);
        Utils.bitmapToMat(mSelectBitmap, mSrcMat);
      } catch (FileNotFoundException ignore) {
        ignore.printStackTrace();
      }
      ImageView ivImage = (ImageView) view.findViewById(R.id.ivImage);
      ImageView ivImageProcessed = (ImageView) view.findViewById(R.id.ivImageProcessed);

      if (mSelectBitmap != null) {
//        Mat kernel = new Mat(3, 3, CvType.CV_16SC1);
//        kernel.put(0, 0,
//            0, -1, 0,
//            -1, 5, -1,
//            0, -1, 0);
//        Imgproc.filter2D(mSrcMat, mSrcMat, mSrcMat.depth(), kernel);

//        Imgproc.blur(mSrcMat, mSrcMat, new Size(3, 3));

//        Imgproc.GaussianBlur(mSrcMat, mSrcMat, new Size(3, 3), 0);

//        Imgproc.medianBlur(mSrcMat, mSrcMat, 3);

//        Mat kernelDilate = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
//        Imgproc.dilate(mSrcMat, mSrcMat, kernelDilate);

//        Mat kernelErode = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5));
//        Imgproc.erode(mSrcMat, mSrcMat, kernelErode);

//        Imgproc.threshold(mSrcMat, mSrcMat, 100, 255, Imgproc.THRESH_TOZERO);

        Imgproc.cvtColor(mSrcMat, mSrcMat, Imgproc.COLOR_BGR2GRAY);
        Imgproc.adaptiveThreshold(mSrcMat, mSrcMat, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
            Imgproc.THRESH_BINARY, 3, 0);

        mProcessedBitmap = Bitmap.createBitmap(mSrcMat.cols(), mSrcMat.rows(),
            Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mSrcMat, mProcessedBitmap);

        ivImage.setImageBitmap(mSelectBitmap);
        ivImageProcessed.setImageBitmap(mProcessedBitmap);
      }
    }
  }

  @Override
  public void onDestroy() {
    if (mSelectBitmap != null) {
      mSelectBitmap.recycle();
      mProcessedBitmap.recycle();
    }
    super.onDestroy();
  }
}
