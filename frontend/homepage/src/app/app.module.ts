import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HomepageFrontComponent} from "./homepage-front/homepage-front.component";
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    AppComponent,
    HomepageFrontComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
       HttpClientModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
