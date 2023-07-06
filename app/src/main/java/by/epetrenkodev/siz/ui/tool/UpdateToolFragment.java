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
import by.epetrenkodev.siz.databinding.FragmentUpdateToolBinding;

public class UpdateToolFragment extends Fragment {
    Bundle args;
    ToolViewModel toolViewModel;
    int position;
    private FragmentUpdateToolBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        toolViewModel = new ViewModelProvider(this).get(ToolViewModel.class);
        binding = FragmentUpdateToolBinding.inflate(inflater, container, false);

        args = getArguments();
        if (args != null) {
            binding.updateToolNameEdit.setText(args.getString("name"));
            binding.updateToolCardCountEdit.setText(String.valueOf(args.getInt("cardCount")));
            binding.updateToolRealCountEdit.setText(String.valueOf(args.getInt("realCount")));
            position = args.getInt("position");
        }
        binding.updateToolUpdateButton.setOnClickListener(this::onUpdateClick);
        binding.updateToolRemoveButton.setOnClickListener(this::onRemoveClick);
        binding.updateToolUpdateButton.setEnabled(false);
        TextWatcher textWatcher = new UpdateToolFragment.Watcher();
        binding.updateToolNameEdit.addTextChangedListener(textWatcher);
        binding.updateToolCardCountEdit.addTextChangedListener(textWatcher);
        binding.updateToolRealCountEdit.addTextChangedListener(textWatcher);
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
        String name = binding.updateToolNameEdit.getText().toString();
        int cardCount = Integer.parseInt(binding.updateToolCardCountEdit.getText().toString());
        int realCount = Integer.parseInt(binding.updateToolRealCountEdit.getText().toString());
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
            binding.updateToolUpdateButton.setEnabled(true);
            if (binding.updateToolNameEdit.getText().toString().isEmpty()
                    || binding.updateToolCardCountEdit.getText().toString().isEmpty()
                    || binding.updateToolRealCountEdit.getText().toString().isEmpty()
                    || (binding.updateToolNameEdit.getText().toString().equals(args.getString("name"))
                    && binding.updateToolCardCountEdit.getText().toString().equals(String.valueOf(args.getInt("cardCount")))
                    && binding.updateToolRealCountEdit.getText().toString().equals(String.valueOf(args.getInt("realCount"))))) {
                binding.updateToolUpdateButton.setEnabled(false);
            }
            binding.updateToolRemoveButton.setEnabled(true);
            if (!binding.updateToolNameEdit.getText().toString().equals(args.getString("name")))
                binding.updateToolRemoveButton.setEnabled(false);
        }
    }
}
