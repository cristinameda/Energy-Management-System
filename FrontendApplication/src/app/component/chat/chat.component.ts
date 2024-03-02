import { Component } from '@angular/core';
import { UserService } from '../../service/user.service';
import { User } from '../../model/user';
import { AuthService } from '../../service/auth.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { NavComponent } from '../nav/nav.component';
import { Message } from '../../model/message';
import { ChatService } from '../../service/chat.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [NavComponent, FormsModule, HttpClientModule, RouterOutlet, RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent {

  users?: User[];
  loggedUserRole?: any;
  loggedUserName?: string;
  selectedUserName?: string;
  messages?: Message[];
  loggedUserId?: string;
  selectedUserId?: string;
  currentMessage?: string;

  constructor(private userService: UserService, private authService: AuthService, private chatService: ChatService) {
    this.getUsers();
    this.loggedUserRole = localStorage.getItem('ROLE');
    var id = localStorage.getItem('ID')
    if (id !== null) {
      this.loggedUserId = id;
      this.userService.getUserById(BigInt(id)).subscribe((user) => {
        this.loggedUserName = user.name;
      });
    }
  }

  getUsers(): void {
    this.loggedUserRole = localStorage.getItem('ROLE');
    if (this.loggedUserRole === 'ADMIN') {
      this.userService.getUsersByRole('CLIENT').subscribe((response) => {
        this.users = response;
        console.log(response);
      });
    } else {
      this.userService.getUsersByRole('ADMIN').subscribe((response) => {
        this.users = response;
        console.log(response);
      });
    }
  }

  changeUser(user: User) {
    this.selectedUserName = user.name;
    this.selectedUserId = user.id!.toString();
    this.initializeMessages();

    this.chatService.getMessages(this.loggedUserId!, this.selectedUserId!).subscribe((result) => {
      this.messages = result
    })

    this.chatService.initializeChatWebSocketConnection()
    this.chatService.subscribeToChatTopic(this.selectedUserId.toString(), this.loggedUserId!.toString()).subscribe((message) => {
        // console.log("primit " + message)
        // this.messages?.push(message)
        this.chatService.getMessages(this.loggedUserId!, this.selectedUserId!).subscribe((result) => {
          this.messages = result
        })
    });
  }

  onSend() {
    var messageToSave = this.composeMessage(this.currentMessage!)
    console.log(messageToSave)
    this.chatService.sendChatMessage(this.loggedUserId!.toString(), this.selectedUserId!.toString(), messageToSave).subscribe();
    this.chatService.saveMessage(messageToSave).subscribe(() => {
      this.chatService.getMessages(this.loggedUserId!, this.selectedUserId!).subscribe((result) => {
        this.messages = result
      })
    });
  }

  composeMessage(message: string): Message {
    let newMessage: Message = {
      from: this.loggedUserId!,
      to: this.selectedUserId!,
      content: message
    }
    return newMessage;
  }

  initializeMessages() {
    this.messages = []
  }
}
