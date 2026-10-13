import { Component, ElementRef, Inject, KeyValueDiffer, OnInit, SimpleChanges, TemplateRef, ViewChild, ViewContainerRef } from '@angular/core';

import { MatSelectionListChange } from '@angular/material/list';
import { ActivatedRoute, Router } from '@angular/router';

import { Ctr } from 'src/app/entities/Ctr';
import { Customer } from 'src/app/entities/Customer';
import { DBEntity } from 'src/app/entities/DBEntity';
import { EntityAddress } from 'src/app/entities/EntityAddress';
import { EntityContact } from 'src/app/entities/EntityContact';
import { ItemOption } from 'src/app/entities/ItemOption';
import { ItemPurchase } from 'src/app/entities/ItemPurchase';
import { ItemPurchaseDetail } from 'src/app/entities/ItemPurchaseDetail';
import { ItemPurchaseDetailOption } from 'src/app/entities/ItemPurchaseDetailOption';
import { MenuItem } from 'src/app/entities/MenuItem';
import { MenuItemOption } from 'src/app/entities/MenuItemOption';
import { PurchasePayload } from 'src/app/entities/PurchasePayload';
import { PurchaseState } from 'src/app/entities/PurchaseState';
import { PurchaseType } from 'src/app/entities/PurchaseType';
import { SelectedCustomer } from 'src/app/entities/SelectedCustomer';
import { SelectedItemPurchaseDetail } from 'src/app/entities/SelectedItemPurchaseDetail';
import { MenuService } from 'src/app/services/menu/menu.service';
import { ReportService } from 'src/app/services/report/report.service';

declare var $: any;
// import { pdfDefaultOptions } from 'ngx-extended-pdf-viewer';
import { Usr } from 'src/app/entities/Usr';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ModalDismissReasons, NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { Tile } from 'src/app/entities/Tile';
import { TileCoordinator } from '@angular/material/grid-list/tile-coordinator';
import { MenuTile } from 'src/app/entities/MenuTile';
import { NewItemModalComponent } from './NewMenuItem/new-item-modal/new-item-modal.component';
import { IpAddressService } from 'src/app/services/ipAddress/ip-address.service';
import { PurchaseDetail } from 'src/app/entities/PurchaseDetail';
import { PurchaseInvoice } from 'src/app/entities/PurchaseInvoice';
import { MenuItemType } from 'src/app/entities/MenuItemType';
import { NodeType } from 'src/app/entities/NodeType';
import { ItemCountModalComponent } from './modals/item-count-modal/item-count-modal.component';
import { GeneralItemModalComponent } from './modals/general-item-modal/general-item-modal.component';
import { AuthService } from 'src/app/services/auth/auth.service';
import { MenuOptionTile } from 'src/app/entities/MenuOptionTile';
import { PaymentType } from 'src/app/entities/PaymentType';
import { UtilitiesService } from 'src/app/services/utilities/utilities.service';
import { MachinePrinter } from 'src/app/entities/MachinePrinter';
import { resolve } from 'path';
import { faFilm, faShirt } from '@fortawesome/free-solid-svg-icons';
import { TailoringComponent } from './modals/tailoring/tailoring.component';


