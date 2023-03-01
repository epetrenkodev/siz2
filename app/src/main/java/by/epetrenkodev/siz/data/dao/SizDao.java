package by.epetrenkodev.siz.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import by.epetrenkodev.siz.data.entities.SizEntity;

@Dao
public interface SizDao {
    @Query("SELECT * FROM sizEntity")
    List<SizEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SizEntity item);

    @Update
    void update(SizEntity item);

    @Delete
    void delete(SizEntity item);
}
