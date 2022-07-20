package com.ailyan.quizz.ui.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ailyan.quizz.R;
import com.ailyan.quizz.data.sources.local.entities.CategoryEntity;
import com.ailyan.quizz.databinding.FragmentCategoryBinding;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<CategoryEntity> categories;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final int rows;
    private final FragmentCategoryBinding binding;

    public CategoryAdapter(Context context, List<CategoryEntity> categories, int rows, FragmentCategoryBinding binding) {
        this.mInflater = LayoutInflater.from(context);
        this.categories = categories;
        this.rows = rows;
        this.binding = binding;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_category, parent, false);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        ViewGroup.MarginLayoutParams lpv = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        parent.post(() -> {
            lp.height = parent.getMeasuredHeight() / rows - lpv.topMargin * 2;
            view.setLayoutParams(lp);
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryEntity category = categories.get(position);
        holder.textView_name.setText(category.name);
        String points = category.score.resultObtained + "/" + category.score.maximumResult +  " points";
        holder.textView_points.setText(points);
        String rate = category.score.getRate() + "%";
        holder.textView_rate.setText(rate);
        holder.progressIndicator_progress.setMax(100);
        holder.progressIndicator_progress.setProgress(category.score.getRate(), true);
        binding.loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView_name;
        TextView textView_points;
        TextView textView_rate;
        CircularProgressIndicator progressIndicator_progress;

        ViewHolder(View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_points = itemView.findViewById(R.id.textView_points);
            textView_rate = itemView.findViewById(R.id.textView_rate);
            progressIndicator_progress = itemView.findViewById(R.id.progressIndicator_progress);
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