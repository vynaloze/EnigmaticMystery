package com.vynaloze.smartmirror.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.annimon.stream.Stream;
import com.vynaloze.smartmirror.model.randomcomment.RandomCommentRepository;
import com.vynaloze.smartmirror.model.randomcomment.pojo.RandomComment;
import com.vynaloze.smartmirror.model.weather.pojo.Weather;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RandomCommentViewModel extends ViewModel {
    private static final String TAG = "RandomCommentViewModel";
    private static final int PERIOD = 17;
    private RandomCommentRepository repository;
    private MutableLiveData<RandomComment> currentComment;
    private Weather currentWeather;

    public LiveData<RandomComment> getCurrentComment() {
        if (currentComment == null) {
            currentComment = new MutableLiveData<>();
            repository = new RandomCommentRepository();
            updateComment();
        }
        return currentComment;
    }

    public void putCurrentWeather(Weather weather) {
        currentWeather = weather;
    }

    private void updateComment() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(new RandomUpdater(), 0, PERIOD, TimeUnit.SECONDS);     //FIXME!!!!! hours, not seconds.
    }

    private class RandomUpdater implements Runnable {
        private final int desiredUpdatePeriod = 3 * 60;  //fixme (tweakme) too - 6?
        private final int possibleBias = 60;
        private Random random = new Random();
        private int minutesUntilNextUpdate = 0;

        @Override
        public void run() {
            minutesUntilNextUpdate -= PERIOD;
            if (isTimeToRun()) {
                updateComment();
                calculateNextUpdateTime();
            } else {
                Log.d(TAG, "It's not time to update RandomComment. Minutes left: " + minutesUntilNextUpdate);
            }
        }

        private boolean isTimeToRun() {
            return minutesUntilNextUpdate <= 0;
        }

        private void updateComment() {
            RandomComment newComment = pickCommentUsingRelativeProbability();
            Log.d(TAG, "Updated RandomComment from " + currentComment.getValue() + " to " + newComment);
            currentComment.postValue(newComment);
        }

        private RandomComment pickCommentUsingRelativeProbability() {
            List<RandomComment> comments = repository.getAllComments(currentWeather);
            double probabilitySum = Stream.of(comments).mapToDouble(RandomComment::getProbability).sum();
            double pickedValue = random.nextDouble() * probabilitySum;
            double cumulativeProbability = 0.0;
            for (RandomComment comment : comments) {
                cumulativeProbability += comment.getProbability();
                if (cumulativeProbability >= pickedValue) {
                    return comment;
                }
            }
            return null;
        }

        private void calculateNextUpdateTime() {
            minutesUntilNextUpdate = desiredUpdatePeriod + random.nextInt(possibleBias) * (random.nextBoolean() ? 1 : -1);
            Log.d(TAG, "Next update happens in " + minutesUntilNextUpdate + " minutes");
        }
    }
}
