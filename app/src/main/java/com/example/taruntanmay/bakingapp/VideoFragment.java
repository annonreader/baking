package com.example.taruntanmay.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.taruntanmay.bakingapp.json.steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment {
    @BindView(R.id.player_view)
    SimpleExoPlayerView playerView;
    @BindView(R.id.tv_video_description)
    TextView videoDescription;
    @BindView(R.id.btn_step_next)
    ImageButton nextStep;
    @BindView(R.id.btn_step_previous)
    ImageButton previousStep;
    @BindView(R.id.step_btn_layout)
    LinearLayout buttonLayout;
    @BindView(R.id.iv_step_thumbnail)
    ImageView stepImage;

    private static final String VIDEO_POSITION = "videoPosition";
    private static final String SAVED_STEPS = "steps";
    private static final String SAVED_STEP_ID = "stepId";

    private SimpleExoPlayer player;
    private long videoPosition = 0;
    private ArrayList<steps> steps;
    private steps step;
    private int stepId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.videoplayerfragment, container, false);
    /*    playerView = (SimpleExoPlayerView)rootView.findViewById(R.id.player_view);
        videoDescription=rootView.findViewById(R.id.tv_video_description);
        nextStep = rootView.findViewById(R.id.btn_step_next);*/
        ButterKnife.bind(this,rootView);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStep();
            }
        });
   //     previousStep = rootView.findViewById(R.id.btn_step_previous);
        previousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousStep();
            }
        });
    //    buttonLayout = rootView.findViewById(R.id.step_btn_layout);
   //     stepImage= rootView.findViewById(R.id.iv_step_thumbnail);
        if (DetailActivity.twoPane) {
            buttonLayout.setVisibility(View.GONE);
        }

        if (savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList(SAVED_STEPS);
            stepId = savedInstanceState.getInt(SAVED_STEP_ID);
            step = steps.get(stepId);
            videoPosition = savedInstanceState.getLong(VIDEO_POSITION);
        } else {
            step = steps.get(stepId);
        }

        if (step.hasVideo()) {
            playerView.setVisibility(View.VISIBLE);
            setupExoPlayer(Uri.parse(step.getVideoURL()));
        } else {
            playerView.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(step.getThumbnailURL())) {
                stepImage.setVisibility(View.VISIBLE);
                setupStepImage(step.getThumbnailURL());
            }
        }

        videoDescription.setText(step.getDescription());
        return rootView;
    }


 /*   private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(this, TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }
        private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

 private void releasePlayer() {
     mNotificationManager.cancelAll();
     mExoPlayer.stop();
     mExoPlayer.release();
     mExoPlayer = null;
 }
 @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }


    // ExoPlayer Event Listeners

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }


 @Override
 public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
     if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
         mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                 mExoPlayer.getCurrentPosition(), 1f);
     } else if((playbackState == ExoPlayer.STATE_READY)){
         mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                 mExoPlayer.getCurrentPosition(), 1f);
     }
     mMediaSession.setPlaybackState(mStateBuilder.build());
     showNotification(mStateBuilder.build());
 }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }

    @Override
    public void onPositionDiscontinuity() {
    }


    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

*/
 public void onActivityCreated (Bundle savedInstanceState) {

     super.onActivityCreated(savedInstanceState);
     Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
     getView().requestFocus();

     getView().setOnKeyListener(new View.OnKeyListener() {
         @Override
         public boolean onKey(View v, int keyCode, KeyEvent event) {
             if (event.getAction() == KeyEvent.ACTION_DOWN) {
                 if (keyCode == KeyEvent.KEYCODE_BACK) {
                     //  Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                //     getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                     Objects.requireNonNull(getFragmentManager()).popBackStack();
                //     Intent i = new Intent(getActivity(), MainActivity.class);
               //      startActivity(i);

                     return true;
                 }
             }
             return false;
         }
     });
 }
    private void setupExoPlayer(Uri videoUrlString) {
        if (videoUrlString == null) return;
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        playerView.setPlayer(player);

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(Objects.requireNonNull(getContext()),
                        Util.getUserAgent(getContext(),
                                "BakingApp"), bandwidthMeter);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(videoUrlString,
                dataSourceFactory,
                extractorsFactory,
                null,
                null);
        player.prepare(videoSource);
        if (videoPosition > 0) {
            player.seekTo(videoPosition);
            player.setPlayWhenReady(true);
        }
        //if tablet, don't make full screen video
        if (!DetailActivity.twoPane) {
            setupPlayerView(getResources().getConfiguration().orientation);
        }
    }

    private void setupStepImage(String thumbnailURL) {
        if ( ! TextUtils.isEmpty(thumbnailURL)) {
            Picasso.with(getContext())
                    .load(thumbnailURL)
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .into(stepImage);
        }
    }

    // make full screen when phone rotate to landscape
    private void setupPlayerView(int orientation) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            buttonLayout.setVisibility(View.GONE);
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            Objects.requireNonNull(((DetailActivity) getActivity()).getSupportActionBar()).hide();
        }
    }

    public void setSteps(List<steps> steps) {
        this.steps = (ArrayList<steps>) steps;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (step.hasVideo()) {
            videoPosition = player.getCurrentPosition();
            outState.putLong(VIDEO_POSITION, videoPosition);
        }
        outState.putParcelableArrayList(SAVED_STEPS, steps);
        outState.putInt(SAVED_STEP_ID, stepId);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }


    void previousStep() {
        if (stepId > 0) {
            VideoFragment fragment = new VideoFragment();
            fragment.setSteps(steps);
            fragment.setStepId(--stepId);
            // make sure only save one videoFragment in backStack
            FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        //    manager.popBackStack();
            manager.beginTransaction()
                    .replace(R.id.instruction_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getContext(), "This is the first step", Toast.LENGTH_LONG).show();
        }
    }

    void nextStep() {
        if (stepId < steps.size() - 1) {
            VideoFragment fragment = new VideoFragment();
            fragment.setSteps(steps);
            fragment.setStepId(++stepId);
            // make sure only save one videoFragment in backStack
            FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
      //      manager.popBackStack();
            manager.beginTransaction()
                    .replace(R.id.instruction_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getContext(),"This is the last step" , Toast.LENGTH_LONG).show();
        }
    }
}