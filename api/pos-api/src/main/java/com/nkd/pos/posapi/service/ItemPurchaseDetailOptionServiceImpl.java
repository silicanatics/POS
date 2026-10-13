package com.nkd.pos.posapi.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.ItemPurchaseDetailOptionRepository;
import com.nkd.pos.posapi.entity.ItemPurchaseDetailOption;

@Service
public class ItemPurchaseDetailOptionServiceImpl implements ItemPurchaseDetailOptionService {

	private Logger logger = Logger.getLogger(getClass().getName());
	@Autowired
	private ItemPurchaseDetailOptionRepository itemPurchaseDetailOptionRepository;
	@Override
	public ItemPurchaseDetailOption save(ItemPurchaseDetailOption itemPurchaseDetailOption) {
		
		logger.info("save");
		return itemPurchaseDetailOptionRepository.save(itemPurchaseDetailOption);
	}
	@Override
	public void removeAll(List<ItemPurchaseDetailOption> itemPurchaseDetailOptionList) {
		
		itemPurchaseDetailOptionRepository.deleteAll(itemPurchaseDetailOptionList);
	}

}
