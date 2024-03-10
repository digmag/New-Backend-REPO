package com.example.Backend.KeyFunctional;

import com.example.Backend.OfficeFunctional.OfficeDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class KeysList {
    private List<KeyDTO> keys = new ArrayList<KeyDTO>();

    public void addToList(KeyDTO keyDTO){ keys.add(keyDTO); }
}
