import { Injectable } from '@angular/core';
import { Conversation } from '../interface/conversation';
import {
  BehaviorSubject,
  Observable,
  Subject,
  Subscriber,
  lastValueFrom,
  map,
} from 'rxjs';
import { HttpClient, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Messages } from '../interface/messages';

@Injectable({
  providedIn: 'root',
})
export class ConversationService {
  constructor(private http: HttpClient) {}

  conversations?: Conversation[];
  result = new BehaviorSubject<Conversation[]>([]);

  getConversations(): Observable<Conversation[]> {
    if (!this.conversations) {
      const url = 'http://localhost:8080/conversations';
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: String(localStorage.getItem('token')),
      });
      this.http
        .get<Conversation[]>(url, { headers: headers })
        .subscribe((response) => {
          this.conversations = response;
          console.log(response);
          // this.conversations=this.conversations.concat(response)
          this.result.next(this.conversations);
        });
    } else {
      this.result.next(this.conversations);
    }
    return this.result;
  }
  refresgetConversations(): void {
    if (this.conversations) {
      this.result.next([])
    }
  }


  getMessages(id: number): Observable<Messages[]> {
    const url = `http://localhost:8080/conversations/${id}`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: String(localStorage.getItem('token')),
    });
    return this.http.get<Messages[]>(url, { headers: headers }).pipe(
      map((messages) => {
        return messages.map((message) => {
          if (message.sender.username === localStorage.getItem('username')) {
            message.self = true;
          }
          return message;
        });
      })
    );
  }

  sendMessage(message: String, conversationId: number) {
    console.log('In service');
    const url = `http://localhost:8080/sendmessage`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: String(localStorage.getItem('token')),
    });
    const data = {
      message: message,
      conversationId: conversationId,
    };
    const response = this.http.post<Messages>(url, data, { headers });
    // response.subscribe(data=>{
    //   console.log(data)
    // })
    return lastValueFrom(response);
  }
}
