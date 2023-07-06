package by.epetrenkodev.siz.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SizEntity {
    @NonNull
    @PrimaryKey
    public String name = "";
    public String date;
    public int period;
}
