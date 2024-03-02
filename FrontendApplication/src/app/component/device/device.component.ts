import { Component, OnInit } from '@angular/core';
import { NavComponent } from '../nav/nav.component';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { DeviceService } from '../../service/device.service';
import { Device } from '../../model/device';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-device',
  standalone: true,
  imports: [NavComponent, FormsModule, HttpClientModule, RouterOutlet, RouterLink, RouterLinkActive, ReactiveFormsModule, CommonModule],
  templateUrl: './device.component.html',
  styleUrl: './device.component.css'
})
export class DeviceComponent implements OnInit {

  devices?: Device[];
  disabled: boolean;
  notCreate: boolean;
  userRole?: string | null;
  userId?: string | null;

  constructor(private deviceService: DeviceService, private authService: AuthService) {
    this.getAllDevices();
    this.disabled = true;
    this.notCreate = true;
  }

  getAllDevices(): void {
    this.userRole = this.authService.getLoggedUserRoles();
    this.userId = this.authService.getLoggedUserId();
    if (this.userRole === 'ADMIN') {
      this.getDevices();
    } else {
      this.getDevicesByUserId(this.userId!)
    }
  }

  ngOnInit(): void {
    this.getAllDevices();
  }

  getDevices(): void {
    this.deviceService.getDevices().subscribe((response) => {
      this.devices = response
    });
  }

  getDevicesByUserId(id: string): void {
    this.deviceService.getDevicesByUserId(id).subscribe((response) => {
      this.devices = response
    });
  }

  toggleTextarea(): void {
    this.disabled = !this.disabled;
  }

  deleteDeviceById(id: string): void {
    this.deviceService.deleteById(id).subscribe(
      () => {
        this.getDevices();
      },
      (error) => {
        console.error('Error deleting device:', error);
      }
    );
  }

  updateDevice(id: string, description: string, address: string, maxConsumption: string, userId: string): void {
    let device: Device = {
      id: id,
      description: description,
      address: address,
      maxConsumption: maxConsumption,
      userId: userId
    }
    this.deviceService.updateDevice(device).subscribe(
      () => {
        this.getDevices();
      },
      (error) => {
        console.error('Error updating device:', error);
      }
    );
  }

  toggleCreate() {
    this.notCreate = !this.notCreate;
  }

  saveDevice(description: string, address: string, maxConsumption: string, userId: string) {
    let device: Device = {
      description: description,
      address: address,
      maxConsumption: maxConsumption,
      userId: userId
    }
    this.deviceService.saveDevice(device).subscribe(
      () => {
        this.getDevices();
        this.notCreate = true;
      },
      (error) => {
        console.error('Error saving device:', error);
      }
    );
  }
}
