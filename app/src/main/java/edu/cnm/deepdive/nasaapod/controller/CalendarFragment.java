package edu.cnm.deepdive.nasaapod.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.view.MonthDayBinder;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.nasaapod.adapter.DayHolder;
import edu.cnm.deepdive.nasaapod.databinding.FragmentCalendarBinding;
import edu.cnm.deepdive.nasaapod.viewmodel.ApodViewModel;

@AndroidEntryPoint
public class CalendarFragment extends Fragment {

  private FragmentCalendarBinding binding;
  private ApodViewModel viewModel;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentCalendarBinding.inflate(inflater, container, false);
    binding.calendar.setDayBinder(new MonthDayBinder<DayHolder>() {

      @Override
      public void bind(@NonNull DayHolder holder, CalendarDay calendarDay) {
        holder.bind(calendarDay);
      }

      @NonNull
      @Override
      public DayHolder create(@NonNull View view) {
        return new DayHolder(view);
      }

    });
    // TODO: 2/28/25 Initialize UI. 
    return binding.getRoot();
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
