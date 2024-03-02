import { Injectable } from '@angular/core';

import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import {from, Observable, Observer} from 'rxjs';
import {mergeMap} from 'rxjs/operators';
import {Message} from "../model/message";
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  baseURL: string = "http://localhost:8085"
  private stompClientChat: Stomp.Client = null!;
  private connectionPromiseChat: Promise<void> = null!;
  private stompClientTyping: Stomp.Client = null!;
  private connectionPromiseTyping: Promise<void> = null!;

  constructor(private http: HttpClient) { }

  saveMessage(message: {}): Observable<any> {
    return this.http.post<any>(this.baseURL + '/chat', message);
  }

  getMessages(id1: string, id2: string): Observable<Message[]> {
    return this.http.get<Message[]>(this.baseURL + '/chat/' + id1 + "/" + id2);
  }

  initializeChatWebSocketConnection(): void {
    const socket = new SockJS(this.baseURL + '/chat-messages');
    this.stompClientChat = Stomp.over(socket);
    this.connectionPromiseChat = new Promise<void>((resolve) => {
      this.stompClientChat.connect({}, (frame) => {
        console.log(frame);
        resolve();
      });
    });
  }

  private waitForChatConnection(): Promise<void> {
    return this.connectionPromiseChat;
  }

  subscribeToChatTopic(id1: string, id2: string): Observable<Message> {
    return from(this.waitForChatConnection()).pipe(
      mergeMap(() =>
        new Observable((observer: Observer<Message>) => {
          this.stompClientChat.subscribe(`/topic/${id1}/${id2}`, (message: { body: string }) => {
            console.log(message);
            observer.next(JSON.parse(message.body));
          });
        })
      )
    );
  }

  sendChatMessage(id1: string, id2: string, message: Message): Observable<string> {
    return from(this.waitForChatConnection()).pipe(
      mergeMap(() =>
        new Observable((observer: Observer<any>) => {
          this.stompClientChat.send(`/topic/${id1}/${id2}`, {}, JSON.stringify(message))
          observer.next(message)
        })
      )
    )
  }
}
