package com.nkd.pos.posapi.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.ItemPurchaseDetailRepository;
import com.nkd.pos.posapi.entity.ItemPurchaseDetail;

@Service
public class ItemPurchaseDetailServiceImpl implements ItemPurchaseDetailService {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	ItemPurchaseDetailRepository itemPurchaseDetailRepository;
	
	@Override
	public ItemPurchaseDetail save(ItemPurchaseDetail itemPurchaseDetail) {

		logger.info("save");
		return itemPurchaseDetailRepository.save(itemPurchaseDetail);
	}

	@Override
	public void removeAll(List<ItemPurchaseDetail> itemPurchaseDetailList) {
		
		itemPurchaseDetailRepository.deleteAll(itemPurchaseDetailList);
	}

	@Override
	public List<ItemPurchaseDetail> findAll() {
		// TODO Auto-generated method stub
		return itemPurchaseDetailRepository.findAll();
	}

}
