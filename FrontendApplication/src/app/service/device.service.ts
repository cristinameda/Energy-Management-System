import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { Device } from '../model/device';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private baseURL = 'http://localhost:8081';

  constructor(private http: HttpClient) { }

  saveDevice(device: Device): Observable<any> {
    return this.http.post<Device>(this.baseURL + '/devices', device);
  }

  getDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(this.baseURL + '/devices');
  }

  getDevicesByUserId(id: string): Observable<Device[]> {
    return this.http.get<Device[]>(this.baseURL + '/devices/user/' + id);
  }

  deleteById(id: string): Observable<any> {
    return this.http.delete<any>(this.baseURL + '/devices/' + id);
  }

  deleteAllByUserById(id: string): Observable<any> {
    return this.http.delete<any>(this.baseURL + '/devices/user/' + id);
  }

  updateDevice(device: Device): Observable<any>{
    return this.http.put<Device>(this.baseURL + '/devices', device);
  }

}
