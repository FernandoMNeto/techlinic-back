package br.com.clinic.entities.models;

import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Hours {
    private List<LocalTime> hours;
    public Hours() {
        this.hours =  new ArrayList<>(Arrays.asList(
                LocalTime.of(8, 0, 0),
                LocalTime.of(8, 30, 0),
                LocalTime.of(9, 0, 0),
                LocalTime.of(9, 30, 0),
                LocalTime.of(10, 0, 0),
                LocalTime.of(10, 30, 0),
                LocalTime.of(11, 0, 0),
                LocalTime.of(11, 30, 0),
                LocalTime.of(14, 0, 0),
                LocalTime.of(14, 30, 0),
                LocalTime.of(15, 0, 0),
                LocalTime.of(15, 30, 0),
                LocalTime.of(16, 0, 0),
                LocalTime.of(16, 30, 0),
                LocalTime.of(17, 0, 0),
                LocalTime.of(17, 30, 0)
        ));
    }

}
