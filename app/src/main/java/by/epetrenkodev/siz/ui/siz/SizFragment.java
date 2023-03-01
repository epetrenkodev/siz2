package by.epetrenkodev.siz.ui.siz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import by.epetrenkodev.siz.databinding.FragmentSizBinding;

public class SizFragment extends Fragment {

    private FragmentSizBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SizViewModel sizViewModel = new ViewModelProvider(this).get(SizViewModel.class);
        binding = FragmentSizBinding.inflate(inflater, container, false);
        SizAdapter adapter = new SizAdapter(requireContext(), sizViewModel.data.getValue(), sizViewModel);
        sizViewModel.data.observe(getViewLifecycleOwner(), adapter::update);
        RecyclerView recyclerView = binding.sizList;
        recyclerView.setAdapter(adapter);
        sizViewModel.loadSizList();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}