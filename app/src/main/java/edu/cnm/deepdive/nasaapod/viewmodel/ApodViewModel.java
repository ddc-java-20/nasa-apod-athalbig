package edu.cnm.deepdive.nasaapod.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.nasaapod.model.entity.Apod;
import edu.cnm.deepdive.nasaapod.service.ApodRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;

@HiltViewModel
public class ApodViewModel extends ViewModel {

  private static final String TAG = ApodViewModel.class.getSimpleName();
  private final ApodRepository repository;
  private final MutableLiveData<YearMonth> yearMonth;
  private final LiveData<List<Apod>> apods;
  private final MutableLiveData<Long> apodId;
  private final MutableLiveData<Throwable> throwable;

  @Inject
  ApodViewModel(ApodRepository repository) {
    this.repository = repository;
    yearMonth = new MutableLiveData<>(YearMonth.now());
    apodId = new MutableLiveData<>();
    apods = Transformations.switchMap(Transformations.distinctUntilChanged(yearMonth),
        this::transformYearMonthToQuery);
    throwable = new MutableLiveData<>();
  }

  public LiveData<List<Apod>> getApods() {
    return apods;
  }

  public LiveData<Apod> getApod() {
    return Transformations.switchMap(Transformations.distinctUntilChanged(apodId), repository::get);
  }

  public LiveData<Map<LocalDate, Apod>> getApodMap() {
    return Transformations.map(apods, (apodList) -> apodList
        .stream()
        .collect(Collectors.toMap(Apod::getDate, (apod) -> apod)));
  }

  public LiveData<YearMonth> getYearMonth() {
    return yearMonth;
  }

  /**
   * @noinspection ResultOfMethodCallIgnored
   */
  @SuppressLint("CheckResult")
  private LiveData<List<Apod>> transformYearMonthToQuery(YearMonth yearMonth) {
    LocalDate startDate = yearMonth.minusMonths(1).atDay(1);
    LocalDate endDate = yearMonth.plusMonths(2).atDay(1);
    throwable.setValue(null);
    repository
        .fetch(startDate, endDate)
        .subscribe(
            () -> {
            },
            throwable::setValue
        );
    return repository.get(startDate, endDate);
  }

  private void postThrowable(Throwable throwable) {
    Log.e(TAG, throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }
}
