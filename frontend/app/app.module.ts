import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { shapes } from 'konva/lib/Shape';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { object_shape } from './object_shape';
import { service } from './service';
import { HttpClientModule } from '@angular/common/http';
import { copy_shape } from './copy';

@NgModule({
  declarations: [
    AppComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    

    
  ],
  providers: [object_shape,service,copy_shape],
  bootstrap: [AppComponent]
})
export class AppModule { }
