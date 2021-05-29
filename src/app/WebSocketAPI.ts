import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import * as Socket from 'socket.io-client';
import { ChatComponent } from './chat/chat.component';
import { commonServiceService } from './common-service.service';


export class WebSocketAPI {
    webSocketEndPoint: string = 'http://localhost:8080/ws';
    topic: string = "/topic/greetings";
    stompClient: any;
    chatComponent: ChatComponent;
    constructor(chatComponent: ChatComponent){
        this.chatComponent = chatComponent;
    }

    //   constructor(private commonService: commonServiceService){
 
    // }


    _connect() {
        console.log("Initialize WebSocket Connection");
        let ws = new SockJS(this.webSocketEndPoint);
        // let ws = Socket(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        const _this = this;
        _this.stompClient.connect({}, function (frame) {
            _this.stompClient.subscribe(_this.topic, function (sdkEvent) {
                _this.onMessageReceived(sdkEvent);
            });
            _this.stompClient.reconnect_delay = 2000;
        }, this.errorCallBack);
    };

    _disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    // on error, schedule a reconnection attempt
    errorCallBack(error) {
        console.log("errorCallBack -> " + error)
        setTimeout(() => {
            this._connect();
        }, 5000);
    }

	/**
	 * Send message to sever via web socket
	 * @param {*} message 
	 */
    _send(message) {
        console.log("calling logout api via web socket");
        this.stompClient.send("/app/hello", {}, JSON.stringify(message));
    }

    onMessageReceived(message) {
        console.log("Message Recieved from Server :: " + message);
    //    let x= this.chatComponent.handleMessage(JSON.parse(message.body));
    //    this.commonService.messageBeSubject.next(message);
    //    return x;
    }
}