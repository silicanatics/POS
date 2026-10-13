import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { MenuItem } from 'src/app/entities/MenuItem';

@Component({
  selector: 'app-item-search-modal',
  templateUrl: './item-search-modal.component.html',
  styleUrls: ['./item-search-modal.component.css']
})
export class ItemSearchModalComponent implements OnInit {

  searchMenuItems!:MenuItem[];
  menuItems!:MenuItem[];
  itemDescription!:string;
  selectedRow!: number;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
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

  searchItemSelected(item: any) {

    // this.menuItemClick(item);
    // this.filterSearchMenuItems('');
    // this.selectedRow = item.id;
    // this.modalService.dismissAll();
    // this.enableBarCodeScanning();
    this.activeModal.close(item);
  }

  createSearchItem(){

  }

}
