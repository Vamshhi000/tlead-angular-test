
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { commonServiceService } from '../common-service.service';

import { User } from '../model/form';
import { ServiceLayerService } from '../service-layer.service';




@Component({
  selector: 'app-otp-verification',
  templateUrl: './otp-verification.component.html',
  styleUrls: ['./otp-verification.component.css']
})
export class OtpVerificationComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<OtpVerificationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,private fb:FormBuilder,private serviceLayer:ServiceLayerService
    ,private commonService:commonServiceService) { }
    userform:FormGroup;
    
    userEmail:string
    isEmail:boolean;
    isOtp:boolean;
    msg:string;
    header:string="Well done!"
    content:string="Click the submit button to send otp Request"
    pBar:boolean;
    otpButton:boolean;
    isNumber:boolean;
    alert:boolean;
    isSucuess:boolean=false;

  


  ngOnInit(): void {
    console.log(this.data);
    this.userform=this.fb.group({
      otp:[''],
      email:['',[Validators.required,Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.com")]],
     

    })

    // Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")

  this.userEmail=this.data.email;
  this.isEmail=true;
  this.isOtp=false;


  

    this.commonService.otpsob$.subscribe((res:boolean)=>{

      if(res){
        this.isEmail=false;
        this.isOtp=true;
        this.header="oh good!"
        this.content="We have sent an one time password to your EMAIL ! Please enter otp here"
      }
    })
  }
sendOtplogics(){
  this.pBar=false;
  this.otpButton=false;
this.isEmail=false;
this.isOtp=true;
this.header="oh good!"
this.content="We have sent an one time password to your EMAIL ! Please enter otp here"

this.alert=true;
this.isSucuess=true

this.commonService.otpBeSubject.next(true);
}

errorLogics(){

  this.alert=true;
  this.isSucuess=false;
  this.pBar=false;
  this.otpButton=false;
}
  


sendOtpRequest(){



  this.pBar=true;
  this.otpButton=true;
this.commonService.usersBeSubject.next(this.userform.value);
  this.serviceLayer.emailVerification(this.userform.value).subscribe((res:any)=>{
    this.msg=res.msg;
  
    this.sendOtplogics();

  },(err:any)=>{

    if(err.status==0){
      this.msg="Internal Server Error";
      this.errorLogics();
    }else{
      this.msg=err.error.msg;
      this.errorLogics();
          
         
          console.log(err)
    }

  })


}
resendOtp(){
  this.pBar=true;
  this.otpButton=true;
  this.commonService.usersob$.subscribe((res:any)=>{

    this.serviceLayer.emailVerification(res).subscribe((res:any)=>{
      this.msg=res.msg;
      this.sendOtplogics();


    },(err:any)=>{
      if(err.status==0){
        this.msg="Internal Server Error";
        this.errorLogics();
      }else{
        this.msg=err.error.msg;
        this.errorLogics();
            
           
            console.log(err)
      }
  
  
    })
  })
 



}

submitOtp(){

let user= new User;
user.email=this.userEmail;
user.otp=this.userform.value.otp;

  this.serviceLayer.otpSubmit(user).subscribe((res:any)=>{

this.msg=res.msg;
this.isSucuess=true;
this.pBar=true;
  this.otpButton=true;
this.alert=true;
let x={
  email:this.userform.value.email,
  msg:"VERIFIED",
  disable:"true"
}
sessionStorage.setItem('verified', JSON.stringify(x));
this.dialogRef.close(
     x
);


  },(err:any)=>{
    if(err.status==0){
      this.msg="Internal Server Error";
      this.errorLogics();
    }else{
      this.msg=err.error.msg;
      this.errorLogics();
          
         
          console.log(err)
    }
    

  })
}


}


