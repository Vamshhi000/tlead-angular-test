import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ServiceLayerService } from 'src/app/service-layer.service';

@Component({
  selector: 'app-delete-users',
  templateUrl: './delete-users.component.html',
  styleUrls: ['./delete-users.component.css']
})
export class DeleteUsersComponent implements OnInit {

  constructor(private snackBar: MatSnackBar,private serviceLayer:ServiceLayerService,public dialogRef: MatDialogRef<DeleteUsersComponent>,@Inject(MAT_DIALOG_DATA) public data: any, public dialog: MatDialog
  ,private router:Router) { }
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
  }


  deleteUser(){
    this.serviceLayer.deleteUsers(this.userdata).subscribe((res:any)=>{

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

onNoClick(): void {
  this.dialogRef.close();
}
}