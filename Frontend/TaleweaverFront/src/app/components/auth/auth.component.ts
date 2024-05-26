import { NgClass } from '@angular/common';
import { Component } from '@angular/core';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatRadioModule} from '@angular/material/radio';
import {MatButtonModule} from '@angular/material/button';
import { AuthService } from '../../services/Auth/auth.service';
import { Router } from '@angular/router';
 

 

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [FormsModule,NgClass, MatFormFieldModule, MatInputModule,FormsModule,MatButtonModule,MatRadioModule,ReactiveFormsModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent {
  isRegister=true;
   user : any;
 constructor(private authService:AuthService, private router:Router){}

  registerForm = new FormGroup({
    fullname : new FormControl("",[Validators.required]),
    email : new FormControl("",[Validators.email,Validators.required]),
    password : new FormControl("",[Validators.required , Validators.min(7)]),

  });

  loginForm = new FormGroup({
    email:new FormControl("",[Validators.required,Validators.email]),
    password:new FormControl("",[Validators.required])
  })

  handleRegister()
  {
    console.log("register",this.registerForm);
    this.authService.register(this.registerForm.value).subscribe({
      next:(response)=>{localStorage.setItem("jwt",response.jwt)
                       
                      console.log("signup success",response)
                     
                    }
     })
  }
  handleLogin()
  {
    console.log("login",this.loginForm);
     this.authService.login(this.loginForm.value).subscribe({
      next:(response)=>{localStorage.setItem("jwt",response.jwt)
                      
                      console.log("login success",response)
                      this.authService.getUserProfile().subscribe(
    
                        {
                          next : (data:any)=>{
                            this.user = data.user ;
                            console.log("User Details :", this.user);
                            localStorage.setItem("user",this.user);
                          }
                        }
                        
                      )
                      this.router.navigateByUrl("/userhome")
                    }
     })
  }
  togglePannel()
  {
    this.isRegister= !this.isRegister;
  }
}
