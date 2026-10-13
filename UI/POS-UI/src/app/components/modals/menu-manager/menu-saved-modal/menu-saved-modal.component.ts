import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { MenuItem } from 'src/app/entities/MenuItem';

@Component({
  selector: 'app-menu-saved-modal',
  templateUrl: './menu-saved-modal.component.html',
  styleUrls: ['./menu-saved-modal.component.css']
})
export class MenuSavedModalComponent implements OnInit {

  @Input()
  data!: any;
  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
    
  }

  close(){
    this.activeModal.close();
  }

}
