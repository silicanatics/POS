package com.nkd.pos.posapi.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.SerializationUtils;
import org.glassfish.jersey.internal.inject.Custom;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkd.pos.posapi.config.RabbitMQConfig;
import com.nkd.pos.posapi.dao.ContactTypeRepository;
import com.nkd.pos.posapi.dao.EntityAddressRepository;
import com.nkd.pos.posapi.dao.EntityContactRepository;
import com.nkd.pos.posapi.dao.EntityTypeRepository;
import com.nkd.pos.posapi.dao.EntitiyRepository;
import com.nkd.pos.posapi.entity.ContactType;
import com.nkd.pos.posapi.entity.Customer;
import com.nkd.pos.posapi.entity.CustomerDiscount;
import com.nkd.pos.posapi.entity.DBEntity;
import com.nkd.pos.posapi.entity.EntityAddress;
import com.nkd.pos.posapi.entity.EntityContact;
import com.nkd.pos.posapi.entity.EntityType;
import com.nkd.pos.posapi.entity.ItemOption;
import com.nkd.pos.posapi.entity.ItemPurchase;
import com.nkd.pos.posapi.entity.ItemPurchaseDetail;
import com.nkd.pos.posapi.entity.ItemPurchaseDetailOption;
import com.nkd.pos.posapi.entity.MenuItemOption;
import com.nkd.pos.posapi.entity.PurchaseInvoice;
import com.nkd.pos.posapi.entity.PurchasePayload;
import com.nkd.pos.posapi.entity.PurchaseState;
import com.nkd.pos.posapi.entity.PurchasesDiscount;
import com.nkd.pos.posapi.entity.Receiver;
import com.nkd.pos.posapi.entity.SelectedItemPurchaseDetail;
import com.nkd.pos.posapi.entity.StockItem;
import com.nkd.pos.posapi.entity.Usr;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	ItemPurchaseService itemPurchaseService;
	@Autowired
	ItemPurchaseDetailService itemPurchaseDetailService;
	@Autowired
	ItemPurchaseDetailOptionService itemPurchaseDetailOptionService;
	@Autowired
	CustomerService customerService;
	@Autowired
	UserService userService;
	@Autowired
	PurchaseInvoiceService purchaseInvoiceService;
	@Autowired
	EntitiyRepository entitiyRepository;
	@Autowired
	EntityTypeRepository entityTypeRepository;
	@Autowired
	EntityAddressRepository entitiyAddressRepository;
	@Autowired
	EntityContactRepository entityContactRepository;
	@Autowired
	ContactTypeRepository contactTypeRepository;
	@Autowired
	EntityManager entityManager;
	@Autowired
	CustomerDiscountService customerDiscountService;

	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;

	public PurchaseServiceImpl(RabbitTemplate rabbitTemplate, Receiver receiver) {
		super();
		this.rabbitTemplate = rabbitTemplate;
		this.receiver = receiver;
	}

	private PurchasePayload processPurchase(PurchasePayload purchasePayload) {

		logger.info("processPurchase");
		ItemPurchase itemPurchase = new ItemPurchase();
		// purchasePayload.getItemPurchase().getId()
		if (purchasePayload.getItemPurchase().getId() != 0) {
			itemPurchase.setId(purchasePayload.getItemPurchase().getId());
			removePurchaseDetails(purchasePayload);
		}
		LocalDateTime purchaseDate = null;
		// PurchaseInvoice newInvoice = generateNewInvoice();
		Customer customer = new Customer();
		if (purchasePayload.getItemPurchase().getCustomer() == null) {
			customer = customerService.getById(1);
		} else {
			Integer id = purchasePayload.getItemPurchase().getCustomer().getId();
			if (id.equals(null) || id.equals(0)) {
				customer = new Customer();

				DBEntity dbEntity = new DBEntity();
				dbEntity.setName(purchasePayload.getItemPurchase().getCustomer().getDbEntity().getName());
				EntityType entityType = entityTypeRepository.getById(4);
				dbEntity.setEntityType(entityType);
				dbEntity = entitiyRepository.save(dbEntity);
				customer.setDbEntity(dbEntity);
				customer.setCustomerId(dbEntity.getName());

				List<EntityAddress> entityAddressList = new ArrayList<EntityAddress>();
				EntityAddress entityAddress = new EntityAddress();
				entityAddress.setDbEntity(dbEntity);
				entityAddress.setPostalCode(0);
				entityAddress.setAddressText(
						purchasePayload.getItemPurchase().getCustomer().getCustomerAddresses().get(0).getAddressText());
				entityAddress = entitiyAddressRepository.save(entityAddress);
				entityAddressList.add(entityAddress);
				customer.setCustomerAddresses(entityAddressList);

				List<EntityContact> entityContactList = new ArrayList<EntityContact>();
				EntityContact entityContact = new EntityContact();
				ContactType contactType = contactTypeRepository.getById(1);
				entityContact.setContactType(contactType);
				entityContact.setDbEntity(dbEntity);
				entityContact.setContact(
						purchasePayload.getItemPurchase().getCustomer().getCustomerContacts().get(0).getContact());
				entityContactList.add(entityContact);
				customer.setCustomerContacts(entityContactList);
				entityContact = entityContactRepository.save(entityContact);

				customer = customerService.save(customer);
				purchasePayload.setCustomer(customer);
				insertPurchaseDiscount(customer);

			} else {
				customer = purchasePayload.getItemPurchase().getCustomer();
			}
		}

		purchasePayload.getItemPurchase().setCustomer(customer);
		Usr user = new Usr();
		if (purchasePayload.getItemPurchase().getUser() == null) {
			user = userService.getById(1);
		} else {
			user = purchasePayload.getItemPurchase().getUser();
		}
		purchaseDate = purchasePayload.getItemPurchase().getPurchaseDate();
		itemPurchase.setCustomer(customer);
		itemPurchase.setUser(user);
		itemPurchase.setAmount(purchasePayload.getItemPurchase().getAmount());
		itemPurchase.setPurchaseDate(purchaseDate);
		itemPurchase.setPurchaseInvoice(purchasePayload.getItemPurchase().getPurchaseInvoice());
		itemPurchase.setAmountTendered(purchasePayload.getItemPurchase().getAmountTendered());
		itemPurchase.setChange_(purchasePayload.getItemPurchase().getChange_());

		itemPurchase.setPurchaseState(purchasePayload.getItemPurchase().getPurchaseState());
		itemPurchase.setPurchaseType(purchasePayload.getItemPurchase().getPurchaseType());

		itemPurchase.setPaymentType(purchasePayload.getItemPurchase().getPaymentType());

		itemPurchase = itemPurchaseService.save(itemPurchase);
		purchasePayload.setItemPurchase(itemPurchase);
		purchasePayload = updatePurchaseDiscount(purchasePayload);
		return purchasePayload;

	}

	private void insertPurchaseDiscount(Customer customer) {
		try {
		LocalDateTime tdNow = LocalDateTime.now();
		Date dateToday = java.sql.Timestamp.valueOf(tdNow);
		CustomerDiscount customerDiscount = new CustomerDiscount();
		customerDiscount.setCustomer(customer);
		customerDiscount.setAccumulatedAmount(0);
		customerDiscount.setDiscountPercent(10);
		customerDiscount.setThreasholdAmount(1000);
		customerDiscount.setDiscount(-1);
		customerDiscount.setEndDate(null);
		customerDiscount.setStartDate(dateToday);
		customerDiscountService.save(customerDiscount);
		} catch (Exception ex) {

		}
	}

	private PurchasePayload updatePurchaseDiscount(PurchasePayload purchasePayload) {

		try {
			Customer customer = purchasePayload.getCustomer();
			LocalDateTime now = LocalDateTime.now();
			Date dateToday = java.sql.Timestamp.valueOf(now);

			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
			String todayNow = now.format(format);

			String qlString = "select cd from CustomerDiscount cd"
					+ " where ((cd.startDate = null) or (cd.startDate <= '" + todayNow + "'))"
					+ " and ((cd.endDate = null) or (cd.endDate > '" + todayNow + "'))" + " and cd.customer.id = "
					+ customer.getId();
			List<CustomerDiscount> existingCustomerDiscountList = null;
			CustomerDiscount customerDiscount = null;
			try {
			 existingCustomerDiscountList = entityManager.createQuery(qlString, CustomerDiscount.class).getResultList();
			 customerDiscount = existingCustomerDiscountList.get(0);
			} catch(Exception ex) {
				
			}

			
			float accumulatedAmount = purchasePayload.getItemPurchase().getAmount();
			accumulatedAmount += customerDiscount.getAccumulatedAmount();

			LocalDateTime tdNow = LocalDateTime.now();

			customerDiscount.setCustomer(customer);
			customerDiscount.setAccumulatedAmount(accumulatedAmount);
			customerDiscount.setPurchases(customerDiscount.getPurchases() + 1);

			customerDiscount = customerDiscountService.save(customerDiscount);

            if(customerDiscount.getAccumulatedAmount() >= customerDiscount.getThreasholdAmount()) {
            	
            	customerDiscount.setEndDate(dateToday);
            	customerDiscount.setDiscount(1);
            	customerDiscount.setItemPurchase(purchasePayload.getItemPurchase());
            	customerDiscountService.save(customerDiscount);
            	
            	CustomerDiscount newCustomerDiscount = new CustomerDiscount();
            	newCustomerDiscount.setCustomer(customer);
            	newCustomerDiscount.setAccumulatedAmount(0);
            	newCustomerDiscount.setDiscountPercent(10);
            	newCustomerDiscount.setThreasholdAmount(1000);
            	newCustomerDiscount.setDiscount(-1);
            	newCustomerDiscount.setEndDate(null);
            	newCustomerDiscount.setStartDate(dateToday);
        		customerDiscountService.save(newCustomerDiscount);
        		
        		ItemPurchase  itemPurchase = purchasePayload.getItemPurchase();
        		itemPurchase.setDiscount(itemPurchase.getAmount()*0.1f);
        		itemPurchase = itemPurchaseService.save(itemPurchase);
        		purchasePayload.setItemPurchase(itemPurchase);
            	
            }
		} catch (Exception ex) {

		}
		return purchasePayload;
	}

	private void removePurchaseDetails(PurchasePayload purchasePayload) {

		ItemPurchase itemPurchase = purchasePayload.getItemPurchase();
		if (itemPurchase.getId() != 0) {
			String qlString = "select pd from ItemPurchaseDetail pd inner join ItemPurchase ip "
					+ " on pd.itemPurchase.id = ip.id and ip.id = " + itemPurchase.getId();
			List<ItemPurchaseDetail> existingItemPurchaseDetailList = entityManager
					.createQuery(qlString, ItemPurchaseDetail.class).getResultList();

			qlString = "select pdo from ItemPurchaseDetailOption pdo inner join ItemPurchaseDetail pd "
					+ " on pdo.itemPurchaseDetail.id = pd.id" + " inner join ItemPurchase ip "
					+ " on pd.itemPurchase.id = ip.id and ip.id = " + itemPurchase.getId();
			List<ItemPurchaseDetailOption> existingItemPurchaseDetailOptionList = entityManager
					.createQuery(qlString, ItemPurchaseDetailOption.class).getResultList();

			itemPurchaseDetailOptionService.removeAll(existingItemPurchaseDetailOptionList);
			itemPurchaseDetailService.removeAll(existingItemPurchaseDetailList);

			itemPurchaseService.delete(itemPurchase);

//			for (SelectedItemPurchaseDetail sipd : purchasePayload.getSelectedItemPurchaseDetails()) {
//				sipd.setId(0);
//				for (MenuItemOption mio : sipd.getMenuItemOptions()) {
//					mio.setId(0);
//				}
//			}

		}

	}

	private List<ItemPurchaseDetail> saveItemPurchaseDetails(PurchasePayload purchasePayload) {
		logger.info("saveItemPurchaseDetails");
		List<ItemPurchaseDetail> retList = new ArrayList<ItemPurchaseDetail>();
		SelectedItemPurchaseDetail selectedItemPurchaseDetail = null;

		Iterator<SelectedItemPurchaseDetail> itemPurchaseDetailIter = purchasePayload.getSelectedItemPurchaseDetails()
				.iterator();
		while (itemPurchaseDetailIter.hasNext()) {
			selectedItemPurchaseDetail = itemPurchaseDetailIter.next();

			// selectedItemPurchaseDetail.getMenuItem().getItems()

			// for (int i = 0; i < selectedItemPurchaseDetail.getMenuItem().getItems(); i++)
			// {

			ItemPurchaseDetail itemPurchaseDetail = new ItemPurchaseDetail();
			itemPurchaseDetail.setItemPurchase(purchasePayload.getItemPurchase());
			itemPurchaseDetail.setMenuItem(selectedItemPurchaseDetail.getMenuItem());
			itemPurchaseDetail.setItems(selectedItemPurchaseDetail.getMenuItem().getItems());
			itemPurchaseDetail.setPrice(selectedItemPurchaseDetail.getPrice());
			itemPurchaseDetail = itemPurchaseDetailService.save(itemPurchaseDetail);

			Iterator<MenuItemOption> menuItemOptionsIter = selectedItemPurchaseDetail.getMenuItemOptions().iterator();
			while (menuItemOptionsIter.hasNext()) {
				MenuItemOption menuItemOption = menuItemOptionsIter.next();
				ItemOption itemOption = menuItemOption.getItemOption();
				// if ((itemOption.isSelected() && !menuItemOption.isDefaultOption()) ||
				// (!itemOption.isSelected() && menuItemOption.isDefaultOption())) {
				ItemPurchaseDetailOption itemPurchaseDetailOption = new ItemPurchaseDetailOption();
				itemPurchaseDetailOption.setItemPurchaseDetail(itemPurchaseDetail);
				itemPurchaseDetailOption.setItemOption(itemOption);
				itemPurchaseDetailOption.setMenuItemOption(menuItemOption);
				itemPurchaseDetailOption.setSelected(itemOption.isSelected());
				itemPurchaseDetailOptionService.save(itemPurchaseDetailOption);
				// }
			}

			retList.add(itemPurchaseDetail);
			// }

		}

		return retList;

	}

	private PurchaseInvoice generateNewInvoice() {

		PurchaseInvoice purchaseInvoice = purchaseInvoiceService.getMaxInvoice();
		int purchaseNumber = purchaseInvoice.getPurchaseNumber();

		PurchaseInvoice newPurchaseInvoice = new PurchaseInvoice();
		newPurchaseInvoice.setInvoiceDate(java.sql.Date.valueOf(LocalDate.now()));
		newPurchaseInvoice.setInvoiceDateTime(java.sql.Timestamp.valueOf(LocalDateTime.now()));
		if (newPurchaseInvoice.getInvoiceDate().equals(purchaseInvoice.getInvoiceDate())) {
			newPurchaseInvoice.setPurchaseNumber(++purchaseNumber);
		} else {
			newPurchaseInvoice.setPurchaseNumber(1);
		}

		return purchaseInvoiceService.savePurchaseInvoice(newPurchaseInvoice);
	}

	@Override
	public PurchasePayload savePurchase(PurchasePayload purchasePayload) {
		logger.info("savePurchase");

		purchasePayload = processPurchase(purchasePayload);

		List<ItemPurchaseDetail> itemPurchaseDetailList = saveItemPurchaseDetails(purchasePayload);

		for (ItemPurchaseDetail ipd : itemPurchaseDetailList) {

			ipd.getItemPurchase().setUser(null);// Damn!!!!!!
		}
		byte[] data = SerializationUtils.serialize((Serializable) itemPurchaseDetailList);

		rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "foo.bar.baz", data);
		try {
			receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return purchasePayload;
	}

	@Override
	public PurchasePayload completePurchase(PurchasePayload purchasePayload) {

		PurchaseState purchaseState = new PurchaseState();
		purchaseState.setId(2);
		purchasePayload.getItemPurchase().setPurchaseState(purchaseState);
		ItemPurchase itemPurchase = purchasePayload.getItemPurchase();

		itemPurchase = itemPurchaseService.save(itemPurchase);
		purchasePayload.setItemPurchase(itemPurchase);

		return purchasePayload;
	}

}
