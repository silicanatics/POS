import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-menu-search',
  templateUrl: './menu-search.component.html',
  styleUrls: ['./menu-search.component.css']
})
export class MenuSearchComponent implements OnInit {

  constructor( public dialogRef: MatDialogRef<MenuSearchComponent>,
    @Inject(MAT_DIALOG_DATA) public data: String) { }

  ngOnInit(): void {
  }

}
