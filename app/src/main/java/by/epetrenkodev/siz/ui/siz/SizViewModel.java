package by.epetrenkodev.siz.ui.siz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SizViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SizViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Спецодежда");
    }

    public LiveData<String> getText() {
        return mText;
    }
}