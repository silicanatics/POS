import { Component, OnInit, Output, EventEmitter, ViewChild, ElementRef, Input, SimpleChanges } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Customer } from 'src/app/entities/Customer';
import { CustomerService } from 'src/app/services/customer/customer.service';
import * as $ from 'jquery'
import { SelectedCustomer } from 'src/app/entities/SelectedCustomer';
import { DBEntity } from 'src/app/entities/DBEntity';
import { EntityContact } from 'src/app/entities/EntityContact';
import { EntityAddress } from 'src/app/entities/EntityAddress';
import { PurchaseType } from 'src/app/entities/PurchaseType';
import { HttpClient } from '@angular/common/http';
import { CustomerDiscount } from 'src/app/entities/CustomerDiscount';
import { Application } from 'src/app/entities/Application';
//C:\Users\Nkateko.Mthembi\POS\sourcecode\NEW\UI\POS-UI\node_modules\@types

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {
  customers: Customer[] = [];
  filteredCustomers: Customer[] = [];
  customerForm!: UntypedFormGroup;
  submitted: boolean = false;
  selectedRow!: number;
  collectMode: boolean = false;
  deliverMode: boolean = false;
  walkInMode: boolean = false;
  lat: number = 51.678418;
  lng: number = 7.809007;
  nameFilter!: string;
  contactFilter!: string;
  purchaseTypeList: PurchaseType[]=[];
  addressFilter!: string;
  @Output() newItemEvent = new EventEmitter<SelectedCustomer>();
  loading:boolean=false;
  clientsLoading:boolean=false;
  apiLoaded:boolean=false;
  customerDiscountList:CustomerDiscount[]=[];
  customerDiscount!:CustomerDiscount;
  application!:Application;
  mode1Text:string="Deliver";
  mode2Text:string="Collect";
  mode3Text:string="Walk In";
  @ViewChild('mode3') mode3!: ElementRef ;
  @Input() mnuTrigger:string | undefined;


  constructor(private customerService: CustomerService, private formBuilder: UntypedFormBuilder,
    httpClient: HttpClient) { 
      httpClient.jsonp('https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE', 'callback')
      .pipe().subscribe(t =>{
        this.apiLoaded = true;
      });      
    }

  ngOnInit(): void {
    let app: string = localStorage.getItem('application')!;
    this.application = JSON.parse(app);
    if(this.application.id == 1){
      this.mode1Text="Deliver";
      this.mode2Text="Collect";
      this.mode3Text="Walk In";
    } else if(this.application.id == 2){
      this.mode1Text="Deliver";
      this.mode2Text="Collect";
      this.mode3Text="Walk In";
    } else if(this.application.id == 3){
      this.mode1Text="Suspend";
      this.mode2Text="Collect";
      this.mode3Text="Walk In";
     
     // this.optionsClick("option3");
    }

    this.getCustomerDiscounts();
    this.getCustomers();
    this.getPurchaseTypeList();
    this.buildForm();
  }

  ngOnChanges(changes: SimpleChanges) {
    if(changes.mnuTrigger.currentValue != undefined){
      if(this.mnuTrigger=="entering"){
      this.mode3.nativeElement.click();
      this.getCustomerDiscounts();
      } else if(this.mnuTrigger=="reset"){
        this.ngOnInit();
      } 
    }
  }
  async getPurchaseTypes(){
    let data = await this.customerService.getCustomers("/customer/getCustomerDiscounts");
    this.customerDiscountList = data;
  }

  async getCustomerDiscounts(){
    let data = await this.customerService.getCustomers("/customer/getCustomerDiscounts");
    this.customerDiscountList = data;
  }

  onNameInputKey(event: any) {
    this.nameFilter = event.target.value;
    this.doCustomerFilter();
  }

  onContactInputKey(event: any) {
    this.contactFilter = event.target.value;
    this.doCustomerFilter();
  }

  onAddressInputKey(event: any) {
    this.addressFilter = event.target.value;
    this.doCustomerFilter();
  }

  doCustomerFilter() {
    if (this.nameFilter == undefined) {
      this.nameFilter = "";
    }
    if (this.contactFilter == undefined) {
      this.contactFilter = "";
    }
    if (this.addressFilter == undefined) {
      this.addressFilter = "";
    }

   // this.customerForm.reset();
    this.filteredCustomers = this.customers.
      filter(e => e.dbEntity.name.toLowerCase().match(this.nameFilter.toLowerCase()));

    this.filteredCustomers = this.filteredCustomers.
      filter(e => e.customerContacts[0].contact.match(this.contactFilter));

    this.filteredCustomers = this.filteredCustomers.
      filter(e => e.customerAddresses[0].addressText.toLowerCase().match(this.addressFilter.toLowerCase()));
  }

  updatePurchaseMode() {
    /*
            this.deliverMode = true;
        this.collectMode = false;
        this.walkInMode = false;
    */
    let selectedCustomer = new SelectedCustomer()
    let purchaseType = new PurchaseType();
    if(this.deliverMode){
      purchaseType = this.purchaseTypeList.filter(pt => pt.id == 3)[0];
    }else if (this.collectMode){
      purchaseType = this.purchaseTypeList.filter(pt => pt.id == 2)[0];
    }else if (this.walkInMode){
      purchaseType = this.purchaseTypeList.filter(pt => pt.id == 1)[0];
    }
    selectedCustomer.purchaseType = purchaseType;

    let customer = new Customer();
    customer.customerAddresses = new Array<EntityAddress>();
    customer.customerContacts = new Array<EntityContact>()
    customer.id = this.f["customerId"].value;
    let dbEntity = new DBEntity();
    dbEntity.name = this.f["customerName"].value;
    customer.dbEntity = dbEntity;
    let customerAddress = new EntityAddress();
    customerAddress.addressText =  this.f["customerAddress"].value;
    customer.customerAddresses.push(customerAddress);
    let customerContact = new EntityContact();
    customerContact.contact = this.f["customerContact"].value;
    customer.customerContacts.push(customerContact);
    selectedCustomer.customer = customer;
    for(var cd of this.customerDiscountList){
      if(cd.customer.id == selectedCustomer.customer.id){
        this.customerDiscount = cd;
        selectedCustomer.customerDiscount = cd;
        break;
      }
    }

    this.newItemEvent.emit(selectedCustomer);    
  }

  Reset(){
    this.customerForm.reset();
    let selectedCustomer = new SelectedCustomer()
    let customer = new Customer();
    let dbEntity = new DBEntity();
    dbEntity.name = 'WalkIn ';
    customer.dbEntity = dbEntity;
    selectedCustomer.customer = customer;
    this.newItemEvent.emit(selectedCustomer);
  }

  buildForm() {
    this.customerForm = this.formBuilder.group({
      customerId: ['', Validators.required],
      customerName: ['', Validators.required],
      customerContact: ['', Validators.required],
      customerAddress: ['', Validators.required]
    });
  }
  get f() { return this.customerForm.controls; }

  tableRowSelected(cust: any) {
    console.log(cust);
    this.selectedRow = cust.id;
    this.f["customerId"].setValue(cust.id);
    this.f["customerName"].setValue(cust.dbEntity.name);
    this.f["customerContact"].setValue(cust.customerContacts[0].contact);
    this.f["customerAddress"].setValue(cust.customerAddresses[0].addressText);
    this.updatePurchaseMode(); //2022/10/28 button press hack!!!!
  }

  optionsClick(event: any) {
    switch (event.target.value) {
      case "option1":
         this.deliverMode = true;
        this.collectMode = false;
        this.walkInMode = false;
        break;
      case "option2":
        this.collectMode = true;
        this.deliverMode = false;
        this.walkInMode = false;
        break;
      case "option3":
        this.walkInMode = true;
        this.collectMode = false;
        this.deliverMode = false;
        break;

      default:
        break;
    }
  }


  private async getCustomers() {
    this.clientsLoading = true;
    this.loading = true;
    let data = await this.customerService.getCustomers("/customer/getCustomers");
    this.loading = false;
    this.customers = data;
    this.clientsLoading = false;
    this.filteredCustomers = this.customers;    
  }

  private async getPurchaseTypeList() {
    
    let data = await this.customerService.getCustomers("/purchase/getPurchaseTypes");
    this.purchaseTypeList =  data;
  }


  onReset() {

  }

}
