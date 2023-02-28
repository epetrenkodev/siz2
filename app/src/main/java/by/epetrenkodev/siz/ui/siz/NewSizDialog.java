package by.epetrenkodev.siz.ui.siz;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

import by.epetrenkodev.siz.R;

public class NewSizDialog extends DialogFragment {

    private Interface callback;
    private Calendar calendar;
    private EditText nameView;
    private EditText periodView;
    private Spinner monthView;
    private EditText yearView;
    private ImageButton todayButton;
    private Button okButton;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (Interface) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_new_siz, null, false);

        calendar = Calendar.getInstance();

        nameView = view.findViewById(R.id.new_siz_name);

        periodView = view.findViewById(R.id.new_siz_period);

        monthView = view.findViewById(R.id.begin_month);
        monthView.setSelection(calendar.get(Calendar.MONTH));

        yearView = view.findViewById(R.id.begin_year);
        yearView.setText(String.valueOf(calendar.get(Calendar.YEAR)));

        todayButton = view.findViewById(R.id.today_button);
        todayButton.setOnClickListener(this::todayButton_onClick);

        AlertDialog dialog = new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.add)
                .setView(view)
                .setPositiveButton(R.string.ok, this::okButton_onClick)
                .setNegativeButton(R.string.cancel, null)
                .create();

        dialog.show();

        okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        okButton.setEnabled(false);
        TextWatcher textWatcher = new Watcher();
        nameView.addTextChangedListener(textWatcher);
        periodView.addTextChangedListener(textWatcher);
        yearView.addTextChangedListener(textWatcher);

        return dialog;
    }

    private void okButton_onClick(DialogInterface dialog, int which) {
        String name = nameView.getText().toString();
        int period = Integer.parseInt(periodView.getText().toString());
        int month = monthView.getSelectedItemPosition();
        int year = Integer.parseInt(yearView.getText().toString());
        calendar.set(year, month, 1);
        Date beginDate = calendar.getTime();
        callback.createSiz(name, beginDate, period);
    }

    private void todayButton_onClick(View view) {
        calendar = Calendar.getInstance();
        monthView.setSelection(calendar.get(Calendar.MONTH));
        yearView.setText(String.valueOf(calendar.get(Calendar.YEAR)));
    }

    public interface Interface {
        void createSiz(String name, Date beginDate, int period);
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
            okButton.setEnabled(true);
            if (nameView.getText().toString().isEmpty() || periodView.getText().toString().isEmpty() || yearView.getText().toString().isEmpty()) {
                okButton.setEnabled(false);
            }
        }
    }
}
