import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, Inject, OnChanges, OnInit } from '@angular/core';
import { MatBottomSheet, MatBottomSheetRef, MAT_BOTTOM_SHEET_DATA } from '@angular/material/bottom-sheet';
import { MatSnackBar } from '@angular/material/snack-bar';
import { commonServiceService } from '../common-service.service';
import { ServiceLayerService } from '../service-layer.service';

@Component({
  selector: 'app-chat-request-and-search',
  templateUrl: './chat-request-and-search.component.html',
  styleUrls: ['./chat-request-and-search.component.css']
})
export class ChatRequestAndSearchComponent implements OnInit {

  constructor(@Inject(MAT_BOTTOM_SHEET_DATA) public data: any,private serviceLayer:ServiceLayerService
 ,private commonService:commonServiceService,private snackBar: MatSnackBar,private bottomSheetRef:MatBottomSheetRef<ChatRequestAndSearchComponent>) { }

  ngOnInit(): void {

    let users=JSON.parse(sessionStorage. getItem('usr'))
    this.email=users.email;
 this.isRequest=this.data.isRequest;
 this.isAccept=this.data.isAccept;
 this.display=this.data.display;

 if(this.isAccept){

  this.getallRequests()

 }

 if(this.data.user.length==0){
  this.snacbar("Data not Found",'mat-warn')
 }

 if(this.data.user[0]==null){
  this.snacbar("Data not Found",'mat-warn')
  this.user=null;
 }
  }

  ngOnChanges():void{

  }
  email:string;
user:any=this.data.user;
isRequest:boolean;
isAccept:boolean;
display:string;
friends:any;
saveRequest(user:any){
 
  this.serviceLayer.saveRequest(user,this.email,this.data.sKey).subscribe((res:any)=>{
console.table(res)
if(res.msg=="Already Send Request"){

  this.snacbar("Already Send Request",'mat-accent');
  
}else{
  this.user=res;
this.snacbar("Request Sent Sucussfully",'mat-primary');
this.bottomSheetRef.dismiss(res);
}

  },(err:any)=>{
    console.log(err)
  })
}
snacbar(msg:string,color:string){

  this.snackBar.open(msg,'', {
    duration: 4000,
    panelClass: ['mat-toolbar', color],
    horizontalPosition:"center" ,
    verticalPosition:"top" ,
});
}

cancelRequest(user:any){

  this.serviceLayer.cancelRequest(user,this.email,this.data.sKey).subscribe((res:any)=>{

    if(res.msg=="Already Canceled Request"){
      this.snacbar("Already Canceled Request",'mat-accent');

    }else{
      console.log(res);
      this.user=res;
      this.snacbar("Request Canceled Sucussfully",'mat-warn')
      this.bottomSheetRef.dismiss(res);
    }
  },(err:any)=>{

    console.log(err);
  })
}

getallRequests(){

  this.serviceLayer.getAcceptRequests(this.email).subscribe((res:any)=>{
    if(res.length===0){
      this.snacbar("No New Requests",'mat-warn')
     }else{
  this.user=res
  console.log(res);
  
}
  },(err:any)=>{
    console.log(err);
  })
}


cancelAccept(user:any){

  this.serviceLayer.canceAccept(user.acceptEmail,this.email).subscribe((res:any)=>{
    this.snacbar("Request canceled sucussfully",'mat-accent');
    if(res.length===0){
      this.snacbar("No New Requests",'mat-warn')
      this.bottomSheetRef.dismiss(res);
     }else{
  this.user=res
  this.bottomSheetRef.dismiss(res);
  console.log(res);

     }
  },(err:any)=>{
    console.log(err);

  })
}

saveFriends(users:any){
  this.serviceLayer.saveFriends(users,this.email).subscribe((res:any)=>{
    this.friends=res;
console.log(res);
this.snacbar("Accepted Sucussfully",'mat-primary');
this.bottomSheetRef.dismiss(res);
  },(err:any)=>{
    this.snacbar(err.msg,'mat-accent');
    console.log(err);
  })
}



}
