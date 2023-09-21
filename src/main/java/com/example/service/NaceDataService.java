package com.example.service;

import org.springframework.stereotype.Service;

import com.example.model.NaceData;
import com.example.repository.NaceDataRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class NaceDataService {

	private final NaceDataRepository naceDataRepository;

	public NaceDataService(NaceDataRepository naceDataRepository) {
		this.naceDataRepository = naceDataRepository;
	}

	public NaceData saveNaceData(NaceData naceData) {
		return naceDataRepository.save(naceData);
	}

	public NaceData getNaceData(Long orderId) {
		Optional<NaceData> optionalNaceData = naceDataRepository.findById(orderId);
		if (optionalNaceData.isPresent()) {
			return optionalNaceData.get();
		} else {
			throw new EntityNotFoundException("NACE data with code " + orderId + " not found");
		}
	}

	/*public List<NaceData> getAllNaceData() {
		return naceDataRepository.findAll();
	}*/
}
