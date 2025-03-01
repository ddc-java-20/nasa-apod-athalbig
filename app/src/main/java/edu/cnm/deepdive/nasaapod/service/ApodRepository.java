package edu.cnm.deepdive.nasaapod.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.nasaapod.R;
import edu.cnm.deepdive.nasaapod.model.dao.ApodDao;
import edu.cnm.deepdive.nasaapod.model.entity.Apod;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ApodRepository {

  public static final LocalDate SERVICE_START_DATE = LocalDate.of(1995, 6, 16);

  private final ApodProxyService proxyService;
  private final ApodDao apodDao;
  private final Scheduler scheduler;
  private final String apiKey;

  @Inject
  ApodRepository(@ApplicationContext Context context, ApodProxyService apodProxyService, ApodDao apodDao) {
    this.proxyService = apodProxyService;
    this.apodDao = apodDao;
    scheduler = Schedulers.io(); // TODO: 2/25/25 Investigate a fixed-size pool
    apiKey = context.getString(R.string.api_key);
  }

  public Completable fetch() {
    return proxyService
        .getToday(apiKey)
        .flatMapCompletable(apodDao::insert)
        .subscribeOn(scheduler);
  }

  public Completable fetch(LocalDate startDate){
    return proxyService
        .getOpenDateRange(startDate, apiKey)
        .flatMapCompletable(apodDao::insert)
        .subscribeOn(scheduler);
  }

  public Completable fetch(LocalDate startDate, LocalDate endDate) {
    return proxyService
        .getDateRange(startDate, endDate, apiKey)
        .flatMapCompletable(apodDao::insert)
        .subscribeOn(scheduler);
  }

  public LiveData<Apod> get(long id) {
    return apodDao.select(id);
  }

  public LiveData<Apod> get() {
    return apodDao.select(LocalDate.now());
  }

  public LiveData<List<Apod>> get(LocalDate startDate, LocalDate endDate) {
    return apodDao.selectRange(startDate, endDate);
  }

  public LiveData<List<Apod>> get(LocalDate startDate) {
    return apodDao.selectOpenRange(startDate);
  }

}
