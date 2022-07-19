package sketcher.scheduling.config;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeConfig {
    public LocalDateTime getLocalTimeNooN() {
//        return LocalDateTime.now().with(LocalTime.NOON);
        return LocalDateTime.now() // 오늘 날짜 가져옴
                .withHour(0)      // 시간 변경
                .withMinute(0)    // 분 변경
                .withSecond(0)    // 초 변경
                .withNano(0);     // 나노초 변경
    }

    public LocalDateTime getLocalTimeMAX() {
        return LocalDateTime.now().with(LocalTime.MAX);
    }

    public int getDayOfWeekNumber(LocalDateTime date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek(); // 오늘 날짜의 요일 가져옴
        return dayOfWeek.getValue() - 1; // 요일의 지정 숫자에서 1을 뺌 (월: 0)
    }

    public LocalDateTime getWeekStart(LocalDateTime date) {
        return date.minusDays(getDayOfWeekNumber(date));
    }

    public LocalDateTime getWeekEnd(LocalDateTime date) {
        return date.plusDays(7 - getDayOfWeekNumber(date));
    }
}