@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {


  filmIcon = faFilm;
  shirtIcon = faShirt;

  price: number = 0;
  amountTendered: number = 0;
  change: number = 0;
  options: boolean | undefined;
  menuItems: MenuItem[] = [];
  searchedMenuItem!: MenuItem;
  searchMenuItems: MenuItem[] = [];
  menuItemOptions: MenuItemOption[] = [];
  menuItemOptionsTMP: MenuItemOption[] = [];
  menuItemsTMP: MenuItem[] = [];
  menuItemsTMP2: MenuItem[] = [];
  menuItemsView: MenuItem[] = [];
  menuItemsView2: MenuItem[] = [];
  selectedItems: MenuItem[] = [];
  itemPurchase!: ItemPurchase;
  itemPurchaseDetails: ItemPurchaseDetail[] = [];
  savedPurchaseDetail: PurchaseDetail | undefined;
  selectedItemPurchaseDetails: SelectedItemPurchaseDetail[] = []
  itemPurchaseDetailOptions: ItemPurchaseDetailOption[] = []
  purchasePayload!: PurchasePayload;
  GlobalItemPurchaseId: number = 0;
  purchaseAmountInsuficient: boolean = false;
  purchaseSuccessfullyProcessed: boolean = false;
  purchaseButtonPressed: boolean = false;
  loading: boolean = false;
  invoiceImage: any;
  selectedRow!: number;
  menuLoading: boolean = false;
  selectedCustomer!: SelectedCustomer;
  collectMode: boolean = false;
  deliverMode: boolean = false;
  walkInMode: boolean = false;
  selectedIndex: number = 0;
  menuPathTrace: String = "";
  clickedItems: MenuItem[] = [];
  backClick: boolean = false;
  barcodeValue!: string;
  value: string = "";
  paymentType!: PaymentType;
  _keybuffer: string = "";

  barcode: string = '';
  values: string[] = [];
  ctrl!: Ctr;
  barcodeForm!: FormGroup;
  bVal!: string;
  purchaseClicked: boolean = false;
  paymentProcessed: boolean = false;
  modalOptions!: NgbModalOptions;
  closeResult!: string;
  menuTiles: MenuTile[] = [];
  menuTiles2: MenuTile[] = [];
  menuTilesTMP: MenuTile[] = [];
  menuOptionTiles: MenuOptionTile[] = [];
  newItemCreation: Boolean = false;
  ipAddress: string = "";
  focused: boolean = true;
  generalItemCaption: string = "";
  generalItemAmount: number = 0;
  clickedButton: String = "";
  barcodeValueNew: string = "";
  showOptions: boolean = false;
  selectedItem!: SelectedItemPurchaseDetail;
  machinePrinters: MachinePrinter[] = [];
  purchModeDesc: string="";
  addressReset: boolean=false;

  itemsRow: number = 0;
  itemsCount: number = 0;
  testcount: number = 0;
  //_keybuffer:string = "";
  suspendBarCodeScanning: boolean = false;

  @ViewChild('btnNewItem')
  newItem!: ElementRef;
  @ViewChild('focusbutton')
  focusButton!: ElementRef;
  @ViewChild("barcodeInput")
  barcodeInput!: ElementRef;

  // @ViewChild('newIteModal', { read: TemplateRef })
  // newItemModal!: TemplateRef<any>;;


  ngOnChanges(changes: SimpleChanges) {
    console.log(changes)
  }

  changeFn(sd: any) {
    console.log(sd);
  }


  constructor(private menuService: MenuService,
    private ipAddressService: IpAddressService,
    private utilitiesService: UtilitiesService,
    private reportService: ReportService,
    private _router: Router,
    private _activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private vref: ViewContainerRef,
    private authservice: AuthService,

    private modalService: NgbModal) {
    this.modalOptions = {
      backdrop: 'static',
      backdropClass: 'customBackdrop'
    }
  }


  ngAfterViewInit() {
    this.enableBarCodeScanning();
    $(document).ready(function () {
      $('#itemsCountModal').on('shown.bs.modal', function () {
        $('#itemCountId1').focus();
      });

    });
  }

  gotodashboard() {
    this.focused = false;
    this._router.navigate(['dashboard'])
  }

  buildForm() {
    this.barcodeForm = this.formBuilder.group({
      barcodeVal: ['', Validators.required]
    });
  }
  get f() { return this.barcodeForm.controls; }

  onKeyTMP(value: string) {
    this.bVal += value;
  }

  onKey(event: any) {
    if (!this.suspendBarCodeScanning) {
      this.barcode = event.target.value;
      this.value = event.target.value;
      // this.noPossibilities(this.value);
    }
  }

  clearTest() {
    this.noPossibilities(this.value);

  }

  disableBarCodeScanning() {
    this.suspendBarCodeScanning = true;
  }

  enableBarCodeScanning() {
    this.suspendBarCodeScanning = false;
    this.barcodeInput.nativeElement.focus();
  }

  ngOnInit(): void {

    this.buildForm();

    // $(document).on("keyup", (e: { keyCode: any; which: any; }) => {
    //   if (this.newItemCreation) {
    //     this._keybuffer = "";
    //     return;
    //   }
    //   if (this.selectedIndex != 0) {
    //     this._keybuffer = "";
    //     return;
    //   }
    //   if (!this.focused) {
    //     this._keybuffer = "";
    //     return;
    //   }
    //   var code = e.keyCode || e.which;
    //   console.log(String.fromCharCode(code).trim());
    //   this._keybuffer += String.fromCharCode(code).trim();
    //   if (code == 13)// Enter key hit
    //   {
    //     this.noPossibilities(this._keybuffer);
    //   }
    // });


    //   this.ctrl = new Ctr();

    this.getUtilities();
    this.getMenus();
    this.getMenusItemOptions();
    this.itemPurchaseDetails = new Array<ItemPurchaseDetail>();
    this.selectedItemPurchaseDetails = new Array<SelectedItemPurchaseDetail>();
    this.initializeSelectedCustomer();
  }

  async getUtilities() {
    let info = await this.utilitiesService.getPrinterInfo("/utils/getInfo");
    this.machinePrinters = info;
    console.log(this.machinePrinters);
  }

  doSomething() {

  }

  noPossibilities(key: string) {
    this.value = key;
    this.findBarcodeItem(key);
    this._keybuffer = "";
  }

  private findBarcodeItem(barcode: string) {
    let found = false;
    for (var item of this.menuItems) {
      if ((item.barcode == barcode)) {
        found = true;
        this._keybuffer = "";
        this.menuItemClick(item);
        this.backClick = false;
        break;
      }
      if ((item.barcode == barcode.substr(0, 6))) {
        const price: number = Number(barcode.substr(7, 3) + "." + barcode.substr(10, 2));
        item.price = price;

        found = true;
        this._keybuffer = "";
        this.menuItemClick(item);
        this.backClick = false;


        break;
      }
    }
    if (!found) {
      console.log("Not found");
      this._keybuffer = "";
      this.newItemCreation = true;
      const modalRef = this.modalService.open(NewItemModalComponent);
      modalRef.componentInstance.my_modal_title = 'Barcode not found';
      modalRef.componentInstance.my_modal_content = 'New Barcode Information';
      modalRef.componentInstance.my_modal_barcode = barcode;

      modalRef.result.then((result: any) => {
        this.saveNewMenuItem(result);
        this.newItemCreation = false;
        this.modalService.dismissAll();
      }, () => {
      });
    }
    this.disableBarCodeScanning();
    this.barcodeValueNew = "";
    this.enableBarCodeScanning();
  }

  async saveNewMenuItem(newMenuItem: MenuItem) {

    let menuItem: MenuItem = await this.menuService.saveMenuItem("/menu/saveNewMenuItem", newMenuItem);
    this.getMenus();
    this.getMenusItemOptions();
    this.menuItemClick(menuItem);

  }

  initializeSelectedCustomer() {
    this.selectedCustomer = new SelectedCustomer();
    let purchaseType = new PurchaseType();
    purchaseType.id = 1;

    this.selectedCustomer.purchaseType = purchaseType;

    let customer = new Customer();
    customer.customerAddresses = new Array<EntityAddress>();
    customer.customerContacts = new Array<EntityContact>()
    customer.id = 1;
    let dbEntity = new DBEntity();
    dbEntity.name = 'WalkIn ';
    customer.dbEntity = dbEntity;
    let customerAddress = new EntityAddress();
    customerAddress.addressText = 'Collection Counter';
    customer.customerAddresses.push(customerAddress);
    let customerContact = new EntityContact();
    customerContact.contact = '000000000000';
    customer.customerContacts.push(customerContact);
    this.selectedCustomer.customer = customer;

    this.collectMode = false;
    this.deliverMode = false;
    this.walkInMode = false;

  }

  tabRowClick(item: any) {
    this.selectedRow = item.id;
  }

  itemCountClick() {
    console.log("Focussed!!");
    // document.getElementById("itemCount")?.focus();
  }

  test123() {
    console.log("123");
  }

  async getMenus() {
    this.menuLoading = true;
    let data = await this.menuService.getMenuItems("/menu/getMenus?mode=1", 1);
    this.menuItems = data;
    this.filter();
    // this.filterGeneral();
    this.menuLoading = false;
    this.getSearchMenuItems();
  }

  filter() {
    this.menuTiles = [];
    this.menuItemsTMP = [];
    this.menuItems.forEach(menuItem => {
      if (menuItem.menuLevel == 0) {
        this.menuItemsTMP.push(menuItem);
      }
    });
    if (this.menuItemsTMP.length > 0) {
      this.menuItemsView = [];
      this.menuItemsView = this.menuItemsTMP;
      this.constructMenuTiles();
    }
  }

  filterGeneral() {
    this.menuTiles = [];
    this.menuItemsTMP = [];
    this.menuItems.forEach(menuItem => {
      if (menuItem.menuLevel == -2) {
        this.menuItemsTMP2.push(menuItem);
      }
    });
    if (this.menuItemsTMP.length > 0) {
      this.menuItemsView2 = [];
      this.menuItemsView2 = this.menuItemsTMP;
      this.constructGeneralMenuTiles();
    }
  }

  constructMenuTiles() {
    for (var menuItem of this.menuItemsView) {
      menuItem.menuLevel == 4
      this.menuTiles.push({
        color: 'lightblue',
        cols: 1,
        rows: 1,
        text: <string>menuItem.caption,
        type: 'button',
        menuItem: menuItem
      });

    }

    console.log(this.menuTiles);
  }

  constructGeneralMenuTiles() {
    for (var menuItem of this.menuItemsView2) {
      menuItem.menuLevel == 4
      this.menuTiles2.push({
        color: 'lightblue',
        cols: 1,
        rows: 1,
        text: <string>menuItem.caption,
        type: 'button',
        menuItem: menuItem
      });

    }

    console.log(this.menuTiles);
  }

  menuTileClick(tile: Tile) {

  }

  getSearchMenuItems() {

    this.searchMenuItems = this.menuItems.filter(f => {
      if (f.nodeType.id == 4) {
        return f;
      } else {
        return null;
      }
    });
  }


  async getMenusItemOptions() {
    let data = await this.menuService.getMenuItemOptions("/menu/getMenuItemOptions", 1);
    this.menuItemOptions = data;
  }

  Reset() {

    this.enableBarCodeScanning();
    this._keybuffer = "";
    this.focused = true;
    this.newItemCreation = false;
    this.clickedButton = "";
    this.price = 0.00;
    this.modalService.dismissAll();
    this.filter();
    this.clickedItems = [];
    this.backClick = false;
    this.selectedItemPurchaseDetails = [];

  }

  back() {
    if (this.purchaseClicked) {
      this.purchaseClicked = false;
      return;
    }

    this.backClick = true;
    let proceed: boolean = true;

    let mi: MenuItem;
    let length: number;
    let count: number = 0;
    while (proceed) {
      this.clickedItems.splice(this.clickedItems.length - 1, 1);
      length = this.clickedItems.length;
      if (length > 0) {

        mi = this.clickedItems[length - 1];

        if (mi.nodeType.id != 4) {
          count++;
        }

        if (count == 2) {
          proceed = false;
        }

        if (!proceed) {
          this.menuItemClick(mi);
        }
      }
      else {
        this.filter();
        this.clickedItems = [];
        this.backClick = false;  //important!!!
        break;
      }

    }
    this.enableBarCodeScanning();
  }

  menuItemClick(_item: MenuItem) {

    if (_item.menuItemType.id == 6) {
      this.openTailoringItemModal();

    } else {

      this.clickedButton = _item.caption;
      if (this.paymentProcessed) {
        this.paymentProcessed = false;
        this.clickedItems = [];
        console.log("this.paymentProcessed");
        // return;
      }

      if (!this.backClick)
        this.clickedItems.push(_item);
      else
        this.backClick = false;


      this.purchaseButtonPressed = false;
      if (this.purchaseSuccessfullyProcessed == true) {
        this.purchaseSuccessfullyProcessed = false;
      }
      this.menuItemsTMP = [];
      this.menuTilesTMP = [];
      this.menuItems.forEach(menuItem => {
        if (menuItem.linkedId == _item.id) {
          this.menuPathTrace = this.menuPathTrace + "/" + menuItem.caption;
          this.menuItemsTMP.push(menuItem);

          this.menuTilesTMP.push({
            color: 'lightblue',
            cols: 1,
            rows: 1,
            text: <string>menuItem.caption,
            type: 'button',
            menuItem: menuItem
          });

        }
      })

      if (this.menuItemsTMP.length > 0) {
        this.menuItemsView = [];
        this.menuItemsView = this.menuItemsTMP;
      }

      if (this.menuTilesTMP.length > 0) {
        this.menuTiles = [];
        this.menuTiles = this.menuTilesTMP;
      }
      else {
        if (_item.nodeType.id == 4) {
          this.price += _item.price;
          this.updateItemPurchaseDetail(_item);
          this.selectedItems.push(_item);
          //document.getElementById('play').play();

          this.enableBarCodeScanning();
          let audio = new Audio();

          audio.src = './assets/beep-08b.wav';
          audio.load();
          audio.play();
        }

      }
      // this.focusButton.nativeElement.focus();

    }
  }





  hasChildren(id: number): boolean {
    let has: boolean = false;
    for (let menuItem of this.menuItems) {
      if (menuItem.linkedId == id) {
        has = true;
        break;
      }
    }
    return has;
  }

  //dale
  attachOptions(_item: MenuItem) {
    //itemPurchaseDetailOptions
    let mio: MenuItemOption;
    for (let mio of this.menuItemOptions) {
      if (mio.menuItem == _item) {
        let menuItemOption: MenuItemOption = new MenuItemOption();
        let itemPurchaseDetailOption: ItemPurchaseDetailOption = new ItemPurchaseDetailOption();
        menuItemOption.id = this.GlobalItemPurchaseId;
        menuItemOption.itemOption = mio.itemOption;
        itemPurchaseDetailOption.menuItemOption = menuItemOption;
        itemPurchaseDetailOption.itemOption = menuItemOption.itemOption;
        this.itemPurchaseDetailOptions.push(itemPurchaseDetailOption);
      }
    }
  }

  updateItemPurchaseDetail(_item: MenuItem) {
    let _selectedItemPuchaseDetail = new SelectedItemPurchaseDetail();
    let menuItem: MenuItem = new MenuItem();
    menuItem.id = _item.id;
    menuItem.caption = _item.caption;
    menuItem.linkedId = _item.linkedId;
    menuItem.menuLevel = _item.menuLevel;
    menuItem.options = _item.options;
    menuItem.price = _item.price;
    menuItem.menuItemType = _item.menuItemType;
    menuItem.unitPrice = _item.price;
    menuItem.items = 1;
    _selectedItemPuchaseDetail.price = _item.price;
    _selectedItemPuchaseDetail.menuItem = menuItem;
    _selectedItemPuchaseDetail.menuItemOptions = new Array<MenuItemOption>();
    _selectedItemPuchaseDetail.id = this.GlobalItemPurchaseId++;

    let mio: MenuItemOption;
    for (let mio of this.menuItemOptions) {
      if (mio.menuItem.id == _item.id) {
        let menuItemOption: MenuItemOption = new MenuItemOption();
        let itemOption: ItemOption = new ItemOption();
        menuItemOption.id = mio.id; // this.GlobalItemPurchaseId;
        menuItemOption.defaultOption = mio.defaultOption;
        itemOption.id = mio.itemOption.id; //  this.GlobalItemPurchaseId;
        itemOption.description = mio.itemOption.description;
        itemOption.price = mio.itemOption.price;
        itemOption.selected = mio.defaultOption;
        menuItemOption.itemOption = itemOption;
        _selectedItemPuchaseDetail.menuItemOptions.push(menuItemOption)
      }
    }
    _selectedItemPuchaseDetail.row = this.selectedItemPurchaseDetails.length + 1;
    this.selectedItemPurchaseDetails.push(_selectedItemPuchaseDetail);
    this.selectedItemPurchaseDetails.sort((a, b) => b.row - a.row);


  }

  splitSelectedItem(id: number) {

    if (this.selectedItemPurchaseDetails[id].menuItem.items == 1) {
      return;
    }
    let copiedItem = this.selectedItemPurchaseDetails[id].menuItem;

    let splitMenuItem: MenuItem = new MenuItem();
    splitMenuItem.id = copiedItem.id;
    splitMenuItem.caption = copiedItem.caption;
    splitMenuItem.linkedId = copiedItem.linkedId;
    splitMenuItem.menuLevel = copiedItem.menuLevel;
    splitMenuItem.menuItemType = copiedItem.menuItemType;
    splitMenuItem.options = copiedItem.options;
    splitMenuItem.price = copiedItem.unitPrice;
    splitMenuItem.unitPrice = copiedItem.unitPrice;

    this.selectedItemPurchaseDetails[id].menuItem.items -= 1;
    this.selectedItemPurchaseDetails[id].menuItem.price -= splitMenuItem.price;
    this.updateItemPurchaseDetail(splitMenuItem);

    this.forceRepaint();
    this.enableBarCodeScanning();
  }


  menuItemsPurchase() {

    this.updatePayLoad();
    this.purchaseClicked = true;
    // this.modalService.open("custom-modal-1");

  }

  async processPurchase() {
    if (this.amountTendered < this.price) {
      console.log(this.amountTendered);
      console.log(this.price);
      this.purchaseAmountInsuficient = true;
    }
    else {

      this.purchasePayload.itemPurchase.paymentType = this.paymentType;

      for (var mc of this.machinePrinters) {
        if (mc.paymentType.id == this.paymentType.id) {
          this.purchasePayload.printerName = mc.printerName;
          break;
        }
      }

      console.log('*****************************this.purchasePayload************************************');
      console.log(this.purchasePayload);
      this.paymentProcessed = false;
      // this.updatePayLoad();
      if (Math.abs(this.purchasePayload.itemPurchase.amountTendered -
        this.purchasePayload.itemPurchase.change_ -
        this.purchasePayload.itemPurchase.amount) > 0.1) {
        window.alert("Sorry!!!Incorrect change calculated. Please redo the transaction.");
        return;
      }
      this.menuLoading = true;

      this.purchasePayload.customer = this.selectedCustomer.customer;
      this.purchasePayload.purchaseType = this.selectedCustomer.purchaseType;
      
      this.purchasePayload.itemPurchase.purchaseInvoice.invoiceDateTime = this.purchasePayload.itemPurchase.purchaseInvoice.invoiceDate;
      let payload123Test = this.menuService.testPrintInvoice("/purchase/savePurchaseItems", this.purchasePayload).subscribe(res => {
        console.log(res);
      });
      let payload = this.menuService.savePurchaseItems("/purchase/savePurchaseItems", this.purchasePayload).subscribe(res => {
        this.selectedItemPurchaseDetails = [];
        //this.purchasePayload = new PurchasePayload();
        this.purchasePayload = <PurchasePayload><unknown>res; // No need for this!!!!!
        this.addressReset = false;
        this.addressReset = true;
        console.log("<<<<<<<<<<<<<<<<<<<<<<<<<res>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        console.log(res);
      });


      this.selectedItemPurchaseDetails = [];
      this.purchasePayload = new PurchasePayload();

      //  this.closeModal('custom-modal-1');
      this.menuLoading = false;

      //   this.fetchInvoice();
      this.purchaseAmountInsuficient = false;
      this.purchaseSuccessfullyProcessed = true;
      this.purchaseButtonPressed = true;
      this.menuItemReset();
      this.price = 0;
      this.selectedItemPurchaseDetails = []

      this.purchaseClicked = false;
      this.paymentProcessed = true;
      this.showOptions = false;
      this.initializeSelectedCustomer();
      this.savedPurchaseDetail = undefined;
      // document.getElementById('btnPrint').click();  
      this.enableBarCodeScanning();
    }
    this.enableBarCodeScanning();
  }



  getAmountEntered(amount: number) {
    this.amountTendered = amount;
    this.change = this.amountTendered - this.price;

    // this.purchaseClicked = false;
  }

  getpaymentType(paymentType: PaymentType) {
    this.paymentType = paymentType;
  }

  processPayment(amount: number) {
    this.processPurchase();
    this.enableBarCodeScanning();
  }

  menuItemReset() {
    this.menuPathTrace = "";
    this.filter();
  }

  async updatePayLoad_Deliveries(purchaseDetail: PurchaseDetail) {

    this.itemPurchase = purchaseDetail.itemPurchase;
    const user = JSON.stringify(localStorage.getItem('currentUser')?.toString());

    this.purchasePayload = new PurchasePayload();
    var usr: string = JSON.parse(user)

    this.purchasePayload.user = JSON.parse(usr);

    this.purchasePayload.clientIP = "0.0.0.0";


    let purchaseState = new PurchaseState();
    purchaseState.id = 2;

    this.itemPurchase.purchaseState = purchaseState;


    this.purchasePayload.itemPurchase = this.itemPurchase;
    this.purchasePayload.selectedItemPurchaseDetails = this.selectedItemPurchaseDetails;

  }

  reprintEvent(purchaseDetail: PurchaseDetail) {

    let selecteCustomer: SelectedCustomer = new SelectedCustomer();
    selecteCustomer.customer = purchaseDetail.itemPurchase.customer;
    selecteCustomer.purchaseType = purchaseDetail.itemPurchase.purchaseType;
    let selItemPurcDets: SelectedItemPurchaseDetail[] = [];

    for (var savedMenuItem of purchaseDetail.itemPurchaseDetailList) {
      let _selectedItemPuchaseDetail = new SelectedItemPurchaseDetail();
      let menuItem: MenuItem
      if (savedMenuItem.menuItem != undefined) {
        menuItem = savedMenuItem.menuItem;
        menuItem.items = savedMenuItem.items; // to be looked at later...
        _selectedItemPuchaseDetail.menuItem = menuItem;
        _selectedItemPuchaseDetail.items = savedMenuItem.items;
        _selectedItemPuchaseDetail.menuItem.unitPrice = savedMenuItem.menuItem.price;
        _selectedItemPuchaseDetail.menuItem.price = savedMenuItem.menuItem.price * savedMenuItem.items;
        _selectedItemPuchaseDetail.menuItemOptions = new Array<MenuItemOption>();
        _selectedItemPuchaseDetail.id = this.GlobalItemPurchaseId++;
      }
      for (var savedMenuItemOption of purchaseDetail.itemPurchaseDetailOptionList) {
        if (savedMenuItemOption.itemPurchaseDetail.id == savedMenuItem.id) {
          let menuItemOption: MenuItemOption = new MenuItemOption();
          menuItemOption.itemOption = savedMenuItemOption.menuItemOption.itemOption;
          menuItemOption.menuItem = savedMenuItemOption.menuItemOption.menuItem;
          menuItemOption.defaultOption = savedMenuItemOption.menuItemOption.defaultOption;
          menuItemOption.id = savedMenuItemOption.menuItemOption.id; // savedMenuItem.id == undefined ? -1 : savedMenuItem.id;// savedMenuItemOption.menuItemOption.id;
          menuItemOption.itemOption.selected = savedMenuItemOption.selected; // .itemOption.selected;

          _selectedItemPuchaseDetail.menuItemOptions.push(menuItemOption);
        }
      }

      _selectedItemPuchaseDetail.row = selItemPurcDets.length + 1;
      selItemPurcDets.push(_selectedItemPuchaseDetail);
      selItemPurcDets.sort((a, b) => b.row - a.row);
    }

    this.purchasePayload = new PurchasePayload();
    this.purchasePayload.itemPurchase = purchaseDetail.itemPurchase;
    this.purchasePayload.selectedItemPurchaseDetails = selItemPurcDets;
    const user = JSON.stringify(localStorage.getItem('currentUser')?.toString());
    var usr: string = JSON.parse(user)
    this.purchasePayload.user = JSON.parse(usr);
   
    let payload123Test = this.menuService.testPrintInvoice("/purchase/savePurchaseItems", this.purchasePayload).subscribe(res => {
      console.log(res);
    });

    //  this.purchasePayload = new PurchasePayload();

  }

  completePurchaseItemEvent(purchaseDetail: PurchaseDetail) {

    let selecteCustomer: SelectedCustomer = new SelectedCustomer();
    selecteCustomer.customer = purchaseDetail.itemPurchase.customer;
    selecteCustomer.purchaseType = purchaseDetail.itemPurchase.purchaseType;
    this.updatePurchaseMode(selecteCustomer); //(selectedCustomer: SelectedCustomer);
    this.selectedItemPurchaseDetails = [];
    this.savedPurchaseDetail = purchaseDetail;
    for (var savedMenuItem of purchaseDetail.itemPurchaseDetailList) {
      let _selectedItemPuchaseDetail = new SelectedItemPurchaseDetail();
      let menuItem: MenuItem
      if (savedMenuItem.menuItem != undefined) {
        menuItem = savedMenuItem.menuItem;
        menuItem.items = savedMenuItem.items; // to be looked at later...
        _selectedItemPuchaseDetail.menuItem = menuItem;
        _selectedItemPuchaseDetail.items = savedMenuItem.items;
        _selectedItemPuchaseDetail.menuItem.unitPrice = savedMenuItem.menuItem.price;
        _selectedItemPuchaseDetail.menuItem.price = savedMenuItem.menuItem.price * savedMenuItem.items;
        _selectedItemPuchaseDetail.menuItemOptions = new Array<MenuItemOption>();
        _selectedItemPuchaseDetail.id = this.GlobalItemPurchaseId++;
      }
      for (var savedMenuItemOption of purchaseDetail.itemPurchaseDetailOptionList) {
        if (savedMenuItemOption.itemPurchaseDetail.id == savedMenuItem.id) {
          let menuItemOption: MenuItemOption = new MenuItemOption();
          menuItemOption.itemOption = savedMenuItemOption.menuItemOption.itemOption;
          menuItemOption.menuItem = savedMenuItemOption.menuItemOption.menuItem;
          menuItemOption.defaultOption = savedMenuItemOption.menuItemOption.defaultOption;
          menuItemOption.id = savedMenuItemOption.menuItemOption.id; // savedMenuItem.id == undefined ? -1 : savedMenuItem.id;// savedMenuItemOption.menuItemOption.id;
          menuItemOption.itemOption.selected = savedMenuItemOption.selected; // .itemOption.selected;

          _selectedItemPuchaseDetail.menuItemOptions.push(menuItemOption);
        }
      }

      _selectedItemPuchaseDetail.row = this.selectedItemPurchaseDetails.length + 1;
      this.selectedItemPurchaseDetails.push(_selectedItemPuchaseDetail);
      this.selectedItemPurchaseDetails.sort((a, b) => b.row - a.row);

      this.forceRepaint();

      // if (this.amountTendered < this.price) {
      //   console.log(this.amountTendered);
      //   console.log(this.price);
      //   this.purchaseAmountInsuficient = true;
      // }
      // else 
      {
        this.paymentProcessed = false;
        this.updatePayLoad_Deliveries(purchaseDetail);

        const user = JSON.stringify(localStorage.getItem('currentUser')?.toString());
        var usr: string = JSON.parse(user)
        this.purchasePayload.user = JSON.parse(usr);

        let payload123Test = this.menuService.testPrintInvoice("/purchase/savePurchaseItems", this.purchasePayload).subscribe(res => {
          console.log(res);
        });
        
        this.menuService.savePurchaseItems("/purchase/completePurchase", this.purchasePayload).subscribe();
        //  this.closeModal('custom-modal-1');
        this.fetchInvoice();
        this.purchaseAmountInsuficient = false;
        this.purchaseSuccessfullyProcessed = true;
        this.purchaseButtonPressed = true;
        this.menuItemReset();
        this.price = 0;
        this.selectedItemPurchaseDetails = [];
        this.purchaseClicked = false;
        this.paymentProcessed = true;
        this.initializeSelectedCustomer();
        this.savedPurchaseDetail = undefined;

      }

    }
    this.enableBarCodeScanning();
  }

  onTabChanged($event: any) {
    let clickedIndex = $event.index;

    if (clickedIndex === 0) {
      this.enableBarCodeScanning();
    }
  }



  updatePurchaseItemEvent(purchaseDetail: PurchaseDetail) {

    let selecteCustomer: SelectedCustomer = new SelectedCustomer();
    selecteCustomer.customer = purchaseDetail.itemPurchase.customer;
    selecteCustomer.purchaseType = purchaseDetail.itemPurchase.purchaseType;
    this.updatePurchaseMode(selecteCustomer); //(selectedCustomer: SelectedCustomer);
    this.selectedItemPurchaseDetails = [];
    this.savedPurchaseDetail = purchaseDetail;
    for (var savedMenuItem of purchaseDetail.itemPurchaseDetailList) {
      let _selectedItemPuchaseDetail = new SelectedItemPurchaseDetail();
      let menuItem: MenuItem
      if (savedMenuItem.menuItem != undefined) {
        menuItem = savedMenuItem.menuItem;
        menuItem.items = savedMenuItem.items; // to be looked at later...
        _selectedItemPuchaseDetail.menuItem = menuItem;
        _selectedItemPuchaseDetail.items = savedMenuItem.items;
        _selectedItemPuchaseDetail.menuItem.unitPrice = savedMenuItem.menuItem.price;
        _selectedItemPuchaseDetail.menuItem.price = savedMenuItem.menuItem.price * savedMenuItem.items;
        _selectedItemPuchaseDetail.menuItemOptions = new Array<MenuItemOption>();
        _selectedItemPuchaseDetail.id = this.GlobalItemPurchaseId++;
      }
      for (var savedMenuItemOption of purchaseDetail.itemPurchaseDetailOptionList) {
        if (savedMenuItemOption.itemPurchaseDetail.id == savedMenuItem.id) {
          let menuItemOption: MenuItemOption = new MenuItemOption();
          menuItemOption.itemOption = savedMenuItemOption.menuItemOption.itemOption;
          menuItemOption.menuItem = savedMenuItemOption.menuItemOption.menuItem;
          menuItemOption.defaultOption = savedMenuItemOption.menuItemOption.defaultOption;
          menuItemOption.id = savedMenuItemOption.menuItemOption.id; // savedMenuItem.id == undefined ? -1 : savedMenuItem.id;// savedMenuItemOption.menuItemOption.id;
          menuItemOption.itemOption.selected = savedMenuItemOption.selected; // .itemOption.selected;

          _selectedItemPuchaseDetail.menuItemOptions.push(menuItemOption);
        }
      }

      _selectedItemPuchaseDetail.row = this.selectedItemPurchaseDetails.length + 1;
      this.selectedItemPurchaseDetails.push(_selectedItemPuchaseDetail);
      this.selectedItemPurchaseDetails.sort((a, b) => b.row - a.row);

      this.forceRepaint();
    }


  }


  updateItemOptions(item: MenuItem) {
  }

  updateNumberOfItem(item: MenuItem) {
  }




  //options
  toggleOptions(item: SelectedItemPurchaseDetail) {
    item.menuItem.options = !item.menuItem.options;
  }

  toggleOptions2(item: SelectedItemPurchaseDetail, i: number, ev: any) {
    let test: string = ev.path[0];
    this.showOptions = !this.showOptions;
    this.selectedItem = item;
    this.selectedItem.menuItem.caption;
    this.selectedItem.menuItemOptions.length;
    if (test.toString() == '[object HTMLTableCellElement]') {
      item.menuItem.options = !item.menuItem.options;
    }

    this.menuOptionTiles = [];
    for (var mio of item.menuItemOptions) {

      this.menuOptionTiles.push({
        color: 'lightblue',
        cols: 1,
        rows: 1,
        text: <string>mio.itemOption.description,
        type: 'button',
        selectedId: i,
        menuItemOption: mio
      });

    }


    this.enableBarCodeScanning();
  }

  menuItemOptionClick(_menuOptionTile: MenuOptionTile) {

    let id: number = _menuOptionTile.selectedId;

    console.log(this.selectedItemPurchaseDetails);

    _menuOptionTile.menuItemOption.itemOption.selected = !_menuOptionTile.menuItemOption.itemOption.selected;


    for (var option of this.selectedItemPurchaseDetails[id].menuItemOptions) {
      if (option.itemOption.id == _menuOptionTile.menuItemOption.itemOption.id) {
        option.itemOption.selected = _menuOptionTile.menuItemOption.itemOption.selected;
        break;
      }
    }
    //this.priceChange(_menuOptionTile.menuItemOption,this.selectedItemPurchaseDetails[id].menuItem.items);
    this.forceRepaint();
    this.enableBarCodeScanning();
  }

  removeSelectedItem(id: number) {
    this.price -= this.selectedItemPurchaseDetails[id].menuItem.price;
    this.selectedItemPurchaseDetails.splice(id, 1);

    this.showOptions = false;
    this.forceRepaint();
    this.enableBarCodeScanning();
  }




  async updatePayLoad() {

    let purchaseInvoice: PurchaseInvoice = await this.menuService.getPurchaseInvoiveNumber("/purchase/getPurchaseInvoive");
    const user = JSON.stringify(localStorage.getItem('currentUser')?.toString());
    console.log(user);
    this.purchasePayload = new PurchasePayload();
    var usr: string = JSON.parse(user)

    this.purchasePayload.user = JSON.parse(usr);
    //  this.purchasePayload.clientIP = this.ipAddress;
    this.purchasePayload.clientIP = "0.0.0.0";
    if (this.savedPurchaseDetail == undefined) {
      this.itemPurchase = new ItemPurchase();
    }
    else {
      this.itemPurchase = this.savedPurchaseDetail.itemPurchase;
    }


    let date: Date = new Date();


    purchaseInvoice.invoiceDate = date.getFullYear() + "/" +
      (date.getMonth() + 1).toString().padStart(2, '0') + "/" +
      date.getDate().toString().padStart(2, '0') + " " +
      date.getHours().toString().padStart(2, '0') + ":" +
      date.getMinutes().toString().padStart(2, '0') + ":" +
      date.getSeconds().toString().padStart(2, '0') + ":" +
      date.getMilliseconds().toString().padStart(3, '0');

    purchaseInvoice.invoiceDateTime = date.getFullYear() + "/" +
      (date.getMonth() + 1).toString().padStart(2, '0') + "/" +
      date.getDate().toString().padStart(2, '0') + " " +
      date.getHours().toString().padStart(2, '0') + ":" +
      date.getMinutes().toString().padStart(2, '0') + ":" +
      date.getSeconds().toString().padStart(2, '0') + ":" +
      date.getMilliseconds().toString().padStart(3, '0');

    this.itemPurchase.purchaseInvoice = purchaseInvoice;


    this.itemPurchase.customer = this.selectedCustomer.customer;
    this.itemPurchase.purchaseType = this.selectedCustomer.purchaseType;
    // let  purchaseType = new PurchaseType();
    // purchaseType.id = 1; //Walk In
    let purchaseState = new PurchaseState();
    if (this.itemPurchase.purchaseType.id == 1) {
      purchaseState.id = 2; //complete...
    } else {
      purchaseState.id = 1;
    }
    this.itemPurchase.purchaseState = purchaseState;
    // this.itemPurchase.purchaseType = purchaseType;
    this.itemPurchase.amount = this.price;
    this.itemPurchase.amountTendered = this.amountTendered;
    this.itemPurchase.change_ = this.amountTendered - this.price;

    //let date: Date = new Date();
    this.itemPurchase.purchaseDate = date.getFullYear() + "/" +
      (date.getMonth() + 1).toString().padStart(2, '0') + "/" +
      date.getDate().toString().padStart(2, '0') + " " +
      date.getHours().toString().padStart(2, '0') + ":" +
      date.getMinutes().toString().padStart(2, '0') + ":" +
      date.getSeconds().toString().padStart(2, '0') + ":" +
      date.getMilliseconds().toString().padStart(3, '0');
    this.purchasePayload.itemPurchase = this.itemPurchase;


    this.purchasePayload.selectedItemPurchaseDetails = this.selectedItemPurchaseDetails;

    this.purchasePayload.customer = new Customer();
    this.purchasePayload.customer.dbEntity = new DBEntity();
    this.purchasePayload.customer.customerAddresses = [];
    this.purchasePayload.customer.customerContacts  = [];

    

  }

  updatePurchaseMode(selectedCustomer: SelectedCustomer) {


    if (selectedCustomer.customer.dbEntity.name == 'WalkIn ') {
      this.initializeSelectedCustomer();
      this.selectedIndex = 0;
    }
    else {
      this.selectedCustomer = selectedCustomer;

      switch (selectedCustomer.purchaseType.id) {
        case 3:
          this.purchModeDesc = "Delivery";
          this.deliverMode = true;
          this.collectMode = false;
          this.walkInMode = false;
          break;
        case 2:
          this.purchModeDesc = "Collection";
          this.collectMode = true;
          this.deliverMode = false;
          this.walkInMode = false;
          break;
        case 1:
          this.purchModeDesc = "Walk In";
          this.walkInMode = true;
          this.collectMode = false;
          this.deliverMode = false;
          break;

        default:
          break;
      }
    }

    this.selectedIndex = 0;
  }

  generalPriceChange(event: any, row: number) {

    let mio2 = this.selectedItemPurchaseDetails[row];
    if (event.target.value < 0) {
      return;
    }
    console.log(event.target.value);
    let price: Number = new Number(event.target.value);
    mio2.menuItem.price = <number>price;

    let check: string = mio2.menuItem.price.toString();
    if (check.length > 1) {
      if (check.substr(0, 1) == '0') {
        check = check.substr(1);
      }
    }
    this.forceRepaint();

  }

  generalClick() {
    document.getElementById("general")?.focus();
  }



  priceChange(op: MenuItemOption, items: number) {

    if (op.itemOption.selected) {
      this.price += op.itemOption.price * items;
    }
    else {
      this.price -= op.itemOption.price * items;
    }
  }

  forceRepaint() {
    let tmp = this.selectedItemPurchaseDetails;
    this.selectedItemPurchaseDetails = [];
    this.selectedItemPurchaseDetails = tmp;
    this.price = 0;
    this.selectedItemPurchaseDetails.forEach(item => {
      this.price += item.menuItem.price;
      item.menuItemOptions.forEach(mio => {
        //  this.priceChange(mio, item.menuItem.items);
        if (mio.itemOption.selected)
          this.price += mio.itemOption.price * item.menuItem.items;
      });
    });

  }

  onListSelectionChange(ev: MatSelectionListChange, i: number) {

    // console.log(ev.options[0].value);
    // console.log(this.selectedItemPurchaseDetails[i].menuItemOptions);

    let menuItemOption: MenuItemOption = ev.options[0].value;
    for (var mio of this.selectedItemPurchaseDetails[i].menuItemOptions) {
      if (mio.id == menuItemOption.id) {
        if (mio.itemOption.id == menuItemOption.itemOption.id) {
          mio.itemOption.selected = !mio.itemOption.selected;
          break;
        }
      }
    }

    // console.log(ev.options[0].selectionList._value)
    // let menuItemOptionList: any =  ev.options[0].selectionList._value;
    // this.selectedItemPurchaseDetails[i].menuItemOptions = menuItemOptionList;

    // if (ev.options[0].selectionList._value != undefined) {
    //   if (ev.options[0].selectionList._value?.length > 0) {
    //     let menuItemOption: MenuItemOption = ev.options[0].value;

    //     // console.log(menuItemOption);
    //     for (var mio of this.selectedItemPurchaseDetails[i].menuItemOptions) {
    //       if (mio.id == menuItemOption.id) {
    //         if (mio.itemOption.id == menuItemOption.itemOption.id) {
    //           mio.itemOption.selected = ev.options[0].selected;
    //           break;
    //         }
    //       }
    //     }

    //   }
    // }

    this.enableBarCodeScanning();

  }



  async getInvoice() {
    this.loading = true;
    let res: any = await this.reportService.getImage("/purchase/getInvoiceImage", this.purchasePayload);//.subscribe(
    var reader = new FileReader();
    reader.onloadend = () => {
      this.invoiceImage = reader.result;
    }
    reader.readAsDataURL(res);
    this.loading = false;
  }

  fetchInvoice() {
    // this.getInvoice();
  }

  insertNewMenuItem(content: any) {

    this.modalService.open(content, this.modalOptions).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });

  }

  openItemCountModal(row: number) {
    this.focused = false;
    this.disableBarCodeScanning();
    const modalRef = this.modalService.open(ItemCountModalComponent);
    modalRef.componentInstance.selectedItemPurchaseDetail = this.selectedItemPurchaseDetails[row];

    modalRef.result.then((result: any) => {
      console.log(result);
      this.itemCountOnChange(result, row);
      this.focused = false;
      this.enableBarCodeScanning();
    }, () => {
    });
  }

  openGeneralItemModal() {
    this.focused = false;
    this.disableBarCodeScanning();
    const modalRef = this.modalService.open(GeneralItemModalComponent);
    modalRef.componentInstance.itemDescription = "General Item";
    modalRef.result.then((result: any) => {
      console.log(result);

      this.menuItemClick(result);
      this.focused = true;
      this.enableBarCodeScanning();
    }, () => {
    });
  }

  openTailoringItemModal() {
    this.focused = false;
    this.disableBarCodeScanning();
    const modalRef = this.modalService.open(TailoringComponent);
    modalRef.componentInstance.itemDescription = "General Item";
    modalRef.result.then((result: any) => {
      console.log(result);

      this.menuItemClick(result);
      this.focused = true;
      this.enableBarCodeScanning();
    }, () => {
    });
  }

  openBusTicketItemModal() {
    this.focused = false;
    this.disableBarCodeScanning();
    const modalRef = this.modalService.open(GeneralItemModalComponent);
    modalRef.componentInstance.itemDescription = "Bus Ticket";
    modalRef.result.then((result: any) => {
      console.log(result);

      this.menuItemClick(result);
      this.focused = true;
      this.enableBarCodeScanning();
    }, () => {
    });
  }

  openAirTimeItemModal() {
    this.focused = false;
    this.disableBarCodeScanning();
    const modalRef = this.modalService.open(GeneralItemModalComponent);
    modalRef.componentInstance.itemDescription = "Air Time";
    modalRef.result.then((result: any) => {
      console.log(result);

      this.menuItemClick(result);
      this.focused = true;
      this.enableBarCodeScanning();
    }, () => {
    });
  }

  createGeneralItem() {

  }

  search(content: any, row: number = 0, contentName: string = '') {

    this.focused = false;
    console.log(content);
    console.log("Searching!!" + content + " row : " + row + " contentName :" + contentName);
    var elmnt = document.getElementById("itemCountId");
    if (contentName == 'itemsCountModal') {
      this.itemsRow = row;
      this.itemsCount = this.selectedItemPurchaseDetails[row].items;
      this.testcount = this.selectedItemPurchaseDetails[row].items;
      elmnt?.focus();
      elmnt?.focus();
      if (elmnt) {
        elmnt.innerHTML = <string><unknown>this.itemsCount;

      }
    }

    document.getElementById("generalAmnt")?.focus();

    this.modalService.open(content, this.modalOptions).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
      console.log("******Closed*******");
      const elmnt = document.getElementById("generalAmnt");
      if (elmnt)
        elmnt.innerText = "0";
      this.focused = true;
      this.enableBarCodeScanning();
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      console.log("******Cross clicked*******");
      this.focused = true;
      this.enableBarCodeScanning();
    });
    this.enableBarCodeScanning();
  }

  changeItemCount() {
    this.modalService.dismissAll();
    if (this.itemsCount == undefined) {
      return;
    }
    this.itemCountOnChange(this.itemsCount, this.itemsRow);

  }

  itemCountOnChange(event: any, row: number) {

    let mio2 = this.selectedItemPurchaseDetails[row];
    if (event < 0) {
      mio2.menuItem.items = 0;
    } else {
      mio2.menuItem.items = event;
    }

    mio2.menuItem.price = mio2.menuItem.unitPrice * mio2.menuItem.items;

    for (var itemop of mio2.menuItemOptions) {
      this.priceChange(itemop, 1);
      if (itemop.itemOption.selected) {
        if (itemop.itemOption.price > 0) {
          //change price!!
        }
      }
    }
    console.log(event);
    this.forceRepaint();
  }


  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  searchItemSelected(item: any) {

    this.menuItemClick(item);
    this.filterSearchMenuItems('');
    this.selectedRow = item.id;
    this.modalService.dismissAll();
    this.enableBarCodeScanning();
  }

  onSearchChange(event: any) {
    let str: string = event.target.value;
    this.filterSearchMenuItems(str);
  }

  filterSearchMenuItems(sch: string) {

    this.searchMenuItems = this.menuItems.filter(f => {
      if (f.nodeType.id == 4) {
        if (f.caption.toLowerCase().includes(sch.toLowerCase())) {
          return f;
        }
        else
          return null;
      } else {
        return null;
      }
    });
  }


}

