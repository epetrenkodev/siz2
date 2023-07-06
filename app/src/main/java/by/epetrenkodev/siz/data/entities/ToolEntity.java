package by.epetrenkodev.siz.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ToolEntity {
    @NonNull
    @PrimaryKey
    public String name;
    public int cardCount;
    public int realCount;
}
