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
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import by.epetrenkodev.siz.R;
import by.epetrenkodev.siz.databinding.FragmentToolUpdateBinding;

public class UpdateToolFragment extends Fragment {
    Bundle args;
    ToolViewModel toolViewModel;
    int position;
    private FragmentToolUpdateBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        toolViewModel = new ViewModelProvider(this).get(ToolViewModel.class);
        binding = FragmentToolUpdateBinding.inflate(inflater, container, false);

        args = getArguments();
        if (args != null) {
            binding.toolUpdateNameEdit.setText(args.getString("name"));
            binding.toolUpdateCardCountEdit.setText(String.valueOf(args.getInt("cardCount")));
            binding.toolUpdateRealCountEdit.setText(String.valueOf(args.getInt("realCount")));
            position = args.getInt("position");
        }
        binding.toolUpdateUpdateButton.setOnClickListener(this::onUpdateClick);
        binding.toolUpdateRemoveButton.setOnClickListener(this::onRemoveClick);
        binding.toolUpdateUpdateButton.setEnabled(false);
        TextWatcher textWatcher = new UpdateToolFragment.Watcher();
        binding.toolUpdateNameEdit.addTextChangedListener(textWatcher);
        binding.toolUpdateCardCountEdit.addTextChangedListener(textWatcher);
        binding.toolUpdateRealCountEdit.addTextChangedListener(textWatcher);
        return binding.getRoot();
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

    private void onRemoveClick(View view) {
        new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.remove)
                .setMessage(args.getString("name"))
                .setPositiveButton(R.string.remove, (dialogInterface, which) -> {
                    toolViewModel.removeTool(position);
                    Navigation.findNavController(view).popBackStack();
                })
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }

    private void onUpdateClick(View view) {
        String name = binding.toolUpdateNameEdit.getText().toString();
        int cardCount = Integer.parseInt(binding.toolUpdateCardCountEdit.getText().toString());
        int realCount = Integer.parseInt(binding.toolUpdateRealCountEdit.getText().toString());
        toolViewModel.updateTool(name, cardCount, realCount, position);
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
            binding.toolUpdateUpdateButton.setEnabled(true);
            if (binding.toolUpdateNameEdit.getText().toString().isEmpty()
                    || binding.toolUpdateCardCountEdit.getText().toString().isEmpty()
                    || binding.toolUpdateRealCountEdit.getText().toString().isEmpty()
                    || (binding.toolUpdateNameEdit.getText().toString().equals(args.getString("name"))
                    && binding.toolUpdateCardCountEdit.getText().toString().equals(String.valueOf(args.getInt("cardCount")))
                    && binding.toolUpdateRealCountEdit.getText().toString().equals(String.valueOf(args.getInt("realCount"))))) {
                binding.toolUpdateUpdateButton.setEnabled(false);
            }
            binding.toolUpdateRemoveButton.setEnabled(true);
            if (!binding.toolUpdateNameEdit.getText().toString().equals(args.getString("name")))
                binding.toolUpdateRemoveButton.setEnabled(false);
        }
    }
}
