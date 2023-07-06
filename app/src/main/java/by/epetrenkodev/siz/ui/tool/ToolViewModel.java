package by.epetrenkodev.siz.ui.tool;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import java.util.List;

import by.epetrenkodev.siz.R;
import by.epetrenkodev.siz.data.ToolRepository;

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

    public void newTool(String name, int cardCount, int realCount) {
        new ToolRepository().create(new ToolItem(name, cardCount, realCount));
    }

    public void removeTool(int position) {
        ToolItem toolItem = toolList.get(position);
        new ToolRepository().delete(toolItem);
        loadToolList();
    }

    public void updateTool(String name, int cardCount, int realCount, int position) {
        ToolItem newToolItem = new ToolItem(name, cardCount, realCount);
        ToolItem removeToolItem = toolList.get(position);
        new ToolRepository().delete(removeToolItem);
        new ToolRepository().create(newToolItem);
        loadToolList();
    }
}