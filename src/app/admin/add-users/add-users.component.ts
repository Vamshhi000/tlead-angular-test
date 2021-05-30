import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Customvalidators } from 'src/app/customValidators/customValidators';
import { ServiceLayerService } from 'src/app/service-layer.service';

@Component({
  selector: 'app-add-users',
  templateUrl: './add-users.component.html',
  styleUrls: ['./add-users.component.css']
})
export class AddUsersComponent implements OnInit {

  constructor(private snackBar: MatSnackBar,private serviceLayer:ServiceLayerService,public dialogRef: MatDialogRef<AddUsersComponent>,@Inject(MAT_DIALOG_DATA) public data: any, public dialog: MatDialog,private fb:FormBuilder
  ,private router:Router) { }
  userform:FormGroup;
  ngOnInit(): void {

   
    this.userform=this.fb.group({
      firstName:['',[Validators.required,Validators.maxLength(30),Validators.minLength(5)]],
      lastName:['',[Validators.required,Validators.maxLength(30),Validators.minLength(5)]],
      phoneNumber:['',[Validators.required,Validators.maxLength(10),Validators.minLength(10)]],
      email:['',[Validators.required,Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      password: ['', Validators.compose([Validators.required,Customvalidators.patternValidator()])],
      reenterPassword: ['', [Validators.required]],
      referalId:['',[Validators.required]]
    },
    {
      validator: Customvalidators.MatchPassword('password', 'reenterPassword'),
    })
  }
  userRegister(){


    this.serviceLayer.AdminregisterUser(this.userform.value).subscribe((res:any)=>{
  
 
  this.snacbar(res.msg,'mat-primary')
  this.dialogRef.close();
    },(err:any)=>{
      this.snacbar(err.error.msg,'mat-warn');
      this.dialogRef.close();
      if(err.status==0){
        localStorage.clear();
        this.dialogRef.close();
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
