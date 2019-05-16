package android.example.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StepsInformationFragment extends Fragment {

    private TextView noVideo, desctriptionTv;
    private PlayerView playerView;
    private SimpleExoPlayer exoPlayer;
    private Button next, previous;
    private List<Steps> steps;
    private int position, nextPosition, previousPosition;
    private boolean mTwoPane;
    private ImageView thumbnail;
    boolean playerStatus;
    long playerPosition;
    String url;
    private int currentWindow;


    public StepsInformationFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_steps_information, container, false);

        //won't save instance?
        if (savedInstanceState != null) {

            playerPosition = savedInstanceState.getLong("playerPosition");
            currentWindow = savedInstanceState.getInt("currentWindow");
            playerStatus = savedInstanceState.getBoolean("playerStatus");

        }
        else {

            playerStatus = true;
            playerPosition = 0;

        }

        noVideo = view.findViewById(R.id.noVideo);
        desctriptionTv = view.findViewById(R.id.description_step_tv);
        next = view.findViewById(R.id.button_next);
        previous = view.findViewById(R.id.button_back);
        playerView = view.findViewById(R.id.exo_play);
        thumbnail = view.findViewById(R.id.thumbnail);

        steps = getArguments().getParcelableArrayList("steps");
        position = getArguments().getInt("position");
        mTwoPane = getArguments().getBoolean("mTwoPane");

        nextPosition = position + 1;
        previousPosition = position - 1;

        desctriptionTv.setText(steps.get(position).getDescription());

        Steps step = steps.get(position);

        if(position == 0 || mTwoPane){
            previous.setVisibility(View.GONE);
        }else {
            previous.setOnClickListener(view1 -> {
                Intent intent = new Intent(getActivity(), StepsDetails.class);
                intent.putParcelableArrayListExtra("steps", (ArrayList<? extends Parcelable>) steps);
                intent.putExtra("position", previousPosition);
                startActivity(intent);
                getActivity().finish();

            });
        }

        if(position == steps.size()-1 || mTwoPane){
            next.setVisibility(View.GONE);
        }else {
            next.setOnClickListener(view12 -> {
                Intent intent = new Intent(getActivity(), StepsDetails.class);
                intent.putParcelableArrayListExtra("steps", (ArrayList<? extends Parcelable>) steps);
                intent.putExtra("position", nextPosition);
                startActivity(intent);
                getActivity().finish();

            });
        }

        if(TextUtils.isEmpty(step.getVideoURL())){

            playerView.setVisibility(View.GONE);

        }else {
            url = step.getVideoURL();

            noVideo.setVisibility(View.GONE);

            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity());
            playerView.setPlayer(exoPlayer);
            Uri uri = Uri.parse(url);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                    Util.getUserAgent(getActivity(), getActivity().getPackageName()));

            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri);


            exoPlayer.prepare(videoSource);
            exoPlayer.setPlayWhenReady(playerStatus);
            exoPlayer.seekTo(currentWindow, playerPosition);

        }

        if(!TextUtils.isEmpty(step.getThumbnailURL())){
            Picasso.get()
                    .load(step.getThumbnailURL())
                    .into(thumbnail);
        }


        return view;
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            // save the player state before releasing its resources
            playerPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playerStatus = exoPlayer.getPlayWhenReady();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(exoPlayer != null) {
            outState.putLong("playerPosition", playerPosition);
            outState.putInt("currentWindow", currentWindow);
            outState.putBoolean("playerStatus", playerStatus);

        }
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

}


