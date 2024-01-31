package org.example.service;

import org.example.model.entity.Part;

import java.io.IOException;
import java.util.Set;

public interface PartService {
    void seedPart() throws IOException;
    Set<Part> getRandomPart();
}
