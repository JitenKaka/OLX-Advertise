package com.olx.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.olx.dto.Advertisements;
import com.olx.entity.AdvertisementEntity;
import com.olx.exception.InvalidAdvertiseIdException;
import com.olx.exception.InvalidUserException;
import com.olx.repo.AdvertisementRepo;

@Service
public class AdvertiseDataServiceImpl implements AdvertiseDataService {

	@Autowired
	MasterDataDelegate masterDataDelegate;
	@Autowired
	private AdvertisementRepo advertisementRepo; 
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	LoginDelegate loginDataDelegate;
	
	
	
	
private AdvertisementEntity getAdvertisementsEntityFromDTO(Advertisements advertisements) {
	AdvertisementEntity advertisementEntity=this.modelMapper.map(advertisements, AdvertisementEntity.class);
		return advertisementEntity;
	}
	
private Advertisements getAdvertisementsDTOFromEntity(AdvertisementEntity advertisementEntity) {
	Advertisements advertisementsDTO=this.modelMapper.map(advertisementEntity, Advertisements.class);
	return advertisementsDTO;
	}
	
	
private List<Advertisements> getAdvertisementsDTOListFromEntityList(List<AdvertisementEntity> advertisementsEntityList){
	List<Advertisements> advertisementsDTOList=new ArrayList<Advertisements>();
	for(AdvertisementEntity stockEntity : advertisementsEntityList){
		advertisementsDTOList.add(getAdvertisementsDTOFromEntity(stockEntity));
	}
   return advertisementsDTOList;
}
	
	
	
	@ExceptionHandler(value= {InvalidAdvertiseIdException.class})
	public ResponseEntity<String> handleInvalidStockIdError(RuntimeException exception,
			WebRequest request){
		
		return new ResponseEntity<String>("Local Handler Invalid InvalidAdvertise Id ",HttpStatus.BAD_REQUEST);
	}


	@Override
	public Advertisements createNewAdvertise(Advertisements advertisements, String authToken) {
		
		if(loginDataDelegate.isTokenvalidate(authToken)) {
			throw new InvalidUserException(authToken);
		}
		
		AdvertisementEntity stockEntity=getAdvertisementsEntityFromDTO(advertisements);
				stockEntity=advertisementRepo.save(stockEntity);
				return getAdvertisementsDTOFromEntity(stockEntity);
	}

	@Override
	public Advertisements updateAdvertiseId(int advertiseId, Advertisements newAdvertisement, String authToken) {
		if(loginDataDelegate.isTokenvalidate(authToken)) {
			throw new InvalidUserException(authToken);
		}
		
		Optional<AdvertisementEntity> opstockentity=advertisementRepo.findById(advertiseId);
        if(opstockentity.isPresent()){
        	AdvertisementEntity advertisementEntity=opstockentity.get();
        	advertisementEntity.setTitle(newAdvertisement.getTitle());
        	advertisementEntity.setPrice(newAdvertisement.getPrice());
        	advertisementEntity.setCategoryId(newAdvertisement.getCategoryId());
        	advertisementEntity.setDescription(newAdvertisement.getDescription());
        	advertisementEntity.setStatus(newAdvertisement.getStatus());
        	advertisementEntity.setCreated_date(newAdvertisement.getCreated_date());
        	advertisementEntity.setModified_date(newAdvertisement.getModified_date());
        	advertisementEntity.setActive(newAdvertisement.getActive());
        	advertisementEntity.setUsername(newAdvertisement.getUsername());
        	advertisementRepo.save(advertisementEntity);
	}
	return null;
	}

	@Override
	public List<Advertisements> getAllUserAdvertise(String authToken) {
		if(loginDataDelegate.isTokenvalidate(authToken)) {
			throw new InvalidUserException(authToken);
		}
		List<AdvertisementEntity> stockEntityList=advertisementRepo.findAll();
		return getAdvertisementsDTOListFromEntityList(stockEntityList);
	}

	@Override
	public Advertisements getUserAdvertiseId(int advertiseId, String authToken) {
		if(loginDataDelegate.isTokenvalidate(authToken)) {
			throw new InvalidUserException(authToken);
		}
		Optional<AdvertisementEntity> opstockentity=advertisementRepo.findById(advertiseId);
		if(opstockentity.isPresent()){
			AdvertisementEntity advertisementEntity=opstockentity.get();
			return getAdvertisementsDTOFromEntity(advertisementEntity);
		}
		return null;
	}

	@Override
	public boolean deleteUserAdvertiseById(int advertiseId, String authToken) {
		if(loginDataDelegate.isTokenvalidate(authToken)) {
			throw new InvalidUserException(authToken);
		}
		advertisementRepo.deleteById(advertiseId);
		return true;
	}

	@Override
	public List<Advertisements> searchAdvertisesByFilterCriteria(String searchText, int categoryId, String postedBy,
			String dateCondition, LocalDate onDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Advertisements> searchAdvertisesBySearchText(String searchText) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Advertisements getadvertiseById(int advertiseId, String authToken) {
		
		if(loginDataDelegate.isTokenvalidate(authToken)) {
			throw new InvalidUserException(authToken);
		}
		System.out.println("print value : " +loginDataDelegate.isTokenvalidate(authToken));
		/*
		 * System.out.println("print value : " + Boolean
		 * store=loginDataDelegate.isTokenvalidate(authToken));
		 */		 
			 Optional<AdvertisementEntity> opstockentity=advertisementRepo.findById(advertiseId);
				if(opstockentity.isPresent()){
					AdvertisementEntity advertisementEntity=opstockentity.get();
					return getAdvertisementsDTOFromEntity(advertisementEntity);
				}
		 
		
		
		return null;
	}

	@Override
	public List<Advertisements> getAllAdvertises() {		 
		 List<AdvertisementEntity> stockEntityList=advertisementRepo.findAll();
		return getAdvertisementsDTOListFromEntityList(stockEntityList);
	}


}
