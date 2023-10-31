import { Injectable } from '@angular/core';
import { Conversation } from '../interface/conversation';
import { Observable, lastValueFrom, map } from 'rxjs';
import { HttpClient, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Messages } from '../interface/messages';

@Injectable({
  providedIn: 'root'
})
export class ConversationService {

  constructor(private http:HttpClient) { }

  getConversations(): Observable<Conversation[]>{
    const url="http://localhost:8080/conversations"
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': String(localStorage.getItem('token'))
    })
    return  this.http.get<Conversation[]>(url, {headers: headers})

  }


  getMessages(id:number): Observable<Messages[]>{

    const url=`http://localhost:8080/conversations/${id}`
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': String(localStorage.getItem('token'))
    })
    return this.http.get<Messages[]>(url, {headers: headers}).pipe(
      map(messages => {
        return messages.map(message=>{
          if(message.sender.username===localStorage.getItem('user')){
            message.self=true
          }
          return message
        })
      })
    
    )
  }

  sendMessage(message:String,conversationId:number){
    console.log("In service")
    const url=`http://localhost:8080/sendmessage`
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': String(localStorage.getItem('token'))
    })
    const data={
      "message":message,
      "conversationId":conversationId
      
    }
    const response=this.http.post<Messages>(url,data,{headers})
    // response.subscribe(data=>{
    //   console.log(data)
    // })
    return lastValueFrom(response)

    

  }

}
