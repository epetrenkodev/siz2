package by.epetrenkodev.siz.data;

import java.util.Date;

import by.epetrenkodev.siz.data.entities.SizEntity;
import by.epetrenkodev.siz.ui.siz.SizItem;

public class Mapper {
    public static SizItem dataToDomain(SizEntity item) {
        return new SizItem(item.name, new Date(item.date), item.period);
    }

    public static SizEntity domainToData(SizItem item) {
        SizEntity data = new SizEntity();
        data.name = item.getName();
        data.date = item.getBeginDate().toString();
        data.period = item.getPeriod();
        return data;
    }
}
