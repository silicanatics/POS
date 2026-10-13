package com.nkd.pos.posapi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.dao.CustomerRepository;
import com.nkd.pos.posapi.dao.DeliveryChargeRepository;
import com.nkd.pos.posapi.dao.EntityAddressRepository;
import com.nkd.pos.posapi.dao.EntityContactRepository;
import com.nkd.pos.posapi.dao.ItemPurchaseRepository;
import com.nkd.pos.posapi.dao.PaymentTypeRepository;
import com.nkd.pos.posapi.dao.PurchaseStateRepository;
import com.nkd.pos.posapi.dao.PurchaseTypeRepository;
import com.nkd.pos.posapi.entity.Customer;
import com.nkd.pos.posapi.entity.DeliveryCharge;
import com.nkd.pos.posapi.entity.EntityAddress;
import com.nkd.pos.posapi.entity.EntityContact;
import com.nkd.pos.posapi.entity.ItemPurchase;
import com.nkd.pos.posapi.entity.ItemPurchaseDetail;
import com.nkd.pos.posapi.entity.ItemPurchaseDetailOption;
import com.nkd.pos.posapi.entity.MenuItem;
import com.nkd.pos.posapi.entity.MenuItemOption;
import com.nkd.pos.posapi.entity.PaymentType;
import com.nkd.pos.posapi.entity.PurchaseDetail;
import com.nkd.pos.posapi.entity.PurchaseState;
import com.nkd.pos.posapi.entity.PurchaseType;

@Service
public class ItemPurchaseServiceImpl implements ItemPurchaseService {

	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	PaymentTypeRepository paymentTypeRepository;
	@Autowired
	ItemPurchaseRepository itemPurchaseRepository;
	@Autowired
	PurchaseStateRepository purchaseStateRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerService customerService;
	@Autowired
	EntityAddressRepository entityAddressRepository;
	@Autowired
	EntityContactRepository entityContactRepository;
	@Autowired
	DeliveryChargeRepository deliveryChargeRepository;
	@Autowired
	EntityManager entityManager;
	@Autowired
	PurchaseTypeRepository purchaseTypeRepository;

	@Override
	public ItemPurchase save(ItemPurchase itemPurchase) {

		logger.info("save");
		return itemPurchaseRepository.save(itemPurchase);
	}

	@Override
	public List<PurchaseDetail> findPurchases(int stateId) {
		logger.info("findIncompletePurchases");
		List<PurchaseDetail> purchaseDetailList = null;
		PurchaseState purchaseState = null;
		List<ItemPurchase> itemPurchaseList = null;
		purchaseState = purchaseStateRepository.getById(stateId);
		if (stateId == 0) {
			itemPurchaseList = itemPurchaseRepository.findAll();
		} else {
			itemPurchaseList = itemPurchaseRepository.getByPurchaseState(purchaseState);
		}

		itemPurchaseList = updatePurchases(itemPurchaseList);
		purchaseDetailList = constructItemPurchaseDetailList(itemPurchaseList);
		return purchaseDetailList;
	}

	private List<PurchaseDetail> constructItemPurchaseDetailList(List<ItemPurchase> itemPurchaseList) {

		//To change tommorow!!
		List<PurchaseDetail> purchaseDetailList = null;
		purchaseDetailList = new ArrayList<PurchaseDetail>();
		

		List<ItemPurchaseDetailOption> itemPurchaseDetailOptionListTMP = null;
		for (ItemPurchase itemPurchase : itemPurchaseList) {
			
			String qlString = "select pd from ItemPurchaseDetail pd inner join ItemPurchase ip "
					+ " on pd.itemPurchase.id = ip.id and ip.id = " + itemPurchase.getId();
			List<ItemPurchaseDetail> existingItemPurchaseDetailList = entityManager
					.createQuery(qlString, ItemPurchaseDetail.class).getResultList();

			qlString = "select pdo from ItemPurchaseDetailOption pdo inner join ItemPurchaseDetail pd "
					+ " on pdo.itemPurchaseDetail.id = pd.id" + " inner join ItemPurchase ip "
					+ " on pd.itemPurchase.id = ip.id and ip.id = " + itemPurchase.getId();
			
			List<ItemPurchaseDetailOption> existingItemPurchaseDetailOptionList = entityManager
					.createQuery(qlString, ItemPurchaseDetailOption.class).getResultList();

			PurchaseDetail purchaseDetail = new PurchaseDetail();
			purchaseDetail.setItemPurchase(itemPurchase);

			itemPurchaseDetailOptionListTMP = new ArrayList<ItemPurchaseDetailOption>();
			List<ItemPurchaseDetail> itemPurchaseDetailList = existingItemPurchaseDetailList.stream()
					.filter(pd -> pd.getItemPurchase().getId() == itemPurchase.getId()).collect(Collectors.toList());

			List<ItemPurchaseDetailOption> itemPurchaseDetailOptionList = existingItemPurchaseDetailOptionList.stream()
					.filter(pdo -> pdo.getItemPurchaseDetail().getItemPurchase().getId() == itemPurchase.getId())
					.collect(Collectors.toList());

			for (ItemPurchaseDetail id : itemPurchaseDetailList) {
				for (ItemPurchaseDetailOption ipo : itemPurchaseDetailOptionList) {
					if (id.getId() == ipo.getItemPurchaseDetail().getId()
							&& itemPurchase.getId() == id.getItemPurchase().getId()) {
						//MenuItemOption mio = ipo.getMenuItemOption();
						MenuItemOption mio = new MenuItemOption();
						mio.setDefaultOption(ipo.getMenuItemOption().isDefaultOption());
						mio.setId(ipo.getMenuItemOption().getId());
						mio.setItemOption(ipo.getMenuItemOption().getItemOption());
						mio.setMenuItem(ipo.getMenuItemOption().getMenuItem());
						ipo.setItemOption(mio.getItemOption());
						ipo.setMenuItemOption(mio);
						itemPurchaseDetailOptionListTMP.add(ipo);
					}
				}
			}

			purchaseDetail.setItemPurchaseDetailList(itemPurchaseDetailList);
			purchaseDetail.setItemPurchaseDetailOptionList(itemPurchaseDetailOptionListTMP);
			purchaseDetailList.add(purchaseDetail);
		}

		return purchaseDetailList;

	}

