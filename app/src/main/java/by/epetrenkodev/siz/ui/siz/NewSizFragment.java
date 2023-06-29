package by.epetrenkodev.siz.ui.siz;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import by.epetrenkodev.siz.R;
import by.epetrenkodev.siz.databinding.FragmentNewSizBinding;

public class NewSizFragment extends Fragment {
    final String TAG = "123";

    NavController navController;

    SizViewModel sizViewModel;
    private FragmentNewSizBinding binding;

    private final NewSizFragment fragment = this;
    private View rootView;
    private boolean isAddEnabled = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sizViewModel = new ViewModelProvider(this).get(SizViewModel.class);
        binding = FragmentNewSizBinding.inflate(inflater, container, false);
        Calendar calendar = Calendar.getInstance();
        binding.selectDateView.beginMonth.setSelection(calendar.get(Calendar.MONTH));
        binding.selectDateView.beginYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        binding.selectDateView.todayButton.setOnClickListener(this::onTodayClick);
        binding.newSizUntilWear.setOnCheckedChangeListener(this::onUntilWearChange);
        TextWatcher textWatcher = new Watcher();
        binding.newSizNameEdit.addTextChangedListener(textWatcher);
        binding.newSizPeriodEdit.addTextChangedListener(textWatcher);
        binding.selectDateView.beginYear.addTextChangedListener(textWatcher);
        rootView = binding.getRoot();

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onPrepareMenu(@NonNull Menu menu) {
                if (menu.findItem(R.id.ok) != null)
                    menu.findItem(R.id.ok).setEnabled(isAddEnabled);
                MenuProvider.super.onPrepareMenu(menu);
                Log.d(TAG, "onPrepareMenu: " + binding.newSizNameEdit.getText().toString());
            }

            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
                menuInflater.inflate(R.menu.new_siz_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.ok) {
                    sizViewModel.acceptSiz();
//                    String name = binding.newSizNameEdit.getText().toString();
//                    int period = Integer.parseInt(binding.newSizPeriodEdit.getText().toString());
//                    int month = binding.selectDateView.beginMonth.getSelectedItemPosition();
//                    int year = Integer.parseInt(binding.selectDateView.beginYear.getText().toString());
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.set(year, month, 1);
//                    Date beginDate = calendar.getTime();
//                    sizViewModel.newSiz(name, beginDate, period);

                    //Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main).popBackStack();
                    //Navigation.findNavController(rootView).popBackStack();
                    //NavHostFragment.findNavController(fragment).popBackStack();
                    //navController.popBackStack();
                    //Log.d(TAG, "onMenuItemSelected: end");
                    return true;
                }
                return false;
            }
        });
    }

    private void onUntilWearChange(CompoundButton compoundButton, boolean b) {
        if (b) {
            binding.newSizPeriodEdit.setText("1200");
            binding.newSizPeriodEdit.setVisibility(View.GONE);
        } else {
            binding.newSizPeriodEdit.setText(null);
            binding.newSizPeriodEdit.setVisibility(View.VISIBLE);
        }
    }

    private void onTodayClick(View view) {
        Calendar calendar = Calendar.getInstance();
        binding.selectDateView.beginMonth.setSelection(calendar.get(Calendar.MONTH));
        binding.selectDateView.beginYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
    }

    private void onAddClick(View view) {
        String name = binding.newSizNameEdit.getText().toString();
        int period = Integer.parseInt(binding.newSizPeriodEdit.getText().toString());
        int month = binding.selectDateView.beginMonth.getSelectedItemPosition();
        int year = Integer.parseInt(binding.selectDateView.beginYear.getText().toString());
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
            isAddEnabled = !(binding.newSizNameEdit.getText().toString().isEmpty()
                    || binding.newSizPeriodEdit.getText().toString().isEmpty()
                    || binding.selectDateView.beginYear.getText().toString().isEmpty());
            requireActivity().invalidateOptionsMenu();
        }
    }
}
