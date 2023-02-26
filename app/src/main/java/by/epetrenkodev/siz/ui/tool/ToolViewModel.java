package by.epetrenkodev.siz.ui.tool;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ToolViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ToolViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Инструмент");
    }

    public LiveData<String> getText() {
        return mText;
    }
}