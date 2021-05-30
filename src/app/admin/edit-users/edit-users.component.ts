import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Customvalidators } from 'src/app/customValidators/customValidators';
import { ServiceLayerService } from 'src/app/service-layer.service';

@Component({
  selector: 'app-edit-users',
  templateUrl: './edit-users.component.html',
  styleUrls: ['./edit-users.component.css']
})
export class EditUsersComponent implements OnInit {

  constructor(private snackBar: MatSnackBar,private serviceLayer:ServiceLayerService,public dialogRef: MatDialogRef<EditUsersComponent>,@Inject(MAT_DIALOG_DATA) public data: any, public dialog: MatDialog
  ,private fb:FormBuilder,private router:Router) { }
 
  
  userform:FormGroup;
x=[]
userdata={
    firstName:"",
    lastName:"",
    phoneNumber:"",
    email:"",
    password:"",
    reenterPassword:"",
    referalId:"",
    status:"",
    orderId:"",
    paymentId:"",
    myRefId:""

  }
  ngOnInit(): void {

    this.userform=this.fb.group({
      firstName:['',[Validators.required,Validators.maxLength(30),Validators.minLength(5)]],
      lastName:['',[Validators.required,Validators.maxLength(30),Validators.minLength(5)]],
      phoneNumber:['',[Validators.required,Validators.maxLength(10),Validators.minLength(10)]],
      email:['',[Validators.required,Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      // password: ['', Validators.compose([Validators.required,Customvalidators.patternValidator()])],
      // reenterPassword: ['', [Validators.required]],
      referalId:[''],
      myRefId:[''],
      status:['',[Validators.required]],
      orderId:['',[Validators.required]],
      paymentId:['',[Validators.required]],
    },
    {
      validator: Customvalidators.MatchPassword('password', 'reenterPassword'),
    })

  
let str=this.data.user.userName

this.x=str.split(/\s+/);

this.userdata.email=this.data.user.email;
this.userdata.firstName=this.x[0];
this.userdata.lastName=this.x[1];
this.userdata.phoneNumber=this.data.user.phoneNumber;
this.userdata.status=this.data.user.paidstatus;
this.userdata.orderId=this.data.user.orderId;
this.userdata.referalId=this.data.user.refId;
this.userdata.myRefId=this.data.user.myRefId;
this.userdata.paymentId=this.data.user.paymentId;
console.log(this.userdata)

  }


  editUsers(){

    this.serviceLayer.editUsers(this.userform.value).subscribe((res:any)=>{
      this.snacbar(res.msg,'mat-primary')
      this.dialogRef.close();

    },(err:any)=>{

      this.snacbar(err.error.msg,'mat-warn');
      this.dialogRef.close();
      if(err.status==0){
        this.dialogRef.close();
        localStorage.clear();
       this.router.navigate(['/AdminLogin/Ij3UsKr1gL4dsZKcZ5FXwJNYh3o0IjURP5DmUeQh0Zrl6']);
       this.snacbar("Session Expired",'mat-warn');
      }

    })
  }

  snacbar(msg:string,color:string){
  
    this.snackBar.open(msg,'', {
      duration: 5000,
      panelClass: ['mat-toolbar', color]
  });
  }
}
