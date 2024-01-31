package org.example.service;

import org.example.model.dto.LocalSupplierDto;
import org.example.model.entity.Supplier;

import java.io.IOException;
import java.util.List;

public interface SupplierService {

    void seedSupplier() throws IOException;

    Supplier getRandomSupplier();

    List<LocalSupplierDto> getAllSuppliersWhichDontImport();
}
