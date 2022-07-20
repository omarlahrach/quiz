package com.ailyan.quizz.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.quizz.R;
import com.ailyan.quizz.ui.viewModels.QuestionViewModel;
import com.ailyan.quizz.utilities.Animation;
import com.squareup.picasso.Picasso;

public class QuestionFragment extends Fragment {
    private TextView textView_questionStatement;
    private ImageView imageView_question;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView_questionStatement = view.findViewById(R.id.textView_questionStatement);
        imageView_question = view.findViewById(R.id.imageView_question);

        QuestionViewModel questionViewModel = new ViewModelProvider(requireActivity()).get(QuestionViewModel.class);

        questionViewModel.selectedQuestion().observe(getViewLifecycleOwner(), question -> {
            textView_questionStatement.setText(question.statement);
            if (question.imageUrl != null) {
                Picasso picasso = Picasso.get();
                picasso.load(question.imageUrl).into(imageView_question);
                imageView_question.setVisibility(View.VISIBLE);
            }
            else {
                imageView_question.setVisibility(View.GONE);
            }
            Animation.appear(textView_questionStatement);
        });
    }
}