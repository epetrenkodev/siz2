package by.epetrenkodev.siz.data;

import java.util.ArrayList;
import java.util.List;

import by.epetrenkodev.siz.App;
import by.epetrenkodev.siz.data.dao.ToolDao;
import by.epetrenkodev.siz.data.entities.ToolEntity;
import by.epetrenkodev.siz.ui.tool.ToolItem;

public class ToolRepository {
    AppDatabase db;
    ToolDao dao;

    public ToolRepository() {
        db = App.getInstance().getDatabase();
        dao = db.toolDao();
    }

    public List<ToolItem> read() {
        List<ToolItem> items = new ArrayList<>();
        List<ToolEntity> data = dao.getAll();
        for (ToolEntity item : data)
            items.add(Mapper.dataToDomain(item));
        return items;
    }

    public void create(ToolItem item) {
        dao.insert(Mapper.domainToData(item));
    }

    public void update(ToolItem item) {
        dao.update(Mapper.domainToData(item));
    }

    public void delete(ToolItem item) {
        dao.delete(Mapper.domainToData(item));
    }
}
