package by.epetrenkodev.siz.ui.siz;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.Calendar;
import java.util.Date;

import by.epetrenkodev.siz.databinding.FragmentSizNewBinding;

public class NewSizFragment extends Fragment {

    SizViewModel sizViewModel;
    private FragmentSizNewBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sizViewModel = new ViewModelProvider(this).get(SizViewModel.class);
        binding = FragmentSizNewBinding.inflate(inflater, container, false);
        Calendar calendar = Calendar.getInstance();
        binding.sizNewSelectDateView.selectDateBeginMonthCombobox.setSelection(calendar.get(Calendar.MONTH));
        binding.sizNewSelectDateView.selectDateBeginYearEdit.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        binding.sizNewSelectDateView.selectDateTodayButton.setOnClickListener(this::onTodayClick);
        binding.sizNewAddButton.setOnClickListener(this::onAddClick);
        binding.sizNewUntilWearCheckbox.setOnCheckedChangeListener(this::onUntilWearChange);
        binding.sizNewAddButton.setEnabled(false);
        TextWatcher textWatcher = new Watcher();
        binding.sizNewNameEdit.addTextChangedListener(textWatcher);
        binding.sizNewPeriodEdit.addTextChangedListener(textWatcher);
        binding.sizNewSelectDateView.selectDateBeginYearEdit.addTextChangedListener(textWatcher);
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

    private void onUntilWearChange(CompoundButton compoundButton, boolean b) {
        if (b) {
            binding.sizNewPeriodEdit.setText("1200");
            binding.sizNewPeriodEdit.setVisibility(View.GONE);
        } else {
            binding.sizNewPeriodEdit.setText(null);
            binding.sizNewPeriodEdit.setVisibility(View.VISIBLE);
        }
    }

    private void onTodayClick(View view) {
        Calendar calendar = Calendar.getInstance();
        binding.sizNewSelectDateView.selectDateBeginMonthCombobox.setSelection(calendar.get(Calendar.MONTH));
        binding.sizNewSelectDateView.selectDateBeginYearEdit.setText(String.valueOf(calendar.get(Calendar.YEAR)));
    }

    private void onAddClick(View view) {
        String name = binding.sizNewNameEdit.getText().toString();
        int period = Integer.parseInt(binding.sizNewPeriodEdit.getText().toString());
        int month = binding.sizNewSelectDateView.selectDateBeginMonthCombobox.getSelectedItemPosition();
        int year = Integer.parseInt(binding.sizNewSelectDateView.selectDateBeginYearEdit.getText().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Date beginDate = calendar.getTime();
        sizViewModel.newSiz(name, beginDate, period);
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
            binding.sizNewAddButton.setEnabled(true);
            if (binding.sizNewNameEdit.getText().toString().isEmpty()
                    || binding.sizNewPeriodEdit.getText().toString().isEmpty()
                    || binding.sizNewSelectDateView.selectDateBeginYearEdit.getText().toString().isEmpty()) {
                binding.sizNewAddButton.setEnabled(false);
            }
        }
    }
}
