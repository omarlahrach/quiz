package com.ailyan.quizz.ui.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ailyan.quizz.R;
import com.ailyan.quizz.databinding.FragmentLevelBinding;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {

    private final Context context;
    private final List<Integer> levels;
    private final LayoutInflater mInflater;
    private LevelAdapter.ItemClickListener mClickListener;
    private final int rows;
    private final FragmentLevelBinding binding;

    public LevelAdapter(Context context, List<Integer> levels, int rows, FragmentLevelBinding binding) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.levels = levels;
        this.rows = rows;
        this.binding = binding;
    }

    @NonNull
    @Override
    public LevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_level, parent, false);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        ViewGroup.MarginLayoutParams lpv = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        lp.height = parent.getMeasuredHeight() / rows - lpv.topMargin * 2;
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelAdapter.ViewHolder holder, int position) {
        Integer level = levels.get(position);
        String title = (level + 1) + "";
        holder.textView_progress.setText(title);
        switch (level) {
            case 0:
                holder.cardView_level.setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_level1));
                holder.indicator_progress.setIndicatorColor(context.getColor(R.color.dark_magenta));
                holder.indicator_progress.setTrackColor(context.getColor(R.color.pink));
                holder.textView_progress.setTextColor(context.getColor(R.color.dark_magenta));
                holder.textView_level.setText(R.string.level1_title);
                holder.textView_level.setTextColor(context.getColor(R.color.dark_magenta));
                break;
            case 1:
                holder.cardView_level.setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_level2));
                holder.indicator_progress.setIndicatorColor(context.getColor(R.color.dark_green));
                holder.indicator_progress.setTrackColor(context.getColor(R.color.green));
                holder.textView_progress.setTextColor(context.getColor(R.color.dark_green));
                holder.textView_level.setText(R.string.level2_title);
                holder.textView_level.setTextColor(context.getColor(R.color.dark_green));
                break;
            case 2:
                holder.cardView_level.setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_level3));
                holder.indicator_progress.setIndicatorColor(context.getColor(R.color.dark_amber));
                holder.indicator_progress.setTrackColor(context.getColor(R.color.amber));
                holder.textView_progress.setTextColor(context.getColor(R.color.dark_amber));
                holder.textView_level.setText(R.string.level3_title);
                holder.textView_level.setTextColor(context.getColor(R.color.dark_amber));
                break;
            case 3:
                holder.cardView_level.setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_level4));
                holder.indicator_progress.setIndicatorColor(context.getColor(R.color.dark_purple));
                holder.indicator_progress.setTrackColor(context.getColor(R.color.purple));
                holder.textView_progress.setTextColor(context.getColor(R.color.dark_purple));
                holder.textView_level.setText(R.string.level4_title);
                holder.textView_level.setTextColor(context.getColor(R.color.dark_purple));
                break;
        }
        binding.loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialCardView cardView_level;
        CircularProgressIndicator indicator_progress;
        TextView textView_progress;
        TextView textView_level;

        ViewHolder(View itemView) {
            super(itemView);
            cardView_level = itemView.findViewById(R.id.cardView_level);
            indicator_progress = itemView.findViewById(R.id.indicator_progress);
            textView_progress = itemView.findViewById(R.id.textView_progress);
            textView_level = itemView.findViewById(R.id.textView_level);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
