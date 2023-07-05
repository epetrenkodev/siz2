package by.epetrenkodev.siz.data;

import java.util.Date;

import by.epetrenkodev.siz.data.entities.SizEntity;
import by.epetrenkodev.siz.data.entities.ToolEntity;
import by.epetrenkodev.siz.ui.siz.SizItem;
import by.epetrenkodev.siz.ui.tool.ToolItem;

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


    public static ToolItem dataToDomain(ToolEntity item) {
        return new ToolItem(item.name, item.cardCount, item.realCount);
    }

    public static ToolEntity domainToData(ToolItem item) {
        ToolEntity data = new ToolEntity();
        data.name = item.getName();
        data.cardCount = item.getCardCount();
        data.realCount = item.getRealCount();
        return data;
    }
}
