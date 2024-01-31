package org.example.service.Impl;

import com.google.gson.Gson;
import org.example.model.dto.PartsSeedDto;
import org.example.model.entity.Part;
import org.example.repository.PartRepository;
import org.example.service.PartService;
import org.example.service.SupplierService;
import org.example.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.example.Constants.GlobalPath.PATH_TO_FILES;

@Service
public class PartServiceImpl implements PartService {
    private static final String PARTS_FILE_NAME = "parts.json";

    private final PartRepository partRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final SupplierService supplierService;

    public PartServiceImpl(PartRepository partRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.supplierService = supplierService;
    }

    @Override
    public void seedPart() throws IOException {
        if (this.partRepository.count() > 0) {
            return;
        }

        PartsSeedDto[] partSeedDtos = gson.fromJson(Files.readString(Path.of(PATH_TO_FILES + PARTS_FILE_NAME)),
                PartsSeedDto[].class);
        Arrays.stream(partSeedDtos)
                .filter(validationUtil::isValid)
                .map(partSeedDto -> {
                    Part part = modelMapper.map(partSeedDto, Part.class);
                    part.setSupplier(this.supplierService.getRandomSupplier());
                    return part;
                })
                .forEach(this.partRepository::save);

    }

    @Override
    public Set<Part> getRandomPart() {
        int numberOfPartsToGet = ThreadLocalRandom.current().nextInt(3, 6);
        long count = this.partRepository.count();
        Set<Part> parts = new HashSet<>();

        for (int i = 0; i < numberOfPartsToGet; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, count + 1);
            parts.add(this.partRepository.findById(randomId).orElse(null));
        }

        return parts;
    }
}
