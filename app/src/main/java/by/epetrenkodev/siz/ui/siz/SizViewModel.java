package by.epetrenkodev.siz.ui.siz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.List;

import by.epetrenkodev.siz.data.SizRepository;

public class SizViewModel extends ViewModel {

    public MutableLiveData<List<SizItem>> data = new MutableLiveData<>();
    private List<SizItem> sizList;

    public SizViewModel() {
        loadSizList();
    }

    public void loadSizList() {
        sizList = new SizRepository().read();
        updateSizList();
    }

    public void updateSiz(int position, String name, Date beginDate, int period) {
//        SizItem sizItem = new SizItem(name, beginDate, period);
//        sizList.set(position, sizItem);
//        updateSizList();
//        new UpdateSizUseCase().execute(sizItem);
    }

    public void deleteSiz(int position) {
//        SizItem sizItem = sizList.get(position);
//        sizList.remove(position);
//        updateSizList();
//        new DeleteSizUseCase().execute(sizItem);
    }

    private void updateSizList() {
        sizList.sort((o1, o2) -> o1.getEndDate().compareTo(o2.getEndDate()));
        data.setValue(sizList);
    }
}