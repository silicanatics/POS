import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationRequest } from 'src/app/entities/AuthenticationRequest';
import { AuthenticationResponse } from 'src/app/entities/AuthenticationResponse';
import { AuthService } from 'src/app/services/auth/auth.service';
import { JWTTokenService } from 'src/app/services/localstore/JWTTokenService';
import { LocalStorageService } from 'src/app/services/localstore/LocalStorageService';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm!: UntypedFormGroup;
  busy: boolean=false;

  constructor(private _router: Router, 
    private authService: AuthService, 
    private formBuilder: UntypedFormBuilder, 
    private localStorage: LocalStorageService,
    private jWTTokenService: JWTTokenService) { }

  ngOnInit(): void {
    this.buildForm();
  }

  buildForm() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get f() { return this.loginForm.controls; }

  async login() {
    const userName: string = this.f["username"].value;
    const password: string = this.f["password"].value;

    let athreq: AuthenticationRequest = new AuthenticationRequest();
    athreq.username = userName;
    athreq.password = password;
    this.busy = true;
    let jwt = await this.authService.authenticate("/auth/authenticate", athreq);
    this.busy = false;
    let authResponce: AuthenticationResponse = new AuthenticationResponse();
    //authResponce.token = jwt;
    if (jwt != null) {
      
      // let exp = <number><unknown>this.jWTTokenService.getExpiryTime() * 1000;
      // var dt = new Date(exp);
      // console.log(dt);
      this.localStorage.set("token", jwt)
      this._router.navigate(['dashboard'])
    }
  }

  passKeyPress(e:any){
   console.log(e.target.value.charcode);
  }

  logout(){
    this.authService.logout();
    
  }

}
