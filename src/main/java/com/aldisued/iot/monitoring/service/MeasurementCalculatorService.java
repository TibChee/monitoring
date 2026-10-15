package com.aldisued.iot.monitoring.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MeasurementCalculatorService {

  public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
    if (0.0 > deviation || deviation > 1.0) { throw new IllegalArgumentException("Deviation must be between 0 and 1!"); }

    var average = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    var lowerBound = average * (1 - deviation);
    var upperBound = average * (1 + deviation);

    return values.stream().filter(value -> lowerBound < value && value < upperBound).toList();
  }

  public List<Double> getMovingAverage(List<Double> data, int windowSize) {
    if (windowSize <= 0) { throw new IllegalArgumentException("Window size must be positive!"); }
    if (windowSize > data.size()) { throw new IllegalArgumentException("Window size cannot exceed the number of values!"); }

    List<Double> movingAverages = new ArrayList<>(data.size() - windowSize + 1);
    double windowSum = 0.0;
    for (int i = 0; i < windowSize; i++) {
      windowSum += data.get(i);
    }
    movingAverages.add(windowSum / windowSize);

    for (int i = windowSize; i < data.size(); i++) {
      windowSum += data.get(i) - data.get(i - windowSize);
      movingAverages.add(windowSum / windowSize);
    }

    return movingAverages;
  }

}
