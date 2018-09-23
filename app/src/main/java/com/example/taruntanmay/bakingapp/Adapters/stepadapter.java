package com.example.taruntanmay.bakingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taruntanmay.bakingapp.DetailActivity;
import com.example.taruntanmay.bakingapp.R;
import com.example.taruntanmay.bakingapp.VideoFragment;
import com.example.taruntanmay.bakingapp.json.*;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class stepadapter extends RecyclerView.Adapter<stepadapter.ViewHolder> {
    private List<steps> steps;

    public stepadapter(List<steps> steps) {
        this.steps = steps;
    }

    @Override
    public stepadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_step, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(stepadapter.ViewHolder holder, int position) {
        steps currentStep = steps.get(position);
        holder.stepIdTv.setText(currentStep.getStepId());
        holder.stepDescription.setText(currentStep.getShortDescription());
        holder.stepId = position;
        if (currentStep.hasVideo()) {
            holder.stepVideoImage.setVisibility(View.VISIBLE);
        } else {
            holder.stepVideoImage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (steps == null) return 0;
        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.stepid)
        TextView stepIdTv;
        @BindView(R.id.stepdescription)
        TextView stepDescription;
        @BindView(R.id.stepvideo)
        ImageView stepVideoImage;
        int stepId;

        public ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        //    stepIdTv = itemView.findViewById(R.id.stepid);
       //     stepDescription = itemView.findViewById(R.id.stepdescription);
         //   stepVideoImage = itemView.findViewById(R.id.stepvideo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoFragment fragment = new VideoFragment();
                    fragment.setSteps(steps);
                    fragment.setStepId(stepId);

                    if (DetailActivity.twoPane) {
                        ((DetailActivity) itemView.getContext())
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.video_container, fragment)
                                .commit();
                    } else {
                        ((DetailActivity) itemView.getContext())
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.instruction_container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });
        }
    }
}
