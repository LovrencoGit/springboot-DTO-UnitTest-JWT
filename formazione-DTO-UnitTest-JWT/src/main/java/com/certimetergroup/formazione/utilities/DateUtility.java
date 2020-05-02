package com.certimetergroup.formazione.utilities;

import com.certimetergroup.formazione.enumeration.ResponseCodeEnum;
import com.certimetergroup.formazione.exception.FailureException;
import org.springframework.http.HttpStatus;

import java.time.*;
import java.util.Date;

public class DateUtility {

    public static Integer calculateAgeOf(LocalDate birthday){
        return DateUtility.calculateAgeOf(birthday, LocalDate.now());
    }
    public static Integer calculateAgeOf(LocalDate birthday, LocalDate today){
        if( birthday == null )      return null;
        if( today == null )         throw new FailureException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCodeEnum.ERR_500);
        Period period = Period.between(birthday, today);
        return period.getYears();
    }



    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
