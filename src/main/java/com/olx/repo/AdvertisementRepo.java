package com.olx.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.olx.entity.AdvertisementEntity;


public interface AdvertisementRepo extends JpaRepository<AdvertisementEntity, Integer> {

}
