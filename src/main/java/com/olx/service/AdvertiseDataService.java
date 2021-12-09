package com.olx.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.olx.dto.Advertisements;

public interface AdvertiseDataService {
	
	public Advertisements createNewAdvertise(Advertisements advertisements,String authToken);
	public Advertisements updateAdvertiseId(int advertiseId, Advertisements newAdvertisement, String authToken);
	public List<Advertisements> getAllUserAdvertise(String authToken);
	public Advertisements getUserAdvertiseId(int advertiseId, String authToken);
	public boolean deleteUserAdvertiseById(int advertiseId, String authToken);
	public List<Advertisements> searchAdvertisesByFilterCriteria(String searchText, int categoryId, String postedBy,
			String dateCondition, LocalDate onDate);
	
	public List<Advertisements> searchAdvertisesBySearchText(String searchText);
	public Advertisements getadvertiseById(int advertiseId, String authToken);
	public List<Advertisements> getAllAdvertises();
	
	

}
