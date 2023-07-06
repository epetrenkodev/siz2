package by.epetrenkodev.siz.ui.siz;

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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import by.epetrenkodev.siz.R;
import by.epetrenkodev.siz.databinding.FragmentSizBinding;

public class SizFragment extends Fragment {

    View rootView;
    private FragmentSizBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SizViewModel sizViewModel = new ViewModelProvider(this).get(SizViewModel.class);
        binding = FragmentSizBinding.inflate(inflater, container, false);
        SizAdapter adapter = new SizAdapter(requireContext(), sizViewModel.data.getValue(), sizViewModel);
        sizViewModel.data.observe(getViewLifecycleOwner(), adapter::update);
        RecyclerView recyclerView = binding.sizList;
        recyclerView.setAdapter(adapter);
        sizViewModel.loadSizList();
        rootView = binding.getRoot();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
                menuInflater.inflate(R.menu.siz_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.siz_action_add) {
                    Navigation.findNavController(rootView).navigate(R.id.nav_siz_new);
                    return true;
                }
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