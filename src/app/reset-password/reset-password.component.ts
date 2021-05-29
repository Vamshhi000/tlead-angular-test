import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Customvalidators } from '../customValidators/customValidators';
import { ServiceLayerService } from '../service-layer.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  constructor(private fb:FormBuilder,private serviceLayer:ServiceLayerService,private snackBar: MatSnackBar
    ,private router:Router,private activatedRoute:ActivatedRoute) { }

    userform:FormGroup;
    msg:string="Please enter your new password"
    myimg:string ="assets/T-LEADl.png";
    pBar:boolean;
    btnDisable:boolean;
    tokenValue:string;
    ngOnInit(): void {
  
      this.userform=this.fb.group({
      
        password: ['', Validators.compose([Validators.required,Customvalidators.patternValidator()])],
        reenterPassword: ['', [Validators.required]]
       
      },
      {
        validator: Customvalidators.MatchPassword('password', 'reenterPassword'),
      }
      )
      this.activatedRoute.queryParams.subscribe(params => {
        console.log(params);
        this.tokenValue = params.token;
        localStorage.setItem("tokenValue",this.tokenValue);
        console.log(this.tokenValue);
      });
    }
  
  forgotPassword(){
  this.pBar=true;
  this.btnDisable=true;
    this.serviceLayer.resetpassword(this.userform.value,this.tokenValue).subscribe((res:any)=>{
  
      this.pBar=false;
      this.btnDisable=false;
     this.msg="Password Reset sucussfull"
     this.router.navigate(['/LoginComponent']);
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
