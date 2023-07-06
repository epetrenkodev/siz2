package by.epetrenkodev.siz.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import by.epetrenkodev.siz.data.entities.ToolEntity;

@Dao
public interface ToolDao {
    @Query("SELECT * FROM toolEntity")
    List<ToolEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ToolEntity item);

    @Update
    void update(ToolEntity item);

    @Delete
    void delete(ToolEntity item);
}
