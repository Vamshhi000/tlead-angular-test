import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServiceLayerService } from '../service-layer.service';

@Component({
  selector: 'app-nav-bar-main',
  templateUrl: './nav-bar-main.component.html',
  styleUrls: ['./nav-bar-main.component.css']
})
export class NavBarMainComponent implements OnInit {

  constructor(private serviceLayer:ServiceLayerService,private router:Router) { }
  myimg:string ="assets/T-LEADl.png";
  ngOnInit(): void {
  }
logout(){


  this.serviceLayer.logout();

  this.router.navigate(['/LoginComponent']);

}

referalView(){

  this.router.navigate(['/viewReferals']);
}

chatService(){

  this.router.navigate(['/chatService']);
}
}
