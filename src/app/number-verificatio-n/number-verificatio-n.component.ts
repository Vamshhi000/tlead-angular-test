import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { commonServiceService } from '../common-service.service';

import { Customvalidators } from '../customValidators/customValidators';
import { User } from '../model/form';
import { ServiceLayerService } from '../service-layer.service';


@Component({
  selector: 'app-number-verificatio-n',
  templateUrl: './number-verificatio-n.component.html',
  styleUrls: ['./number-verificatio-n.component.css']
})
export class NumberVerificatioNComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<NumberVerificatioNComponent>,
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
 
    this.userform=this.fb.group({
      otp:['',[]],
      number:['',[Validators.required,Customvalidators.phoneNumberValidator,Validators.minLength(10),Validators.maxLength(10)]]
     

    })

    

  this.userEmail=this.data.number;
  this.isEmail=true;
  this.isOtp=false;


  

    this.commonService.numotpsob$.subscribe((res:boolean)=>{

      if(res){
        this.isEmail=false;
        this.isOtp=true;
        this.header="oh good!"
        this.content="We have sent an one time password to your NUMBER ! Please enter otp here"
      }
    })
  }


errorLogics(){

  this.alert=true;
  this.isSucuess=false;
  this.pBar=false;
  this.otpButton=false;
}
  

sendNumberOtp(){
 
  this.pBar=true;
  this.otpButton=true;

  let user=new User();
 
  this.commonService.usersob$.subscribe((res:any)=>{
    console.log("num otp usersob$"+res);
    user.email=res.email;
  },(err:any)=>{
console.log("num otp usersob$"+err);

  })
  user.phoneNumber="+91"+this.userform.value.number

  this.commonService.otpUserBeSubject.next(user);
  this.serviceLayer.numberVerification(user).subscribe((res:any)=>{
  this.msg=res.msg;

  this.pBar=false;
 
  this.otpButton=false;
  this.isNumber=false;
  this.isEmail=false;
  this.isOtp=true;

  this.header="oh good!"
  this.content="We have sent an one time password to your NUMBER ! Please enter otp here"
  this.commonService.numotpBeSubject.next(true);

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
  this.commonService.otpUserusersob$.subscribe((res:any)=>{

    this.serviceLayer.numberVerification(res).subscribe((res:any)=>{
      this.msg=res.msg;
      this.pBar=false;
      this.otpButton=false;
    this.isEmail=false;
    this.isOtp=true;
    this.header="oh good!"
    this.content="We have sent an one time password to your NUMBER ! Please enter otp here"
    
    this.alert=true;
    this.isSucuess=true
    
    this.commonService.otpBeSubject.next(true);


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








submitNumberOtp(){
  let user=new User();

  this.commonService.usersob$.subscribe((res:any)=>{
    console.log("submit otp usersob$"+res);
    user.email=res.email;
    user.phoneNumber=this.userform.value.number;
    this.commonService.usersBeSubject.next(user);
  },(err:any)=>{
console.log("submit otp usersob$"+err);

  })
  user.otp=this.userform.value.otp;

  this.serviceLayer.otpSubmit(user).subscribe((res:any)=>{

    this.msg=res.msg;
this.isSucuess=true;
this.pBar=true;
  this.otpButton=true;
this.alert=true;

let x={

  msg:"VERIFIED",
  disable:"true",
  bothverified:true,
  number:this.userform.value.number
  
}
sessionStorage.setItem('numverified', JSON.stringify(x));
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
