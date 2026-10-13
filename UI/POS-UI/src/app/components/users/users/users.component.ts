import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DBEntity } from 'src/app/entities/DBEntity';
import { Usr } from 'src/app/entities/Usr';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  userForm!: UntypedFormGroup;
  msg:string="";

  constructor(private formBuilder: UntypedFormBuilder, private userService:UserService,  private _router: Router) { }

  ngOnInit(): void {
    this.buildForm();
  }

  buildForm() {
    this.userForm = this.formBuilder.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      initials: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }
  get f() { return this.userForm.controls; }

  save() {
    let user:Usr = new Usr();
    let entity:DBEntity = new DBEntity();

    entity.name = this.f["name"].value;
    entity.surname = this.f["surname"].value;
    entity.initials = this.f["initials"].value;

    user.dBEntity = entity;
    user.username = this.f["username"].value;
    user.password = this.f["password"].value;
    user.enabled = true;
    this.userService.saveUser("/user/saveUser",user);
    this.msg = "user saved!!"
    this.userForm.reset();
  }
 

  gotodashboard() {
    this._router.navigate(['dashboard'])
  }

}
