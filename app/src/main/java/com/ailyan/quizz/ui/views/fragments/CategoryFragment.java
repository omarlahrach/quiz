package com.ailyan.quizz.ui.views.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ailyan.quizz.R;
import com.ailyan.quizz.data.sources.local.entities.CategoryEntity;
import com.ailyan.quizz.databinding.FragmentCategoryBinding;
import com.ailyan.quizz.ui.viewModels.CategoryViewModel;
import com.ailyan.quizz.ui.views.adapters.CategoryAdapter;

import java.util.List;

public class CategoryFragment extends Fragment implements CategoryAdapter.ItemClickListener {
    private FragmentCategoryBinding binding;
    private int selectedLevel;
    private List<CategoryEntity> categories;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null)
            selectedLevel = arguments.getInt("selectedLevel");
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (selectedLevel) {
            case 0:
                binding.level.setText(R.string.level1_title);
                break;
            case 1:
                binding.level.setText(R.string.level2_title);
                break;
            case 2:
                binding.level.setText(R.string.level3_title);
                break;
            case 3:
                binding.level.setText(R.string.level4_title);
                break;
        }

        CategoryViewModel categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        categoryViewModel.loadCategoriesByLevel(selectedLevel).observe(getViewLifecycleOwner(), categories -> {
            this.categories = categories;
            int rows, cols;
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rows = 2;
                cols = 2;
            } else {
                rows = 4;
                cols = 1;
            }
            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categories, rows, binding);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), cols);
            categoryAdapter.setClickListener(this);
            binding.categories.setAdapter(categoryAdapter);
            binding.categories.setLayoutManager(gridLayoutManager);
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        CategoryEntity selectedCategory = categories.get(position);
        Bundle args = new Bundle();
        args.putSerializable("selectedCategory", selectedCategory);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, MainFragment.class, args, "Main")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}