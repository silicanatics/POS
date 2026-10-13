import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
  newBarcodeForm!: FormGroup;
  @ViewChild("caption")
  captionElement!: ElementRef;
  parentIdList=[{'id':1,'description':'Groceries'},{'id':6721,'description':'Clothing'},{'id':2211,'description':'Drinks'}];

  
  constructor(public activeModal: NgbActiveModal,
    private menuService: MenuService,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.buildForm();
    
  }



  ngAfterViewInit(): void {
    this.captionElement.nativeElement.focus();
    if(this.my_modal_barcode.substr(0,3) == '200'){
      this.newBarcodeForm.controls["itemParentId"].setValue(this.parentIdList[1]);
    } else{
      this.newBarcodeForm.controls["itemParentId"].setValue(this.parentIdList[0]);
    }
  }
    
  buildForm() {
    this.newBarcodeForm = this.formBuilder.group({
      itemName : ['', Validators.required],
      itemParentId: ['', Validators.required],
      itemPrice : ['', Validators.required],
      barcodeVal: ['', Validators.required]
    });
  }

  changeParentId(event:any){

  }

  get f() { return this.newBarcodeForm.controls; }

  async save(){
    let nodeType = new NodeType();
    nodeType.id = 4;
  let newMenuItem = new MenuItem();
  newMenuItem.caption = this.f["itemName"].value;
  newMenuItem.price = this.f["itemPrice"].value;
  newMenuItem.barcode = this.my_modal_barcode;
  const parent:any = this.f["itemParentId"].value;
  
  if(newMenuItem.barcode.substr(0,3) == '200'){
    let pr:string =  newMenuItem.barcode.substr(8,2) + "." + newMenuItem.barcode.substr(10,2);
    let barCodePrice:Number = new Number(pr);
    newMenuItem.price = <number>barCodePrice;
    
    newMenuItem.barcode = newMenuItem.barcode.substr(0,6);
    let  mit:MenuItemType = new MenuItemType();
    mit.id = 2;
    newMenuItem.menuItemType = mit;
    newMenuItem.linkedId = parent.id; //Butchery ID
    newMenuItem.menuLevel = 3;

  } else {
    let  mit:MenuItemType = new MenuItemType();
    mit.id = 3;
    newMenuItem.menuItemType = mit;
    newMenuItem.linkedId = parent.id; //Grocery ID
    newMenuItem.menuLevel = 3;
  }
  
 
  newMenuItem.nodeType = nodeType;
  this.activeModal.close(newMenuItem);
  }

}
