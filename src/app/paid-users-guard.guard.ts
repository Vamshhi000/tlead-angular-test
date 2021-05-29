import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { ServiceLayerService } from './service-layer.service';

@Injectable({
  providedIn: 'root'
})
export class PaidUsersGuardGuard implements CanActivate {
  constructor(private serviceLayer:ServiceLayerService) { }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if(this.serviceLayer.isPaid()&&this.serviceLayer.isLoggedIn()){

        return true;
      }
    
    
      return false;
  }
  
}
