package android.example.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

public class StepsInformationFragment extends Fragment {

    private TextView noVideo, desctriptionTv;
    private PlayerView playerView;
    private SimpleExoPlayer exoPlayer;
    private Button next, back;
    private List<Steps> steps;
    private int position;
    boolean playerStatus;
    long playerPosition;
    String url;

    public StepsInformationFragment(){
        setRetainInstance(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_steps_information, container, false);

        if (savedInstanceState != null) {
            playerStatus = savedInstanceState.getBoolean("playerState");
            playerPosition = savedInstanceState.getLong("playerPosition");

        }
        else {
            playerStatus = true;
            playerPosition = 0;
        }

        noVideo = view.findViewById(R.id.noVideo);
        desctriptionTv = view.findViewById(R.id.description_step_tv);
        next = view.findViewById(R.id.button_next);
        back = view.findViewById(R.id.button_back);
        playerView = view.findViewById(R.id.exo_play);

        steps = getArguments().getParcelableArrayList("steps");
        position = getArguments().getInt("position");

        desctriptionTv.setText(steps.get(position).getDescription());

        Steps step = steps.get(position);

        if(position == 0){
            back.setVisibility(View.GONE);
        }else {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), StepsDetails.class);
                    intent.putParcelableArrayListExtra("steps", (ArrayList<? extends Parcelable>) steps);
                    intent.putExtra("position", position-1);
                    startActivity(intent);
                    getActivity().finish();

                }
            });
        }

        if(position == steps.size()-1){
            next.setVisibility(View.GONE);
        }else {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), StepsDetails.class);
                    intent.putParcelableArrayListExtra("steps", (ArrayList<? extends Parcelable>) steps);
                    intent.putExtra("position", position+1);
                    startActivity(intent);
                    getActivity().finish();

                }
            });
        }

        if(step.getVideoURL().equals("")){
            playerView.setVisibility(View.GONE);
        }else {
            noVideo.setVisibility(View.GONE);
            url = step.getVideoURL();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity());
            playerView.setPlayer(exoPlayer);
            Uri uri = Uri.parse(url);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                    Util.getUserAgent(getActivity(), getActivity().getPackageName()));

            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri);


            exoPlayer.prepare(videoSource);
            exoPlayer.setPlayWhenReady(playerStatus);
            exoPlayer.seekTo(playerPosition);
        }


        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putBoolean("playerState", exoPlayer.getPlayWhenReady());
        outState.putLong("playerPosition", exoPlayer.getCurrentPosition());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(exoPlayer != null){
            exoPlayer.stop();
            exoPlayer.release();
        }

    }
}