	private List<ItemPurchase> updatePurchases(List<ItemPurchase> itemPurchaseList) {

		
		List<Integer> DBEntityIds = new ArrayList<Integer>();
		Iterator<ItemPurchase> itemPurchaseListIter = itemPurchaseList.iterator();
//		while (itemPurchaseListIter.hasNext()) {
//			ItemPurchase itemPurchase = itemPurchaseListIter.next();
//			DBEntityIds.add(itemPurchase.getCustomer().getDbEntity().getId());
//		}

		List<EntityAddress> entitiyAddressList = entityAddressRepository.findAll();
		List<EntityContact> entityContactList = entityContactRepository.findAll();

		ItemPurchase itemPurchase = null;
		while (itemPurchaseListIter.hasNext()) {
			itemPurchase = itemPurchaseListIter.next();
			Customer customer = itemPurchase.getCustomer();
			List<EntityAddress> entityAddressListTmp = entitiyAddressList.stream()
					.filter(ea -> ea.getDbEntity().getId() == customer.getDbEntity().getId())
					.collect(Collectors.toList());
			if (!entityAddressListTmp.isEmpty()) {
				customer.setCustomerAddresses(entityAddressListTmp);
			}

			List<EntityContact> entityContactListTmp = entityContactList.stream()
					.filter(ec -> ec.getDbEntity().getId() == customer.getDbEntity().getId())
					.collect(Collectors.toList());
			if (!entityContactListTmp.isEmpty()) {
				customer.setCustomerContacts(entityContactListTmp);
			}
		}

		return itemPurchaseList;
	}

	@Override
	public void delete(ItemPurchase itemPurchase) {
		itemPurchaseRepository.delete(itemPurchase);
		
	}

	@Override
	public List<PurchaseDetail> findPurchasesByType(int purchaseTypeId, String strDate) {

		logger.info("findPurchasesByType");
		List<PurchaseDetail> purchaseDetailList = null;
		try {
			
			//date = "2022 Mar 04";
		
		LocalDate localDate = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("yyyy/MM/dd")); 
		PurchaseState purchaseState = null;
		List<ItemPurchase> itemPurchaseList = null;
		
		String date = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		String qlString = "";
		if(purchaseTypeId < 1) {
			qlString = "select ip from ItemPurchase ip "
					+ " inner join PurchaseInvoice pi "
					+ " on ip.purchaseInvoice.invoiceNumber = pi.invoiceNumber "
					+ " where pi.invoiceDate = '" + date +"'";
			
		} else {
			qlString = "select ip from ItemPurchase ip "
					+ " inner join PurchaseInvoice pi "
					+ " on ip.purchaseInvoice.invoiceNumber = pi.invoiceNumber "
					+ " where ip.purchaseType.id =  " + purchaseTypeId  
					+ " and pi.invoiceDate = '" + date +"'";
		}
			
		List<ItemPurchase> itemPurchases = entityManager
				.createQuery(qlString, ItemPurchase.class).getResultList();
		
		
		itemPurchaseList = updatePurchases(itemPurchases);
		purchaseDetailList = constructItemPurchaseDetailList(itemPurchaseList);
	
		}
		catch(Exception ex) {
			logger.info("findPurchasesByType Exception " + ex.getMessage() );
		}
		return purchaseDetailList;
		
	}

	@Override
	public List<PurchaseDetail> findPurchasesByDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DeliveryCharge> getDeliveryChargeList() {
	
		return deliveryChargeRepository.findAll();
	}

	@Override
	public List<PaymentType> getPaymentTypeList() {
		// TODO Auto-generated method stub
		return paymentTypeRepository.findAll();
	}

	@Override
	public List<PurchaseType> getPurchaseTypeList() {
		// TODO Auto-generated method stub
		return purchaseTypeRepository.findAll();
	}


}
