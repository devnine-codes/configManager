package com.aice.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.util.ObjectUtils;

import com.aice.enums.EnumDateTimeFormat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilDateTime {
    public final ZoneId zoneId = ZoneId.of("Asia/Seoul");
    /**
     * String To ZonedDateTime<br>
     * @param dateTimeValue (2021-08-02 13:43:19.123)
     * @param dateTimeFormat (yyyy-MM-dd HH:mm:ss.SSS)
     * @return
     */
    public ZonedDateTime StringToZonedDateTime(String dateTimeValue,String dateTimeFormat) {
        String dtFormat = EnumDateTimeFormat.YmdHms.getDtFormat();
        if(ObjectUtils.isEmpty(dateTimeFormat) == false) {
            dtFormat = dateTimeFormat;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dtFormat);
            return LocalDateTime.parse(dateTimeValue,formatter).atZone(this.zoneId);
        }catch(Exception e) {
            log.error("StringToZonedDateTime val:{},format:{}",dateTimeValue,dateTimeFormat);
            log.error("StringToZonedDateTime",e);
            return null;
        }
    }

    /**
     * String To ZonedDateTime<br>
     * @param dateTimeValue (2021-08-02 13:43:19.123)
     * @param dateTimeFormat (yyyy-MM-dd HH:mm:ss.SSS)
     * @return
     */
    public ZonedDateTime StringToZonedDateTime(String dateTimeValue,EnumDateTimeFormat dateTimeFormat) {
        try {
            return this.StringToZonedDateTime(StringReFormat(dateTimeValue,dateTimeFormat),dateTimeFormat.getDtFormat());
        }catch(Exception e) {
            log.error("val:{},format:{}",dateTimeValue,dateTimeFormat.getDtFormat());
            log.error("StringToZonedDateTime",e);
        }
        return null;
    }

    /**
     * String To ZonedDateTime<br>
     * @param dateTimeValue (yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public ZonedDateTime StringToZonedDateTime(String dateTimeValue) {
        return this.StringToZonedDateTime(dateTimeValue,EnumDateTimeFormat.YmdHms.getDtFormat());
    }

    /**
     * String To LocalDateTime<br>
     * @param dateTimeValue (2021-08-02 13:43:19.123)
     * @param dateTimeFormat (yyyy-MM-dd HH:mm:ss.SSS)
     * @return
     */
    public LocalDateTime StringToLocalDateTime(String dateTimeValue,String dateTimeFormat) {
        return this.StringToZonedDateTime(dateTimeValue,dateTimeFormat).toLocalDateTime();
    }

    /**
     * String To LocalDateTime<br>
     * @param dateTimeValue (2021-08-02 13:43:19.123)
     * @param dateTimeFormat (yyyy-MM-dd HH:mm:ss.SSS)
     * @return
     */
    public LocalDateTime StringToLocalDateTime(String dateTimeValue,EnumDateTimeFormat dateTimeFormat) {
        return this.StringToZonedDateTime(dateTimeValue,dateTimeFormat).toLocalDateTime();
    }

    /**
     * String To LocalDateTime<br>
     * @param dateTimeValue (yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public LocalDateTime StringToLocalDateTime(String dateTimeValue) {
        return this.StringToLocalDateTime(dateTimeValue,EnumDateTimeFormat.YmdHms.getDtFormat());
    }

    /**
     * String To Date<br>
     * @param dateTimeValue (2021-08-02 13:43:19.123)
     * @param dateTimeFormat (yyyy-MM-dd HH:mm:ss.SSS)
     * @return
     */
    public Date StringToDate(String dateTimeValue,String dateTimeFormat) {
        return Date.from(this.StringToZonedDateTime(dateTimeValue,dateTimeFormat).toInstant());
    }

    /**
     * String To Date<br>
     * @param dateTimeValue (2021-08-02 13:43:19.123)
     * @param dateTimeFormat (yyyy-MM-dd HH:mm:ss.SSS)
     * @return
     */
    public Date StringToDate(String dateTimeValue,EnumDateTimeFormat dateTimeFormat) {
        return Date.from(this.StringToZonedDateTime(dateTimeValue,dateTimeFormat).toInstant());
    }

    /**
     * String To Date<br>
     * @param dateTimeValue (yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public Date StringToDate(String dateTimeValue) {
        return this.StringToDate(dateTimeValue,EnumDateTimeFormat.YmdHms.getDtFormat());
    }

    /**
     * ZonedDateTime To String<br>
     * @param dateTimeValue
     * @param dateTimeFormat (yyyy-MM-dd HH:mm:ss.SSS)
     * @return
     */
    public String ZonedDateTimeToString(ZonedDateTime dateTimeValue,String dateTimeFormat) {
        String dtFormat = EnumDateTimeFormat.YmdHms.getDtFormat();
        if(ObjectUtils.isEmpty(dateTimeFormat) == false) {
            dtFormat = dateTimeFormat;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dtFormat);
            return dateTimeValue.format(formatter);
        }catch(Exception e) {
            log.error("ZonedDateTimeToString val:{},format:{}",dateTimeValue,dateTimeFormat);
            log.error("ZonedDateTimeToString",e);
            return null;
        }
    }

    /**
     * ZonedDateTime To String<br>
     * @param dateTimeValue
     * @param dateTimeFormat (yyyy-MM-dd HH:mm:ss.SSS)
     * @return
     */
    public String ZonedDateTimeToString(ZonedDateTime dateTimeValue,EnumDateTimeFormat dateTimeFormat) {
        return ZonedDateTimeToString(dateTimeValue,dateTimeFormat.getDtFormat());
    }

    /**
     * LocalDateTime To String<br>
     * @param dateTimeValue
     * @param dateTimeFormat (yyyy-MM-dd HH:mm:ss.SSS)
     * @return
     */
    public String LocalDateTimeToString(LocalDateTime dateTimeValue,String dateTimeFormat) {
        return this.ZonedDateTimeToString(dateTimeValue.atZone(this.zoneId),dateTimeFormat);
    }

    /**
     * LocalDateTime To String<br>
     * @param dateTimeValue
     * @param dateTimeFormat (yyyy-MM-dd HH:mm:ss.SSS)
     * @return
     */
    public String LocalDateTimeToString(LocalDateTime dateTimeValue,EnumDateTimeFormat dateTimeFormat) {
        return this.ZonedDateTimeToString(dateTimeValue.atZone(this.zoneId),dateTimeFormat.getDtFormat());
    }

    /**
     * Date To String<br>
     * @param dateTimeValue
     * @param dateTimeFormat (yyyy-MM-dd HH:mm:ss.SSS)
     * @return
     */
    public String DateToString(Date dateTimeValue,String dateTimeFormat) {
        return this.ZonedDateTimeToString(dateTimeValue.toInstant().atZone(this.zoneId),dateTimeFormat);
    }

    public String DateToString(Date dateTimeValue,EnumDateTimeFormat dateTimeFormat) {
        return this.ZonedDateTimeToString(dateTimeValue.toInstant().atZone(this.zoneId),dateTimeFormat.getDtFormat());
    }

    /**
     * Date To ZonedDateTime<br>
     * @param dateTimeValue
     * @return
     */
    public ZonedDateTime DateToZonedDateTime(Date dateTimeValue) {
        return ZonedDateTime.ofInstant(dateTimeValue.toInstant(),this.zoneId);
    }

    /**
     * Date To LocalDateTime<br>
     * @param dateTimeValue
     * @return
     */
    public LocalDateTime DateToLocalDateTime(Date dateTimeValue) {
        return LocalDateTime.ofInstant(dateTimeValue.toInstant(),this.zoneId);
    }

    public String StringReFormat(String dateTimeValue,EnumDateTimeFormat dateTimeFormat) {
        String result = null;
        try {
            switch(dateTimeFormat) {
            case YmdHmsMi:
            case YmdHmsMiTrim:
                if(dateTimeValue.length() <= dateTimeFormat.getDtFormat().length()) {
                    String[] dtSplit = dateTimeValue.split("\\.",-1);
                    int misec = 0;
                    if(dtSplit.length >= 2) {
                        misec = Integer.parseInt(dtSplit[1]);
                    }
                    result = String.format("%s.%03d",dtSplit[0],misec);
                }else {
                    result = dateTimeValue;
                }
                break;
            default:
                result = dateTimeValue;
                break;
            }
        }catch(Exception e) {
            log.error("StringReFormat val:{},format:{}",dateTimeValue,dateTimeFormat.getDtFormat());
            log.error("StringReFormat",e);
        }
        return result;
    }

    /**
     * 시간차 계산
     * @param zdtMin
     * @param zdtMax
     * @param chronoUnit
     * @return
     */
    public long diffDateTime(ZonedDateTime zdtMin,ZonedDateTime zdtMax,ChronoUnit chronoUnit) {
        return chronoUnit.between(zdtMin,zdtMax);
    }

    /**
     * 현재시간
     * @param format
     * @return
     */
    public String getCurrentDateTimeString(String format) {
        String result = null;
        try {
            LocalDateTime dt = LocalDateTime.now(this.zoneId);
            result = dt.format(DateTimeFormatter.ofPattern(format));
        }catch(Exception e) {
            log.error("datetime parse error",e);
        }
        return result;
    }

    public LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now(this.zoneId);
    }

    public ZonedDateTime getCurrentZonedDateTime() {
        return ZonedDateTime.now(this.zoneId);
    }

    public ZonedDateTime LongToZonedDateTime(long unixTime) {
        ZonedDateTime result = null;
        try {
            result = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(unixTime)
                ,zoneId
            );
        }catch(Exception e) {
            log.error("datetime parse error",e);
        }
        return result;
    }

    /**
     * unixTime값은 밀리초를 포함해야 한다(1000을 곱해서 넣을것)
     * @param dtMin
     * @param dtMax
     */
    public long diffDateTime(long dtMin,long dtMax,ChronoUnit chronoUnit) {
        ZonedDateTime zdtMin = LongToZonedDateTime(dtMin);
        ZonedDateTime zdtMax = LongToZonedDateTime(dtMax);
        return chronoUnit.between(zdtMin,zdtMax);
    }

    public long diffDateTime(LocalDateTime dtMin,LocalDateTime dtMax,ChronoUnit chronoUnit) {
        ZonedDateTime zdtMin = dtMin.atZone(this.zoneId);
        ZonedDateTime zdtMax = dtMax.atZone(this.zoneId);
        return chronoUnit.between(zdtMin,zdtMax);
    }

    public long getCurrentMilliSecondsFull() {
        long result = 0;
        try {
            ZonedDateTime dt = ZonedDateTime.now(this.zoneId);
            result = dt.toInstant().toEpochMilli();
        }catch(Exception e) {
            log.error("datetime parse error",e);
        }
        return result;
    }
    
    public int getCurrentMilliSeconds() {
        int result = 0;
        try {
            result = (int) (getCurrentMilliSecondsFull() % 1000L);
        }catch(Exception e) {
            log.error("datetime parse error",e);
        }
        return result;
    }
}
