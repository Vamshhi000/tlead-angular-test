import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ServiceLayerService } from 'src/app/service-layer.service';
import { AddUsersComponent } from '../add-users/add-users.component';
import { DeleteUsersComponent } from '../delete-users/delete-users.component';
import { EditUsersComponent } from '../edit-users/edit-users.component';
import { ViewDataComponent } from '../view-data/view-data.component';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {

  constructor(private serviceLayer:ServiceLayerService,private snackBar: MatSnackBar,public dialog: MatDialog
    ,private router:Router) { }

  ngOnInit(): void {
    this.getAllAdminUsers();
  }
pageNo=0;

userData=[];
myimg:string ="assets/T-LEADl.png";
  getAllAdminUsers(){


    
    this.serviceLayer.getAllAdminData(this.pageNo).subscribe((res:any)=>{
      this.pageNo++;
        console.log(res);

        for(var resp of res){
          this.userData.push(resp);
        }

        if(res[0]==null){
          this.snacbar("NO MORE USERS",'mat-warn');
        }

  
    },(err:any)=>{

      console.log(err);
      if(err.status==0){
        localStorage.clear();
       this.router.navigate(['/AdminLogin/Ij3UsKr1gL4dsZKcZ5FXwJNYh3o0IjURP5DmUeQh0Zrl6']);
      }
    });
  }

  snacbar(msg:string,color:string){

    this.snackBar.open(msg,'', {
      duration: 5000,
      panelClass: ['mat-toolbar', color]
  });
  }


  viewData(user:any){
    const dialogRef = this.dialog.open(ViewDataComponent, {
      width: '350px',
      height:'470px',
      data: {user}
    });
    dialogRef.afterClosed().subscribe((res:any) => {

        if(res!=undefined){
          this.userData=res;
               }
 


    },(err)=>{
  console.log(err);
    });

  }



  openAddCustomer(){
    const dialogRef = this.dialog.open(AddUsersComponent, {
      width: '450px',
      height:'600px',
    
    });
    dialogRef.afterClosed().subscribe((res:any) => {

        if(res!=undefined){
          this.userData=res;
               }
 


    },(err)=>{
  console.log(err);
    });
  }

  editUser(user:any){

    const dialogRef = this.dialog.open(EditUsersComponent, {
      width: '500px',
      height:'550px',
      data: {user}
    });
    dialogRef.afterClosed().subscribe((res:any) => {

        if(res!=undefined){
          this.userData=res;
               }
 


    },(err)=>{
  console.log(err);
    });
 
  }



  delete(user:any){

    const dialogRef = this.dialog.open(DeleteUsersComponent, {
      width: '300px',
      data: {user}
    });

    dialogRef.afterClosed().subscribe((result) => {
if(result!=undefined){
 

  
      

}



      
      
    });
  }

  logout(){


    this.serviceLayer.logout();
  
    this.router.navigate(['/LoginComponent']);
  
  }
}
