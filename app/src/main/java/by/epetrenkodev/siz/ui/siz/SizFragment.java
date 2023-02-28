package by.epetrenkodev.siz.ui.siz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import by.epetrenkodev.siz.R;
import by.epetrenkodev.siz.databinding.FragmentSizBinding;

public class SizFragment extends Fragment implements SizAdapter.OnAddClickListener{

    String TAG = "123";

    private FragmentSizBinding binding;
    SizViewModel sizViewModel;
    SizAdapter adapter;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sizViewModel = new ViewModelProvider(this).get(SizViewModel.class);

        binding = FragmentSizBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sizViewModel.data.observe(getViewLifecycleOwner(), data -> adapter.notifyDataSetChanged());
        adapter = new SizAdapter(requireContext(), sizViewModel.data.getValue(), this::updateSiz_onClick, this);
        recyclerView = binding.sizList;
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        sizViewModel.loadSizList();
    }

    public void updateSiz_onClick(SizItem sizItem, int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    @Override
//    public void createSiz(String name, Date beginDate, int period) {
//        sizViewModel.createSiz(name, beginDate, period);
//    }
//
//    @Override
//    public void updateSiz(int position, String name, Date beginDate, int period, String prevname) {
//        if (Objects.equals(name, prevname)) {
//            sizViewModel.updateSiz(position, name, beginDate, period);
//        } else {
//            sizViewModel.createSiz(name, beginDate, period);
//            sizViewModel.deleteSiz(position);
//        }
//    }
//
//    @Override
//    public void deleteSiz(int position) {
//        sizViewModel.deleteSiz(position);
//    }

    @Override
    public void onAddClick(View view) {
        Navigation.findNavController(view).navigate(R.id.new_siz_fragment);
    }
}