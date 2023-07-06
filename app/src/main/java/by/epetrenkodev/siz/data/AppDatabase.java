package by.epetrenkodev.siz.data;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import by.epetrenkodev.siz.data.dao.SizDao;
import by.epetrenkodev.siz.data.dao.ToolDao;
import by.epetrenkodev.siz.data.entities.SizEntity;
import by.epetrenkodev.siz.data.entities.ToolEntity;

@Database(entities = {SizEntity.class, ToolEntity.class}, version = 2, autoMigrations = {@AutoMigration(from = 1, to = 2)})
public abstract class AppDatabase extends RoomDatabase {
    public abstract SizDao sizDao();

    public abstract ToolDao toolDao();
}
