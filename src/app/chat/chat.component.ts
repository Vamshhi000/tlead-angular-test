import { AfterViewInit, Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { commonServiceService } from '../common-service.service';

import { WebSocketAPI } from '../WebSocketAPI';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { User } from '../model/form';

import { ChatRequestAndSearchComponent } from '../chat-request-and-search/chat-request-and-search.component';
import { ServiceLayerService } from '../service-layer.service';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit,AfterViewInit {

  constructor(private commonService: commonServiceService,private _bottomSheet: MatBottomSheet
    ,private snackBar: MatSnackBar,private servicelayer:ServiceLayerService,private el: ElementRef, private renderer:Renderer2) {

     }


  title = 'angular8-springboot-websocket';
  myimg:string ="assets/profile.png";
  nochatsimg:string="assets/noimage.png"
  webSocketAPI: WebSocketAPI;
  greeting: any;
  name:string;

  message:string
  x:any=[];
  sx:string;
 email:string
 friends:any;
 chatPerson:string;
 myChatId:string;
 msgmodel:any

  ngOnInit() {

    let users=JSON.parse(sessionStorage. getItem('usr'))
    this.email=users.email;

    this.getallfrnds();
    

  }

  ngAfterViewInit(){

    this.renderer.setStyle(this.el.nativeElement.ownerDocument.body,'backgroundColor', '#374048');
    
    }
webSocketEndPoint: string = 'https://team-tlead.herokuapp.com:8080/ws';
// topic: string = "/topic/greetings";

stompClient: any;






connect(chatId:any) {
  console.log(this.sx);
    console.log("Initialize WebSocket Connection");
    let ws = new SockJS(this.webSocketEndPoint);

    // let ws = Socket(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    this.commonService.messageBe$.subscribe((res)=>{
      _this.stompClient.connect({}, function (frame) {
     
        _this.stompClient.subscribe("/user/"+chatId+"/queue/messages", function (sdkEvent) {
            _this.onMessageReceived(sdkEvent);
        });
        _this.stompClient.reconnect_delay = 2000;
    }, this.errorCallBack);
    },(err:any)=>{})
    // _this.stompClient.connect({}, function (frame) {
     
    //     _this.stompClient.subscribe("/user/"++"/queue/messages", function (sdkEvent) {
    //         _this.onMessageReceived(sdkEvent);
    //     });
    //     _this.stompClient.reconnect_delay = 2000;
    // }, this.errorCallBack);
};

disconnect() {
    if (this.stompClient !== null) {
        this.stompClient.disconnect();
    }
    console.log("Disconnected");
}

// on error, schedule a reconnection attempt
errorCallBack(error) {
    console.log("errorCallBack -> " + error)
    setTimeout(() => {
        // this.connect();
    }, 5000);
}

/**
* Send message to sever via web socket
* @param {*} message 
*/
send() {

    // console.log("calling logout api via web socket");
    // this.servicelayer.getChatsbyId(this.msgmodel.chatId).subscribe((res:any)=>{
    //   this.msgmodel.msg=this.message
    //   console.log(res+"chatssssssssssssssss");
    //   this.stompClient.send("/app/hello", {
    //   },JSON.stringify(this.msgmodel));
    //   this.x=res;
   


    // },(err:any)=>{
    //  console.log(err+"chatsssssssssssss");
    // }) 
    this.stompClient.send("/app/hello", {}, JSON.stringify(this.name));
this.msgmodel.msg=this.message

    this.stompClient.send("/app/hello", {
        },JSON.stringify(this.msgmodel));
 
}

onMessageReceived(message) {
    console.log("Message Recieved from Server :: " + message.body);

   let xx= (JSON.parse(message.body));

this.x.push(xx);
}

getallRequests(){

  this.servicelayer.getAcceptRequests(this.email).subscribe((res:any)=>{
    if(res.length===0){
      this.snacbar("No New Requests",'mat-warn')
     }else{
      this._bottomSheet.open(ChatRequestAndSearchComponent,{data:{user:res,isAccept:true,isRequest:false,display:"CHAT REQUESTS"},hasBackdrop:true});
  console.log(res);
  
}
  },(err:any)=>{
    console.log(err);
  })
}

  openBottomSheet(){
   this._bottomSheet.open(ChatRequestAndSearchComponent,{data:{isAccept:true,isRequest:false,display:"CHAT REQUESTS"},hasBackdrop:true});

  }


  searchResult(){
   this.servicelayer.getSearchResults(this.name,this.email).subscribe((res:any)=>{
    this._bottomSheet.open(ChatRequestAndSearchComponent,{data:{user:res,sKey:this.name,isRequest:true,isAccept:false,display:"SEND REQUESTS"}});
    console.table(res);
   },(err:any)=>{
     console.log(err)
   })
   this._bottomSheet._openedBottomSheetRef.afterDismissed().subscribe((res:any)=>{
    this.getallfrnds();
   })

  }
getallfrnds(){

  this.servicelayer.getAllfrnds(this.email).subscribe((res:any)=>{
    this.friends=res;
    this.myChatId=res[0].senderId;

    console.log('mychatId'+this.myChatId);
    this.connect(res[0].senderId);
  },(err:any)=>{
console.log(err);
  })
}

bindChatPerson(persons:any){

  this.chatPerson=persons.recipientName;
  console.log(persons);
  this.msgmodel=persons;

  this.servicelayer.getChatsbyId(persons.chatId).subscribe((res:any)=>{

    this.x=res;
    console.log(this.x+"chatsssssssssssss");
   },(err:any)=>{
   console.log(err+"chatsssssssssssss");
  }) 

}

sendd(){



}
snacbar(msg:string,color:string){

  this.snackBar.open(msg,'', {
    duration: 4000,
    panelClass: ['mat-toolbar', color],
    horizontalPosition:"center" ,
    verticalPosition:"top" ,
});
}
}
