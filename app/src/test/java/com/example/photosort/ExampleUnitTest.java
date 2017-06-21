package com.example.photosort;

import com.example.photosort.mydb.DataStorageImp;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by mikez on 5/24/2017.
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception{
        assertEquals(4,2+2);
    }

    @Test
    public void dataStorage() throws Exception{
        DataStorageImp db = new DataStorageImp();
        db.saveState("Testing");
        assertEquals("Testing", db.getState());
    }
}
