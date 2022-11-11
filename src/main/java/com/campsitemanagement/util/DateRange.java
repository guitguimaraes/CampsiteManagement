package com.campsitemanagement.util;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class responsible to handle date range.
 */
@AllArgsConstructor
public class DateRange implements Iterable<LocalDate> {
    private final LocalDate startDate;
    private final LocalDate endDate;

    @Override
    public Iterator<LocalDate> iterator() {
        return stream().iterator();
    }

    public Stream<LocalDate> stream() {
        return Stream.iterate(startDate, d -> d.plusDays(1))
                .limit(ChronoUnit.DAYS.between(startDate, endDate) + 1);
    }

    /**
     * method responsible to return all LocalDates between dates.
     * @return LocalDate list
     */
    public List<LocalDate> toList() {
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {
            dates.add(d);
        }
        return dates;
    }
}