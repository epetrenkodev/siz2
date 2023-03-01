package by.epetrenkodev.siz.ui.siz;

import java.util.Calendar;
import java.util.Date;

public class SizItem {

    private final Date beginDate;
    private int period;
    private String name;

    public SizItem(String name, Date beginDate, int period) {
        this.name = name;
        this.beginDate = beginDate;
        this.period = period;
    }

    public Status getStatus() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getEndDate());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date leftDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date rightDate = calendar.getTime();
        Date currentDate = new Date();
        if (period >= 1200) return Status.UNTIL_WEAR;
        if (currentDate.after(leftDate) && currentDate.before(rightDate))
            return Status.GET;
        if (getEndDate().before(new Date()))
            return Status.OVERDUE;
        return Status.NORMAL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        calendar.add(Calendar.MONTH, period);
        return calendar.getTime();
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public enum Status {OVERDUE, GET, NORMAL, UNTIL_WEAR}
}
