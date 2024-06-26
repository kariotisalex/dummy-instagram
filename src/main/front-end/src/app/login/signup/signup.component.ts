import { Component } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {Router, RouterLink} from "@angular/router";
import {UserService} from "../../services/user.service";
import {CommonModule} from "@angular/common";
import {NavigationService} from "../../services/navigation.service";

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    RouterLink
  ],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  error : string = "";
  value : string = "";
  valueV: string = "";
  constructor(
    private router : Router,
    private userService : UserService,
    private navigation : NavigationService
  ) {}


  signupHandling = new FormGroup({
    username : new FormControl('',[Validators.required ]),
    password : new FormControl('', Validators.required),
    passwordV : new FormControl('', Validators.required),
  });

  onSubmit(){
    const username  : string = this.signupHandling.value.username as string;
    const password  : string = this.signupHandling.value.password as string;
    const passwordV : string = this.signupHandling.value.passwordV as string;

    if (passwordV == password){
      this.userService.signup(username,password)
        .subscribe( {
          next: x => {
            this.navigation.goToLogin();
          },
          error: (err: HttpErrorResponse) => {
            this.error = err.error;
          }
        });
    }else{
      this.error = "Password is not the same in fields!"
      this.value = "";
      this.valueV = "";
    }


  }

  changing(){
    this.error="";
  }
  checking() : string {
    const password  : string = this.signupHandling.value.password as string;
    const passwordV : string = this.signupHandling.value.passwordV as string;
    if ((password != "") && (passwordV != "")){
      if(passwordV == password){
        return 'greenClass'
      }else{
        return 'redClass'
      }
    }else {
      return '';
    }
  }
}
