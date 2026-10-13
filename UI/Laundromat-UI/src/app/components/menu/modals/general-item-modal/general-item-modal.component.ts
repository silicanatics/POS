import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { MenuItem } from 'src/app/entities/MenuItem';
import { MenuItemType } from 'src/app/entities/MenuItemType';
import { MenuTile } from 'src/app/entities/MenuTile';
import { NodeType } from 'src/app/entities/NodeType';
import { MenuService } from 'src/app/services/menu/menu.service';

@Component({
  selector: 'app-general-item-modal',
  templateUrl: './general-item-modal.component.html',
  styleUrls: ['./general-item-modal.component.css']
})
export class GeneralItemModalComponent implements OnInit {

  @ViewChild("itemCount")
  itemCountElement!: ElementRef;
   @ViewChild("itemCaption")
   captionElement!: ElementRef;
   @Input() public itemDescription!:string;;
   menuTiles: MenuTile[] = [];
  menuTiles2: MenuTile[] = [];
  menuTilesTMP: MenuTile[] = [];
  menuItems: MenuItem[] = [];
  menuItemsView: MenuItem[] = [];
  menuItemsTMP: MenuItem[] = [];
  searchMenuItems : MenuItem[] = [];
  menuLoading:boolean=false;
  price:number=0;
  caption: string="GeneralItem";
  constructor(public activeModal: NgbActiveModal, private menuService: MenuService,
    private elementRef: ElementRef) { }

  ngOnInit(): void {
    this.caption = this.itemDescription;
  }

  ngAfterViewInit(): void {    
    this.getMenus();
     const elmnt1 = this.itemCountElement.nativeElement;
    elmnt1.click();    
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
      if (menuItem.menuLevel == -2) {
        this.menuItemsTMP.push(menuItem);
      }
    });
    if (this.menuItemsTMP.length > 0) {
      this.menuItemsView = [];
      this.menuItemsView = this.menuItemsTMP;
      this.constructMenuTiles();
    }
  }

  menuItemClick(_item: MenuItem) {
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
  getSearchMenuItems() {

    this.searchMenuItems = this.menuItems.filter(f => {
      if (f.nodeType.id == 4) {
        return f;
      } else {
        return null;
      }
    });
  }


  createGeneralItem() {

    if(this.price <= 0){
      return;
    }

    let generalMenuItem: MenuItem = new MenuItem();
   
    generalMenuItem.caption = this.caption;
    generalMenuItem.price = this.price;
    generalMenuItem.unitPrice = this.price;

    generalMenuItem.linkedId = 6379;
    
    //In case there is no match, error should be avoided.....
    generalMenuItem.id = 6380;

    if (this.caption == "General Item")
    generalMenuItem.id = 6380;
    if (this.caption == "Bus Ticket")
    generalMenuItem.id = 6382;
    if (this.caption == "Air Time")
    generalMenuItem.id = 6383;

      

    generalMenuItem.items = 1;
    generalMenuItem.menuLevel = 3;

    let menuItemType: MenuItemType = new MenuItemType();
    menuItemType.id = 5;
    generalMenuItem.menuItemType = menuItemType;

    let nodeType: NodeType = new NodeType();
    nodeType.id = 4;
    generalMenuItem.nodeType = nodeType;

  
    this.activeModal.close(generalMenuItem);

  }

}
