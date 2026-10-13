import { NestedTreeControl } from '@angular/cdk/tree';
import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatTreeNestedDataSource } from '@angular/material/tree';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BehaviorSubject } from 'rxjs';
import { ItemOption } from 'src/app/entities/ItemOption';
import { MenuItem } from 'src/app/entities/MenuItem';
import { MenuItemOption } from 'src/app/entities/MenuItemOption';
import { MenuItemPayload } from 'src/app/entities/MenuItemPayload';
import { MenuItemType } from 'src/app/entities/MenuItemType';
import { MenuNode } from 'src/app/entities/MenuNode';
import { NodeType } from 'src/app/entities/NodeType';
import { MenuService } from 'src/app/services/menu/menu.service';
import { MenuSavedModalComponent } from '../../modals/menu-manager/menu-saved-modal/menu-saved-modal.component';


@Component({
  selector: 'app-menu-manager',
  templateUrl: './menu-manager.component.html',
  styleUrls: ['./menu-manager.component.css']
})

export class MenuManagerComponent implements OnInit {
  treeControl = new NestedTreeControl<MenuNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<MenuNode>();
  menuItems: MenuItem[] = [];
  menuLoading: boolean = false;
  menuNodes: MenuNode[] = [];
  selectedMenuNode!: MenuNode;
  menuItemForm!: FormGroup;
  ItemOptioForm!: FormGroup;
  menuSearchForm!: FormGroup;
  submitted: boolean = false;
  menuItemOptions: MenuItemOption[] = [];
  menuItemOptionsTMP: MenuItemOption[] = [];
  menuItemOptionsBKP: MenuItemOption[] = [];
  itemOptions: ItemOption[] = [];
  menuItemTypeList: MenuItemType[] = [];
  nodeId: number = 0;
  has_Children: boolean = false;
  selectedIndex: number = 0;
  defaultChecked: boolean = false;
  newOption: boolean = false;
  updateClicked: boolean = false;
  rootName: String = "";
  selectdedNode!: MenuNode;
  menuSearch: string = "";
  mitId: number = 0;
  nodeDescription: string = "";
  barcode: string = "";

  constructor(private menuService: MenuService,
    private formBuilder: FormBuilder,
    private modalService: NgbModal,
    private _router: Router) {
  }

  dashboard() {
    this._router.navigate(['dashboard'])
  }

  hasChild = (_: number, node: MenuItem) => !!node.children && node.children.length > 0;

  ngOnInit(): void {
    this.initialise();
  }

  initialise() {
    this.getMenus();
    this.getMenusItemOptions();
    this.getMenuItemTypes();
    this.getItemOptions();
    this.buildForm();
    this.selectdedNode = new MenuNode();
    this.selectdedNode.nodeType = new NodeType();
    this.selectdedNode.menuItemType = new MenuItemType();
  }

  buildForm() {
    this.menuItemForm = this.formBuilder.group({
      caption: ['', Validators.required],
      barcode: ['', Validators.required],
      price: ['', Validators.required],
      menuLevel: ['', Validators.required],
      menuItemType: [''],
      id: ['',],
      parentId: ['',],
      nodeType: ['',],
      linkedId: ['',]
    });

    this.ItemOptioForm = this.formBuilder.group({
      name: ['', Validators.required],
      price: ['', Validators.required],
      id: ['', Validators.required]
    });

    this.menuSearchForm = this.formBuilder.group({
      search: ['']
    });

  }
  get f() { return this.menuItemForm.controls; }
  get j() { return this.ItemOptioForm.controls; }
  get h() { return this.menuSearchForm.controls; }

  async getMenus() {
    this.menuLoading = true;
    let data: MenuItem[] = await this.menuService.getMenuItems("/menu/getMenus?mode=1", 1);
    this.menuItems = data.sort((a, b) => {
      if (a.caption > b.caption) {
        return 1;
      }
      if (a.caption < b.caption) {
        return -1;
      }
      return 0;
    });
    this.menuLoading = false;
    this.ConstructNodes();
  }



  async getMenuItemTypes() {
    let data = await this.menuService.getMenuItemTypes("/menu/getMenuItemTypes", 1);
    this.menuItemTypeList = data;

  }

