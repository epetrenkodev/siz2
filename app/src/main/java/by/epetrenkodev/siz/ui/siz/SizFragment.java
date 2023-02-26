package by.epetrenkodev.siz.ui.siz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import by.epetrenkodev.siz.databinding.FragmentSizBinding;

public class SizFragment extends Fragment {

    private FragmentSizBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SizViewModel sizViewModel = new ViewModelProvider(this).get(SizViewModel.class);

        binding = FragmentSizBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSiz;
        sizViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}