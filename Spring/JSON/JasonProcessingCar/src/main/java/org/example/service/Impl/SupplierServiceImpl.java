package org.example.service.Impl;

import com.google.gson.Gson;
import org.example.model.dto.LocalSupplierDto;
import org.example.model.dto.SupplierSeedDto;
import org.example.model.entity.Supplier;
import org.example.repository.SupplierRepository;
import org.example.service.SupplierService;
import org.example.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.example.Constants.GlobalPath.PATH_TO_FILES;

@Service
public class SupplierServiceImpl implements SupplierService {
    private static final String SUPPLIER_PATH = "suppliers.json";
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(Gson gson, ValidationUtil validationUtil, SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSupplier() throws IOException {
        if(supplierRepository.count()>0){
            return;
        }
        String content = Files.readString(Path.of(PATH_TO_FILES + SUPPLIER_PATH));
        SupplierSeedDto[] supplierSeedDtos = gson.fromJson(content, SupplierSeedDto[].class);
        Arrays.stream(supplierSeedDtos)
                .filter(validationUtil::isValid)
                .map(supplierSeedDto -> modelMapper.map(supplierSeedDto, Supplier.class))
                .forEach(supplierRepository::save);

    }

    @Override
    public Supplier getRandomSupplier() {
        long randomId = ThreadLocalRandom.current().nextLong(1,supplierRepository.count() + 1);
        return supplierRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<LocalSupplierDto> getAllSuppliersWhichDontImport() {
        return supplierRepository.findAllByImportedFalse()
                .stream().map(supplier ->{
                    LocalSupplierDto localSupplierDto = modelMapper.map(supplier, LocalSupplierDto.class);
                    localSupplierDto.setPartsCount(supplier.getParts().size());
                    return localSupplierDto;
                })
                .collect(Collectors.toList());

    }
}
