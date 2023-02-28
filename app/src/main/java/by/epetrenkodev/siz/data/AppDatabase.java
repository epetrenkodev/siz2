package by.epetrenkodev.siz.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import by.epetrenkodev.siz.data.dao.SizDao;
import by.epetrenkodev.siz.data.entities.SizEntity;

@Database(entities = {SizEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SizDao itemDao();
}
