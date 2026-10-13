import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-tailoring',
  templateUrl: './tailoring.component.html',
  styleUrls: ['./tailoring.component.css']
})
export class TailoringComponent implements OnInit {

  @Input() public itemDescription!:String;
  constructor() { }

  ngOnInit(): void {
  }

}
