import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ServiceLayerService } from '../service-layer.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  constructor(private fb:FormBuilder,private serviceLayer:ServiceLayerService,private snackBar: MatSnackBar
    ,private router:Router) { }
  userform:FormGroup;
  msg:string="Please enter your registered E-mail id to request RESET password LINK"
  myimg:string ="assets/T-LEADl.png";
  pBar:boolean;
  btnDisable:boolean;
 
  ngOnInit(): void {

    this.userform=this.fb.group({
     email:['',[Validators.required,Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],

   
    })
  
  }

forgotPassword(){
this.pBar=true;
this.btnDisable=true;
  this.serviceLayer.forgotPassword(this.userform.value).subscribe((res:any)=>{

    this.pBar=false;
    this.btnDisable=false;
    localStorage.setItem("tokenValue","GHi0X9EbSnaPACqQdKkXBqilBFWA1bQMiGARsOcUXziSa");
   this.msg="Please check your email we send a link to RESET your password"

    this.snacbar(res.msg,'mat-primary')


  },(err:any)=>{
    this.pBar=false;
    this.btnDisable=false;
    this.snacbar(err.error.msg,'mat-warn');
  })
}

snacbar(msg:string,color:string){

  this.snackBar.open(msg,'', {
    duration: 5000,
    panelClass: ['mat-toolbar', color]
});
}


}
