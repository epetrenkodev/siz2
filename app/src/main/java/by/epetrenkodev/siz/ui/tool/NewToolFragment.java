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

import by.epetrenkodev.siz.databinding.FragmentNewToolBinding;


public class NewToolFragment extends Fragment {
    ToolViewModel toolViewModel;
    private FragmentNewToolBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        toolViewModel = new ViewModelProvider(this).get(ToolViewModel.class);
        binding = FragmentNewToolBinding.inflate(inflater, container, false);

        binding.newToolAddButton.setOnClickListener(this::onAddClick);
        binding.newToolAddButton.setEnabled(false);
        TextWatcher textWatcher = new NewToolFragment.Watcher();
        binding.newToolNameEdit.addTextChangedListener(textWatcher);
        binding.newToolCardCountEdit.addTextChangedListener(textWatcher);
        binding.newToolRealCountEdit.addTextChangedListener(textWatcher);
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
        String name = binding.newToolNameEdit.getText().toString();
        int cardCount = Integer.parseInt(binding.newToolCardCountEdit.getText().toString());
        int realCount = Integer.parseInt(binding.newToolRealCountEdit.getText().toString());
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
            binding.newToolAddButton.setEnabled(true);
            if (binding.newToolNameEdit.getText().toString().isEmpty()
                    || binding.newToolCardCountEdit.getText().toString().isEmpty()
                    || binding.newToolRealCountEdit.getText().toString().isEmpty()) {
                binding.newToolAddButton.setEnabled(false);
            }
        }
    }
}
