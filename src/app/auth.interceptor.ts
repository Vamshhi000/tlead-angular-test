import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { Observable, of } from "rxjs";
import 'rxjs/add/operator/do';
import { ServiceLayerService } from "./service-layer.service";
// import { do } from "rxjs/operators";
@Injectable()
export class AuthInterceptor implements HttpInterceptor{
  constructor(public dialog: MatDialog,private serviceLayer:ServiceLayerService
   ,private snackBar: MatSnackBar
    ,private router:Router) { }
  snacbar(msg:string,color:string){

    this.snackBar.open(msg,'', {
      duration: 5000,
      panelClass: ['mat-toolbar', color]
  });
  }
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        var newReq=req;
        console.log(req.url.slice(22,req.url.length));
        console.log(typeof req.url);

        let token=localStorage.getItem("token");
        let token$=localStorage.getItem("Adminn");
        if(token!=null){
     
                newReq=newReq.clone({setHeaders:{Authorization:`Bearer ${token}`}})
    
          
        }
        if(token$!=null){
     
            newReq=newReq.clone({setHeaders:{Authorization:`Adminn ${token$}`}})

      
    }





    return next.handle(newReq).do((event: HttpEvent<any>) => {
        if(event instanceof HttpResponse){
     
        }
      }, (err: any) => {

        if(err instanceof HttpErrorResponse){
          if(err.status === 0){
            
           console.log("token expired")
           this.serviceLayer.logout()
           this.snacbar("Session Expired",'mat-warn');
           this.router.navigate(['/LoginComponent']);
          }
        }
      });

    
}
}