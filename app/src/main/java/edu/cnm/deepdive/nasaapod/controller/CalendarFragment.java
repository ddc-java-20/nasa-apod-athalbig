package edu.cnm.deepdive.nasaapod.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.nasaapod.viewmodel.ApodViewModel;

@AndroidEntryPoint
public class CalendarFragment extends Fragment {

  private ApodViewModel viewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    // TODO: 2/28/25 Initialize UI. 
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(requireActivity()) //Connect to ViewModel & observe LiveData.
        .get(ApodViewModel.class);
    // TODO: 2/28/25 Observe livedata and start asynchronous processes, as necessary.
  }

  @Override
  public void onDestroyView() {
    // TODO: 2/28/25 Release references to binding.
    super.onDestroyView();
  }
}
