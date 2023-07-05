package by.epetrenkodev.siz.ui.tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import by.epetrenkodev.siz.R;

public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final ToolAdapter.OnToolClickListener onToolClickListener;
    //private final Context context;
    private List<ToolItem> toolList;

    ToolAdapter(Context context, List<ToolItem> toolList, ToolAdapter.OnToolClickListener onToolClickListener) {
        this.toolList = toolList;
        this.inflater = LayoutInflater.from(context);
        this.onToolClickListener = onToolClickListener;
        //this.context = context;
    }

    @NonNull
    @Override
    public ToolAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.element_tool, parent, false);
        return new ToolAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToolAdapter.ViewHolder holder, int position) {
        if (position != toolList.size()) {
            ToolItem toolItem = toolList.get(position);

            holder.nameView.setText(toolItem.getName());
            holder.cardCountView.setText(toolItem.getCardCount());
            holder.realCountView.setText(toolItem.getRealCount());

            if (toolItem.getTotal() == 0)
                holder.statusView.setImageResource(R.drawable.ic_smile_green);
            else if (toolItem.getTotal() > 0)
                holder.statusView.setImageResource(R.drawable.ic_smile_yellow);
            else
                holder.statusView.setImageResource(R.drawable.ic_smile_red);

            holder.itemView.setOnClickListener(view -> onToolClickListener.onToolClick(view, toolItem, position));
        }
    }

    @Override
    public int getItemCount() {
        return toolList.size();
    }

    public void update(List<ToolItem> list) {
        toolList = list;
    }

    interface OnToolClickListener {
        void onToolClick(View view, ToolItem toolItem, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView statusView;
        final TextView nameView;
        final TextView cardCountView;
        final TextView realCountView;

        ViewHolder(View view) {
            super(view);
            statusView = view.findViewById(R.id.tool_status_image);
            nameView = view.findViewById(R.id.tool_name);
            cardCountView = view.findViewById(R.id.card_count);
            realCountView = view.findViewById(R.id.real_count);
        }
    }
}
