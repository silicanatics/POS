import { Component, ElementRef, HostListener, Input, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SelectedItemPurchaseDetail } from 'src/app/entities/SelectedItemPurchaseDetail';

@Component({
  selector: 'app-item-count-modal',
  templateUrl: './item-count-modal.component.html',
  styleUrls: ['./item-count-modal.component.css']
})
export class ItemCountModalComponent implements OnInit {

  @ViewChild("itemCount")
  itemCountElement!: ElementRef;
  @ViewChild("caption")
  captionElement!: ElementRef;
  currentCount:number=0;
  @Input()
  selectedItemPurchaseDetail: SelectedItemPurchaseDetail = new SelectedItemPurchaseDetail;

  ngAfterViewInit(): void {
 
    this.currentCount = this.selectedItemPurchaseDetail.menuItem.items;
    const elmnt = this.itemCountElement.nativeElement;
    //elmnt.focus();
    //elmnt.select();
    elmnt.click();
    this.captionElement.nativeElement.innerHTML = this.selectedItemPurchaseDetail.menuItem.caption;
  }

  constructor(public activeModal: NgbActiveModal,
             private elementRef: ElementRef) {    
  }

  @HostListener('focus') onFocus() {
    this.elementRef.nativeElement.select();
  }

  ngOnInit(): void {
    
  }

  save(){
    //this.selectedItemPurchaseDetail.menuItem.items = this.currentCount;
    this.activeModal.close(this.currentCount);
  }

}