  async getMenusItemOptions() {
    let data = await this.menuService.getMenuItemOptions("/menu/getMenuItemOptions", 1);
    this.menuItemOptions = data;

  }

  async getItemOptions() {
    let data = await this.menuService.getItemOptions("/menu/getItemOptions", 1);
    this.itemOptions = data;
  }

  ConstructNodes() {
    let hasChildren: boolean;
    for (let menuItem of this.menuItems) {
      if (!menuItem.caption.toLowerCase().includes(this.menuSearch.toLowerCase()))
        continue;
      if (menuItem.linkedId != 0)
        continue;
      if (this.hasChildren(menuItem.id)) {
        let node: MenuNode = new MenuNode();
        this.createNodes(node, menuItem);
        this.menuNodes.push(node);
      }
    }
    this.dataSource.data = this.menuNodes;
  }

  searchItem() {
    console.log("searchItem");
    this.menuSearch = this.menuSearchForm.controls["search"].value;

    this.menuNodes = [];
    this.ConstructNodes();
  }

  SearchItemByBarCode() {
    this.menuSearch = this.menuSearchForm.controls["search"].value;
    let node:MenuNode ;
    this.menuNodes = [];
    this.ConstructNodes();
    for (var mi of this.menuItems) {
      if (mi.barcode == this.menuSearch) {
        node = mi;
        this.getMenuItems(node)
        break;
      }
    }
    
  }

  createNodes(node: MenuNode, menuItem: MenuItem) {

    node.caption = menuItem.caption;
    node.id = menuItem.id;
    node.price = menuItem.price;
    node.barcode = menuItem.barcode;
    node.nodeType = menuItem.nodeType;
    node.menuItemType = menuItem.menuItemType;

    let children = this.returnListOfChildren(menuItem.id);
    let childNodes: MenuNode[] = this.createChildNodes(children);
    node.children = childNodes;
    for (let childNode of childNodes) {
      let item: MenuItem = children.filter(i => i.id == childNode.id)[0];
      this.createNodes(childNode, item);
    }

  }

