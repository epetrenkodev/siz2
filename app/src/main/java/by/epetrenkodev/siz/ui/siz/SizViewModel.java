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

    private void loadSizList() {
        sizList = new SizRepository().read();
        updateSizList();
    }

    public void createSiz(String name, Date beginDate, int period) {
        SizItem sizItem = new SizItem(name, beginDate, period);
        sizList.add(sizItem);
        updateSizList();
        new SizRepository().create(sizItem);
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
        //Collections.sort(sizList, (o1, o2) -> o1.getEndDate().compareTo(o2.getEndDate()));
        sizList.sort((o1, o2) -> o1.getEndDate().compareTo(o2.getEndDate()));
        data.setValue(sizList);
    }
}