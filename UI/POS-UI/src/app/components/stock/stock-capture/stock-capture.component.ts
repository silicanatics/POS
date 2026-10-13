import { Component, OnInit } from '@angular/core';
import { FormGroup, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Customer } from 'src/app/entities/Customer';
import { StockItem } from 'src/app/entities/StockItem';
import { StockStorage } from 'src/app/entities/StockStorage';
import { MenuService } from 'src/app/services/menu/menu.service';
import { StockService } from 'src/app/services/stock/stock.service';


@Component({
  selector: 'app-stock-capture',
  templateUrl: './stock-capture.component.html',
  styleUrls: ['./stock-capture.component.css']
})
export class StockCaptureComponent implements OnInit {

  customerForm!: UntypedFormGroup;
  loading: boolean = false;
  nameFilter!: string;
  contactFilter!: string;
  addressFilter!: string;
  stockItems: StockItem[] = [];
  stockStorages: StockStorage[] = [];
  filteredStockStorages: StockStorage[] = [];
  selectedStockStorage !:StockStorage;
  selectedStockStorageRow!: number;
  uploadedFile:any;


  customers: Customer[] = [];
  filteredCustomers: Customer[] = [];

  constructor(private menuService: MenuService,
    private stockService: StockService,
    private formBuilder: UntypedFormBuilder,
    private modalService: NgbModal,
    private _router: Router) {
  }

  dashboard() {
    this._router.navigate(['dashboard'])
  }


  onTabChanged(ev: any) {
  }



  ngOnInit(): void {
    this.initialise();
  }

  async initialise() {

    this.buildForm();
    await this.getStockItems();
    await this.getStockStorage();
  }

  buildForm() {
    this.customerForm = this.formBuilder.group({
      customerId: ['', Validators.required],
      customerName: ['', Validators.required],
      customerContact: ['', Validators.required],
      startDate: ['', Validators.required],
      itemName: ['', Validators.required],
      dateCaptured: ['', Validators.required],
      endDate: ['', Validators.required],
      current: ['', Validators.required],
      initialQuantity: ['', Validators.required],
      price: ['', Validators.required],
      itemCostPrice: ['', Validators.required],
      itemResellPrice: ['', Validators.required],
      quantityLeft: ['', Validators.required],
      sequence: ['', Validators.required],
      soldQuantity: ['', Validators.required],
      profit: ['', Validators.required],



    });
  }
  get c() { return this.customerForm.controls; }


  stockStorageRowClick(stockStorage: StockStorage) {
    this.selectedStockStorageRow = stockStorage.id;
    this.selectedStockStorage = stockStorage;
    this.c['itemName'].setValue(stockStorage.stockItem.menuItem.caption);
    this.c['dateCaptured'].setValue(stockStorage.dateCaptured);
    this.c['endDate'].setValue(stockStorage.endDate);
    this.c['initialQuantity'].setValue(stockStorage.initialQuantity);
    this.c['current'].setValue(stockStorage.initialQuantity);
    this.c['price'].setValue(stockStorage.price);
    this.c['quantityLeft'].setValue(stockStorage.quantityLeft);
    this.c['sequence'].setValue(stockStorage.sequence);
    this.c['soldQuantity'].setValue(stockStorage.soldQuantity);
    this.c['startDate'].setValue(stockStorage.startDate);
    this.c['profit'].setValue(stockStorage.profit);
    this.c['itemCostPrice'].setValue(stockStorage.itemCostPrice);
    this.c['itemResellPrice'].setValue(stockStorage.itemResellPrice);

  }

  saveStockStorage(){
   this.loading = true;
   let stockStorage: StockStorage = new StockStorage();
   stockStorage.id = this.selectedStockStorage.id;

   this.selectedStockStorage.itemResellPrice = this.c['itemResellPrice'].value;
   this.selectedStockStorage.current = this.c['current'].value;
   this.selectedStockStorage.initialQuantity = this.c['initialQuantity'].value;
   this.selectedStockStorage.itemCostPrice = this.c['itemCostPrice'].value;
   this.selectedStockStorage.price = this.c['price'].value;
   this.selectedStockStorage.quantityLeft = this.c['quantityLeft'].value;
   this.selectedStockStorage.sequence = this.c['sequence'].value;
   this.selectedStockStorage.soldQuantity = this.c['soldQuantity'].value;
   this.selectedStockStorage.stockItem.menuItem.caption = this.c['itemName'].value;
  
   
   this.stockService.saveStockStorageItem("/stock/saveStockStorageItem",this.selectedStockStorage).subscribe(   
    si => {
      this.loading = false;
    }
  );

  }

  selectFile(ev:any){

    this.uploadedFile = ev;
  }

  updateAllStock() {
    this.loading = true;
    this.stockService.updateAllStock("/stock/updateAllStockItems").subscribe(
      si => {
        this.loading = false;
      }
    );
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

    this.nameFilter = this.c['customerName'].value;
    this.contactFilter = this.c['customerContact'].value;

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
    this.loading = true;
    if (this.nameFilter != "")
      this.filteredStockStorages = this.stockStorages.
        filter(e => e.stockItem.menuItem.caption.toLowerCase().match(this.nameFilter.toLowerCase()));

    if (this.contactFilter != "")
      this.filteredStockStorages = this.stockStorages.
        filter(e => e.stockItem.menuItem.barcode == this.contactFilter);
    this.loading = false;
    // this.filteredCustomers = this.filteredCustomers.
    //   filter(e => e.customerContacts[0].contact.match(this.contactFilter));

    // this.filteredCustomers = this.filteredCustomers.
    //   filter(e => e.customerAddresses[0].addressText.toLowerCase().match(this.addressFilter.toLowerCase()));
  }


  async getStockItems() {
    let data = await this.menuService.getStockItems("/stock/getStockItems");
    this.stockItems = data;
  }

  async getStockStorage() {
    this.loading = true;
    let data = await this.menuService.getStockItems("/stock/getStockStorage");
    this.loading = false;
    this.stockStorages = data;
    this.filteredStockStorages = data;
  }


}
