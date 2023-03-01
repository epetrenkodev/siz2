package by.epetrenkodev.siz.ui.siz;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.Calendar;
import java.util.Date;

import by.epetrenkodev.siz.R;
import by.epetrenkodev.siz.databinding.FragmentUpdateSizBinding;

public class UpdateSizFragment extends Fragment {
    Bundle args;
    SizViewModel sizViewModel;
    int position;
    private FragmentUpdateSizBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sizViewModel = new ViewModelProvider(this).get(SizViewModel.class);
        binding = FragmentUpdateSizBinding.inflate(inflater, container, false);

        args = getArguments();
        if (args != null) {
            binding.updateSizNameEdit.setText(args.getString("name"));
            binding.selectDateView.beginMonth.setSelection(args.getInt("month"));
            binding.selectDateView.beginYear.setText(String.valueOf(args.getInt("year")));
            binding.updateSizPeriodEdit.setText(String.valueOf(args.getInt("period")));
            position = args.getInt("position");
            if (args.getInt("period") >= 1200) {
                binding.updateSizUntilWear.setChecked(true);
                binding.updateSizPeriodEdit.setVisibility(View.GONE);
            }
        }
        binding.selectDateView.todayButton.setOnClickListener(this::onTodayClick);
        binding.updateSizUpdateButton.setOnClickListener(this::onUpdateClick);
        binding.updateSizRemoveButton.setOnClickListener(this::onRemoveClick);
        binding.updateSizUntilWear.setOnCheckedChangeListener(this::onUntilWearChange);
        binding.updateSizUpdateButton.setEnabled(false);
        TextWatcher textWatcher = new UpdateSizFragment.Watcher();
        binding.updateSizNameEdit.addTextChangedListener(textWatcher);
        binding.updateSizPeriodEdit.addTextChangedListener(textWatcher);
        binding.selectDateView.beginYear.addTextChangedListener(textWatcher);
        binding.selectDateView.beginMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void onUntilWearChange(CompoundButton compoundButton, boolean b) {
        if (b) {
            binding.updateSizPeriodEdit.setText("1200");
            binding.updateSizPeriodEdit.setVisibility(View.GONE);
        } else {
            binding.updateSizPeriodEdit.setText(null);
            binding.updateSizPeriodEdit.setVisibility(View.VISIBLE);
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
        String name = binding.updateSizNameEdit.getText().toString();
        int period = Integer.parseInt(binding.updateSizPeriodEdit.getText().toString());
        int month = binding.selectDateView.beginMonth.getSelectedItemPosition();
        int year = Integer.parseInt(binding.selectDateView.beginYear.getText().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Date beginDate = calendar.getTime();
        sizViewModel.updateSiz(name, beginDate, period, position);
        Navigation.findNavController(view).popBackStack();
    }

    private void onTodayClick(View view) {
        Calendar calendar = Calendar.getInstance();
        binding.selectDateView.beginMonth.setSelection(calendar.get(Calendar.MONTH));
        binding.selectDateView.beginYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
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
            binding.updateSizUpdateButton.setEnabled(true);
            if (binding.updateSizNameEdit.getText().toString().isEmpty()
                    || binding.updateSizPeriodEdit.getText().toString().isEmpty()
                    || binding.selectDateView.beginYear.getText().toString().isEmpty()
                    || (binding.updateSizNameEdit.getText().toString().equals(args.getString("name"))
                    && binding.updateSizPeriodEdit.getText().toString().equals(String.valueOf(args.getInt("period")))
                    && binding.selectDateView.beginMonth.getSelectedItemPosition() == args.getInt("month")
                    && binding.selectDateView.beginYear.getText().toString().equals(String.valueOf(args.getInt("year"))))) {
                binding.updateSizUpdateButton.setEnabled(false);
            }
            binding.updateSizRemoveButton.setEnabled(true);
            if (!binding.updateSizNameEdit.getText().toString().equals(args.getString("name")))
                binding.updateSizRemoveButton.setEnabled(false);
        }
    }
}
