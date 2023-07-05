package by.epetrenkodev.siz.ui.tool;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import java.util.Calendar;
import java.util.List;

import by.epetrenkodev.siz.R;
import by.epetrenkodev.siz.data.SizRepository;
import by.epetrenkodev.siz.data.ToolRepository;
import by.epetrenkodev.siz.ui.siz.SizAdapter;
import by.epetrenkodev.siz.ui.siz.SizItem;

public class ToolViewModel extends ViewModel implements ToolAdapter.OnToolClickListener {

    public MutableLiveData<List<ToolItem>> data = new MutableLiveData<>();
    private List<ToolItem> toolList;

    public ToolViewModel() {
        loadToolList();
    }

    public void loadToolList() {
        toolList = new ToolRepository().read();
        data.setValue(toolList);
    }

    @Override
    public void onToolClick(View view, ToolItem toolItem, int position) {
        Bundle args = new Bundle();
        args.putString("name", toolItem.getName());
        args.putInt("cardCount", toolItem.getCardCount());
        args.putInt("realCount", toolItem.getRealCount());
        args.putInt("position", position);

        Navigation.findNavController(view).navigate(R.id.update_tool_fragment, args);
    }
}