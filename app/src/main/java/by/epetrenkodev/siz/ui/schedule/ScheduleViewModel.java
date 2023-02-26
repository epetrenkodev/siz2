package by.epetrenkodev.siz.ui.schedule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScheduleViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ScheduleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("График");
    }

    public LiveData<String> getText() {
        return mText;
    }
}