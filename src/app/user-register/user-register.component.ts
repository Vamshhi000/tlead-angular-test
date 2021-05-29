import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { commonServiceService } from '../common-service.service';

import { Customvalidators } from '../customValidators/customValidators';
import { User } from '../model/form';
import { NumberVerificatioNComponent } from '../number-verificatio-n/number-verificatio-n.component';

import { OtpVerificationComponent } from '../otp-verification/otp-verification.component';
import { ServiceLayerService } from '../service-layer.service';





@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css']
})
export class UserRegisterComponent implements OnInit {

  constructor(private fb:FormBuilder,public dialog: MatDialog,private serviceLayer:ServiceLayerService,
    private commonservice:commonServiceService,private routees: ActivatedRoute,private snackBar: MatSnackBar
    ,private router:Router) { }
  myimg:string ="assets/T-LEADl.png";
  userform:FormGroup;
  isOk:boolean=true;
  emailDisabled:boolean=false;
  verifyEmail:string="VERIFY EMAIL";
  verified:any;
  numDisabled:boolean=true;
 verifyNum:string="VERIFY NUMBER";
x:boolean=true
 numIcon:boolean;
 emailIcon:boolean;
bothVerify:boolean=true;
// alert:boolean;
// isSucuess:boolean;
msg:string;
  ngOnInit(): void {


    this.userform=this.fb.group({
      firstName:['',[Validators.required,Validators.maxLength(30),Validators.minLength(5)]],
      lastName:['',[Validators.required,Validators.maxLength(30),Validators.minLength(5)]],
      number:['',[Validators.required,Validators.maxLength(10),Validators.minLength(10)]],
      email:['',[Validators.required,Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      password: ['', Validators.compose([Validators.required,Customvalidators.patternValidator()])],
      reenterPassword: ['', [Validators.required]],
      referalId:['',[Validators.required]]
    },
    {
      validator: Customvalidators.MatchPassword('password', 'reenterPassword'),
    })
   
    
    this.verified=sessionStorage.getItem('verified');
   
let obj=JSON.parse(this.verified);
console.log(this.verified)
console.log(obj);

  }



  
ddisable(){

  this.routees.paramMap.subscribe(params => {
    var id = params.get('id');
    console.log(id);
  });
}
  sendemailRequest(): void {
    const dialogRef = this.dialog.open(OtpVerificationComponent, {
      width: '400px',
      data: {email: this.userform.value.email}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
     if(result!=null){
       this.emailIcon=true;
      this.numDisabled=false;
      this.emailDisabled=result.disable
      this.verifyEmail=result.msg;
      
      this.userform.get('email').disable();
      this.userform.value.email=result.email;
      console.log(this.userform.value);
     }
  
    });
  }

  sendNumberRequest(): void {
    const dialogRef = this.dialog.open(NumberVerificatioNComponent, {
      width: '400px',
    
      data: {number: this.userform.value.number}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      // this.commonservice.otpBeSubject.next(result);
      
     if(result!=null){
      this.numIcon=true;
      this.numDisabled=result.disable
      this.verifyNum=result.msg;
      this.bothVerify=!result.bothverified;
      this.userform.get('number').disable();
      let user = new User()
      let emailObj=JSON.parse(this.verified);
      user.email=emailObj.email;
      user.phoneNumber=result.number;
      this.commonservice.usersBeSubject.next(user);
      // this.userform.value.phoneNumber=result.number;

      console.log(this.userform.value);
      
     }
  
    });
  }
userRegister(){


  this.serviceLayer.registerUser(this.userform.value).subscribe((res:any)=>{

    
    this.router.navigate(['/LoginComponent']);
this.snacbar(res.msg,'mat-primary')

  },(err:any)=>{
   
   
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

 
