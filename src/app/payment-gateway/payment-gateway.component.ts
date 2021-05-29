import { AfterViewInit, Component, ElementRef, NgZone, OnInit, Renderer2, SystemJsNgModuleLoader } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from '../model/form';
import { ICustomWindow, ServiceLayerService } from '../service-layer.service';
import Swal from 'sweetalert2';



@Component({
  selector: 'app-payment-gateway',
  templateUrl: './payment-gateway.component.html',
  styleUrls: ['./payment-gateway.component.css']
})
export class PaymentGatewayComponent implements OnInit,AfterViewInit {
  constructor(private serviceLayer:ServiceLayerService,private snackBar: MatSnackBar
    ,private router:Router,private zone: NgZone,private el: ElementRef, private renderer:Renderer2
  ) { 
      this._window = this.serviceLayer.nativeWindow;
    }

  msg:string="Please enter your registered E-mail id to request RESET password LINK"
  myimg:string ="assets/T-LEADl.png";
  pBar:boolean;
  btnDisable:boolean;
  public options: any;
  disapiar:boolean=true;
   uuser:any=new User();

   users:any;
  
  ngOnInit(): void {
    
    
    this.users=JSON.parse(localStorage. getItem('usr'))
  console.log(this.users);
  }

  ngAfterViewInit(){

    this.renderer.setStyle(this.el.nativeElement.ownerDocument.body,'backgroundColor', '#374048');
    
    }

  public _window: ICustomWindow;
  public rzp: any;

 
 

paymentObject(payobj:any,user:any){
  var ref = this;
   this.options = {
    key: "rzp_test_onuC0CoGHQvLsf", // add razorpay key here
    name: 'T-LEAD',
    description: 'Shopping',
    amount: payobj.amount, // razorpay takes amount in paisa
    image: "assets/T-LEADl.png",
    currency: "INR",
    order_id:payobj.id,
    prefill: {
      name: user.username,
      email: user.email,
      contact: user.phoneNumber // add your email id
    },
    handler:function (response){
      // ref.updateOrder(response);
ref.verifySignature(response);
      
    },
    notes: {},
    theme: {
      color: '#008080'
    },

    modal: {
      ondismiss: (() => {
        this.zone.run(() => {
          // add current page routing if payment fails
        })
      })
    }
  };
  
}


  paymentHandler(res: any) {
    this.zone.run(() => {
      // add API call here
    });
  }


x= new User();

createOrder(){


  this.serviceLayer.createOrder(this.users).subscribe((res:any)=>{
    this.paymentObject(res,this.users);
    
    this.rzp = new this.serviceLayer.nativeWindow['Razorpay'](this.options);
    this.rzp.on('payment.failed', function (response){
      console.log(response.error.code);
      console.log(response.error.description);
      console.log(response.error.source);
      console.log(response.error.step);
      console.log(response.error.reason);
      console.log(response.error.metadata.order_id);
      console.log(response.error.metadata.payment_id);
      // Swal.fire('Oops...', 'Something went wrong!', 'error')
      });
    this.rzp.open();
    console.log(res+"payment");
  },(err:any)=>{
    console.log(err+"payment");



  })
}



swal(){

 this.disapiar=false
 Swal.fire(
  'Good job!',
  'You clicked the button!',
  'success'
)

}





snacbar(msg:string,color:string){

  this.snackBar.open(msg,'', {
    duration: 5000,
    panelClass: ['mat-toolbar', color]
});
}




verifySignature(responce:any){


this.serviceLayer.getOrderId(this.users.email).subscribe((res:any)=>{
  this.serviceLayer.hmac_sha256(res + "|" + responce.razorpay_payment_id).subscribe((res:any)=>{

    let genarated_signature=res;
    console.log(genarated_signature);
    if (genarated_signature == responce.razorpay_signature) {

      let user=new User();
      user.paymentId=responce.razorpay_payment_id;
      user.orderId=responce.razorpay_order_id;
      user.status="paid";
      user.email=this.users.email;
 this.serviceLayer.updateOrder(user).subscribe((res:any)=>{
   console.log(res);
   localStorage.setItem("status","paid");
   Swal.fire({
             
    title: 'GOOD JOB!',
    text: 'CONGRATS !! PAYMENT SUCCESSFUL!',
    icon: 'success',
    showCancelButton: false,
    confirmButtonText: 'Ok',
    
 
  }).then((result) => {
    
    if (result.value) {
      this.router.navigate(['/homeScreen']);
      location.replace("http://localhost:4200/homeScreen");
     
  
    } 
  })
 },(err:any)=>{
  Swal.fire({
    icon: 'error',
    title: 'Please raise a query',
    text: 'Something went wrong!',
    footer: '<a href>Why do I have this issue?</a>'
  })
 })

    }
   
    },
    (err:any)=>{
      Swal.fire({
        icon: 'error',
        title: 'Please raise a query',
        text: 'Something went wrong!',
        footer: '<a href>Why do I have this issue?</a>'
      })
    
    })


},(err:any)=>{

  Swal.fire({
    icon: 'error',
    title: 'Please raise a query',
    text: 'Something went wrong!',
    footer: '<a href>Why do I have this issue?</a>'
  })
})



}
}
