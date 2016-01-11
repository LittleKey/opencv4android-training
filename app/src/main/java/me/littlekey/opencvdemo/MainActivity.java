package me.littlekey.opencvdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by littlekey on 16/1/11.
 */
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_container);
    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.fragment_container);
    if (fragment == null) {
      fragment = MainFragment.newInstance(getIntent().getData());
      fm.beginTransaction()
          .add(R.id.fragment_container, fragment)
          .commit();
    }
  }
}
