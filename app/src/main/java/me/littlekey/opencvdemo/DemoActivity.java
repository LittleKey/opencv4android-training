package me.littlekey.opencvdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by littlekey on 16/1/8.
 */
public class DemoActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_container);

    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.fragment_container);
    if (fragment == null) {
      fragment = DemoFragment.newInstance();
      fm.beginTransaction()
          .add(R.id.fragment_container, fragment)
          .commit();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, requestCode, data);
    switch (requestCode) {
      case Const.SELECT_PHOTO:
        if (resultCode == RESULT_OK) {
          Intent intent = new Intent(this, MainActivity.class);
          intent.setData(data.getData());
          startActivity(intent);
        }
        break;
    }
  }
}
