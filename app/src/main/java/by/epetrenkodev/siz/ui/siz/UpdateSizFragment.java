package by.epetrenkodev.siz.ui.siz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.Calendar;
import java.util.Date;

import by.epetrenkodev.siz.R;
import by.epetrenkodev.siz.databinding.FragmentSizUpdateBinding;

public class UpdateSizFragment extends Fragment {
    Bundle args;
    SizViewModel sizViewModel;
    int position;
    private FragmentSizUpdateBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sizViewModel = new ViewModelProvider(this).get(SizViewModel.class);
        binding = FragmentSizUpdateBinding.inflate(inflater, container, false);

        args = getArguments();
        if (args != null) {
            binding.sizUpdateNameEdit.setText(args.getString("name"));
            binding.sizNewSelectDateView.selectDateBeginMonthCombobox.setSelection(args.getInt("month"));
            binding.sizNewSelectDateView.selectDateBeginYearEdit.setText(String.valueOf(args.getInt("year")));
            binding.sizUpdatePeriodEdit.setText(String.valueOf(args.getInt("period")));
            position = args.getInt("position");
            if (args.getInt("period") >= 1200) {
                binding.sizUpdateUntilWearCheckbox.setChecked(true);
                binding.sizUpdatePeriodEdit.setVisibility(View.GONE);
            }
        }
        binding.sizNewSelectDateView.selectDateTodayButton.setOnClickListener(this::onTodayClick);
        binding.sizUpdateUpdateButton.setOnClickListener(this::onUpdateClick);
        binding.sizUpdateRemoveButton.setOnClickListener(this::onRemoveClick);
        binding.sizUpdateUntilWearCheckbox.setOnCheckedChangeListener(this::onUntilWearChange);
        binding.sizUpdateUpdateButton.setEnabled(false);
        TextWatcher textWatcher = new UpdateSizFragment.Watcher();
        binding.sizUpdateNameEdit.addTextChangedListener(textWatcher);
        binding.sizUpdatePeriodEdit.addTextChangedListener(textWatcher);
        binding.sizNewSelectDateView.selectDateBeginYearEdit.addTextChangedListener(textWatcher);
        binding.sizNewSelectDateView.selectDateBeginMonthCombobox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                new Watcher().afterTextChanged(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

    @SuppressLint("SetTextI18n")
    private void onUntilWearChange(CompoundButton compoundButton, boolean b) {
        if (b) {
            binding.sizUpdatePeriodEdit.setText("1200");
            binding.sizUpdatePeriodEdit.setVisibility(View.GONE);
        } else {
            binding.sizUpdatePeriodEdit.setText(null);
            binding.sizUpdatePeriodEdit.setVisibility(View.VISIBLE);
        }
    }

    private void onRemoveClick(View view) {
        new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.remove)
                .setMessage(args.getString("name"))
                .setPositiveButton(R.string.remove, (dialogInterface, which) -> {
                    sizViewModel.removeSiz(position);
                    Navigation.findNavController(view).popBackStack();
                })
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }

    private void onUpdateClick(View view) {
        String name = binding.sizUpdateNameEdit.getText().toString();
        int period = Integer.parseInt(binding.sizUpdatePeriodEdit.getText().toString());
        int month = binding.sizNewSelectDateView.selectDateBeginMonthCombobox.getSelectedItemPosition();
        int year = Integer.parseInt(binding.sizNewSelectDateView.selectDateBeginYearEdit.getText().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Date beginDate = calendar.getTime();
        sizViewModel.updateSiz(name, beginDate, period, position);
        Navigation.findNavController(view).popBackStack();
    }

    private void onTodayClick(View view) {
        Calendar calendar = Calendar.getInstance();
        binding.sizNewSelectDateView.selectDateBeginMonthCombobox.setSelection(calendar.get(Calendar.MONTH));
        binding.sizNewSelectDateView.selectDateBeginYearEdit.setText(String.valueOf(calendar.get(Calendar.YEAR)));
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
            binding.sizUpdateUpdateButton.setEnabled(true);
            if (binding.sizUpdateNameEdit.getText().toString().isEmpty()
                    || binding.sizUpdatePeriodEdit.getText().toString().isEmpty()
                    || binding.sizNewSelectDateView.selectDateBeginYearEdit.getText().toString().isEmpty()
                    || (binding.sizUpdateNameEdit.getText().toString().equals(args.getString("name"))
                    && binding.sizUpdatePeriodEdit.getText().toString().equals(String.valueOf(args.getInt("period")))
                    && binding.sizNewSelectDateView.selectDateBeginMonthCombobox.getSelectedItemPosition() == args.getInt("month")
                    && binding.sizNewSelectDateView.selectDateBeginYearEdit.getText().toString().equals(String.valueOf(args.getInt("year"))))) {
                binding.sizUpdateUpdateButton.setEnabled(false);
            }
            binding.sizUpdateRemoveButton.setEnabled(true);
            if (!binding.sizUpdateNameEdit.getText().toString().equals(args.getString("name")))
                binding.sizUpdateRemoveButton.setEnabled(false);
        }
    }
}
