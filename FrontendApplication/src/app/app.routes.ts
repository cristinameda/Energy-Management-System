import { Routes } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { UserComponent } from './component/user/user.component';
import { ChatComponent } from './component/chat/chat.component';
import { DeviceComponent } from './component/device/device.component';
import { authGuard } from './guard/auth.guard';

export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'users', component: UserComponent, canActivate: [authGuard]},
    {path: 'chat', component: ChatComponent},
    {path: 'devices', component: DeviceComponent}
];
