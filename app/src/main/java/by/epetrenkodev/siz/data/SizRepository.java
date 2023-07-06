package by.epetrenkodev.siz.data;

import java.util.ArrayList;
import java.util.List;

import by.epetrenkodev.siz.App;
import by.epetrenkodev.siz.data.dao.SizDao;
import by.epetrenkodev.siz.data.entities.SizEntity;
import by.epetrenkodev.siz.ui.siz.SizItem;

public class SizRepository {
    AppDatabase db;
    SizDao dao;

    public SizRepository() {
        db = App.getInstance().getDatabase();
        dao = db.sizDao();
    }

    public void create(SizItem item) {
        dao.insert(Mapper.domainToData(item));
    }

    public List<SizItem> read() {
        List<SizItem> items = new ArrayList<>();
        List<SizEntity> data = dao.getAll();
        for (SizEntity item : data)
            items.add(Mapper.dataToDomain(item));
        return items;
    }

    public void update(SizItem item) {
        dao.update(Mapper.domainToData(item));
    }

    public void delete(SizItem item) {
        dao.delete(Mapper.domainToData(item));
    }
}
