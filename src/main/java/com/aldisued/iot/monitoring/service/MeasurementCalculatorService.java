package com.aldisued.iot.monitoring.service;


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
    // TODO: Task 10
    return List.of();
  }

}
