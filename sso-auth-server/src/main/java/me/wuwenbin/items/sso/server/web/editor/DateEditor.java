package me.wuwenbin.items.sso.server.web.editor;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wuwenbin
 * @date 2017/5/20
 */
public class DateEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if (!StringUtils.hasText(text)) {
            setValue(null);
        } else {
            String[] texts = text.split(" ");
            //看传过来的类型
            if (texts.length == 2) {
                String[] dates = null;
                if (texts[0].split("-").length == 3) {
                    dates = texts[0].split("-");
                }
                if (texts[0].split("/").length == 3) {
                    dates = texts[0].split("/");
                }
                int year = Integer.parseInt(dates[0]);
                int month = Integer.parseInt(dates[1]);
                int day = Integer.parseInt(dates[2]);

                String[] times = texts[1].split(":");
                int hour = Integer.parseInt(times[0]);
                int minutes = Integer.parseInt(times[1]);
                int second = Integer.parseInt(times[2]);
                LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minutes, second);
                setValue(localDateTime);
            } else {
                String[] dates = null;
                if (texts[0].split("-").length == 3) {
                    dates = texts[0].split("-");
                }
                if (texts[0].split("/").length == 3) {
                    dates = texts[0].split("/");
                }
                int year = Integer.parseInt(dates[0]);
                int month = Integer.parseInt(dates[1]);
                int day = Integer.parseInt(dates[2]);
                LocalDateTime localDateTime = LocalDateTime.of(year, month, day, 0, 0, 0);
                setValue(localDateTime);
            }
        }
    }

    @Override
    public String getAsText() {
        return getValue().toString();
    }

    private static LocalDateTime string2Date(String strDate) {
        if (strDate == null || "".equals(strDate)) {
            throw new RuntimeException("str date null");
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(strDate);
        return LocalDateTime.parse(strDate, dtf);
    }


}
