package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.entity.SensorType;
import com.aldisued.iot.monitoring.repository.SensorReadingRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class MeasurementService {

  private final SensorReadingRepository sensorReadingRepository;

  public MeasurementService(SensorReadingRepository sensorReadingRepository) {
    this.sensorReadingRepository = sensorReadingRepository;
  }

  public List<Double> getMeasurementValuesBySensorType(SensorType sensorType, LocalDateTime from, LocalDateTime to) {
    var sensorReadings = sensorReadingRepository.findBySensorTypeAndTimestampBetweenOrderByTimestampAsc(sensorType, from, to);

    return sensorReadings.stream().map(SensorReading::getValue).collect(Collectors.toList());
  }

  public Optional<Double> getAverageTemperature(LocalDateTime from, LocalDateTime to) {
    var sensorReadings = sensorReadingRepository.findBySensorTypeAndTimestampBetweenOrderByTimestampAsc(SensorType.TEMPERATURE, from, to);
    var average = sensorReadings.stream().mapToDouble(SensorReading::getValue).average();

    return average.stream().boxed().findFirst();
  }

}
