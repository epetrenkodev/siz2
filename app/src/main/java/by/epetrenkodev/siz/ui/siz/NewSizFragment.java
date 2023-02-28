package by.epetrenkodev.siz.ui.siz;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.Calendar;
import java.util.Date;

import by.epetrenkodev.siz.R;
import by.epetrenkodev.siz.data.SizRepository;
import by.epetrenkodev.siz.databinding.FragmentNewSizBinding;


public class NewSizFragment extends Fragment {

    private FragmentNewSizBinding binding;
    private Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewSizBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        calendar = Calendar.getInstance();

        binding.selectDateView.beginMonth.setSelection(calendar.get(Calendar.MONTH));
        binding.selectDateView.beginYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));

        binding.selectDateView.todayButton.setOnClickListener(this::todayButton_onClick);
        binding.newSizAddButton.setOnClickListener(this::onAddClick);
        binding.newSizAddButton.setEnabled(false);
        TextWatcher textWatcher = new Watcher();
        binding.newSizNameEdit.addTextChangedListener(textWatcher);
        binding.newSizPeriodEdit.addTextChangedListener(textWatcher);
        binding.selectDateView.beginYear.addTextChangedListener(textWatcher);

        return root;
    }

    private void todayButton_onClick(View view) {
        calendar = Calendar.getInstance();
        binding.selectDateView.beginMonth.setSelection(calendar.get(Calendar.MONTH));
        binding.selectDateView.beginYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
    }

    private void onAddClick(View view)
    {
        String name = binding.newSizNameEdit.getText().toString();
        int period = Integer.parseInt(binding.newSizPeriodEdit.getText().toString());
        int month = binding.selectDateView.beginMonth.getSelectedItemPosition();
        int year = Integer.parseInt(binding.selectDateView.beginYear.getText().toString());
        calendar.set(year, month, 1);
        Date beginDate = calendar.getTime();
        SizItem sizItem = new SizItem(name, beginDate, period);
        new SizRepository().create(sizItem);
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
            binding.newSizAddButton.setEnabled(true);
            if (binding.newSizNameEdit.getText().toString().isEmpty()
                    || binding.newSizPeriodEdit.getText().toString().isEmpty()
                    || binding.selectDateView.beginYear.getText().toString().isEmpty()) {
                binding.newSizAddButton.setEnabled(false);
            }
        }
    }
}
