import { Component, ElementRef, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { MenuItem } from 'src/app/entities/MenuItem';
import { MenuItemType } from 'src/app/entities/MenuItemType';
import { NodeType } from 'src/app/entities/NodeType';
import { MenuService } from 'src/app/services/menu/menu.service';

@Component({
  selector: 'app-new-item-modal',
  templateUrl: './new-item-modal.component.html',
  styleUrls: ['./new-item-modal.component.css']
})
export class NewItemModalComponent implements OnInit {

  @Input() my_modal_title: any;
  @Input() my_modal_content: any;
  @Input() my_modal_barcode: any;
  @Input() my_parent_node: any;

  newBarcodeForm!: UntypedFormGroup;
  @ViewChild("caption")
  captionElement!: ElementRef;
  parentIdList = [{ 'id': 5516, 'description': 'Groceries' }, { 'id': 6721, 'description': 'Butcher' }, { 'id': 2211, 'description': 'Drinks' }];
  addToRoot: boolean = false;

  constructor(public activeModal: NgbActiveModal,
    private menuService: MenuService,
    private formBuilder: UntypedFormBuilder) { }

  rootSwitch() {
    this.addToRoot = !this.addToRoot;
  }

  ngOnInit(): void {
    this.buildForm();

  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.my_parent_node != undefined) {
      this.parentIdList.push(this.my_parent_node);
    }
  }

  ngAfterViewInit(): void {
    this.captionElement.nativeElement.focus();
    console.log(this.my_parent_node);
    if (this.my_parent_node != undefined) {
      this.parentIdList.push({ 'id': this.my_parent_node.id, 'description': this.my_parent_node.caption });
      this.newBarcodeForm.controls["itemParentId"].setValue(this.parentIdList[3]);
    } else
      if (this.my_modal_barcode.substr(0, 3) == '200' || this.my_modal_barcode.substr(0, 3) == '202') {
        this.newBarcodeForm.controls["itemParentId"].setValue(this.parentIdList[1]);
      } else {
        this.newBarcodeForm.controls["itemParentId"].setValue(this.parentIdList[0]);
      }
  }

  buildForm() {
    this.newBarcodeForm = this.formBuilder.group({
      itemName: ['', Validators.required],
      itemParentId: ['', Validators.required],
      itemPrice: ['', Validators.required],
      barcodeVal: ['', Validators.required]
    });
  }

  changeParentId(event: any) {

  }

  get f() { return this.newBarcodeForm.controls; }

  async save() {
    let nodeType = new NodeType();

    let newMenuItem = new MenuItem();
    newMenuItem.caption = this.f["itemName"].value;
    newMenuItem.price = this.f["itemPrice"].value;
    newMenuItem.barcode = this.my_modal_barcode;
    const parent: any = this.f["itemParentId"].value;

    if (newMenuItem.price == 0) {
      nodeType.id = 3;
    } else
      nodeType.id = 4;


    if (newMenuItem.barcode.substr(0, 3) == '200' || newMenuItem.barcode.substr(0, 3) == '202' ) {
      let pr: string = newMenuItem.barcode.substr(8, 2) + "." + newMenuItem.barcode.substr(10, 2);
      let barCodePrice: Number = new Number(pr);
      newMenuItem.price = <number>barCodePrice;
      newMenuItem.items = newMenuItem.price / newMenuItem.unitPrice;

      newMenuItem.barcode = newMenuItem.barcode.substr(0, 6);
      let mit: MenuItemType = new MenuItemType();
      mit.id = 2;
      newMenuItem.menuItemType = mit;
      if (!this.addToRoot) {
        newMenuItem.linkedId = parent.id; //Butchery ID
        newMenuItem.menuLevel = 3;
      } else {
        mit.id = 1;
        newMenuItem.menuItemType = mit;
        newMenuItem.linkedId = 0
        newMenuItem.menuLevel = 0;
      }

    } else if (this.my_parent_node != undefined) {
      let mit: MenuItemType = new MenuItemType();
      mit.id = 3;
      newMenuItem.menuItemType = mit;
      if (!this.addToRoot) {
        newMenuItem.linkedId = this.my_parent_node.id;
        if (newMenuItem.price > 0) {
          newMenuItem.menuLevel = 3;
          nodeType.id = 4;
        }
        else {
          newMenuItem.menuLevel = 2;
          nodeType.id = 3;
        }
      } else {
        mit.id = 1;
        newMenuItem.menuItemType = mit;
        newMenuItem.linkedId = 0
        newMenuItem.menuLevel = 0;
        nodeType.id = 1;
      }
    }

    else {
      let mit: MenuItemType = new MenuItemType();
      mit.id = 3;
      newMenuItem.menuItemType = mit;
      if (!this.addToRoot) {
        newMenuItem.linkedId = parent.id; //Grocery ID
        newMenuItem.menuLevel = 3;
      } else {
        mit.id = 1;
        newMenuItem.menuItemType = mit;
        newMenuItem.linkedId = 0
        newMenuItem.menuLevel = 0;
      }
    }

    newMenuItem.nodeType = nodeType;
    this.activeModal.close(newMenuItem);
  }

}
