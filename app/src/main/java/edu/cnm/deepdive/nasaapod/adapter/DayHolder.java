package edu.cnm.deepdive.nasaapod.adapter;

import android.view.View;
import androidx.annotation.NonNull;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.view.ViewContainer;
import edu.cnm.deepdive.nasaapod.databinding.DayCalendarBinding;

public class DayHolder extends ViewContainer {

  private final DayCalendarBinding binding;

  public DayHolder(@NonNull View view) {
    super(view);
    binding = DayCalendarBinding.bind(view);
  }

  public void bind(CalendarDay calendarDay) {
    binding.getRoot().setText(String.valueOf(calendarDay.getDate().getDayOfMonth()));
  }
}
