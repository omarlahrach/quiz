package com.ailyan.quizz.ui.views.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.quizz.R;
import com.ailyan.quizz.ui.viewModels.LoginViewModel;
import com.ailyan.quizz.ui.viewModels.QuestionViewModel;
import com.ailyan.quizz.ui.views.dialogs.QuitDialogFragment;
import com.ailyan.quizz.ui.views.fragments.CategoryFragment;
import com.ailyan.quizz.ui.views.fragments.LevelFragment;
import com.ailyan.quizz.ui.views.fragments.MainFragment;
import com.ailyan.quizz.utilities.SharedData;
import com.ailyan.quizz.utilities.enums.DataSource;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Intent intent = getIntent();
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        String[] data = sharedText.split("&");
        String username = data[0];
        String password = data[1];
        SharedData.add(getApplication(), username, "username");
        SharedData.add(getApplication(), password, "password");*/

        setContentView(R.layout.activity_game);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, LevelFragment.class, null, "Level")
                    .commit();
        }

        QuestionViewModel questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.selectedDataSource().observe(this, dataSource -> {
            if (dataSource == DataSource.REMOTE)
                questionViewModel.loadAllRemoteQuestions(loginViewModel);
        });
    }

    @Override
    public void onBackPressed() {
        LevelFragment levelFragment = (LevelFragment) getSupportFragmentManager().findFragmentByTag("Level");
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("Main");
        CategoryFragment categoryFragment = (CategoryFragment) getSupportFragmentManager().findFragmentByTag("Category");
        if (levelFragment != null && levelFragment.isVisible())
            new QuitDialogFragment().show(getSupportFragmentManager(), "Quit game");
        if (mainFragment != null && mainFragment.isVisible())
            new QuitDialogFragment().show(getSupportFragmentManager(), "Quit main");
        if (categoryFragment != null && categoryFragment.isVisible()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, LevelFragment.class, null, "Level")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }
}