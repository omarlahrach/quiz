package com.ailyan.quizz.ui.views.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.ailyan.quizz.R;
import com.ailyan.quizz.data.sources.local.entities.AnswerEntity;
import com.ailyan.quizz.databinding.FragmentAnswerBinding;
import com.ailyan.quizz.ui.viewModels.AnswerViewModel;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private final Context context;
    private final List<AnswerEntity> answers;
    private final AnswerViewModel answerViewModel;
    private final LayoutInflater mInflater;
    private final FragmentAnswerBinding binding;

    public AnswerAdapter(Context context, List<AnswerEntity> answers, AnswerViewModel answerViewModel,FragmentAnswerBinding binding) {
        this.mInflater = LayoutInflater.from(context);
        this.answers = answers;
        this.context = context;
        this.answerViewModel = answerViewModel;
        this.binding = binding;
    }

    @NonNull
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_answer, parent, false);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        ViewGroup.MarginLayoutParams lpv = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        lp.height = parent.getMeasuredHeight() / 2 - lpv.topMargin * 2;
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.ViewHolder holder, int position) {
        AnswerEntity answer = answers.get(position);
        holder.textView_answer.setText(answer.statement);
        if (answer.imageUrl != null)
            Picasso.get().load(answer.imageUrl).into(holder.imageView_answer);
        binding.answers.setVisibility(View.VISIBLE);
        binding.loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView_answer;
        TextView textView_answer;
        ImageView imageView_answer;

        ViewHolder(View itemView) {
            super(itemView);
            cardView_answer = itemView.findViewById(R.id.cardView_answer);
            textView_answer = itemView.findViewById(R.id.textView_answer);
            imageView_answer = itemView.findViewById(R.id.imageView_answer);
            cardView_answer.setOnClickListener(view -> {
                answerViewModel.selectedAnswer().postValue(answers.get(getAdapterPosition()));
                if (!cardView_answer.isChecked()) {
                    cardView_answer.setChecked(true);
                    Drawable icon;
                    ColorStateList color;
                    if (answers.get(getAdapterPosition()).isCorrect) {
                        color = context.getColorStateList(R.color.green);
                        icon = AppCompatResources.getDrawable(context, R.drawable.ic_check);
                    } else {
                        color = context.getColorStateList(R.color.red);
                        icon = AppCompatResources.getDrawable(context, R.drawable.ic_close);
                    }
                    cardView_answer.setCheckedIcon(icon);
                    cardView_answer.setStrokeColor(color);
                    cardView_answer.setCheckedIconTint(color);
                }
                else {
                    cardView_answer.setChecked(false);
                    cardView_answer.setStrokeColor(context.getColorStateList(R.color.light_colorOnPrimary));
                }
            });
        }
    }
}
