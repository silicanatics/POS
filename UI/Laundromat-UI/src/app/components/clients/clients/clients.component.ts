import { Component, OnInit, Output, EventEmitter, Input, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Customer } from 'src/app/entities/Customer';
import { CustomerService } from 'src/app/services/customer/customer.service';
import * as $ from 'jquery'
import { SelectedCustomer } from 'src/app/entities/SelectedCustomer';
import { DBEntity } from 'src/app/entities/DBEntity';
import { EntityContact } from 'src/app/entities/EntityContact';
import { EntityAddress } from 'src/app/entities/EntityAddress';
import { PurchaseType } from 'src/app/entities/PurchaseType';
import { HttpClient } from '@angular/common/http';
//C:\Users\Nkateko.Mthembi\POS\sourcecode\NEW\UI\POS-UI\node_modules\@types

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {
  customers: Customer[] = [];
  filteredCustomers: Customer[] = [];
  customerForm!: FormGroup;
  submitted: boolean = false;
  selectedRow!: number;
  collectMode: boolean = false;
  deliverMode: boolean = false;
  walkInMode: boolean = false;
  lat: number = 51.678418;
  lng: number = 7.809007;
  nameFilter!: string;
  contactFilter!: string;
  addressFilter!: string;
  @Input() addressReset:boolean | undefined;
  @Output() newItemEvent = new EventEmitter<SelectedCustomer>();
  loading:boolean=false;
  clientsLoading:boolean=false;
  apiLoaded:boolean=false;
  



  constructor(private customerService: CustomerService, private formBuilder: FormBuilder,
    httpClient: HttpClient) { 
      httpClient.jsonp('https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE', 'callback')
      .pipe().subscribe(t =>{
        this.apiLoaded = true;
      });      
    }

  ngOnInit(): void {
    this.getCustomers();
    this.buildForm();
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

  ngOnChanges(changes: SimpleChanges) {
    if(changes.addressReset != undefined){
      if(this.addressReset = true){
        this.getCustomers();
        this.buildForm();
      }
    }
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
      purchaseType.id = 3;
    }else if (this.collectMode){
      purchaseType.id = 2;
    }else if (this.walkInMode){
      purchaseType.id = 1;
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


  onReset() {

  }

}
