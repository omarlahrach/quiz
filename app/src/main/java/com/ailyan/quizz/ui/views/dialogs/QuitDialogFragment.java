package com.ailyan.quizz.ui.views.dialogs;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.quizz.R;
import com.ailyan.quizz.ui.viewModels.ProgressViewModel;
import com.ailyan.quizz.ui.views.fragments.CategoryFragment;
import com.ailyan.quizz.utilities.SharedData;

import java.util.Objects;

public class QuitDialogFragment extends DialogFragment {
    private View root;
    private Button btn_cancel;
    private Button btn_yes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dialog_quit, container, false);
        loadUIElements();
        handleClicks();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            Objects.requireNonNull(getDialog()).getWindow().setLayout((int) (width - width * 0.1), (int) (height - height * 0.2));
        else
            Objects.requireNonNull(getDialog()).getWindow().setLayout((int) (width - width * 0.1), (int) (height - height * 0.6));
    }

    private void handleClicks() {
        btn_cancel.setOnClickListener(view -> this.dismiss());
        btn_yes.setOnClickListener(view -> {
            if (this.getTag() != null) {
                switch (this.getTag()) {
                    case "Quit main":
                        ProgressViewModel progressViewModel = new ViewModelProvider(requireActivity()).get(ProgressViewModel.class);
                        progressViewModel.getPoints().postValue(0);
                        int selectedLevel = (Integer) SharedData.get(requireActivity().getApplication(), Integer.class, "selectedLevel");
                        Bundle args = new Bundle();
                        args.putInt("selectedLevel", selectedLevel);
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, CategoryFragment.class, args, "Category")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                .commit();
                        this.dismiss();
                        break;
                    case "Quit game":
                        requireActivity().finishAffinity();
                        break;
                }
            }
        });
    }

    private void loadUIElements() {
        TextView textView_title = root.findViewById(R.id.textView_title);
        btn_cancel = root.findViewById(R.id.btn_cancel);
        btn_yes = root.findViewById(R.id.btn_yes);
        btn_cancel.setTextSize(btn_yes.getTextSize());
        if (this.getTag() != null) {
            switch (this.getTag()) {
                case "Quit main":
                    textView_title.setText(R.string.quit_level);
                    break;
                case "Quit game":
                    textView_title.setText(R.string.quit_game);
                    break;
            }
        }
    }
}