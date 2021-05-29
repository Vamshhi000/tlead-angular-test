import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ServiceLayerService } from 'src/app/service-layer.service';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent implements OnInit {

  constructor(private renderer:Renderer2,private el: ElementRef,private fb:FormBuilder,private serviceLayer:ServiceLayerService,private snackBar: MatSnackBar,private router:Router) { }
  myimg:string ="assets/T-LEADl.png";
  userform:FormGroup;
  ngOnInit(): void {

    this.userform=this.fb.group({
      email:['',[Validators.required]],
       password:['', Validators.required],
    
     })
  }
  ngAfterViewInit(){

    this.renderer.setStyle(this.el.nativeElement.ownerDocument.body,'backgroundColor', '#374048');
    }


    TleadLogin(){
      this.serviceLayer.adminLogin(this.userform.value).subscribe((res:any)=>{

        console.log(res);
        this.serviceLayer.loginAdminSet(res);
        this.snacbar(res.msg,'mat-primary')
        this.router.navigate(['/adminHome']);
      },(err:any)=>{
        let obj= JSON.parse(err.error);
        this.snacbar(obj.msg,'mat-warn');
        this.router.navigate(['/AdminLogin/Ij3UsKr1gL4dsZKcZ5FXwJNYh3o0IjURP5DmUeQh0Zrl6']);
        
      })
    }

    snacbar(msg:string,color:string){

      this.snackBar.open(msg,'', {
        duration: 5000,
        panelClass: ['mat-toolbar', color]
    });
    }

    
}
