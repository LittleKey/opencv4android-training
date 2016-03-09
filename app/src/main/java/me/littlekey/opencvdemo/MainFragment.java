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
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
//        Mat blur1 = new Mat();
//        Mat blur2 = new Mat();
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

//        Mat grad_x = new Mat();
//        Mat abs_grad_x = new Mat();
//        Mat grad_y = new Mat();
//        Mat abs_grad_y = new Mat();
//        Mat tempDst = new Mat();
//        Mat tempDstNorm = new Mat();
//        Mat cannyEdges = new Mat();
//        Mat lines = new Mat();
        Mat cannyEdges = new Mat();
        Mat hierarchy = new Mat();
        List<MatOfPoint> contourList = new ArrayList<>();
        Imgproc.cvtColor(mSrcMat, mSrcMat, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(mSrcMat, cannyEdges, 10, 100);
        Imgproc.findContours(cannyEdges, contourList, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat contours = new Mat();
        contours.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC3);
//        Random r = new Random();
        Scalar s = new Scalar(255, 255, 255);
        for (int i = 0; i < contourList.size(); ++i) {
          Imgproc.drawContours(contours, contourList, i, s, -1);
        }
        mSrcMat = contours;
//        Imgproc.Canny(mSrcMat, cannyEdges, 10, 100);
//        Imgproc.HoughLinesP(cannyEdges, lines, 1, Math.PI / 180, 50, 20, 20);
//        Mat houghLines = new Mat();
//        houghLines.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC1);
//        for (int i = 0; i < lines.cols(); ++i) {
//          double[] points = lines.get(0, i);
//          double x1 = points[0];
//          double y1 = points[1];
//          double x2 = points[2];
//          double y2 = points[3];
//
//          Point pt1 = new Point(x1, y1);
//          Point pt2 = new Point(x2, y2);
//          Imgproc.line(houghLines, pt1, pt2, new Scalar(255, 0, 0), 1);
//        }
//        mSrcMat = houghLines;
//        Imgproc.cornerHarris(mSrcMat, tempDst, 2, 3, 0.04);
//        Core.normalize(tempDst, tempDstNorm, 0, 255, Core.NORM_MINMAX);
//        Core.convertScaleAbs(tempDstNorm, mSrcMat);
//        Random r = new Random();
//        for (int i = 0; i < tempDstNorm.cols(); ++i) {
//          for (int j = 0; j < tempDstNorm.rows(); ++j) {
//            double[] value = tempDstNorm.get(j, i);
//            if (value[0] > 150) {
//              Imgproc.circle(mSrcMat, new Point(i, j), 5, new Scalar(r.nextInt(255)), 2);
//            }
//          }
//        }
//        Imgproc.Sobel(mSrcMat, grad_x, CvType.CV_16S, 1, 0, 3, 1, 0);
//        Imgproc.Sobel(mSrcMat, grad_y, CvType.CV_16S, 0, 1, 3, 1, 0);
//        Core.convertScaleAbs(grad_x, abs_grad_x);
//        Core.convertScaleAbs(grad_y, abs_grad_y);
//        Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 1, mSrcMat);
//        Imgproc.Canny(mSrcMat, mSrcMat, 10, 100);
//        Imgproc.GaussianBlur(mSrcMat, blur1, new Size(15, 15), 5);
//        Imgproc.GaussianBlur(mSrcMat, blur2, new Size(21, 21), 5);
//        Core.absdiff(blur1, blur2, mSrcMat);
//        Core.multiply(mSrcMat, new Scalar(100), mSrcMat);
//        Imgproc.threshold(mSrcMat, mSrcMat, 50, 255, Imgproc.THRESH_BINARY_INV);
//        Imgproc.adaptiveThreshold(mSrcMat, mSrcMat, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
//            Imgproc.THRESH_BINARY, 3, 0);

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
