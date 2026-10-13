package com.nkd.pos.posapi.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.SerializationUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nkd.pos.posapi.service.StockService;

@Component
public class Receiver {

	@Autowired
	StockService stockService;

	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(byte[] message) {

		try {
			List<ItemPurchaseDetail> objects = SerializationUtils.deserialize(message);

			Gson gson = new Gson();

			String jsonString = gson.toJson(objects);
			List<ItemPurchaseDetail> itemPurchaseDetailList = new ArrayList<ItemPurchaseDetail>();
			ItemPurchaseDetail[] serialDetailsRecords = gson.fromJson(jsonString, ItemPurchaseDetail[].class);
			for (ItemPurchaseDetail item : serialDetailsRecords) {
				item.toString();
				itemPurchaseDetailList.add(item);
			}

			stockService.updateStockInUse(itemPurchaseDetailList);
			System.out.println("Received <***************" + itemPurchaseDetailList + "***********************>");
			latch.countDown();
		} catch (Exception ex) {

		}
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}
