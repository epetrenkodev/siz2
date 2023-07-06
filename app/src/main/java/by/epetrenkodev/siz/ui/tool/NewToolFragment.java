package by.epetrenkodev.siz.ui.tool;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import by.epetrenkodev.siz.databinding.FragmentToolNewBinding;


public class NewToolFragment extends Fragment {
    ToolViewModel toolViewModel;
    private FragmentToolNewBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        toolViewModel = new ViewModelProvider(this).get(ToolViewModel.class);
        binding = FragmentToolNewBinding.inflate(inflater, container, false);

        binding.toolNewAddButton.setOnClickListener(this::onAddClick);
        binding.toolNewAddButton.setEnabled(false);
        TextWatcher textWatcher = new NewToolFragment.Watcher();
        binding.toolNewNameEdit.addTextChangedListener(textWatcher);
        binding.toolNewCardCountEdit.addTextChangedListener(textWatcher);
        binding.toolNewRealCountEdit.addTextChangedListener(textWatcher);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
                //menuInflater.inflate(R.menu.tool_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
    }

    private void onAddClick(View view) {
        String name = binding.toolNewNameEdit.getText().toString();
        int cardCount = Integer.parseInt(binding.toolNewCardCountEdit.getText().toString());
        int realCount = Integer.parseInt(binding.toolNewRealCountEdit.getText().toString());
        toolViewModel.newTool(name, cardCount, realCount);
        Navigation.findNavController(view).popBackStack();
    }

    private class Watcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            binding.toolNewAddButton.setEnabled(true);
            if (binding.toolNewNameEdit.getText().toString().isEmpty()
                    || binding.toolNewCardCountEdit.getText().toString().isEmpty()
                    || binding.toolNewRealCountEdit.getText().toString().isEmpty()) {
                binding.toolNewAddButton.setEnabled(false);
            }
        }
    }
}
