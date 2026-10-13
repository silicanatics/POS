import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { PurchasePayload } from 'src/app/entities/PurchasePayload';
import { SelectedItemPurchaseDetail } from 'src/app/entities/SelectedItemPurchaseDetail';

@Component({
  selector: 'app-reprint-kitchen-slip-modal',
  templateUrl: './reprint-kitchen-slip-modal.component.html',
  styleUrls: ['./reprint-kitchen-slip-modal.component.css']
})
export class ReprintKitchenSlipModalComponent implements OnInit {


  @ViewChild("caption")
  captionElement!: ElementRef;
  currentCount:number=0;
  @Input()
  purchasePayload: PurchasePayload = new PurchasePayload;
  
  constructor(public activeModal: NgbActiveModal,
    private elementRef: ElementRef) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
 

    this.captionElement.nativeElement.innerHTML = "Reprint Slip?";
  }

  save(e:boolean){
    //this.selectedItemPurchaseDetail.menuItem.items = this.currentCount;
    this.activeModal.close(e);
  }

}
