import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { User } from '../../model/user';
import { NavComponent } from '../nav/nav.component';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [NavComponent, FormsModule, HttpClientModule, RouterOutlet, RouterLink, RouterLinkActive, ReactiveFormsModule, CommonModule],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent implements OnInit {

  users?: User[];
  disabled: boolean;
  notCreate: boolean;

  constructor(private userService: UserService) {
    this.getUsers();
    this.disabled = true;
    this.notCreate = true;
  }

  getUsers(): void {
    this.userService.getUsers().subscribe((response) => {
      this.users = response
    });
  }

  ngOnInit(): void {
    this.getUsers();
  }

  toggleTextarea(): void {
    this.disabled = !this.disabled;
  }

  deleteUserById(id: string): void {
    this.userService.deleteUserById(id).subscribe(
      () => {
        this.getUsers();
      },
      (error) => {
        console.error('Error deleting user:', error);
      }
    );
  }

  updateUser(id: string, name: string, username: string, address: string, dob: string): void {
    let user: User = {
      id: id,
      name: name,
      username: username,
      address: address,
      dob: dob
    }
    this.userService.updateUser(user).subscribe(
      () => {
        this.getUsers();
      },
      (error) => {
        console.error('Error updating user:', error);
      }
    );
  }

  toggleCreate() {
    this.notCreate = !this.notCreate;
  }

  saveUser(name: string, username: string, password: string, address: string, dob: string) {
    let user: User = {
      name: name,
      username: username,
      password: password,
      address: address,
      dob: dob
    }
    this.userService.saveUser(user).subscribe(
      () => {
        this.getUsers();
        this.notCreate = true;
      },
      (error) => {
        console.error('Error saving user:', error);
      }
    );
  }
}
