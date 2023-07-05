package by.epetrenkodev.siz.ui.tool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import by.epetrenkodev.siz.databinding.FragmentToolBinding;

public class ToolFragment extends Fragment {
    View rootView;
    private FragmentToolBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ToolViewModel toolViewModel = new ViewModelProvider(this).get(ToolViewModel.class);
        binding = FragmentToolBinding.inflate(inflater, container, false);
        ToolAdapter adapter = new ToolAdapter(requireContext(), toolViewModel.data.getValue(), toolViewModel);
        toolViewModel.data.observe(getViewLifecycleOwner(), adapter::update);
        RecyclerView recyclerView = binding.toolList;
        recyclerView.setAdapter(adapter);
        toolViewModel.loadToolList();
        rootView = binding.getRoot();
        return rootView;


//        ToolViewModel toolViewModel = new ViewModelProvider(this).get(ToolViewModel.class);
//
//        binding = FragmentToolBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
////        final TextView textView = binding.textTool;
////        toolViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
                //menuInflater.inflate(R.menu.siz_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}