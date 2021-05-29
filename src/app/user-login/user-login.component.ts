import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from '../model/form';
import { ServiceLayerService } from '../service-layer.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit {

  constructor(private fb:FormBuilder,private serviceLayer:ServiceLayerService,private snackBar: MatSnackBar
    ,private router:Router) { }
  userform:FormGroup;
  myimg:string ="assets/T-LEADl.png";
  disableLogin:boolean=false;
  pBar:boolean;
  ngOnInit(): void {
  
    this.userform=this.fb.group({
     email:['',[Validators.required,Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      password:['', Validators.compose([Validators.required])],
   
    })
  
  }


  // printUsers(){

  //   console.log(this.userform.value);
  // }
  jwtLogin(){
    this.pBar=true;
    this.disableLogin=true;
    this.serviceLayer.jwtLogin(this.userform.value).subscribe((res:any)=>{
      this.pBar=false;
      this.disableLogin=false;
     let obj= JSON.parse(res);
      console.log(obj.token+"jwt");
    let userrrr=new User();
    userrrr.email=obj.email;
    userrrr.phoneNumber=obj.phoneNumber
    userrrr.username=obj.userName;
      this.serviceLayer.loginUser(obj,userrrr);
      if(obj.status=="paid"){
        this.router.navigate(['/homeScreen']);
      }else{
        this.router.navigate(['/paymentComponent']);
      }
     
  
  this.snacbar(obj.msg,'mat-primary')
  
      // localStorage.setItem("token",obj.token);
    },(err:any)=>{
      this.disableLogin=false;
      this.pBar=false;
   if(err.status==0){
    this.serviceLayer.logout();
    this.router.navigate(['/LoginComponent']);
    this.snacbar("Internal Server error",'mat-warn');
   }else{
    this.serviceLayer.logout();
    this.router.navigate(['/LoginComponent']);
    let obj= JSON.parse(err.error);
    this.snacbar(obj.msg,'mat-warn');
   }
      
     
    })
  }

  getUser(){

    this.serviceLayer.getUser().subscribe((res)=>{

      console.log(res);
     
    },(err:any)=>{

      console.log(err);
    })
  }
snacbar(msg:string,color:string){

  this.snackBar.open(msg,'', {
    duration: 5000,
    panelClass: ['mat-toolbar', color]
});
}

}