  createChildNodes(menuItemlist: MenuItem[]): MenuNode[] {
    let menuNodes: MenuNode[] = [];
    for (let menuItem of menuItemlist) {
      let menuNode: MenuNode = new MenuNode();
      menuNode.id = menuItem.id;
      menuNode.linkedId = menuItem.linkedId;
      menuNode.caption = menuItem.caption;
      menuNode.price = menuItem.price;
      menuNode.nodeType = menuItem.nodeType;
      menuNode.menuItemType = menuItem.menuItemType;
      menuNode.linkedId = menuItem.linkedId;
      menuNode.menuLevel = menuItem.menuLevel;
      menuNode.barcode = menuItem.barcode;
      menuNodes.push(menuNode);
    }
    return menuNodes
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

  returnListOfChildren(id: number): MenuItem[] {
    var children: MenuItem[] = [];
    for (let mi of this.menuItems) {
      if (mi.linkedId != id)
        continue;
      let menuItem = new MenuItem();
      menuItem.caption = mi.caption;
      menuItem.id = mi.id;
      menuItem.price = mi.price;
      menuItem.nodeType = mi.nodeType;
      menuItem.menuItemType = mi.menuItemType;
      menuItem.linkedId = mi.linkedId;
      menuItem.menuLevel = mi.menuLevel;
      menuItem.barcode = mi.barcode;
      children.push(menuItem);
    }
    return children;
  }

  getMenuItems(node: MenuNode) {

    this.menuItemForm.reset();
    if (node.nodeType.id < 4) {
      this.nodeDescription = <string>node.caption;
    } else {
      this.nodeDescription = <string>this.getChildNode(node).caption;
    }
    this.selectdedNode = node;
    this.nodeId = node.id;
    this.has_Children = this.hasChildren(node.id);
    let parentNode = this.getParentNode(node);
    this.f["caption"].setValue(node.caption);
    this.f["price"].setValue(node.price);
    this.f["id"].setValue(node.id);
    this.f["menuItemType"].reset();// setValue(node.menuItemType);
    this.f["menuItemType"].setValue(node.menuItemType);
    this.f["nodeType"].setValue(node.nodeType);
    this.f["linkedId"].setValue(node.linkedId);
    this.f["barcode"].setValue(node.barcode);
    this.f["menuLevel"].setValue(node.menuLevel);
    this.f["parentId"].setValue(parentNode.id);
    this.mitId = 0;
    this.mitId = node.menuItemType.id;

    this.menuItemOptionsTMP = this.menuItemOptions.filter(e => e.menuItem.id == node.id);
    this.menuItemOptionsBKP = this.menuItemOptions.filter(e => e.menuItem.id == node.id);

    //
    this.selectedMenuNode = node;
    //console.log(this.selectedMenuNode);

    for (var rr in this.menuItemForm.controls) {
      this.f[rr].disable();
    }

  }



  setMenuItemTypeOption(node: MenuNode) {

  }

  getParentNode(node: MenuNode): MenuNode {

    let parent: MenuNode = new MenuNode();
    for (var item of this.menuItems) {
      if (item.linkedId == node.id) {
        parent = item;
        break;
      }
      // if(mi.linkedId == node.id)
      // console.log(mi);
    }


    return parent;;
  }

  getChildNode(node: MenuNode): MenuNode {

    let parent: MenuNode = new MenuNode();
    for (var item of this.menuItems) {
      if (item.id == node.linkedId) {
        parent = item;
        break;
      }
      // if(mi.linkedId == node.id)
      // console.log(mi);
    }


    return parent;;
  }

  addNewMenu() {
    this.has_Children = true;
  }

  removeOption(opts: MenuItemOption) {

    let i = this.menuItemOptionsTMP.indexOf(opts);

    this.menuItemOptionsTMP.splice(i, 1);
  }



  createNewMenuNode() {

    // this.selectedMenuNode.id = -1;
    //  this.has_Children = false;
    this.selectdedNode.id = -1;
    this.selectdedNode.barcode = "";
    this.selectdedNode.caption = "";
    this.selectdedNode.price = 0;

    let newMenuNode = this.selectdedNode;

    this.f["caption"].setValue(newMenuNode.caption);
    this.f["price"].setValue(newMenuNode.price);
    this.f["id"].setValue(newMenuNode.id);
    this.f["nodeType"].setValue(newMenuNode.nodeType);
    this.f["menuItemType"].setValue(newMenuNode.menuItemType);
    this.f["linkedId"].setValue(newMenuNode.linkedId);
    this.f["barcode"].setValue(newMenuNode.barcode);

    for (var rr in this.menuItemForm.controls) {
      this.f[rr].enable();
    }


  }

  searchByBarCode() {
    if (this.barcode.length > 0) {
      for (var menuNode of this.menuNodes) {
        if (menuNode.barcode == this.barcode) {
          this.getMenuItems(menuNode);
          break;
        }
      }
    }
  }



  updateMenuNode() {
    for (var rr in this.menuItemForm.controls) {
      this.f[rr].enable();
    }
  }


  returnAllChildren(id: number): MenuItem[] {

    let tempChildren: MenuItem[];
    let deepChildren: MenuItem[] = [];
    let children: MenuItem[] = this.returnListOfChildren(this.selectedMenuNode.id);

    let childrenFound: Boolean = true;

    // while (childrenFound) {
    //   childrenFound = false;
    for (var child of children) {
      childrenFound = false;
      tempChildren = this.returnListOfChildren(child.id);
      if (tempChildren.length > 0) {

        for (var tempChild of tempChildren) {
          if (!deepChildren.includes(tempChild)) {
            deepChildren.push(tempChild);
            childrenFound = true;
          }
        }

      }
    }
    // }

    deepChildren.forEach(dChild => {
      children.push(dChild);
    });

    return children;
  }

  async saveOptions() {

    let menuItemPayload: MenuItemPayload = new MenuItemPayload();
    menuItemPayload.menuItemOptions = this.menuItemOptionsTMP;
    let menuItem = new MenuItem();
    let menuItems: MenuItem[] = [];
    if (this.selectedMenuNode.id != -1) {
      menuItem.id = this.selectedMenuNode.id;
      menuItems = this.returnAllChildren(this.selectedMenuNode.id);
    }


    // if(this.hasChildren(menuItems))

    menuItems.forEach(mi => {
      mi.menuItemType = this.f["menuItemType"].value;
    })

    if (menuItems.length > 1) {
      menuItemPayload.menuItems = menuItems;
    }
    else {
      menuItem.caption = this.f["caption"].value;
      menuItem.barcode = this.f["barcode"].value;
      menuItem.price = this.f["price"].value;
      menuItem.linkedId = this.f["linkedId"].value;
      menuItem.menuLevel = this.f["menuLevel"].value;
      menuItem.nodeType = this.f["nodeType"].value;
      menuItem.menuItemType = this.f["menuItemType"].value;
      menuItemPayload.menuItems = [];
      menuItemPayload.menuItems.push(menuItem);
    }
    let data = await this.menuService.saveMenuItemOptions("/menu/saveMenuItemOptions", menuItemPayload);

    const modalRef = this.modalService.open(MenuSavedModalComponent);
    modalRef.componentInstance.data = data;
    this.getMenusItemOptions();
    this.initialise();
  }

  /*
  export class MenuItem{
     id!: number;
     items!: number;
     linkedId!: number;
     caption!: String;
     unitPrice!:number;
     price!: number;
     barcode!:String;
     menuLevel!: number;
     nodeType!:NodeType;
     options!: boolean;
     children!: MenuItem[];

}
  */
  async updateNode() {
    let menuItem = new MenuItem();
    if (this.menuItemOptionsTMP.length > 0) {
      this.saveOptions();
    }
    else {
      menuItem.id = this.selectedMenuNode.id;
      menuItem.caption = this.f["caption"].value;
      menuItem.linkedId = this.f["linkedId"].value;
      menuItem.menuLevel = this.f["menuLevel"].value;
      menuItem.nodeType = this.f["nodeType"].value;
      let mit: MenuItemType = new MenuItemType();
      mit.id = 5;
      menuItem.menuItemType = mit;
      let data = await this.menuService.saveMenuNode("/menu/updateNode", menuItem);
      const modalRef = this.modalService.open(MenuSavedModalComponent);
      modalRef.componentInstance.data = data;
    }
  }

  saveNewNode() {
    if (this.selectedMenuNode.nodeType.id == 4) {
      this.saveOptions();
    }
    else {
      this.updateNode();
    }
  }

  addOption(opts: ItemOption) {
    this.nodeId
    if (this.optionExists(opts))
      return;
    let menuItemOption: MenuItemOption = new MenuItemOption();
    menuItemOption.defaultOption = false;
    menuItemOption.itemOption = opts;

    menuItemOption.menuItem = this.menuItems.filter(i => i.id == this.nodeId)[0];
    //menuItemOption.menuItem.menuItemType = 
    this.menuItemOptionsTMP.push(menuItemOption);
  }

  optionExists(opts: ItemOption): boolean {
    let exists: boolean = false;

    for (var mio of this.menuItemOptionsTMP) {
      if (mio.itemOption.id == opts.id) {
        exists = true;
        break;
      }
    }
    return exists;
  }

  changeOptionDefault(opts: MenuItemOption) {
    opts.defaultOption = !opts.defaultOption;

  }

  changeItemType(e: any) {
    //  const value:MenuItemType = e.target.value;
    //this.menuItemForm.controls["menuItemType"].setValue(e.target.value);
  }

  saveOption() {
    let itemOption = new ItemOption();
    itemOption.description = this.j["name"].value;
    itemOption.price = this.j["price"].value;
    itemOption.id = this.j["id"].value
    this.menuService.saveItemOption("/menu/saveItemOption", itemOption);
    this.ItemOptioForm.reset();
  }

  clickOption(opts: ItemOption) {
    if (this.updateClicked) {
      this.updateClicked = false;
      return;
    }
    this.j["name"].setValue(opts.description);
    this.j["price"].setValue(opts.price);
    this.j["id"].setValue(opts.id);
    this.newOption = false;
    this.updateClicked = false;
  }

  createNewOption() {
    this.newOption = true;
    this.ItemOptioForm.reset();

  }

  updateCurrentOption() {
    this.updateClicked = true;
    this.newOption = !this.newOption;
  }

}

