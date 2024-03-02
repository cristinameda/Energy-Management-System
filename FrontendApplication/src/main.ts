import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { tokenInterceptor } from './app/helper/token.interceptor';

bootstrapApplication(AppComponent, {
  providers: [
    // routes
    provideRouter(routes), 
    // http client
    provideHttpClient(
      // registering interceptors
      withInterceptors([tokenInterceptor])
    )
]
}).catch((err) => console.error(err));
