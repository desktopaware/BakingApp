package android.example.bakingapp;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class StepsDetails extends AppCompatActivity {

    List<Steps> steps;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);

        steps = getIntent().getParcelableArrayListExtra("steps");
        position = getIntent().getIntExtra("position", 0);

        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) steps);
        bundle.putInt("position", position);

        FragmentManager fragmentManager = getSupportFragmentManager();

        StepsInformationFragment stepsInformationFragment = (StepsInformationFragment) fragmentManager.findFragmentByTag("StepsInformationFragment");

        if(stepsInformationFragment == null) {

            stepsInformationFragment = new StepsInformationFragment();
            stepsInformationFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.steps_details, stepsInformationFragment,"StepsInformationFragment")
                    .commit();
        }

    }
}
