import { AfterViewInit, Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { commonServiceService } from '../common-service.service';
import { ServiceLayerService } from '../service-layer.service';
import { ViewReferalsComponent } from '../view-referals/view-referals.component';

// import {ShareButtonsModule} from 'ngx-sharebuttons/buttons';
// import {ShareIconsModule} from 'ngx-sharebuttons/icons';
@Component({
  selector: 'app-home-screen',
  templateUrl: './home-screen.component.html',
  styleUrls: ['./home-screen.component.css']
})
export class HomeScreenComponent implements OnInit,AfterViewInit {

  constructor(private renderer:Renderer2,private el: ElementRef,private commonService:commonServiceService,
    private serviceLayer:ServiceLayerService,private router:Router,public dialog: MatDialog) {

   }
email:string;
users:any;
referalId:string="";
refLink:string=""
map=new Map();
x=["tttdgfeeee","sfsfssafre"]
  ngOnInit(): void {

    this.users=JSON.parse(localStorage. getItem('usr'))
    this.email=this.users.email;
    
    this.getrefId();
  }

  
  ngAfterViewInit(){

    this.renderer.setStyle(this.el.nativeElement.ownerDocument.body,'backgroundColor', '#374048');
    
    }


getrefId(){
this.serviceLayer.getRefId(this.email).subscribe((res:any)=>{
  this.referalId=res.refId
  this.refLink=`https://t-lead.herokuapp.com/registerUser?refId=${this.referalId}`
  // this.refLink="https://www.task.telangana.gov.in/Job-Openings"
},(err:any)=>{
 
  console.log(err);
})

    }


    getallReferals(){

      this.serviceLayer.getAllReferals(this.email).subscribe((res:any)=>{
        

     
     
    let keyss=Object.entries(res);
     console.log(keyss);
     console.log(res);


      },(err:any)=>{
 
        console.log(err);
      })
    }

    referalView(){

      this.router.navigate(['/viewReferals']);
    }



}
