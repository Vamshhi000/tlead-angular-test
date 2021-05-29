import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
@Injectable()
export class AuthInterceptor implements HttpInterceptor{
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


        return next.handle(newReq);
    }




    
}