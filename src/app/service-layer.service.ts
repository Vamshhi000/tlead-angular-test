import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './model/form';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { commonServiceService } from './common-service.service';


export interface ICustomWindow extends Window {
  __custom_global_stuff: string;
}

function getWindow(): any {
  return window;
}

@Injectable({
  providedIn: 'root'
})
export class ServiceLayerService {

  constructor(private http:HttpClient,private commonServiceService:commonServiceService) { }
  isAuthenticated:boolean;
  user:Observable<User>
  baseUrl:String='http://localhost:8080';


  get nativeWindow(): ICustomWindow {
    return getWindow();
  }
  emailVerification(user:User):Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/emailVerification`,user);
   }

   otpSubmit(user:User):Observable<User>{

    return this.http.post<User>(`${this.baseUrl}/emialOtpSubmit`,user);
   }
   numberVerification(user:User):Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/sendSms`,user);
   }

   registerUser(user:User):Observable<User> {
this.commonServiceService.usersob$.subscribe((res:User)=>{
  user.email=res.email;
  user.phoneNumber=res.phoneNumber;
})
console.log(user+"register");
    
    return this.http.post<User>(`${this.baseUrl}/registerUser`,user);
   }

   jwtLogin(user:User){

    return this.http.post("http://localhost:8080/loginUser",user,{responseType: 'text'});
   }

  //  getUser():Observable<any>{

  //   return this.http.get<any>("http://localhost:7070/vamsi",);
  //  }


//   getUser(){
//     let token:any=localStorage.getItem("token");
//   let x=  JSON.stringify(token.token);

//     console.log(x)
//     var reqHeader = new HttpHeaders({ 
      
   
     
//         'Authorization': 'Bearer ' + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTQiLCJleHAiOjE2MTk0MzA4OTEsImlhdCI6MTYxOTQxMjg5MX0.NchqFu1KAMqzPcqUzqvWq6vIqxzvC4V1hJgWJB65hLb0h-MKYdPtU3QYzOusJlTfGa4dsneSCe-Sh-oHwA155Q"
//      });
//     return this.http.get("http://localhost:7070/welcome",{responseType: 'text', headers: reqHeader });
// }

// getUser(){

//   return this.http.get("http://localhost:8080/welcome");
// }

getUser(){

  return this.http.get("http://localhost:8080/sample",{responseType: 'text'});
}


loginUser(user:any,usr:any){

  localStorage.setItem("token",user.token);
  localStorage.setItem("status",user.status);
 localStorage.setItem("usr",JSON.stringify(usr));
 sessionStorage.setItem("usr",JSON.stringify(usr));
  return true;
}

isLoggedIn(){

  let token=localStorage.getItem("token")

  if(token==undefined || token=='' || token==null){

    return false;

  }else{

    return true;
  }
}

isReset(){

  let tokenValue=localStorage.getItem("tokenValue");

  if(tokenValue==undefined || tokenValue=='' || tokenValue==null ||tokenValue=="undefined"){

    return false;

  }else{

    return true;
  }
}

isPaid(){

  let status=localStorage.getItem("status");

  if(status=="paid"){

    return true;

  }else{

    return false;
  }
}
isUnpaid(){
  let status=localStorage.getItem("status");
  if(status=="paid"){
    return false;
  }else{
    return true
  }


}

logout(){

  localStorage.removeItem("token");
  localStorage.clear()
  sessionStorage.clear()
  return true;
}

getToken(){

  return localStorage.getItem("token");
}

forgotPassword(user:User):Observable<User>{

  return this.http.post<User>(`${this.baseUrl}/sendEmailToReset`,user);


}

resetpassword(user:User,token:String):Observable<User>{
  console.log(`${this.baseUrl}/resetPassword?token=${token}`);
  return this.http.post<User>(`${this.baseUrl}/resetPassword?token=${token}`,user,);
 
}

createOrder(user:User):Observable<any>{

  return this.http.post<any>(`${this.baseUrl}/payments/createOrder`,user);

}

updateOrder(user:User):Observable<any>{

  return this.http.put<any>(`${this.baseUrl}/payments/updateOrder`,user);
}

hmac_sha256(responce:string):Observable<any>{
  return this.http.put(`${this.baseUrl}/payments/signarture`,responce,{responseType: 'text'});

}

getOrderId(email:string):Observable<any>{
  return this.http.put(`${this.baseUrl}/payments/orderId`,email,{responseType: 'text'});

}

getRefId(email:string):Observable<any>{
  return this.http.get(`${this.baseUrl}/${email}`);

}



getAllReferals(email:string):Observable<Map<string,any>>{
  return this.http.get<Map<string,any>>(`${this.baseUrl}/viewReferals/${email}`);
}

getSearchResults(name:string,email:string):Observable<any>{
  return this.http.get<any>(`${this.baseUrl}/chatRequest/searchResult/${name}?email=${email}`);
}



saveRequest(user:any,email:string,sKey:string):Observable<any>{
  return this.http.post<any>(`${this.baseUrl}/chatRequest/saveRequests/${sKey}?email=${email}`,user);
}


cancelRequest(user:any,email:string,sKey:string):Observable<any>{
  return this.http.put<any>(`${this.baseUrl}/chatRequest/cancelRequest/${sKey}?email=${email}`,user);
}

getAcceptRequests(email:string):Observable<any>{
  return this.http.get<any>(`${this.baseUrl}/chatAccept/getAllRequsts/${email}`);
}

canceAccept(email:string,acceptEmail:string):Observable<any>{
  return this.http.get<any>(`${this.baseUrl}/chatAccept/cancelAccept/${email}?email=${acceptEmail}`);
}

saveFriends(users:any,email:string):Observable<any>{
  return this.http.post<any>(`${this.baseUrl}/chat/saveFriends?email=${email}`,users); 
}

getAllfrnds(email:string):Observable<any>{
  return this.http.get<any>(`${this.baseUrl}/chat/getfrnds/${email}`); 
}

getChatsbyId(chatId:string):Observable<any>{

return this.http.get<any>(`${this.baseUrl}/msg/getChats/${chatId}`); 
}


loginAdminSet(admin:any){

  localStorage.setItem("Adminn",admin.token);
  localStorage.removeItem("token");
  return true;
}

isAdminLoggedIn(){

  let token=localStorage.getItem("Adminn")

  if(token==undefined || token=='' || token==null){

    return false;

  }else{

    return true;
  }
}








adminLogin(users:any){

  return this.http.post<any>(`${this.baseUrl}/Admin/login`,users); 

}

getAllAdminData(pageNo:any):Observable<any>{
  return this.http.get<any>(`${this.baseUrl}/Admin/getAllResults/${pageNo}`);
}

editUsers(users:any):Observable<any>{
  return this.http.put<any>(`${this.baseUrl}/Admin/editUsers/`,users);

}

deleteUsers(user:any):Observable<any>{
  return this.http.put<any>(`${this.baseUrl}/Admin/deleteUsers/`,user);

}
}
