package by.epetrenkodev.siz.ui.siz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

import by.epetrenkodev.siz.R;

public class SizAdapter extends RecyclerView.Adapter<SizAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<SizItem> sizList;
    private final OnSizClickListener onClickListener;
    private final Context context;
    private final OnAddClickListener onClickAddListener;

    SizAdapter(Context context, List<SizItem> sizList, OnSizClickListener onClickListener, OnAddClickListener onClickAddListener) {
        this.sizList = sizList;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
        this.onClickAddListener = onClickAddListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(viewType, parent, false);
        ViewHolder holder = new ViewHolder(view);
        if (viewType == R.layout.element_add_botton)
            holder.addButton.setOnClickListener(onClickAddListener::onAddClick);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position != sizList.size()) {
            SizItem sizItem = sizList.get(position);
            String[] months = context.getResources().getStringArray(R.array.months);

            holder.nameView.setText(sizItem.getName());

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(sizItem.getBeginDate());
            String beginMonth = months[calendar.get(Calendar.MONTH)];
            String beginYear = String.valueOf(calendar.get(Calendar.YEAR));
            holder.beginDateView.setText(context.getString(R.string.month_and_year, beginMonth, beginYear));

            calendar.setTime(sizItem.getEndDate());
            String endMonth = months[calendar.get(Calendar.MONTH)];
            String endYear = String.valueOf(calendar.get(Calendar.YEAR));
            holder.endDateView.setText(context.getString(R.string.month_and_year, endMonth, endYear));

            switch (sizItem.getStatus()) {
                case NORMAL:
                    holder.statusView.setImageResource(R.drawable.ic_smile_green);
                    break;
                case GET:
                    holder.statusView.setImageResource(R.drawable.ic_smile_yellow);
                    break;
                case OVERDUE:
                    holder.statusView.setImageResource(R.drawable.ic_smile_red);
                    break;
            }
            holder.itemView.setOnClickListener(view -> onClickListener.onSizClick(sizItem, position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == sizList.size()) ? R.layout.element_add_botton : R.layout.element_siz;
    }

    @Override
    public int getItemCount() {
        return sizList.size() + 1;
    }

    interface OnSizClickListener {
        void onSizClick(SizItem sizItem, int position);
    }

    interface OnAddClickListener {
        void onAddClick(View view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView statusView;
        final TextView nameView;
        final TextView beginDateView;
        final TextView endDateView;
        final TextView addButton;

        ViewHolder(View view) {
            super(view);
            statusView = view.findViewById(R.id.status_image);
            nameView = view.findViewById(R.id.siz_name);
            beginDateView = view.findViewById(R.id.begin_date);
            endDateView = view.findViewById(R.id.end_date);
            addButton = view.findViewById(R.id.add_button);
        }
    }
}
