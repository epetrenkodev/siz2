package by.epetrenkodev.siz.ui.siz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import by.epetrenkodev.siz.databinding.FragmentSizBinding;

public class SizFragment extends Fragment implements NewSizDialog.Interface, UpdateSizDialog.Interface, SizAdapter.OnAddClickListener{

    private FragmentSizBinding binding;
    SizViewModel sizViewModel;
    SizAdapter adapter;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sizViewModel = new ViewModelProvider(this).get(SizViewModel.class);

        binding = FragmentSizBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sizViewModel.data.observe(getViewLifecycleOwner(), data -> adapter.notifyDataSetChanged());
        adapter = new SizAdapter(requireContext(), sizViewModel.data.getValue(), this::updateSiz_onClick, this);
        recyclerView = binding.sizList;
        recyclerView.setAdapter(adapter);

        return root;
    }

    public void updateSiz_onClick(SizItem sizItem, int position) {
        DialogFragment dialog = new UpdateSizDialog();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sizItem.getBeginDate());

        Bundle args = new Bundle();
        args.putString("name", sizItem.getName());
        args.putInt("month", calendar.get(Calendar.MONTH));
        args.putInt("year", calendar.get(Calendar.YEAR));
        args.putInt("period", sizItem.getPeriod());
        args.putInt("position", position);

        dialog.setArguments(args);
        //dialog.show(getSupportFragmentManager(), "updateSiz");
        dialog.show(getParentFragmentManager(), "updateSiz");
    }

    public void addSiz_onClick() {
        new NewSizDialog().show(getParentFragmentManager(), "newSiz");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void createSiz(String name, Date beginDate, int period) {
        sizViewModel.createSiz(name, beginDate, period);
    }

    @Override
    public void updateSiz(int position, String name, Date beginDate, int period, String prevname) {
        if (Objects.equals(name, prevname)) {
            sizViewModel.updateSiz(position, name, beginDate, period);
        } else {
            sizViewModel.createSiz(name, beginDate, period);
            sizViewModel.deleteSiz(position);
        }
    }

    @Override
    public void deleteSiz(int position) {
        sizViewModel.deleteSiz(position);
    }

    @Override
    public void onAddClick() {
        new NewSizDialog().show(getFragmentManager(), "newSiz");
    }
}