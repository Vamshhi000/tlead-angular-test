import { Component, ElementRef, Inject, OnInit, Renderer2 } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ServiceLayerService } from '../service-layer.service';

@Component({
  selector: 'app-view-referals',
  templateUrl: './view-referals.component.html',
  styleUrls: ['./view-referals.component.css']
})
export class ViewReferalsComponent implements OnInit {

  constructor(private serviceLayer:ServiceLayerService,private renderer:Renderer2,private el: ElementRef) { }
  email:string;
  users:any;
  map = new Map();
  ngOnInit(): void {
    this.users=JSON.parse(localStorage. getItem('usr'))
    this.email=this.users.email;
   this.getallReferals()
  }
  ammount=["MMMMNH9519@x@gmail.com"];


  getallReferals(){

    this.serviceLayer.getAllReferals(this.email).subscribe((res:any)=>{
      console.log(res);
      const keys = Object.keys(res);  
     
      for(let i = 0; i < keys.length; i++){
         //inserting new key value pair inside map
         this.map.set(keys[i], res[keys[i]]);
      };
    

   
   
  // let keyss=Object.entries(res);

   console.log(this.map);


    },(err:any)=>{

      console.log(err);
    })
  }

  ngAfterViewInit(){

    this.renderer.setStyle(this.el.nativeElement.ownerDocument.body,'backgroundColor', '#374048');
    
    }

    
}
