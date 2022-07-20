package com.ailyan.quizz.ui.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ailyan.quizz.data.repositories.CategoryRepository;
import com.ailyan.quizz.data.sources.local.entities.CategoryEntity;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryViewModel extends AndroidViewModel {
    private static final String TAG = CategoryViewModel.class.getSimpleName();
    private final MutableLiveData<List<CategoryEntity>> categories = new MutableLiveData<>();
    private final CategoryRepository categoryRepository;
    private Disposable disposable;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        this.categoryRepository = new CategoryRepository(application);
    }

    public LiveData<List<CategoryEntity>> loadCategoriesByLevel(int level) {
        disposable = categoryRepository.loadCategoriesByLevel(level)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        categories::postValue,
                        throwable -> Log.e(TAG, "Cannot load categories!", throwable)
                );
        return categories;
    }

    public void updateCategory(CategoryEntity category) {
        categoryRepository.updateCategory(category);
    }

    @Override
    protected void onCleared() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        super.onCleared();
    }
}
